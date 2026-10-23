package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class SesionRars {

    private final String sessionId;
    private final Process process;
    private final OutputStreamWriter stdin;
    private final StringBuilder pendiente = new StringBuilder();
    private final Object lock = new Object();
    private volatile boolean finLectura = false;

    public SesionRars(String sessionId, String rarsJar, String archivoAsm, String workDir)
            throws IOException {
        this.sessionId = sessionId;

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", rarsJar, "nc", "sm", archivoAsm);
        pb.directory(new File(workDir));
        pb.redirectErrorStream(true);

        this.process = pb.start();
        this.stdin = new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8);

        Thread lector = new Thread(() -> {
            try (Reader r = new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)) {
                char[] buf = new char[1024];
                int n;
                while ((n = r.read(buf)) != -1) {
                    synchronized (lock) {
                        pendiente.append(buf, 0, n);
                    }
                }
            } catch (IOException ignored) {
            } finally {
                finLectura = true;
            }
        }, "rars-lector-" + sessionId);
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
                int tam;
                synchronized (lock) {
                    tam = pendiente.length();
                }
                if (tam != ultimo) {
                    ultimo = tam;
                    ultimoCambio = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - ultimoCambio > 250 && tam > 0) {
                    return;
                }
                if (finLectura) {
                    return;
                }
                Thread.sleep(40);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String leerNuevo() {
        synchronized (lock) {
            String s = pendiente.toString();
            pendiente.setLength(0);
            return s;
        }
    }

    public String enviar(String texto) {
        if (texto != null && !finLectura) {
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
        if (!finLectura) {
            return false;
        }
        synchronized (lock) {
            return pendiente.length() == 0;
        }
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
            try { stdin.close(); } catch (Exception ignored) { }
        }
    }
}
