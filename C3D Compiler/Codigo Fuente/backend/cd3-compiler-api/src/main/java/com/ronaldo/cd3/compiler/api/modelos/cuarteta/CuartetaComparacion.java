package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaComparacion extends Cuarteta {

    public CuartetaComparacion(OperadorCuarteta operador, String arg1,
            String arg2, String resultado, int fila, int columna) {
        super(operador, arg1, arg2, resultado, fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String a1 = ctx.formatearOperando(getArg1());
        String a2 = ctx.formatearOperando(getArg2());
        String res = ctx.formatearOperando(getResultado());

        String op;
        switch (getOperador()) {
            case MENOR_Q:    op = " < "; break;
            case MENOR_EQ_Q: op = " <= "; break;
            case MAYOR_Q:    op = " > "; break;
            case MAYOR_EQ_Q: op = " >= "; break;
            default:         op = " < "; break;
        }
        ctx.linea(sb, res + " = " + a1 + op + a2 + ";");
    }
}
