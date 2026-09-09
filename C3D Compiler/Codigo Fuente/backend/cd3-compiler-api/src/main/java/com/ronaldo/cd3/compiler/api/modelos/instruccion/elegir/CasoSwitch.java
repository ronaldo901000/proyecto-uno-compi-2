package com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir;

import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CasoSwitch extends Nodo {

    private Expresion valor;
    private List<Instruccion> intruccionesInternas;

    public CasoSwitch(Expresion valor, List<Instruccion> intruccionesInternas, int fila, int columna) {
        super(fila, columna);
        this.valor = valor;
        this.intruccionesInternas = intruccionesInternas;
    }

    public Expresion getValor() {
        return valor;
    }

    public List<Instruccion> getIntruccionesInternas() {
        return intruccionesInternas;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

}
