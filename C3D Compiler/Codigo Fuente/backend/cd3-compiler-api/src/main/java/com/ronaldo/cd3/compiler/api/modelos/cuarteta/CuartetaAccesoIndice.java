package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaAccesoIndice extends Cuarteta {

    public CuartetaAccesoIndice(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.ACCESO_INDICE, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String a1 = ctx.formatearOperando(getArg1());
        String a2 = ctx.formatearOperando(getArg2());
        String res = ctx.formatearOperando(getResultado());
        String acceso = a1 + "[(int)" + a2 + "]";
        String plano = ctx.aplanarSiPunteroArreglo(acceso);
        
        if (plano.contains("*") && a1.indexOf('[') < 0
                && res.indexOf('[') < 0) {
            int ini = a1.length() + 1;
            int fin = plano.length() - 1;
            if (fin > ini) {
                String idxPlano = plano.substring(ini, fin);
                ctx.linea(sb, res + " = " + a1 + " + " + idxPlano + ";");
                return;
            }
        }
        
        ctx.linea(sb, res + " = " + plano + ";");
    }
}
