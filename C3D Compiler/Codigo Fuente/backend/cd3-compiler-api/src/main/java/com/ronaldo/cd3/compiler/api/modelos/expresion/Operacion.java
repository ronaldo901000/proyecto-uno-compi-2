package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.Operador;

/**
 *
 * @author ronaldo
 */
public class Operacion extends Expresion {

    private Expresion izquierda;
    private Expresion derecha;
    private Operador operador;

    public Operacion(Expresion izquierda, Expresion derecha, Operador operador, int fila, int columna) {
        super(fila, columna);
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.operador = operador;
    }

    public Expresion getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Operacion izquierda) {
        this.izquierda = izquierda;
    }

    public Expresion getDerecha() {
        return derecha;
    }

    public void setDerecha(Operacion derecha) {
        this.derecha = derecha;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
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
