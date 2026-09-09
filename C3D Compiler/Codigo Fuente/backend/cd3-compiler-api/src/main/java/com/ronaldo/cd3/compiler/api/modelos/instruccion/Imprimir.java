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

    public Imprimir(Expresion valor, int fila, int columna) {
        super(fila, columna);
        this.valor = valor;
    }

    public Expresion getValor() {
        return valor;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (valor != null) {
            valor.verificarSemantica(contexto);
        }
    }

}