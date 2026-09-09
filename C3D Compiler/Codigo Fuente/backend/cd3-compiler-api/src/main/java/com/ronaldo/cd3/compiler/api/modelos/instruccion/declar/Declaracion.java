package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public abstract class Declaracion extends Nodo implements Instruccion {

    protected String tipoDato;
    protected String id;

    public Declaracion(String tipoDato, String id, int fila, int columna) {
        super(fila, columna);
        this.tipoDato = tipoDato;
        this.id = id;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public String getId() {
        return id;
    }

}
