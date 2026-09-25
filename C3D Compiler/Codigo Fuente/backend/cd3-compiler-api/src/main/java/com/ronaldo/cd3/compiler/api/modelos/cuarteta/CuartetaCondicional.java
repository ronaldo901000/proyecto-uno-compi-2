package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaCondicional extends Cuarteta {

    public CuartetaCondicional(OperadorCuarteta operador, String arg1,
            String arg2, String resultado, int fila, int columna) {
        super(operador, arg1, arg2, resultado, fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String a1 = ctx.formatearOperando(getArg1());
        if (getOperador() == OperadorCuarteta.IF_FALSO) {
            ctx.linea(sb, "if (!(" + a1 + ")) goto " + getArg2() + ";");
        } else {
            ctx.linea(sb, "if (" + a1 + ") goto " + getArg2() + ";");
        }
    }
}
