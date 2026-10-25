package com.ronaldo.cd3.compiler.api.services.ejecutor.riscv;

/**
 * 
 * @author ronaldo
 */
public class BufferSalida {

    private final StringBuilder pendiente = new StringBuilder();
    private volatile boolean finLectura = false;


    public synchronized void agregar(char[] datos, int inicio, int cantidad) {
        pendiente.append(datos, inicio, cantidad);
    }

    public synchronized int tamaño() {
        return pendiente.length();
    }


    public synchronized String extraer() {
        String s = pendiente.toString();
        pendiente.setLength(0);
        return s;
    }


    public void marcarFin() {
        finLectura = true;
    }

    public boolean haTerminadoLectura() {
        return finLectura;
    }
}