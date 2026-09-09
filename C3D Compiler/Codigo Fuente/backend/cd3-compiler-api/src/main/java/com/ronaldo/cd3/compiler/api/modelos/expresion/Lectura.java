package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;

/**
 *
 * @author ronaldo
 */
public class Lectura extends Expresion implements Instruccion{

    public Lectura(int fila, int columna) {
        super(fila, columna);
    }
}
