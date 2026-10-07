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
        return resolverSobrecarga(sobrecargas, tiposArgumentos);
    }

    /**
     * METODO QUE SE ENCARGA DE VERIFICAR LA LISTA DE FUNCIONES CANDIDATAS, CON
     * LOS PARAMETROS DADOS
     *
     * @param candidatas
     * @param tiposArgumentos
     * @return
     */
    public SimboloFuncion resolverSobrecarga(List<SimboloFuncion> candidatas,
            List<Tipo> tiposArgumentos) {

        if (candidatas == null || candidatas.isEmpty()) {
            return null;
        }

        List<SimboloFuncion> conMismaCantidad
                = filtrarPorCantidadDeParametros(candidatas, tiposArgumentos);

        SimboloFuncion coincidenciaExacta = null;
        List<SimboloFuncion> porConversion = new ArrayList<>();

        for (SimboloFuncion sobrecarga : conMismaCantidad) {
            if (coincideExactamente(sobrecarga, tiposArgumentos)) {
                if (coincidenciaExacta != null) {
                    return null;
                }
                coincidenciaExacta = sobrecarga;
            } else if (coincideConConversion(sobrecarga, tiposArgumentos)) {
                porConversion.add(sobrecarga);
            }
        }

        if (coincidenciaExacta != null) {
            return coincidenciaExacta;
        }
        return elegirMasEspecifica(porConversion);
    }

    /**
     * COMPARA UNA CANDIDATA CON LAS DEMAS CANDIDATAS
     * @param candidatas
     * @return 
     */
    private SimboloFuncion elegirMasEspecifica(List<SimboloFuncion> candidatas) {
        for (SimboloFuncion candidata : candidatas) {
            boolean ganaATodas = true;
            for (SimboloFuncion otra : candidatas) {
                if (candidata != otra && !esMasEspecifica(candidata, otra)) {
                    ganaATodas = false;
                    break;
                }
            }
            if (ganaATodas) {
                return candidata;
            }
        }
        return null;
    }

    /**
     * RETORNA VERDADERO SOLO SI LA CANDIDATA ES MAS ESPECIFICA QUE OTRA CANDIDATA
     */
    private boolean esMasEspecifica(SimboloFuncion candidata, SimboloFuncion otra) {
        for (int i = 0; i < candidata.getParametros().size(); i++) {
            Tipo tipoCandidata = candidata.getParametros().get(i).getTipo();
            Tipo tipoOtra = otra.getParametros().get(i).getTipo();
            if (!tipos.esAsignable(tipoOtra, tipoCandidata)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deja solo las sobrecargas que reciben tantos parametros como argumentos
     * se pasaron.
     */
    private List<SimboloFuncion> filtrarPorCantidadDeParametros(
            List<SimboloFuncion> sobrecargas, List<Tipo> tiposArgumentos) {
        int cantidadArgumentos = (tiposArgumentos != null) ? tiposArgumentos.size() : 0;

        List<SimboloFuncion> resultado = new ArrayList<>();
        for (SimboloFuncion sobrecarga : sobrecargas) {
            if (sobrecarga.getParametros().size() == cantidadArgumentos) {
                resultado.add(sobrecarga);
            }
        }
        return resultado;
    }

    /**
     * true si cada argumento tiene exactamente el mismo tipo que su parametro.
     */
    private boolean coincideExactamente(SimboloFuncion sobrecarga, List<Tipo> tiposArgumentos) {
        List<SimboloParametro> parametros = sobrecarga.getParametros();
        for (int i = 0; i < parametros.size(); i++) {
            if (!tiposCoincidenExacto(parametros.get(i).getTipo(), tiposArgumentos.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * true si cada argumento se puede asignar a su parametro (ej. entero ->
     * decimal).
     */
    private boolean coincideConConversion(SimboloFuncion sobrecarga, List<Tipo> tiposArgumentos) {
        List<SimboloParametro> parametros = sobrecarga.getParametros();
        for (int i = 0; i < parametros.size(); i++) {
            if (!tipos.esAsignable(parametros.get(i).getTipo(), tiposArgumentos.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean tiposCoincidenExacto(Tipo parametro, Tipo argumento) {
        return parametro != null && argumento != null && parametro.esIgual(argumento);
    }
}
