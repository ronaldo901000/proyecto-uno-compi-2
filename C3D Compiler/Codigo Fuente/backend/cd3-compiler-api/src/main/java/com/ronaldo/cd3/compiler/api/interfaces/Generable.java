package com.ronaldo.cd3.compiler.api.interfaces;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;

/**
 *
 * @author ronaldo
 */
public interface Generable {

    String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas);
}