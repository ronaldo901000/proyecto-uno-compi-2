package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;

public class CuartetaAsignacion extends Cuarteta {

    public CuartetaAsignacion(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.ASIGNACION, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String res = ctx.formatearOperando(getResultado());
        if (res == null) {
            return;
        }
        if (ctx.esNuevoArregloHaciaVariable(getArg1(), getResultado())) {
            String cantidad = calcularCantidadTotal(getArg1(), ctx);
            if (cantidad == null) {
                cantidad = "100";
            }
            String elemento = ctx.tipoCDeElementoArreglo(getResultado());
            ctx.linea(sb, res + " = (" + elemento + "*)calloc("
                    + cantidad + ", sizeof(" + elemento + "));");
            return;
        }
        String a1 = ctx.formatearOperando(getArg1());
        if (a1 != null && esAsignacionArreglo(res, a1, ctx)) {
            ctx.linea(sb, "memcpy(" + res + ", " + a1
                    + ", sizeof(" + res + "));");
            return;
        }
        ctx.linea(sb, (a1 != null)
                ? (res + " = " + a1 + ";")
                : (res + " = 0;"));
    }

    private String calcularCantidadTotal(String origen, ContextoTraduccion ctx) {
        Tipo tipo = ctx.tipoDeTemporal(origen);
        if (tipo instanceof TipoArreglo) {
            TipoArreglo arr = (TipoArreglo) tipo;
            int total = 1;
            for (int d : arr.getDimensiones()) {
                total *= d;
            }
            return String.valueOf(total);
        }
        return ctx.arregloNuevoCantidad(origen);
    }

    private boolean esAsignacionArreglo(String res, String a1,
            ContextoTraduccion ctx) {
        String raizArg1 = raizSinIndices(getArg1());
        return ctx.esArreglo(raizArg1);
    }

    private String raizSinIndices(String nombre) {
        int corchete = nombre.indexOf('[');
        return (corchete >= 0) ? nombre.substring(0, corchete) : nombre;
    }
}
