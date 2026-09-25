package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

/**
 * Cuarteta que acumula un argumento de una llamada a funcion. Los argumentos
 * se encolan en el contexto hasta que llega la cuarteta LLAMADA que cierra la
 * secuencia.
 */
public class CuartetaParametro extends Cuarteta {

    public CuartetaParametro(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.PARAMETRO, arg1, arg2, resultado, fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        ctx.agregarArgumentoPendiente(getArg1());
    }
}
