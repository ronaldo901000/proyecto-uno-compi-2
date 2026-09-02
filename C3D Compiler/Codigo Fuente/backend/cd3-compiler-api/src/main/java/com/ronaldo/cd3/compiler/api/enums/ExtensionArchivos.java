package com.ronaldo.cd3.compiler.api.enums;

/**
 *
 * @author ronaldo
 */
public enum ExtensionArchivos {

    Y("y"), Z("y"), PIG("pig");

    private String texto;

    private ExtensionArchivos(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return this.texto;
    }
}
