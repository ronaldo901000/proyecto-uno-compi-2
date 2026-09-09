package com.ronaldo.cd3.compiler.api.modelos.instruccion;

import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public class Asignacion extends Nodo implements Instruccion {

    private Expresion objetivo;
    private Expresion valor;

    public Asignacion(Expresion objetivo, Expresion valor, int fila, int columna) {
        super(fila, columna);
        this.objetivo = objetivo;
        this.valor = valor;
    }

    public Expresion getObjetivo() {
        return objetivo;
    }

    public Expresion getValor() {
        return valor;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

}
