package com.ronaldo.cd3.compiler.api.services.ejecutor.riscv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * 
 * @author ronaldo
 */
public class LectorSalida implements Runnable {

    private final InputStream entrada;
    private final BufferSalida buffer;

    public LectorSalida(InputStream entrada, BufferSalida buffer) {
        this.entrada = entrada;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try (Reader r = new InputStreamReader(entrada, StandardCharsets.UTF_8)) {
            char[] buf = new char[1024];
            int n = r.read(buf);
            while (n != -1) {
                buffer.agregar(buf, 0, n);
                n = r.read(buf);
            }
        } catch (IOException ignored) {
        } finally {
            buffer.marcarFin();
        }
    }
}