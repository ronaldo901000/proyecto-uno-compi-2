package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;

/**
 *
 * @author ronaldo
 */
public class LlamadaThis extends Expresion implements Instruccion {

    private Reglas reglas = new Reglas();
    private AccesoVariable variable;
    private Llamada llamada;

    public LlamadaThis(AccesoVariable variable, Llamada llamada, int fila, int columna) {
        super(fila, columna);
        this.variable = variable;
        this.llamada = llamada;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (esMetodo()) {
            llamada.verificarSemantica(contexto);
            setTipo(llamada.getTipo());
        } else {
            variable.verificarSemantica(contexto);
            setTipo(variable.getTipo());
        }

    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (esMetodo()) {
            return llamada.generarCuartetas(contexto, cuartetas);
        }
        
        return variable.generarCuartetas(contexto, cuartetas);

    }

    private boolean esMetodo() {
        return llamada != null;
    }

}
