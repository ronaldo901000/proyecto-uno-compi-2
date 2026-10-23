package com.ronaldo.cd3.compiler.api.modelos.tipos;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class TipoStructura extends Tipo {

    private static final int TAMAÑO_REFERENCIA = 4;
    private static final int ENCABEZADO_CLASE = 4;
    private int idClase;

    private String nombreStruct;
    private String ambito;
    private Map<String, Tipo> atributos;
    private TipoStructura tipoPadre;

    public TipoStructura(String nombreStruct, String ambito) {
        super(TipoDato.ESTRUCTURA);
        this.nombreStruct = nombreStruct;
        this.ambito = ambito;
        this.atributos = new LinkedHashMap<>();
    }

    /**
     * METODO ENCARGADO DE VERIFICAR SI UNA CLASE ES SUBTIPO DE OTRA
     *
     * @param otro
     * @return
     */
    public boolean esSubtipoDe(Tipo otro) {
        for (TipoStructura actual = this; actual != null; actual = actual.tipoPadre) {
            if (actual.esIgual(otro)) {
                return true;
            }
        }
        return false;
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

    /**
     * Busca el atributo en esta clase, si no esta busca en sus ancestros
     */
    public boolean existeAtributo(String nombreAtributo) {
        return getTipoAtributo(nombreAtributo) != null;
    }

    /**
     * Busca el tipo del atributo en esta clase, si no esta, en sus ancestros
     */
    public Tipo getTipoAtributo(String nombreAtributo) {
        for (TipoStructura actual = this; actual != null; actual = actual.tipoPadre) {
            Tipo tipo = actual.atributos.get(nombreAtributo);
            if (tipo != null) {
                return tipo;
            }
        }
        return null;
    }

    public Map<String, Tipo> getAtributos() {
        return atributos;
    }

    /**
     * todos los atributos en el orden en que se ubican en el heap, primero los
     * del ancestro mas lejano, luego los de cada clase hija, y al final los
     * propios
     */
    public Map<String, Tipo> getAtributosCompletos() {
        Map<String, Tipo> completos = new LinkedHashMap<>();
        if (tipoPadre != null) {
            completos.putAll(tipoPadre.getAtributosCompletos());
        }
        for (Map.Entry<String, Tipo> atributo : atributos.entrySet()) {
            // si el hijo redeclara un nombre, se conserva la posicion del padre
            completos.putIfAbsent(atributo.getKey(), atributo.getValue());
        }
        return completos;
    }

    public int getOffsetAtributo(String nombreAtributo) {
        int offset = tamañoEncabezado();
        for (Map.Entry<String, Tipo> atributo : getAtributosCompletos().entrySet()) {
            offset = alinear(offset, alineacionEnObjeto(atributo.getValue()));
            if (atributo.getKey().equals(nombreAtributo)) {
                return offset;
            }
            offset += tamañoEnObjeto(atributo.getValue());
        }
        return -1;
    }

    public int getTamañoHeap() {
        int offset = tamañoEncabezado();
        for (Tipo tipoAtributo : getAtributosCompletos().values()) {
            offset = alinear(offset, alineacionEnObjeto(tipoAtributo));
            offset += tamañoEnObjeto(tipoAtributo);
        }
        return alinear(offset, alineacion());
    }

    private int tamañoEnObjeto(Tipo tipoAtributo) {
        if (tipoAtributo instanceof TipoStructura
                || tipoAtributo instanceof TipoArreglo) {
            return TAMAÑO_REFERENCIA;
        }
        return tipoAtributo.tamañoBytes();
    }

    private int alinear(int offset, int alineacion) {
        if (alineacion <= 1) {
            return offset;
        }
        int resto = offset % alineacion;
        return (resto == 0) ? offset : offset + (alineacion - resto);
    }

    public TipoStructura getTipoPadre() {
        return tipoPadre;
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
        // Las clases Z son por referencia (viven en el heap). Las estructuras
        // de Y son por valor: residen directamente en el frame.
        return esClase();
    }

    @Override
    public boolean esNumerico() {
        return false;
    }

    @Override
    public int tamañoBytes() {
        return getTamañoHeap();
    }

    @Override
    public int alineacion() {
        int alineacion = (tamañoEncabezado() > 0) ? TAMAÑO_REFERENCIA : 1;
        for (Tipo tipoAtributo : getAtributosCompletos().values()) {
            alineacion = Math.max(alineacion, alineacionEnObjeto(tipoAtributo));
        }
        return alineacion;
    }

    private int alineacionEnObjeto(Tipo tipoAtributo) {
        if (tipoAtributo instanceof TipoStructura
                || tipoAtributo instanceof TipoArreglo) {
            return TAMAÑO_REFERENCIA;
        }
        return tipoAtributo.alineacion();
    }

    @Override
    public String tipoC() {
        return "struct " + this.nombreStruct;
    }

    @Override
    public String toString() {
        return this.nombreStruct;
    }

    public void setTipoPadre(TipoStructura tipoPadre) {
        this.tipoPadre = tipoPadre;
    }

    public boolean esClase() {
        return "clase_z".equals(ambito);
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    private int tamañoEncabezado() {
        return esClase() ? ENCABEZADO_CLASE : 0;
    }
}
