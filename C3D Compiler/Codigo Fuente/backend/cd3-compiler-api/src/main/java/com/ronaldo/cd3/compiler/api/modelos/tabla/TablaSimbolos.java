package com.ronaldo.cd3.compiler.api.modelos.tabla;

import com.ronaldo.cd3.compiler.api.enums.RolSimbolo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.Simbolo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class TablaSimbolos {

    private final String nombreAmbito;
    private final TablaSimbolos padre;
    private final Map<String, Simbolo> simbolos;
    private final List<TablaSimbolos> ambitos;

    private int siguientePosicion;
    private int tamanoAmbito;

    public TablaSimbolos(String nombreAmbito, TablaSimbolos padre) {
        this.nombreAmbito = nombreAmbito;
        this.padre = padre;
        this.simbolos = new LinkedHashMap<>();
        this.ambitos = new ArrayList<>();
        this.siguientePosicion = 0;
        this.tamanoAmbito = 0;
    }

    public static TablaSimbolos nuevoGlobal() {
        return new TablaSimbolos("global", null);
    }

    public String getNombreAmbito() {
        return nombreAmbito;
    }

    public TablaSimbolos getPadre() {
        return padre;
    }

    public boolean esGlobal() {
        return padre == null;
    }

    public boolean agregar(Simbolo simbolo) {
        if (simbolos.containsKey(simbolo.getId())) {
            return false;
        }
        simbolos.put(simbolo.getId(), simbolo);
        return true;
    }

    public boolean existe(String id) {
        for (TablaSimbolos ambito = this; ambito != null; ambito = ambito.padre) {
            if (ambito.simbolos.containsKey(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeLocal(String id) {
        return simbolos.containsKey(id);
    }

    public Simbolo buscar(String id) {
        for (TablaSimbolos ambito = this; ambito != null; ambito = ambito.padre) {
            Simbolo simbolo = ambito.simbolos.get(id);
            if (simbolo != null) {
                return simbolo;
            }
        }
        return null;
    }

    public Simbolo buscarLocal(String id) {
        return simbolos.get(id);
    }

    public SimboloVariable buscarVariable(String id) {
        Simbolo simbolo = buscar(id);
        if (simbolo instanceof SimboloVariable) {
            return (SimboloVariable) simbolo;
        }
        return null;
    }

    public SimboloFuncion buscarFuncion(String id) {
        Simbolo simbolo = buscar(id);
        if (simbolo instanceof SimboloFuncion || simbolo == null) {
            return (SimboloFuncion) simbolo;
        }
        return null;
    }

    public TablaSimbolos nuevoAmbito(String nombre) {
        TablaSimbolos hijo = new TablaSimbolos(nombre, this);
        this.ambitos.add(hijo);
        return hijo;
    }

    public TablaSimbolos getAmbito(String nombre) {
        for (TablaSimbolos ambito : ambitos) {
            if (ambito.nombreAmbito.equals(nombre)) {
                return ambito;
            }
        }
        return null;
    }

    public List<TablaSimbolos> getAmbitos() {
        return ambitos;
    }

    public Map<String, Simbolo> getSimbolos() {
        return simbolos;
    }

    public List<Simbolo> getSimbolosPorRol(RolSimbolo rol) {
        List<Simbolo> resultado = new ArrayList<>();
        for (Simbolo simbolo : simbolos.values()) {
            if (simbolo.getRol() == rol) {
                resultado.add(simbolo);
            }
        }
        return resultado;
    }

    public SimboloClase buscarClase(String nombreClase) {
        for (TablaSimbolos ambito = this; ambito != null; ambito = ambito.padre) {
            Simbolo simbolo = ambito.simbolos.get(nombreClase);
            if (simbolo instanceof SimboloClase) {
                return (SimboloClase) simbolo;
            }
        }
        return null;
    }

    public int asignarPosicion(SimboloVariable variable) {
        int posicion = siguientePosicion;
        variable.setPosicion(posicion);
        siguientePosicion += variable.getTipo().tamanoBytes();
        return posicion;
    }

    public int asignarPosicion(SimboloVariable variable, Tipo tipo, boolean global) {
        variable.setTipo(tipo);
        variable.setEsGlobal(global);
        return asignarPosicion(variable);
    }

    public int getSiguientePosicion() {
        return siguientePosicion;
    }

    public void setSiguientePosicion(int siguientePosicion) {
        this.siguientePosicion = siguientePosicion;
    }

    public int getTamanoAmbito() {
        return tamanoAmbito;
    }

    public void setTamanoAmbito(int tamanoAmbito) {
        this.tamanoAmbito = tamanoAmbito;
    }

    public void cerrarAmbito() {
        this.tamanoAmbito = this.siguientePosicion;
    }

    @Override
    public String toString() {
        return "Ambito: " + nombreAmbito + " -> " + simbolos.keySet();
    }

    public void imprimir() {
        imprimir(0);
    }

    private void imprimir(int nivel) {
        StringBuilder sangria = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            sangria.append("  ");
        }
        System.out.println(sangria + "AMBITO: " + nombreAmbito);
        for (Simbolo simbolo : simbolos.values()) {
            System.out.println(sangria + "  - " + simbolo.getRol()
                    + " " + simbolo.getId()
                    + " : " + simbolo.getTipo());
        }
        for (TablaSimbolos ambito : ambitos) {
            ambito.imprimir(nivel + 1);
        }
    }
}