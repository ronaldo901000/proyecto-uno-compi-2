package com.ronaldo.cd3.compiler.api.enums;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> todas() {
        List<String> extensiones = new ArrayList<>();
        for (ExtensionArchivos ext : ExtensionArchivos.values()) {
            extensiones.add(ext.getTexto());
        }
        return extensiones;
    }
}
