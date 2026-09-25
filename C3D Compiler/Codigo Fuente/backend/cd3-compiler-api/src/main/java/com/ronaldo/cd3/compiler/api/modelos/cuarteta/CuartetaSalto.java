package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaSalto extends Cuarteta {

    public CuartetaSalto(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.GOTO, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        ctx.linea(sb, "goto " + getArg1() + ";");
    }
}
