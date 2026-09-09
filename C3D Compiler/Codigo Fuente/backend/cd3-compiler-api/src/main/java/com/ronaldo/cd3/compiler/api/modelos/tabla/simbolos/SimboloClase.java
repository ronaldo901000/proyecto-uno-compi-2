package com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos;

import com.ronaldo.cd3.compiler.api.enums.RolSimbolo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class SimboloClase extends Simbolo {

    private final Map<String, SimboloVariable> atributos;
    private final Map<String, SimboloFuncion> metodos;
    private SimboloFuncion constructor;
    private int tamanoHeap;

    public SimboloClase(String id, int tamanoHeap) {
        super(id, new TipoStructura(id), RolSimbolo.CLASE);
        this.atributos = new LinkedHashMap<>();
        this.metodos = new LinkedHashMap<>();
        this.tamanoHeap = tamanoHeap;
    }

    public Map<String, SimboloVariable> getAtributos() {
        return atributos;
    }

    public SimboloVariable getAtributo(String nombreAtributo) {
        return atributos.get(nombreAtributo);
    }

    public void agregarAtributo(SimboloVariable atributo) {
        this.atributos.put(atributo.getId(), atributo);
    }

    public Map<String, SimboloFuncion> getMetodos() {
        return metodos;
    }

    public SimboloFuncion getMetodo(String nombreMetodo) {
        return metodos.get(nombreMetodo);
    }

    public void agregarMetodo(SimboloFuncion metodo) {
        metodo.setEsMetodo(true);
        metodo.setNombreClase(this.getId());
        this.metodos.put(metodo.getId(), metodo);
    }

    public SimboloFuncion getConstructor() {
        return constructor;
    }

    public void setConstructor(SimboloFuncion constructor) {
        this.constructor = constructor;
    }

    public int getTamanoHeap() {
        return tamanoHeap;
    }

    public void setTamanoHeap(int tamanoHeap) {
        this.tamanoHeap = tamanoHeap;
    }
}