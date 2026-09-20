package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public abstract class Expresion extends Nodo implements Verificable {

    protected TipoDato resultado;
    protected Tipo tipo;

    public Expresion(int fila, int columna) {
        super(fila, columna);
    }

    public abstract String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas);

    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
        this.resultado = (tipo != null) ? tipo.getTipoDato() : null;
    }

    public TipoDato getResultado() {
        return resultado;
    }

    public void setResultado(TipoDato resultado) {
        this.resultado = resultado;
    }

}
