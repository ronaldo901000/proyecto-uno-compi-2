package com.ronaldo.cd3.compiler.api.resources;

import com.ronaldo.cd3.compiler.api.dtos.entrada.EntradaDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.EjecutarSesionEnviarDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.EjecutarSesionRespuestaDTO;
import com.ronaldo.cd3.compiler.api.exceptions.EntradaException;
import com.ronaldo.cd3.compiler.api.services.analisis.Analizador;
import com.ronaldo.cd3.compiler.api.services.generador.riscv.GeneradorRiscV;
import com.ronaldo.cd3.compiler.api.services.generador.riscv.SesionVenus;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Recurso REST para ejecucion interactiva de codigo RISC-V mediante Venus.
 * Mantiene sesiones de proceso vivo para permitir enviar entrada por stdin
 * y recibir salida acumulada entre peticiones HTTP.
 *
 * Endpoints:
 *   POST /ejecutar-sesion/iniciar   - Compila archivos y lanza Venus (devuelve sessionId)
 *   POST /ejecutar-sesion/enviar    - Envía texto stdin a una sesion viva (devuelve salida)
 *   POST /ejecutar-sesion/detener   - Mata el proceso de una sesion
 */
@jakarta.ws.rs.Path("/ejecutar-sesion")
public class EjecutarSesionResource {

    private static final String VENUS_JAR = System.getProperty("venus.jar.path",
            "/home/ronaldo/Descargas/venus.jar");
    private static final String WORK_DIR = System.getProperty("venus.work.dir",
            "/tmp/opencode/unificado");

    // Mapa de sesiones activas: sessionId -> SesionVenus
    private static final Map<String, SesionVenus> sesiones = new ConcurrentHashMap<>();

    /**
     * Inicia una sesion interactiva: analiza los archivos fuente, genera el
     * codigo RISC-V (.s) y lanza un proceso de Venus vivo.
     */
    @POST
    @jakarta.ws.rs.Path("/iniciar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciar(EntradaDTO entrada) {
        try {
            // 1. Analizar y generar codigo RISC-V
            Analizador analizador = new Analizador();
            analizador.iniciar(entrada);

            GeneradorRiscV generador = new GeneradorRiscV();
            String codigoRiscV = generador.generar(analizador.getCuartetas());

            if (codigoRiscV == null || codigoRiscV.isBlank()) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("No se genero codigo RISC-V").build();
            }

            // 2. Escribir archivo .s en disco
            java.nio.file.Path workDir = java.nio.file.Paths.get(WORK_DIR);
            Files.createDirectories(workDir);
            java.nio.file.Path asmFile = workDir.resolve("sesion_" + UUID.randomUUID() + ".s");
            Files.writeString(asmFile, codigoRiscV);

            // 3. Lanzar proceso de Venus vivo
            String sessionId = UUID.randomUUID().toString();
            SesionVenus sesion = new SesionVenus(sessionId, VENUS_JAR, asmFile.toString(), WORK_DIR);
            sesiones.put(sessionId, sesion);

            EjecutarSesionRespuestaDTO respuesta = new EjecutarSesionRespuestaDTO(
                    true,
                    sessionId,
                    sesion.getSalidaAcumulada(),
                    null,
                    codigoRiscV
            );

            return Response.ok(respuesta).build();

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
     * Envía texto al stdin de una sesion viva y devuelve la salida generada.
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

            SesionVenus sesion = sesiones.get(sessionId);
            if (sesion == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Sesion no encontrada o ya finalizada").build();
            }

            if (!sesion.estaVivo()) {
                sesiones.remove(sessionId);
                return Response.status(Response.Status.GONE)
                        .entity("El proceso de Venus ya termino").build();
            }

            String salida = sesion.enviar(texto);

            EjecutarSesionRespuestaDTO respuesta = new EjecutarSesionRespuestaDTO(
                    true,
                    sessionId,
                    salida,
                    null,
                    null
            );

            return Response.ok(respuesta).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + ex.getMessage()).build();
        }
    }

    /**
     * Detiene una sesion interactiva y libera el proceso de Venus.
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

            SesionVenus sesion = sesiones.remove(sessionId);
            if (sesion != null) {
                sesion.detener();
            }

            EjecutarSesionRespuestaDTO respuesta = new EjecutarSesionRespuestaDTO(
                    true,
                    sessionId,
                    null,
                    "Sesion detenida",
                    null
            );

            return Response.ok(respuesta).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + ex.getMessage()).build();
        }
    }
}
