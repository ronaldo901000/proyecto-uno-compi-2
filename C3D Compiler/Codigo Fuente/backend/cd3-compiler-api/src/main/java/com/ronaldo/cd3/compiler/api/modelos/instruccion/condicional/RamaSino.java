package com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional;

import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class RamaSino extends Nodo {

    private Expresion condicion;
    private List<Instruccion> instruccionesInternas;

    public RamaSino(Expresion condicion, List<Instruccion> instruccionesInternas, int fila, int columna) {
        super(fila, columna);
        this.condicion = condicion;
        this.instruccionesInternas = instruccionesInternas;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public List<Instruccion> getInstruccionesInternas() {
        return instruccionesInternas;
    }

}
