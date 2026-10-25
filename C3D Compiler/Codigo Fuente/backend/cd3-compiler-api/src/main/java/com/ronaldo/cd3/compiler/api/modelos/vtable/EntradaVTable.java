package com.ronaldo.cd3.compiler.api.modelos.vtable;

/**
 * @author ronaldo
 */
public class EntradaVTable {

    private final String firma;
    private final String etiquetaImplementacion;

    public EntradaVTable(String firma, String etiquetaImplementacion) {
        this.firma = firma;
        this.etiquetaImplementacion = etiquetaImplementacion;
    }

    public String getFirma() {
        return firma;
    }

    public String getEtiquetaImplementacion() {
        return etiquetaImplementacion;
    }
}
