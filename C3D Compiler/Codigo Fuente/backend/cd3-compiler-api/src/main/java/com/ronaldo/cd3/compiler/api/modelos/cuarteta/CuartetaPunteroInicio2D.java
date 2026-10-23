package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.ir.EmisorIR;

public class CuartetaPunteroInicio2D extends Cuarteta {

    public CuartetaPunteroInicio2D(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.PUNTERO_INICIO_2D, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aceptar(EmisorIR emisor) {
        emisor.emitirNuevoArreglo2D(direccion(emisor, getResultado()),
                valor(emisor, getArg1()), valor(emisor, getArg2()));
    }
}
