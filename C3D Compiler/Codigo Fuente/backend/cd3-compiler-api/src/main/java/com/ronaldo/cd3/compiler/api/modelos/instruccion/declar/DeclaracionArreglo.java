package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class DeclaracionArreglo extends Declaracion {

    private List<Expresion> dimensiones;
    private List<Expresion> valoresIniciales;

    public DeclaracionArreglo(List<Expresion> dimensiones, List<Expresion> valoresIniciales, String tipoDato, String id, int fila, int columna) {
        super(tipoDato, id, fila, columna);
        this.dimensiones = dimensiones;
        this.valoresIniciales = valoresIniciales;
    }

    public List<Expresion> getDimensiones() {
        return dimensiones;
    }

    public List<Expresion> getValoresIniciales() {
        return valoresIniciales;
    }

}
