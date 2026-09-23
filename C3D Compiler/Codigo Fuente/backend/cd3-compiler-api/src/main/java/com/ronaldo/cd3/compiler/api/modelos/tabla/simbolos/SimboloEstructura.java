package com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos;

import com.ronaldo.cd3.compiler.api.enums.RolSimbolo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class SimboloEstructura extends Simbolo {

    private final Map<String, SimboloVariable> atributos;
    private int tamañoHeap;

    public SimboloEstructura(String id, Map<String, SimboloVariable> atributos, int tamañoHeap) {
        super(id, new TipoStructura(id), RolSimbolo.TIPO_ESTRUCTURA);
        this.atributos = new LinkedHashMap<>(atributos);
        this.tamañoHeap = tamañoHeap;
    }

    public Map<String, SimboloVariable> getAtributos() {
        return atributos;
    }

    public SimboloVariable getAtributo(String nombreAtributo) {
        return atributos.get(nombreAtributo);
    }

    public int getTamañoHeap() {
        return tamañoHeap;
    }

    public void setTamañoHeap(int tamañoHeap) {
        this.tamañoHeap = tamañoHeap;
    }
}