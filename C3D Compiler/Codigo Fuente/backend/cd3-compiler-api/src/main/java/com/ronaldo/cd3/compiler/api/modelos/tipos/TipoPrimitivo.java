package com.ronaldo.cd3.compiler.api.modelos.tipos;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;

public class TipoPrimitivo extends Tipo {

    public TipoPrimitivo(TipoDato tipoDato) {
        super(tipoDato);
    }

    @Override
    public boolean esIgual(Tipo otro) {
        if (!(otro instanceof TipoPrimitivo)) {
            return false;
        }
        return this.getTipoDato() == otro.getTipoDato();
    }

    @Override
    public boolean esPorReferencia() {
        return this.getTipoDato() == TipoDato.CADENA
                || this.getTipoDato() == TipoDato.NULO;
    }

    @Override
    public boolean esNumerico() {
        return this.getTipoDato() == TipoDato.ENTERO
                || this.getTipoDato() == TipoDato.DECIMAL;
    }

    @Override
    public int tamañoBytes() {
        switch (this.getTipoDato()) {
            case ENTERO:
                return 4;
            case DECIMAL:
                return 8;
            case CADENA:
            case NULO:
                return 8;
            case CHAR:
            case BOOLEAN:
                return 1;
            case VOID:
            default:
                return 0;
        }
    }

    @Override
    public String tipoC() {
        switch (this.getTipoDato()) {
            case ENTERO:
                return "int";
            case DECIMAL:
                return "double";
            case CADENA:
                return "char*";
            case CHAR:
                return "char";
            case BOOLEAN:
                return "int";
            case NULO:
                return "void*";
            case VOID:
                return "void";
            default:
                return "error";
        }
    }

    @Override
    public String toString() {
        return this.getTipoDato().name();
    }
}