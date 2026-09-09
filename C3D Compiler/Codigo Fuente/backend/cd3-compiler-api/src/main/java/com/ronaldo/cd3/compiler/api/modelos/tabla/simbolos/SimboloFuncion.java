package com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos;

import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class SimboloFuncion extends Simbolo {

    private List<SimboloParametro> parametros;
    private int tamanoFrame;
    private String etiquetaInicio;

    public SimboloFuncion(String id, Tipo tipoRetorno,
            List<SimboloParametro> parametros,
            int tamanoFrame, String etiquetaInicio) {

        super(id, tipoRetorno);
        this.parametros = parametros;
        this.tamanoFrame = tamanoFrame;
        this.etiquetaInicio = etiquetaInicio;
    }

    public List<SimboloParametro> getParametros() {
        return parametros;
    }

    public int getTamanoFrame() {
        return tamanoFrame;
    }

    public String getEtiquetaInicio() {
        return etiquetaInicio;
    }
}
