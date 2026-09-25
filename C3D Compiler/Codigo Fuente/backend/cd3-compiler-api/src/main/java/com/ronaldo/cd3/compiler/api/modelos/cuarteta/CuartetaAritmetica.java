package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;

public class CuartetaAritmetica extends Cuarteta {

    public CuartetaAritmetica(OperadorCuarteta operador, String arg1,
            String arg2, String resultado, int fila, int columna) {
        super(operador, arg1, arg2, resultado, fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String a1 = ctx.formatearOperando(getArg1());
        String a2 = ctx.formatearOperando(getArg2());
        String res = ctx.formatearOperando(getResultado());

        if (getOperador() == OperadorCuarteta.SUMA
                && res != null
                && ctx.tipoDeOperando(getResultado()) != null
                && ctx.esDeTipo(ctx.tipoDeOperando(getResultado()),
                        com.ronaldo.cd3.compiler.api.enums.TipoDato.CADENA)) {
            ctx.linea(sb, res + " = cad_concat("
                    + ctx.operandoParaCadena(a1) + ", "
                    + ctx.operandoParaCadena(a2) + ");");
            return;
        }

        String op;
        switch (getOperador()) {
            case SUMA:    op = " + "; break;
            case RESTA:   op = " - "; break;
            case MULTIPLICACION: op = " * "; break;
            case DIVISION: op = " / "; break;
            case MODULO:  op = " % "; break;
            default:      op = " + "; break;
        }

        if (getOperador() == OperadorCuarteta.MODULO
                && ctx.esEntero(a1) && ctx.esEntero(a2)) {
            ctx.linea(sb, res + " = " + a1 + " % " + a2 + ";");
        } else if (getOperador() == OperadorCuarteta.MODULO) {
            ctx.linea(sb, res + " = fmod(" + a1 + ", " + a2 + ");");
        } else {
            ctx.linea(sb, res + " = " + a1 + op + a2 + ";");
        }
    }
}
