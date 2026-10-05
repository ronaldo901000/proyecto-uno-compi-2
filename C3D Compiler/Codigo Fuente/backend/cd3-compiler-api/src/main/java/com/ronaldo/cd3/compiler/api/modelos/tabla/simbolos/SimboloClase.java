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
    private int tamañoHeap;
    private TablaSimbolos ambito;
    private SimboloClase clasePadre;
    private String rutaPaquete;

    public SimboloClase(String id, int tamañoHeap) {
        super(id, new TipoStructura(id), RolSimbolo.CLASE);
        this.atributos = new LinkedHashMap<>();
        this.metodos = new LinkedHashMap<>();
        this.constructores = new ArrayList<>();
        this.tamañoHeap = tamañoHeap;
    }

    public SimboloClase getClasePadre() {
        return clasePadre;
    }

    public void setClasePadre(SimboloClase clasePadre) {
        this.clasePadre = clasePadre;
    }

    public SimboloVariable buscarAtributo(String nombreAtributo) {
        for (SimboloClase actual = this; actual != null; actual = actual.clasePadre) {
            SimboloVariable atributo = actual.atributos.get(nombreAtributo);
            if (atributo != null) {
                return atributo;
            }
        }
        return null;
    }

    public Map<String, SimboloVariable> getAtributos() {
        return atributos;
    }

    public SimboloVariable getAtributo(String nombreAtributo) {
        return atributos.get(nombreAtributo);
    }

    public void agregarAtributo(SimboloVariable atributo) {
        atributo.setNombreClase(this.getId());
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

    public int getTamañoHeap() {
        return tamañoHeap;
    }

    public void setTamañoHeap(int tamañoHeap) {
        this.tamañoHeap = tamañoHeap;
    }

    public TablaSimbolos getAmbito() {
        return ambito;
    }

    public void setAmbito(TablaSimbolos ambito) {
        this.ambito = ambito;
    }

    public String getRutaPaquete() {
        return rutaPaquete;
    }

    public void setRutaPaquete(String rutaPaquete) {
        this.rutaPaquete = rutaPaquete;
    }

}
