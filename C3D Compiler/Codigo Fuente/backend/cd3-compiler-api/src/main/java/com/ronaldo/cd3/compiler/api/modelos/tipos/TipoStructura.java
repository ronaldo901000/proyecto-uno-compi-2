package com.ronaldo.cd3.compiler.api.modelos.tipos;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class TipoStructura extends Tipo {

    private String nombreStruct;
    private String ambito;
    private Map<String, Tipo> atributos;

    public TipoStructura(String nombreStruct, String ambito) {
        super(TipoDato.ESTRUCTURA);
        this.nombreStruct = nombreStruct;
        this.ambito = ambito;
        this.atributos = new LinkedHashMap<>();
    }

    public TipoStructura(String nombreStruct) {
        this(nombreStruct, "global");
    }

    public String getNombreStruct() {
        return nombreStruct;
    }

    public String getAmbito() {
        return ambito;
    }

    public void agregarAtributo(String nombreAtributo, Tipo tipoAtributo) {
        this.atributos.put(nombreAtributo, tipoAtributo);
    }

    public boolean existeAtributo(String nombreAtributo) {
        return this.atributos.containsKey(nombreAtributo);
    }

    public Tipo getTipoAtributo(String nombreAtributo) {
        return this.atributos.get(nombreAtributo);
    }

    public Map<String, Tipo> getAtributos() {
        return atributos;
    }

    public int getTamanoHeap() {
        int tamano = 0;
        for (Tipo tipoAtributo : atributos.values()) {
            tamano += tipoAtributo.tamanoBytes();
        }
        return tamano;
    }

    @Override
    public boolean esIgual(Tipo otro) {
        if (!(otro instanceof TipoStructura)) {
            return false;
        }
        TipoStructura tStruct = (TipoStructura) otro;
        return this.nombreStruct.equalsIgnoreCase(tStruct.getNombreStruct());
    }

    @Override
    public boolean esPorReferencia() {
        return true;
    }

    @Override
    public boolean esNumerico() {
        return false;
    }

    @Override
    public int tamanoBytes() {
        return getTamanoHeap();
    }

    @Override
    public String tipoC() {
        return "struct " + this.nombreStruct;
    }

    @Override
    public String toString() {
        return this.nombreStruct;
    }
}