package com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos;

import com.ronaldo.cd3.compiler.api.enums.RolSimbolo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public abstract class Simbolo {

    private final String id;
    private Tipo tipo;
    private final RolSimbolo rol;

    public Simbolo(String id, Tipo tipo, RolSimbolo rol) {
        this.id = id;
        this.tipo = tipo;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public RolSimbolo getRol() {
        return rol;
    }
}