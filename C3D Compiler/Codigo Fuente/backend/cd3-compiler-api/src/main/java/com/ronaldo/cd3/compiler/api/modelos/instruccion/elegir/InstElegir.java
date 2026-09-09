package com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir;

import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class InstElegir extends Nodo implements Instruccion {

    private Expresion valorEvaluado;
    private List<CasoSwitch> casos;

    public InstElegir(Expresion valorEvaluado, List<CasoSwitch> casos, int fila, int columna) {
        super(fila, columna);
        this.valorEvaluado = valorEvaluado;
        this.casos = casos;
    }

    public Expresion getValorEvaluado() {
        return valorEvaluado;
    }

    public void setValorEvaluado(Expresion valorEvaluado) {
        this.valorEvaluado = valorEvaluado;
    }

    public List<CasoSwitch> getCasos() {
        return casos;
    }

    public void setCasos(List<CasoSwitch> casos) {
        this.casos = casos;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

}
