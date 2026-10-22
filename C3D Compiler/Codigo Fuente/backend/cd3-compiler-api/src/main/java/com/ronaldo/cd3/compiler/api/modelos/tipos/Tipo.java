package com.ronaldo.cd3.compiler.api.modelos.tipos;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;

public abstract class Tipo {

    private TipoDato tipoDato;

    public Tipo(TipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    public abstract boolean esIgual(Tipo otro);

    public abstract boolean esPorReferencia();

    public abstract boolean esNumerico();

    public abstract int tamañoBytes();

    public int alineacion() {
        int tamaño = tamañoBytes();
        if (tamaño <= 1) {
            return 1;
        }
        return Math.min(tamaño, 8);
    }

    public abstract String tipoC();
}
