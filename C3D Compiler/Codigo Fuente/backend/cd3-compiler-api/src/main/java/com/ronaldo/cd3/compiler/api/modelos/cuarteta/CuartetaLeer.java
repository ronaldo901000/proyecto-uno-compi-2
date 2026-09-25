package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

public class CuartetaLeer extends Cuarteta {

    public CuartetaLeer(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.LEER, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String res = ctx.formatearOperando(getResultado());
        if (res == null) {
            ctx.linea(sb, ";");
            return;
        }
        Tipo tipo = ctx.tipoDeVariableDe(res);
        if (ctx.esDeTipo(tipo, TipoDato.DECIMAL)) {
            ctx.linea(sb, "scanf(\"%lf\", &" + res + ");");
        } else if (ctx.esDeTipo(tipo, TipoDato.CHAR)) {
            ctx.linea(sb, "scanf(\" %c\", &" + res + ");");
        } else if (ctx.esDeTipo(tipo, TipoDato.CADENA)) {
            ctx.linea(sb, "scanf(\"%s\", " + res + ");");
        } else {
            ctx.linea(sb, "scanf(\"%d\", &" + res + ");");
        }
    }
}
