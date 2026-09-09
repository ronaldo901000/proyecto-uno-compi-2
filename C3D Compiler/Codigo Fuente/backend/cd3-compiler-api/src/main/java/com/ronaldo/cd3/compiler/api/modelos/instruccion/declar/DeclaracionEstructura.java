package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class DeclaracionEstructura extends Declaracion {

    private List<Expresion> valoresIniciales;
    private Expresion valorExpresion;

    public DeclaracionEstructura(List<Expresion> valoresIniciales, Expresion valorExpresion, String tipoDato, String id, int fila, int columna) {
        super(tipoDato, id, fila, columna);
        this.valoresIniciales = valoresIniciales;
        this.valorExpresion = valorExpresion;
    }

    public List<Expresion> getValoresIniciales() {
        return valoresIniciales;
    }

    public Expresion getValorExpresion() {
        return valorExpresion;
    }

}
