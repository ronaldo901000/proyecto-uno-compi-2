package com.ronaldo.cd3.compiler.api.enums;

/**
 *
 * @author ronaldo
 */
public enum ExtensionArchivos {

    Y("y"), Z("z"), PIG("pig");

    private String texto;

    private ExtensionArchivos(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return this.texto;
    }

    public static java.util.List<String> todas() {
        java.util.List<String> extensiones = new java.util.ArrayList<>();
        for (ExtensionArchivos ext : ExtensionArchivos.values()) {
            extensiones.add(ext.getTexto());
        }
        return extensiones;
    }
}
