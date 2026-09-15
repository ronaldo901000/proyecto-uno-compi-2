package com.ronaldo.cd3.compiler.api.modelos.programaPig;

/**
 *
 * @author ronaldo
 */
public class ImportacionPig {

    private String ruta;
    private int fila;
    private int columna;

    public ImportacionPig(String ruta, int fila, int columna) {
        this.ruta = ruta;
        this.fila = fila;
        this.columna = columna;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}