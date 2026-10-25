package com.ronaldo.cd3.compiler.api.exceptions;

public class SesionException extends Exception {

    public enum Tipo {
        INVALIDA, NO_ENCONTRADA, FINALIZADA
    }

    private final Tipo tipo;

    public SesionException(Tipo tipo, String mensaje) {
        super(mensaje);
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
