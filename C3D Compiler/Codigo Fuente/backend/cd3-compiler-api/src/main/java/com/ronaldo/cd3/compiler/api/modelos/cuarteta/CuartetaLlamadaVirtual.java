package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.ir.EmisorIR;

/**
 * 
 * @author ronaldo
 */
public class CuartetaLlamadaVirtual extends Cuarteta {

    public CuartetaLlamadaVirtual(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.LLAMADA_VIRTUAL, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aceptar(EmisorIR emisor) {
        int slot = 0;
        try {
            slot = Integer.parseInt(getArg2());
        } catch (NumberFormatException ignored) {
            slot = 0;
        }
        emisor.emitirLlamadaVirtual(valor(emisor, getArg1()), slot,
                direccion(emisor, getResultado()));
    }
}
