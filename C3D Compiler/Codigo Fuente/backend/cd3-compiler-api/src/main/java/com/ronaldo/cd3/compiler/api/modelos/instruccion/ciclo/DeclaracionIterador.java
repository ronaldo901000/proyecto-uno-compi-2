package com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo;

import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public class DeclaracionIterador extends Nodo implements Instruccion {

    private String tipoDato;
    private String id;
    private Expresion valorInicial;

    public DeclaracionIterador(String tipoDato, String id, Expresion valorInicial, int fila, int columna) {
        super(fila, columna);
        this.tipoDato = tipoDato;
        this.id = id;
        this.valorInicial = valorInicial;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expresion getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(Expresion valorInicial) {
        this.valorInicial = valorInicial;
    }

}
