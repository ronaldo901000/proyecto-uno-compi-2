package com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos;

import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class SimboloEstructura extends Simbolo {

    private Map<String, SimboloVariable> atributos;
    private int tamañoHeap;

    public SimboloEstructura(String id, Map<String, SimboloVariable> atributos, int tamañoHeap) {
        super(id, new TipoStructura(id));
        this.atributos = atributos;
        this.tamañoHeap = tamañoHeap;
    }

    public Map<String, SimboloVariable> getAtributos() {
        return atributos;
    }

    public int getTamañoHeap() {
        return tamañoHeap;
    }
}
