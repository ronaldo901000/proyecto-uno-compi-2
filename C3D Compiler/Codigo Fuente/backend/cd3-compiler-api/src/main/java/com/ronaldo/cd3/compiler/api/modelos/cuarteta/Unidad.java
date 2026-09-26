package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import java.util.ArrayList;
import java.util.List;

/*
 * @author ronaldo
 */
public class Unidad {

    private final String nombre;
    private final List<Cuarteta> cuartetas;

    public Unidad(String nombre) {
        this.nombre = nombre;
        this.cuartetas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Cuarteta> getCuartetas() {
        return cuartetas;
    }
}