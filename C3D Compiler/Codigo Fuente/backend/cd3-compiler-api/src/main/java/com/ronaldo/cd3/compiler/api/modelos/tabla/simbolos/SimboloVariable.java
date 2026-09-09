package com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos;

import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public class SimboloVariable extends Simbolo {

    private int posicion;

    public SimboloVariable(String id, Tipo tipo, int posicion) {
        super(id, tipo);
        this.posicion = posicion;
    }

    public int getPosicion() {
        return posicion;
    }
}
