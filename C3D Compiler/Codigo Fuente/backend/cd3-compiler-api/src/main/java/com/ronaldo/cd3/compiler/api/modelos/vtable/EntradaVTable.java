package com.ronaldo.cd3.compiler.api.modelos.vtable;

/**
 * Entrada de una vtable: asocia la firma canonica de un metodo virtual con la
 * etiqueta (unidad) que lo implementa en una clase concreta.
 *
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
