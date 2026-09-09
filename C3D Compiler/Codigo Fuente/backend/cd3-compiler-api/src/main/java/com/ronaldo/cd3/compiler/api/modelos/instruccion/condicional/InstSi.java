package com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional;

import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class InstSi extends Nodo implements Instruccion {

    private Expresion condicion;
    private List<Instruccion> instruccionesInternasSi;
    private List<RamaSino> ramasSino;
    private List<Instruccion> instruccionesInternasContrario;

    public InstSi(Expresion condicion, List<Instruccion> instruccionesInternasSi,
            List<RamaSino> ramasSino, List<Instruccion> instruccionesInternasContrario,
            int fila, int columna) {

        super(fila, columna);
        this.condicion = condicion;
        this.instruccionesInternasSi = instruccionesInternasSi;
        this.ramasSino = ramasSino;
        this.instruccionesInternasContrario = instruccionesInternasContrario;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public List<Instruccion> getInstruccionesInternasSi() {
        return instruccionesInternasSi;
    }

    public List<RamaSino> getRamasSino() {
        return ramasSino;
    }

    public List<Instruccion> getInstruccionesInternasContrario() {
        return instruccionesInternasContrario;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

}
