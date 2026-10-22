package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.ir.EmisorIR;

public class CuartetaAccesoIndice extends Cuarteta {

    public CuartetaAccesoIndice(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.ACCESO_INDICE, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aceptar(EmisorIR emisor) {
        emisor.emitirAccesoIndice(valor(emisor, getArg1()),
                valor(emisor, getArg2()), direccion(emisor, getResultado()));
    }
}
