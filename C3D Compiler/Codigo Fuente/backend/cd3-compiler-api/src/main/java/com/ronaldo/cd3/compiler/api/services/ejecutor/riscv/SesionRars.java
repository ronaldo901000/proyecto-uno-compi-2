package com.ronaldo.cd3.compiler.api.services.ejecutor.riscv;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author ronaldo
 */
public class SesionRars {

    private final String sessionId;
    private final Process process;
    private final OutputStreamWriter stdin;
    private final BufferSalida buffer = new BufferSalida();

    public SesionRars(String sessionId, String rarsJar, String archivoAsm, String workDir)
            throws IOException {
        this.sessionId = sessionId;

        // Comando: java -jar rars.jar nc sm archivo.s
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", rarsJar, "nc", "sm", archivoAsm);
        pb.directory(new File(workDir));
        pb.redirectErrorStream(true);

        this.process = pb.start();
        this.stdin = new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8);

        // Hilo en segundo plano que lee la salida del proceso
        LectorSalida tarea = new LectorSalida(process.getInputStream(), buffer);
        Thread lector = new Thread(tarea, "rars-lector-" + sessionId);
        lector.setDaemon(true);
        lector.start();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void esperarSalida(long maxMs) {
        long fin = System.currentTimeMillis() + maxMs;
        int ultimo = -1;
        long ultimoCambio = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() < fin) {
                int tam = buffer.tamaño();

                if (tam != ultimo) {
                    ultimo = tam;
                    ultimoCambio = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - ultimoCambio > 250 && tam > 0) {
                    return;
                }

                if (buffer.haTerminadoLectura()) {
                    return;
                }
                Thread.sleep(40);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String leerNuevo() {
        return buffer.extraer();
    }

    public String enviar(String texto) {
        if (texto != null && !buffer.haTerminadoLectura()) {
            try {
                stdin.write(texto);

                if (!texto.endsWith("\n")) {
                    stdin.write("\n");
                }
                stdin.flush();
            } catch (IOException e) {
            }
        }
        esperarSalida(1500);
        return leerNuevo();
    }

    public boolean terminado() {
        return buffer.haTerminadoLectura() && buffer.tamaño() == 0;
    }

    public boolean estaVivo() {
        return process.isAlive();
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
        }
    }
}
