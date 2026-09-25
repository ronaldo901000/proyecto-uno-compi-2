package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.List;

/**
 * Cuarteta que cierra una llamada a funcion: ensambla la invocacion con los
 * argumentos acumulados por las cuartetas PARAMETRO anteriores y la asigna al
 * temporal receptor, o la emite sola si la funcion retorna void.
 */
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
            for (int i = 0; i < args.size()
                    && paramIdx < params.size(); i++) {
                SimboloParametro param = params.get(paramIdx);
                if (param.getTipo() instanceof TipoStructura
                        && !ctx.esObjetoPorReferencia(
                                param.getTipo())) {
                    args.set(i, "&" + args.get(i));
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
}
