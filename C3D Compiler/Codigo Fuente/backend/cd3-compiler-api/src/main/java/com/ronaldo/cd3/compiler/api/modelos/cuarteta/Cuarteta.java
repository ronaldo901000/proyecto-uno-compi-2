package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

/**
 * @author ronaldo
 */
public abstract class Cuarteta {

    private OperadorCuarteta operador;
    private String arg1;
    private String arg2;
    private String resultado;
    private int fila;
    private int columna;

    public Cuarteta(OperadorCuarteta operador, String arg1, String arg2,
            String resultado, int fila, int columna) {
        this.operador = operador;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.resultado = resultado;
        this.fila = fila;
        this.columna = columna;
    }

    public abstract void aCodigoC(StringBuilder sb, ContextoTraduccion ctx);

    public OperadorCuarteta getOperador() {
        return operador;
    }

    public void setOperador(OperadorCuarteta operador) {
        this.operador = operador;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public String toString() {
        return "(" + operador + ", " + arg1 + ", " + arg2 + ", "
                + resultado + ")";
    }
}
