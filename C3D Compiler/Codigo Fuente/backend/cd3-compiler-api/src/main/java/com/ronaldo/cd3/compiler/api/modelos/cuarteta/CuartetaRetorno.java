package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

/*
 */
public class CuartetaRetorno extends Cuarteta {

    public CuartetaRetorno(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.RETORNO, arg1, arg2, resultado, fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        if (ctx.terminoConRetorno()) {
            return;
        }
        String dir = ctx.formatearOperando(getArg1());
        if (dir != null) {
            ctx.linea(sb, "return " + dir + ";");
        } else if (ctx.esVoidUnidad()) {
            ctx.linea(sb, "return;");
        } else {
            ctx.linea(sb, "return 0;");
        }
        ctx.setTerminoConRetorno(true);
    }
}
