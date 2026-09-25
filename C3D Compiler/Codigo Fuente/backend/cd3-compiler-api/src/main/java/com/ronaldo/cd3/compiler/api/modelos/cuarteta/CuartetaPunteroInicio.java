package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

public class CuartetaPunteroInicio extends Cuarteta {

    public CuartetaPunteroInicio(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.PUNTERO_INICIO, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String res = ctx.formatearOperando(getResultado());
        if (res != null && ctx.contieneArregloNuevo(getResultado())) {
            return;
        }
        Tipo tipoObjeto = ctx.tipoDeOperando(getResultado());
        String tipoBaseC = (tipoObjeto != null && ctx.tipoCValido(tipoObjeto))
                ? tipoObjeto.tipoC() : "void";
        String asignacion = ctx.esObjetoPorReferencia(tipoObjeto)
                ? "calloc(1, sizeof(" + tipoBaseC + "))"
                : "malloc(sizeof(" + tipoBaseC + "))";
        ctx.linea(sb, res + " = (" + tipoBaseC + "*)" + asignacion + ";");
    }
}
