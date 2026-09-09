package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;

/**
 *
 * @author ronaldo
 */
public class DeclaracionVariable extends Declaracion {

    private Expresion valorInicial;

    public DeclaracionVariable(Expresion valorInicial, String tipoDato, String id, int fila, int columna) {
        super(tipoDato, id, fila, columna);
        this.valorInicial = valorInicial;
    }

    public Expresion getValorInicial() {
        return valorInicial;
    }

}
