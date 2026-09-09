package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public abstract class Expresion extends Nodo {

    protected TipoDato resultado;

    public Expresion(int fila, int columna) {
        super(fila, columna);
    }

    public TipoDato getResultado() {
        return resultado;
    }

    public void setResultado(TipoDato resultado) {
        this.resultado = resultado;
    }

}
