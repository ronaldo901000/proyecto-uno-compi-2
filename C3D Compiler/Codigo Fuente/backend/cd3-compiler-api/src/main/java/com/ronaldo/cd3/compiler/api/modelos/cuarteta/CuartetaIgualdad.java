package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaIgualdad extends Cuarteta {

    public CuartetaIgualdad(OperadorCuarteta operador, String arg1,
            String arg2, String resultado, int fila, int columna) {
        super(operador, arg1, arg2, resultado, fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String a1 = ctx.formatearOperando(getArg1());
        String a2 = ctx.formatearOperando(getArg2());
        String res = ctx.formatearOperando(getResultado());

        if (ctx.usaComparacionCadenasPara(a1, a2)) {
            String cadOp = (getOperador() == OperadorCuarteta.IGUAL)
                    ? "cad_igual" : "cad_distinto";
            ctx.linea(sb, res + " = " + cadOp + "(" + a1 + ", " + a2 + ");");
            return;
        }

        String op = (getOperador() == OperadorCuarteta.IGUAL)
                ? " == " : " != ";
        ctx.linea(sb, res + " = " + a1 + op + a2 + ";");
    }
}
