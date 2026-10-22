package com.ronaldo.cd3.compiler.api.resources;

import com.ronaldo.cd3.compiler.api.dtos.entrada.EntradaDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.EjecutarRespuestaDTO;
import com.ronaldo.cd3.compiler.api.exceptions.EntradaException;
import com.ronaldo.cd3.compiler.api.services.analisis.Analizador;
import com.ronaldo.cd3.compiler.api.services.generador.riscv.GeneradorRiscV;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Recurso REST para compilar y ejecutar codigo RISC-V generado por el compilador.
 * Usa Venus (java -jar venus.jar) para ejecutar el ensamblador .s generado.
 */
@Path("/ejecutar")
public class EjecutarResource {

    private static final String VENUS_JAR = System.getProperty("venus.jar.path",
            "/home/ronaldo/Descargas/venus.jar");
    private static final String WORK_DIR = System.getProperty("venus.work.dir",
            "/tmp/opencode/unificado");

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ejecutar(EntradaDTO entrada) {
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

            // 2. Escribir archivo .s
            java.nio.file.Path workDir = Paths.get(WORK_DIR);
            Files.createDirectories(workDir);
            java.nio.file.Path asmFile = workDir.resolve("salida.s");
            Files.writeString(asmFile, codigoRiscV);

            // 3. Ejecutar con Venus
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-jar", VENUS_JAR, asmFile.toString());
            pb.directory(workDir.toFile());
            pb.redirectErrorStream(true);

            Process process = pb.start();

            // Enviar entrada si la hay (stdin)
            String entradaEstandar = entrada.getEntradaEstandar();
            if (entradaEstandar != null && !entradaEstandar.isBlank()) {
                try (OutputStream os = process.getOutputStream()) {
                    os.write(entradaEstandar.getBytes());
                    os.flush();
                }
            }

            // Leer salida
            StringBuilder salida = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    salida.append(linea).append("\n");
                }
            }

            int rc = process.waitFor();

            EjecutarRespuestaDTO respuesta = new EjecutarRespuestaDTO(
                    rc == 0,
                    salida.toString(),
                    rc != 0 ? "Codigo de salida: " + rc : null,
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
}
