package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.ir.EmisorIR;

public class CuartetaSalto extends Cuarteta {

    public CuartetaSalto(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.GOTO, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aceptar(EmisorIR emisor) {
        emisor.emitirSalto(etiqueta(emisor, getArg1()));
    }
}
