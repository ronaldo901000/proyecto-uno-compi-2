package com.ronaldo.cd3.compiler.api.modelos.expresion;

/**
 *
 * @author ronaldo
 */
public class ExpIndice extends Expresion {

    private Expresion arreglo;
    private Expresion indice;

    public ExpIndice(Expresion arreglo, Expresion indice, int fila, int columna) {
        super(fila, columna);
        this.arreglo = arreglo;
        this.indice = indice;
    }

    public Expresion getArreglo() {
        return arreglo;
    }

    public Expresion getIndice() {
        return indice;
    }
}
