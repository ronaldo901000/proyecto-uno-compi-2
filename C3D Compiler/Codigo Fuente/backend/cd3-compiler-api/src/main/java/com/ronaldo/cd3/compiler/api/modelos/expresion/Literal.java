package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;

/**
 *
 * @author ronaldo
 */
public class Literal extends Expresion {

    private Object contenido;
    public Literal(Object contenido, int fila, int columna) {
        super(fila, columna);
        this.contenido = contenido;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }

    public TipoDato getResultado() {
        return resultado;
    }

    public void setResultado(TipoDato resultado) {
        this.resultado = resultado;
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

}
