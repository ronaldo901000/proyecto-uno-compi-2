package com.ronaldo.cd3.compiler.api.resources;

import com.ronaldo.cd3.compiler.api.dtos.entrada.EntradaDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.EjecutarSesionEnviarDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.EjecutarSesionRespuestaDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.exceptions.EntradaException;
import com.ronaldo.cd3.compiler.api.services.analisis.Analizador;
import com.ronaldo.cd3.compiler.api.services.generador.riscv.GeneradorRiscV;
import com.ronaldo.cd3.compiler.api.services.generador.riscv.SesionRars;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Recurso REST para ejecucion interactiva de codigo RISC-V mediante RARS 1.6.
 * Mantiene sesiones de proceso vivo para permitir enviar entrada por stdin y
 * recibir salida acumulada entre peticiones HTTP.
 *
 * Endpoints:
 *   POST /ejecutar-sesion/iniciar   - Analiza, genera .s y lanza RARS vivo (devuelve sessionId)
 *   POST /ejecutar-sesion/enviar    - Envia texto a stdin y devuelve la salida
 *   POST /ejecutar-sesion/salida    - Lee salida pendiente sin escribir (polling)
 *   POST /ejecutar-sesion/detener   - Mata el proceso de una sesion y borra el .s
 */
@jakarta.ws.rs.Path("/ejecutar-sesion")
public class EjecutarSesionResource {

    private static final String RARS_JAR = System.getProperty("rars.jar.path",
            "/home/ronaldo/Descargas/rars.jar");
    private static final String WORK_DIR = System.getProperty("rars.work.dir",
            "/home/ronaldo/Documentos/2026/Segundo Semestre/Cursos/1. Compi 2/Proyectos/Proyecto Uno-Dos/C3D Compiler/C3D Compiler/Generados");

    // Mapa de sesiones activas: sessionId -> SesionRars
    private static final Map<String, SesionRars> sesiones = new ConcurrentHashMap<>();

    /**
     * Inicia una sesion interactiva: analiza los archivos fuente, genera el
     * codigo RISC-V (.s) y lanza un proceso de RARS vivo.
     */
    @POST
    @jakarta.ws.rs.Path("/iniciar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciar(EntradaDTO entrada) {
        try {
            // 1. Analizar (lexico, sintactico y semantico)
            Analizador analizador = new Analizador();
            RespuestaDTO resDto = analizador.iniciar(entrada);

            if (resDto.isHayErrores()) {
                EjecutarSesionRespuestaDTO resp = new EjecutarSesionRespuestaDTO(
                        false,
                        null,
                        null,
                        resDto.getErrores(),
                        null,
                        null
                );
                return Response.ok(resp).build();
            }

            // 2. Generar codigo RISC-V
            GeneradorRiscV generador = new GeneradorRiscV();
            String codigoRiscV = generador.generar(analizador.getCuartetas());

            if (codigoRiscV == null || codigoRiscV.isBlank()) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("No se genero codigo RISC-V").build();
            }

            // 3. Escribir archivo .s en disco
            Path workDir = java.nio.file.Paths.get(WORK_DIR);
            Files.createDirectories(workDir);
            Path asmFile = workDir.resolve("salida.s");
            Files.writeString(asmFile, codigoRiscV);

            // 4. Lanzar proceso de RARS vivo y esperar su salida inicial
            String sessionId = UUID.randomUUID().toString();
            SesionRars sesion = new SesionRars(sessionId, RARS_JAR, asmFile.toString(), WORK_DIR);
            sesion.esperarSalida(3000);

            EjecutarSesionRespuestaDTO resp = new EjecutarSesionRespuestaDTO(
                    true,
                    sessionId,
                    sesion.leerNuevo(),
                    null,
                    codigoRiscV,
                    analizador.getCuartetas().getCuartetas()
            );

            boolean terminado = sesion.terminado();
            resp.setTerminado(terminado);
            if (!terminado) {
                sesiones.put(sessionId, sesion);
            }

            return Response.ok(resp).build();

        } catch (EntradaException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + ex.getMessage()).build();
        }
    }

    /**
     * Envia texto al stdin de una sesion y devuelve la salida generada.
     */
    @POST
    @jakarta.ws.rs.Path("/enviar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviar(EjecutarSesionEnviarDTO dto) {
        try {
            String sessionId = dto.getSessionId();
            String texto = dto.getTexto();

            if (sessionId == null || sessionId.isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("sessionId es requerido").build();
            }

            SesionRars sesion = sesiones.get(sessionId);
            if (sesion == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Sesion no encontrada o ya finalizada").build();
            }

            if (!sesion.estaVivo()) {
                sesiones.remove(sessionId);
                return Response.status(Response.Status.GONE)
                        .entity("El proceso de RARS ya termino").build();
            }

            String salida = sesion.enviar(texto);

            EjecutarSesionRespuestaDTO respuesta = new EjecutarSesionRespuestaDTO(
                    true,
                    sessionId,
                    salida,
                    null,
                    null,
                    null
            );

            boolean terminado = sesion.terminado();
            respuesta.setTerminado(terminado);
            if (terminado) {
                sesiones.remove(sessionId);
            }

            return Response.ok(respuesta).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + ex.getMessage()).build();
        }
    }

    /**
     * Lee la salida pendiente sin escribir nada al proceso (para polling).
     */
    @POST
    @jakarta.ws.rs.Path("/salida")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response salida(EjecutarSesionEnviarDTO dto) {
        try {
            String sessionId = dto.getSessionId();

            if (sessionId == null || sessionId.isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("sessionId es requerido").build();
            }

            SesionRars sesion = sesiones.get(sessionId);
            if (sesion == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Sesion no encontrada o ya finalizada").build();
            }

            sesion.esperarSalida(300);

            EjecutarSesionRespuestaDTO respuesta = new EjecutarSesionRespuestaDTO(
                    true,
                    sessionId,
                    sesion.leerNuevo(),
                    null,
                    null,
                    null
            );

            boolean terminado = sesion.terminado();
            respuesta.setTerminado(terminado);
            if (terminado) {
                sesiones.remove(sessionId);
            }

            return Response.ok(respuesta).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + ex.getMessage()).build();
        }
    }

    /**
     * Detiene una sesion interactiva, libera el proceso de RARS y borra el .s.
     */
    @POST
    @jakarta.ws.rs.Path("/detener")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response detener(EjecutarSesionEnviarDTO dto) {
        try {
            String sessionId = dto.getSessionId();

            if (sessionId == null || sessionId.isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("sessionId es requerido").build();
            }

            SesionRars sesion = sesiones.remove(sessionId);
            if (sesion != null) {
                sesion.detener();
            }

            EjecutarSesionRespuestaDTO respuesta = new EjecutarSesionRespuestaDTO(
                    true,
                    sessionId,
                    null,
                    null,
                    null,
                    null
            );
            respuesta.setTerminado(true);

            return Response.ok(respuesta).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + ex.getMessage()).build();
        }
    }
}
