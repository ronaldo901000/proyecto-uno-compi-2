package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaEtiqueta extends Cuarteta {

    public CuartetaEtiqueta(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.ETIQUETA, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        ctx.linea(sb, getArg1() + ":;");
    }
}
