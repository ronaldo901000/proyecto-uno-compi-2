package com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos;

import com.ronaldo.cd3.compiler.api.enums.RolSimbolo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class SimboloFuncion extends Simbolo {

    private final List<SimboloParametro> parametros;
    private int tamanoFrame;
    private String etiquetaInicio;
    private String nombreClase;
    private boolean esMetodo;

    public SimboloFuncion(String id, Tipo tipoRetorno,
            List<SimboloParametro> parametros,
            int tamanoFrame, String etiquetaInicio) {
        super(id, tipoRetorno, RolSimbolo.FUNCION);
        this.parametros = parametros;
        this.tamanoFrame = tamanoFrame;
        this.etiquetaInicio = etiquetaInicio;
        this.nombreClase = null;
        this.esMetodo = false;
    }

    public List<SimboloParametro> getParametros() {
        return parametros;
    }

    public Tipo getTipoRetorno() {
        return this.getTipo();
    }

    public void setTipoRetorno(Tipo tipoRetorno) {
        this.setTipo(tipoRetorno);
    }

    public int getTamanoFrame() {
        return tamanoFrame;
    }

    public void setTamanoFrame(int tamanoFrame) {
        this.tamanoFrame = tamanoFrame;
    }

    public String getEtiquetaInicio() {
        return etiquetaInicio;
    }

    public void setEtiquetaInicio(String etiquetaInicio) {
        this.etiquetaInicio = etiquetaInicio;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public boolean esMetodo() {
        return esMetodo;
    }

    public void setEsMetodo(boolean esMetodo) {
        this.esMetodo = esMetodo;
    }
}