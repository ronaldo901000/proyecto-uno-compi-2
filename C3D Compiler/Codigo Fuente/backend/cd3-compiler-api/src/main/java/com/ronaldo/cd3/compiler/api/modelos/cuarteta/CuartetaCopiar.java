package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaCopiar extends Cuarteta {

    public CuartetaCopiar(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.COPIAR, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String a1 = ctx.formatearOperando(getArg1());
        String res = ctx.formatearOperando(getResultado());
        ctx.linea(sb, "memcpy(" + res + ", " + a1
                + ", sizeof(" + res + "));");
    }
}
