package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.ir.EmisorIR;

public class CuartetaCondicional extends Cuarteta {

    public CuartetaCondicional(OperadorCuarteta operador, String arg1,
            String arg2, String resultado, int fila, int columna) {
        super(operador, arg1, arg2, resultado, fila, columna);
    }

    @Override
    public void aceptar(EmisorIR emisor) {
        emisor.emitirCondicional(getOperador(), valor(emisor, getArg1()),
                etiqueta(emisor, getArg2()));
    }
}
