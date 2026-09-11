package com.ronaldo.cd3.compiler.api.modelos.instruccion;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public class Imprimir extends Nodo implements Instruccion {

    private Expresion valor;
    private boolean conSaltoLinea;

    public Imprimir(Expresion valor, int fila, int columna) {
        this(valor, true, fila, columna);
    }

    public Imprimir(Expresion valor, boolean conSaltoLinea, int fila, int columna) {
        super(fila, columna);
        this.valor = valor;
        this.conSaltoLinea = conSaltoLinea;
    }

    public Expresion getValor() {
        return valor;
    }

    public boolean isConSaltoLinea() {
        return conSaltoLinea;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (valor != null) {
            valor.verificarSemantica(contexto);
        }
    }

}