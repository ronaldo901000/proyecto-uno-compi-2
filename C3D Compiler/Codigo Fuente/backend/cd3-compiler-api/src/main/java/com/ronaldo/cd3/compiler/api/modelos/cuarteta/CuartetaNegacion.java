package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaNegacion extends Cuarteta {

    public CuartetaNegacion(OperadorCuarteta operador, String arg1,
            String arg2, String resultado, int fila, int columna) {
        super(operador, arg1, arg2, resultado, fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String a1 = ctx.formatearOperando(getArg1());
        String res = ctx.formatearOperando(getResultado());

        String op = (getOperador() == OperadorCuarteta.NOT) ? "!" : "-";
        ctx.linea(sb, res + " = " + op + a1 + ";");
    }
}
