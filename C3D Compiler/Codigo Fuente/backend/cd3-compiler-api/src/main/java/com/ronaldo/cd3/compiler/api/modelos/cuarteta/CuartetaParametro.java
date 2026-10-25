package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.ir.EmisorIR;

/**
 * 
 * @author ronaldo
 */
public class CuartetaParametro extends Cuarteta {

    public CuartetaParametro(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.PARAMETRO, arg1, arg2, resultado, fila, columna);
    }

    @Override
    public void aceptar(EmisorIR emisor) {
        emisor.emitirParametro(valor(emisor, getArg1()));
    }
}
