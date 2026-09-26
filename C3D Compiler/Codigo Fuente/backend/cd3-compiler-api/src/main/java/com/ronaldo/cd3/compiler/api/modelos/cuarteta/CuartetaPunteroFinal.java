package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaPunteroFinal extends Cuarteta {

    public CuartetaPunteroFinal(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.PUNTERO_FINAL, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        
    }
}
