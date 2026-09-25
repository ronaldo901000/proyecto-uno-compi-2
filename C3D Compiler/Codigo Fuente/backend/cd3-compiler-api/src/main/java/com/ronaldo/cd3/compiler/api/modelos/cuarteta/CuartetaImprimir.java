package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

public class CuartetaImprimir extends Cuarteta {

    public CuartetaImprimir(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.IMPRIMIR, arg1, arg2, resultado,
                fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String a1 = ctx.formatearOperando(getArg1());
        boolean conSalto = "true".equalsIgnoreCase(getResultado());
        String salto = conSalto ? "\\n" : "";

        if (a1 == null) {
            ctx.linea(sb, "printf(\"" + salto + "\");");
            return;
        }

        boolean esCadena = ctx.esCategoria(a1,
                TipoOperando.CONSTANTE_CADENA);
        Tipo tipo = ctx.tipoDeOperando(a1);
        if (tipo == null) {
            tipo = ctx.tipoDeOperando(getArg1());
        }
        String formato;
        if (esCadena || ctx.esDeTipo(tipo, TipoDato.CADENA)) {
            formato = "%s";
        } else if (ctx.esDeTipo(tipo, TipoDato.CHAR)) {
            formato = "%c";
        } else if (ctx.esDeTipo(tipo, TipoDato.ENTERO)
                || ctx.esDeTipo(tipo, TipoDato.BOOLEAN)) {
            formato = "%d";
        } else {
            formato = "%g";
        }
        ctx.linea(sb, "printf(\"" + formato + salto + "\", " + a1 + ");");
    }
}
