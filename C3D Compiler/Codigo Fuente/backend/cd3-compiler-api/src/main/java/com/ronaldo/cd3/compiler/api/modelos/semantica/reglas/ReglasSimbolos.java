package com.ronaldo.cd3.compiler.api.modelos.semantica.reglas;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReglasSimbolos {

    private final ReglasTipos tipos;

    public ReglasSimbolos(ReglasTipos tipos) {
        this.tipos = tipos;
    }

    public SimboloVariable registrarVariable(Contexto ctx, String id, Tipo tipo,
            int fila, int columna) {
        TablaSimbolos ambito = ctx.getAmbito();
        if (ambito.existeLocal(id) || ambito.hayFuncion(id)) {
            ctx.agregarError(fila, columna, id,
                    "Ya existe una variable llamada '" + id + "' en este ambito");
            return null;
        }
        SimboloVariable variable = new SimboloVariable(id, tipo, 0);
        ambito.asignarPosicion(variable);
        ambito.agregar(variable);
        return variable;
    }

    public SimboloFuncion resolverFuncion(Contexto ctx, String nombre,
            List<Tipo> tiposArgumentos) {
        List<SimboloFuncion> sobrecargas = ctx.getAmbito().buscarSobrecargas(nombre);
        if (sobrecargas.isEmpty()) {
            return null;
        }
        return resolverEntre(sobrecargas, tiposArgumentos);
    }

    public SimboloFuncion resolverEntre(List<SimboloFuncion> sobrecargas,
            List<Tipo> tiposArgumentos) {
        if (sobrecargas == null || sobrecargas.isEmpty()) {
            return null;
        }
        int numeroArgumentos = (tiposArgumentos != null) ? tiposArgumentos.size() : 0;

        List<SimboloFuncion> mismaArity = new ArrayList<>();
        for (SimboloFuncion sobrecarga : sobrecargas) {
            if (sobrecarga.getParametros().size() == numeroArgumentos) {
                mismaArity.add(sobrecarga);
            }
        }
        if (mismaArity.isEmpty()) {
            return null;
        }
        SimboloFuncion exacta = null;
        SimboloFuncion candidata = null;
        for (SimboloFuncion sobrecarga : mismaArity) {
            boolean coincideExacto = true;
            boolean coincideAsignable = true;
            List<SimboloParametro> parametros = sobrecarga.getParametros();
            for (int i = 0; i < parametros.size(); i++) {
                Tipo tipoParametro = parametros.get(i).getTipo();
                Tipo tipoArgumento = tiposArgumentos.get(i);
                if (!tiposCoincidenExacto(tipoParametro, tipoArgumento)) {
                    coincideExacto = false;
                }
                if (!tipos.esAsignable(tipoParametro, tipoArgumento)) {
                    coincideAsignable = false;
                }
            }
            if (coincideExacto) {
                if (exacta != null) {
                    return null;
                }
                exacta = sobrecarga;
            } else if (coincideAsignable) {
                if (candidata != null) {
                    return null;
                }
                candidata = sobrecarga;
            }
        }
        return (exacta != null) ? exacta : candidata;
    }

    private boolean tiposCoincidenExacto(Tipo parametro, Tipo argumento) {
        return parametro != null && argumento != null && parametro.esIgual(argumento);
    }
}
