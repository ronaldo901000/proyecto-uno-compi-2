package com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos;

import com.ronaldo.cd3.compiler.api.enums.RolSimbolo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class SimboloClase extends Simbolo {

    private final Map<String, SimboloVariable> atributos;
    private final Map<String, SimboloFuncion> metodos;
    private final List<SimboloFuncion> constructores;
    private SimboloFuncion constructor;
    private int tamanoHeap;

    public SimboloClase(String id, int tamanoHeap) {
        super(id, new TipoStructura(id), RolSimbolo.CLASE);
        this.atributos = new LinkedHashMap<>();
        this.metodos = new LinkedHashMap<>();
        this.constructores = new ArrayList<>();
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

    public List<SimboloFuncion> getMetodosLista() {
        return new ArrayList<>(metodos.values());
    }

    public List<SimboloFuncion> getMetodosPorNombre(String nombreMetodo) {
        List<SimboloFuncion> encontrados = new ArrayList<>();
        for (SimboloFuncion metodo : metodos.values()) {
            if (metodo.getId().equals(nombreMetodo)) {
                encontrados.add(metodo);
            }
        }
        return encontrados;
    }

    public SimboloFuncion getMetodo(String nombreMetodo) {
        for (SimboloFuncion metodo : metodos.values()) {
            if (metodo.getId().equals(nombreMetodo)) {
                return metodo;
            }
        }
        return null;
    }

    public void agregarMetodo(SimboloFuncion metodo) {
        metodo.setEsMetodo(true);
        metodo.setNombreClase(this.getId());
        this.metodos.put(TablaSimbolos.claveFuncion(
                metodo.getId(), tiposDe(metodo)), metodo);
    }

    public SimboloFuncion getConstructor() {
        return constructor;
    }

    public void setConstructor(SimboloFuncion constructor) {
        this.constructor = constructor;
    }

    public void agregarConstructor(SimboloFuncion constructor) {
        constructor.setNombreClase(this.getId());
        if (this.constructor == null) {
            this.constructor = constructor;
        }
        this.constructores.add(constructor);
    }

    public List<SimboloFuncion> getConstructores() {
        return constructores;
    }

    private List<Tipo> tiposDe(SimboloFuncion funcion) {
        List<Tipo> tipos = new ArrayList<>();
        for (SimboloParametro parametro : funcion.getParametros()) {
            tipos.add(parametro.getTipo());
        }
        return tipos;
    }

    public int getTamanoHeap() {
        return tamanoHeap;
    }

    public void setTamanoHeap(int tamanoHeap) {
        this.tamanoHeap = tamanoHeap;
    }
}