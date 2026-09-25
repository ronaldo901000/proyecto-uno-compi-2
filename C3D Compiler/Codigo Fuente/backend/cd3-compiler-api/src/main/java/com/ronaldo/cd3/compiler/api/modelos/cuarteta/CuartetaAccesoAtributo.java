package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaAccesoAtributo extends Cuarteta {

    public CuartetaAccesoAtributo(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.ACCESO_ATRIBUTO, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String a1 = ctx.formatearOperando(getArg1());
        String a2 = ctx.formatearOperando(getArg2());
        String res = ctx.formatearOperando(getResultado());

        if (a1 != null && a2 != null && a1.indexOf('[') < 0) {
            ctx.linea(sb, res + " = " + a1 + "_" + a2 + ";");
        } else {
            ctx.linea(sb, (a1 != null)
                    ? (res + " = " + a1 + ";")
                    : (res + " = 0;"));
        }
    }
}
