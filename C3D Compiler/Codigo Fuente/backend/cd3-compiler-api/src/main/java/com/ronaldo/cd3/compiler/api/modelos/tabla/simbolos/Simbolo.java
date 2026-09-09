package com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos;

import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public abstract class Simbolo {

    private String id;
    private Tipo tipo;

    public Simbolo(String id, Tipo tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
