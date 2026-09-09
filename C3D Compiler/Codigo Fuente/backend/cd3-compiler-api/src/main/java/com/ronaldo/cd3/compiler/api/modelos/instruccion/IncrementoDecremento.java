package com.ronaldo.cd3.compiler.api.modelos.instruccion;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public class IncrementoDecremento extends Nodo implements Instruccion {

    private Expresion objetivo;
    private Operador operador;

    public IncrementoDecremento(Expresion objetivo, Operador operador, int fila, int columna) {
        super(fila, columna);
        this.objetivo = objetivo;
        this.operador = operador;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

}
