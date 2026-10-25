package com.ronaldo.cd3.compiler.api.services.ejecutor.riscv;

import com.ronaldo.cd3.compiler.api.dtos.respuesta.EjecutarSesionRespuestaDTO;
import com.ronaldo.cd3.compiler.api.exceptions.SesionException;
import com.ronaldo.cd3.compiler.api.exceptions.SesionException.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Cuarteta;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author ronaldo
 */
public class EjecutorRiscV {

    private static final String RARS_JAR = System.getProperty("rars.jar.path",
            "/home/ronaldo/Descargas/rars.jar");

    private static final String WORK_DIR = System.getProperty("rars.work.dir",
            "/home/ronaldo/Documentos/2026/Segundo Semestre/Cursos/1. Compi 2/Proyectos/Proyecto Uno-Dos/C3D Compiler/C3D Compiler/Generados");

    private static final Map<String, SesionRars> sesiones = new ConcurrentHashMap<>();
    private final GeneradorArchivo archivos = new GeneradorArchivo(WORK_DIR);

    public EjecutarSesionRespuestaDTO iniciar(String codigoRiscV, List<Cuarteta> cuartetas) throws Exception {
        Path asm = archivos.guardar("salida.s", codigoRiscV);

        String id = UUID.randomUUID().toString();
        SesionRars sesion = new SesionRars(id, RARS_JAR, asm.toString(), WORK_DIR);
        sesion.esperarSalida(3000);

        EjecutarSesionRespuestaDTO resp = new EjecutarSesionRespuestaDTO(
                true, id, sesion.leerNuevo(), null, codigoRiscV, cuartetas);

        boolean terminado = sesion.terminado();
        resp.setTerminado(terminado);
        if (!terminado) {
            sesiones.put(id, sesion);
        }
        return resp;
    }

    public EjecutarSesionRespuestaDTO enviar(String id, String texto) throws Exception {
        SesionRars sesion = obtener(id);

        if (!sesion.estaVivo()) {
            sesiones.remove(id);
            throw new SesionException(Tipo.FINALIZADA, "El proceso de RARS ya termino");
        }

        String salida = sesion.enviar(texto);
        return respuesta(id, sesion, salida);
    }

    public EjecutarSesionRespuestaDTO leerSalida(String id) throws Exception {
        SesionRars sesion = obtener(id);
        sesion.esperarSalida(300);
        return respuesta(id, sesion, sesion.leerNuevo());
    }

    public EjecutarSesionRespuestaDTO detener(String id) throws Exception {
        validarId(id);
        SesionRars sesion = sesiones.remove(id);
        if (sesion != null) {
            sesion.detener();
        }
        EjecutarSesionRespuestaDTO resp
                = new EjecutarSesionRespuestaDTO(true, id, null, null, null, null);
        resp.setTerminado(true);
        return resp;
    }

    // ---------------- helpers ----------------
    private void validarId(String id) throws SesionException {
        if (id == null || id.isBlank()) {
            throw new SesionException(Tipo.INVALIDA, "sessionId es requerido");
        }
    }

    private SesionRars obtener(String id) throws SesionException {
        validarId(id);
        SesionRars sesion = sesiones.get(id);
        if (sesion == null) {
            throw new SesionException(Tipo.NO_ENCONTRADA, "Sesion no encontrada o ya finalizada");
        }
        return sesion;
    }

    private EjecutarSesionRespuestaDTO respuesta(String id, SesionRars sesion, String salida) {
        EjecutarSesionRespuestaDTO resp
                = new EjecutarSesionRespuestaDTO(true, id, salida, null, null, null);
        boolean terminado = sesion.terminado();
        resp.setTerminado(terminado);
        if (terminado) {
            sesiones.remove(id);
        }
        return resp;
    }
}