package com.ronaldo.cd3.compiler.api.services.traduccion;

import java.util.ArrayList;
import java.util.List;

import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Cuarteta;

class Unidad {

    final String nombre;
    final List<Cuarteta> cuartetas = new ArrayList<>();

    Unidad(String nombre) {
        this.nombre = nombre;
    }
}