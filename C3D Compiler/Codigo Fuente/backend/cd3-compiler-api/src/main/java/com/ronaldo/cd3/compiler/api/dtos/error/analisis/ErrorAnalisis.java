package com.ronaldo.cd3.compiler.api.dtos.error.analisis;

/**
 *
 * @author ronaldo
 */
public class ErrorAnalisis {

    protected int fila;
    protected int columna;
    protected String lexema;
    protected String descripcion;
    protected String tipo;
    protected String ruta;

    public ErrorAnalisis(int fila, int columna, String lexema, String descripcion, String ruta) {
        this.fila = fila;
        this.columna = columna;
        this.lexema = lexema;
        this.descripcion = descripcion;
        this.ruta = ruta;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    
}
