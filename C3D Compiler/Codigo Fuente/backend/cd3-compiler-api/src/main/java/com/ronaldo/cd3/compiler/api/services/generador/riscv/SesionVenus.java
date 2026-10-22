package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * Gestiona una sesion interactiva de Venus (java -jar venus.jar).
 * Mantiene el proceso vivo para permitir enviar entrada por stdin y leer
 * la salida acumulada entre peticiones HTTP.
 */
public class SesionVenus {

    private final String sessionId;
    private final Process process;
    private final PrintWriter stdin;
    private final BufferedReader stdout;
    private final StringBuilder salidaAcumulada = new StringBuilder();

    public SesionVenus(String sessionId, String venusJar, String archivoAsm, String workDir)
            throws IOException {
        this.sessionId = sessionId;

        ProcessBuilder pb = new ProcessBuilder(
                "java", "-jar", venusJar, archivoAsm);
        pb.directory(new java.io.File(workDir));
        pb.redirectErrorStream(true);

        this.process = pb.start();
        this.stdin = new PrintWriter(new OutputStreamWriter(process.getOutputStream()), true);
        this.stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Leer la salida inicial de forma no bloqueante (menus, prompts)
        drenarSalidaInicial();
    }

    private void drenarSalidaInicial() {
        try {
            // Esperar un poco a que Venus emita salida inicial
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String linea;
        try {
            while (stdout.ready() && (linea = stdout.readLine()) != null) {
                salidaAcumulada.append(linea).append("\n");
            }
        } catch (IOException e) {
            // Ignorar errores de lectura inicial
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    /**
     * Envía texto al stdin de Venus y devuelve la salida generada.
     */
    public String enviar(String texto) {
        if (texto == null) {
            return salidaAcumulada.toString();
        }

        // Enviar texto con salto de línea
        stdin.print(texto);
        if (!texto.endsWith("\n")) {
            stdin.print("\n");
        }
        stdin.flush();

        // Leer respuesta disponible (con timeout corto)
        StringBuilder respuesta = new StringBuilder();
        try {
            Thread.sleep(200); // Dar tiempo a Venus de procesar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            while (stdout.ready()) {
                String linea = stdout.readLine();
                if (linea == null) {
                    break; // Proceso terminó
                }
                respuesta.append(linea).append("\n");
                salidaAcumulada.append(linea).append("\n");
            }
        } catch (IOException e) {
            // Proceso puede haber terminado
        }

        return respuesta.toString();
    }

    /**
     * Devuelve toda la salida acumulada hasta ahora.
     */
    public String getSalidaAcumulada() {
        return salidaAcumulada.toString();
    }

    public boolean estaVivo() {
        try {
            int rc = process.exitValue();
            return false; // Si no lanza excepcion, el proceso ya termino
        } catch (IllegalThreadStateException e) {
            return true; // El proceso sigue vivo
        }
    }

    public void detener() {
        try {
            process.destroyForcibly();
            process.waitFor(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            try {
                stdin.close();
            } catch (Exception ignored) {
            }
            try {
                stdout.close();
            } catch (Exception ignored) {
            }
        }
    }
}
