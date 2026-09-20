package com.ronaldo.cd3.compiler.api.interfaces;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;

/**
 *
 * @author ronaldo
 */
public interface Generable {

    /**
     * Genera las cuartetas (codigo de tres direcciones) del nodo.
     *
     * Para las expresiones devuelve la direccion (literal, variable o
     * temporal) donde queda almacenado el resultado. Para las instrucciones
     * devuelve null, ya que solo emiten cuartetas al {@code ListaCuartetas}.
     */
    String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas);
}