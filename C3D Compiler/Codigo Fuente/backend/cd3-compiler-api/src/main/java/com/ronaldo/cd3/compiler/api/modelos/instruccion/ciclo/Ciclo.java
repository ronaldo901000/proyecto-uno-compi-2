package com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo;

import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public abstract class Ciclo extends Nodo implements Instruccion {

    protected List<Instruccion> instruccionesInternas;
    protected Expresion condicion;

    public Ciclo(List<Instruccion> instruccionesInternas, Expresion condicion, int fila, int columna) {
        super(fila, columna);
        this.instruccionesInternas = instruccionesInternas;
        this.condicion = condicion;
    }

    public List<Instruccion> getInstruccionesInternas() {
        return instruccionesInternas;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public void setCondicion(Expresion condicion) {
        this.condicion = condicion;
    }

    public void setInstruccionesInternas(List<Instruccion> instruccionesInternas) {
        this.instruccionesInternas = instruccionesInternas;
    }

}
