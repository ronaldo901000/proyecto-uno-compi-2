package com.ronaldo.cd3.compiler.api.modelos.tabla;

import com.ronaldo.cd3.compiler.api.enums.RolSimbolo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.Simbolo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
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
    private int tamañoAmbito;

    public TablaSimbolos(String nombreAmbito, TablaSimbolos padre) {
        this.nombreAmbito = nombreAmbito;
        this.padre = padre;
        this.simbolos = new LinkedHashMap<>();
        this.ambitos = new ArrayList<>();
        this.siguientePosicion = 0;
        this.tamañoAmbito = 0;
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
        String clave = claveDeSimbolo(simbolo);
        if (simbolos.containsKey(clave)) {
            return false;
        }
        simbolos.put(clave, simbolo);
        return true;
    }

    private String claveDeSimbolo(Simbolo simbolo) {
        if (simbolo instanceof SimboloFuncion) {
            SimboloFuncion funcion = (SimboloFuncion) simbolo;
            List<Tipo> tipos = new ArrayList<>();
            for (SimboloParametro parametro : funcion.getParametros()) {
                tipos.add(parametro.getTipo());
            }
            return claveFuncion(funcion.getId(), tipos);
        }
        return simbolo.getId();
    }

    public static String claveFuncion(String nombre, List<Tipo> tipos) {
        StringBuilder sb = new StringBuilder(nombre).append('(');
        if (tipos != null) {
            for (int i = 0; i < tipos.size(); i++) {
                if (i > 0) {
                    sb.append(',');
                }
                Tipo tipo = tipos.get(i);
                sb.append(tipo != null ? tipo.toString() : "?");
            }
        }
        return sb.append(')').toString();
    }

    public boolean hayFuncion(String id) {
        for (TablaSimbolos ambito = this; ambito != null; ambito = ambito.padre) {
            for (Simbolo simbolo : ambito.simbolos.values()) {
                if (simbolo instanceof SimboloFuncion
                        && simbolo.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existeOtroSimbolo(String id) {
        for (TablaSimbolos ambito = this; ambito != null; ambito = ambito.padre) {
            for (Simbolo simbolo : ambito.simbolos.values()) {
                if (!(simbolo instanceof SimboloFuncion)
                        && simbolo.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existeFuncion(String nombre, List<Tipo> tiposParametros) {
        String clave = claveFuncion(nombre, tiposParametros);
        for (TablaSimbolos ambito = this; ambito != null; ambito = ambito.padre) {
            Simbolo simbolo = ambito.simbolos.get(clave);
            if (simbolo instanceof SimboloFuncion) {
                return true;
            }
        }
        return false;
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
        for (TablaSimbolos ambito = this; ambito != null; ambito = ambito.padre) {
            for (Simbolo simbolo : ambito.simbolos.values()) {
                if (simbolo instanceof SimboloFuncion
                        && simbolo.getId().equals(id)) {
                    return (SimboloFuncion) simbolo;
                }
            }
        }
        return null;
    }

    public List<SimboloFuncion> buscarSobrecargas(String id) {
        for (TablaSimbolos ambito = this; ambito != null; ambito = ambito.padre) {
            List<SimboloFuncion> encontradas = new ArrayList<>();
            for (Simbolo simbolo : ambito.simbolos.values()) {
                if (simbolo instanceof SimboloFuncion
                        && simbolo.getId().equals(id)) {
                    encontradas.add((SimboloFuncion) simbolo);
                }
            }
            if (!encontradas.isEmpty()) {
                return encontradas;
            }
        }
        return new ArrayList<>();
    }

    public String generarEtiquetaFuncion(String id) {
        int contador = 0;
        for (Simbolo simbolo : simbolos.values()) {
            if (simbolo instanceof SimboloFuncion
                    && simbolo.getId().equals(id)) {
                contador++;
            }
        }
        return (contador == 0) ? "fun_" + id : "fun_" + id + "_" + contador;
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

    public int getTamañoAmbito() {
        return tamañoAmbito;
    }

    public void setTamañoAmbito(int tamanoAmbito) {
        this.tamañoAmbito = tamanoAmbito;
    }

    public void cerrarAmbito() {
        this.tamañoAmbito = this.siguientePosicion;
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