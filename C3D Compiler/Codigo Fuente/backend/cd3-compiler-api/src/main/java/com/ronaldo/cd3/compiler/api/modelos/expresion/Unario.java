package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.Operador;

/**
 *
 * @author ronaldo
 */
public class Unario extends Expresion {

    private Expresion exp;
    private Operador operador;

    public Unario(Expresion exp, Operador operador, int fila, int columna) {
        super(fila, columna);
        this.exp = exp;
        this.operador = operador;
    }

    public Expresion getExp() {
        return exp;
    }

    public Operador getOperador() {
        return operador;
    }

}
