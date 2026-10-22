package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.ir.EmisorIR;

public class CuartetaEtiqueta extends Cuarteta {

    public CuartetaEtiqueta(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.ETIQUETA, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aceptar(EmisorIR emisor) {
        emisor.emitirEtiqueta(etiqueta(emisor, getArg1()));
    }
}
