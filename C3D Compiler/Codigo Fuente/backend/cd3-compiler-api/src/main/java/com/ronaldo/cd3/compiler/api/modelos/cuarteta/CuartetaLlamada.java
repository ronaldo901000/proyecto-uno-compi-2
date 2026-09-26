package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.List;


public class CuartetaLlamada extends Cuarteta {

    public CuartetaLlamada(String arg1, String arg2, String resultado,
            int fila, int columna) {
        super(OperadorCuarteta.LLAMADA, arg1, arg2, resultado, fila, columna);
    }

    @Override
    public void aCodigoC(StringBuilder sb, ContextoTraduccion ctx) {
        String res = ctx.formatearOperando(getResultado());
        List<String> args = new ArrayList<>(
                ctx.getArgumentosPendientes());
        List<SimboloParametro> params = ctx.getCuartetas()
                .parametrosDeFuncion(getArg1());
        if (params != null && !params.isEmpty()) {
            int paramIdx = 0;
            String clase = ctx.claseDeUnidad(getArg1());
            if (clase != null) {
                paramIdx = 1;
            }
            List<SimboloParametro> paramsLlamador = ctx.getCuartetas()
                    .parametrosDeFuncion(ctx.getUnidadActual());
            for (int i = 0; i < args.size()
                    && paramIdx < params.size(); i++) {
                SimboloParametro param = params.get(paramIdx);
                if (param.getTipo() instanceof TipoStructura
                        && !ctx.esObjetoPorReferencia(
                                param.getTipo())) {
                    String argRaiz = extraerRaiz(args.get(i));
                    if (!yaEsPuntero(argRaiz, paramsLlamador)) {
                        args.set(i, "&" + args.get(i));
                    }
                }
                paramIdx++;
            }
        }
        String llamada = getArg1() + "("
                + String.join(", ", args) + ")";
        ctx.limpiarArgumentosPendientes();
        Tipo tipoFuncion = ctx.tipoDeFuncionDe(getArg1());
        boolean esLlamadaVoid = ctx.esTipoVoid(tipoFuncion)
                || tipoFuncion == null;
        if (res != null && !esLlamadaVoid) {
            ctx.linea(sb, res + " = " + llamada + ";");
        } else {
            ctx.linea(sb, llamada + ";");
        }
    }

    private String extraerRaiz(String operando) {
        if (operando == null) {
            return null;
        }
        String raiz = operando;
        if (raiz.startsWith("&")) {
            raiz = raiz.substring(1);
        }
        int bracket = raiz.indexOf('[');
        if (bracket >= 0) {
            raiz = raiz.substring(0, bracket);
        }
        int dot = raiz.indexOf('.');
        if (dot >= 0) {
            raiz = raiz.substring(0, dot);
        }
        int arrow = raiz.indexOf('>');
        if (arrow >= 0 && arrow > 0 && raiz.charAt(arrow - 1) == '-') {
            raiz = raiz.substring(0, arrow - 1);
        }
        return raiz;
    }

    private boolean yaEsPuntero(String argRaiz,
            List<SimboloParametro> paramsLlamador) {
        if (paramsLlamador == null || argRaiz == null) {
            return false;
        }
        for (SimboloParametro p : paramsLlamador) {
            if (argRaiz.equals(p.getId())
                    && p.getTipo() instanceof TipoStructura) {
                return true;
            }
        }
        return false;
    }
}
