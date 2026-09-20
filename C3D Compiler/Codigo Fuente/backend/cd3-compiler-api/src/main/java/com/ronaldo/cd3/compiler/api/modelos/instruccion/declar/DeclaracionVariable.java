package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public class DeclaracionVariable extends Declaracion {

    private final Reglas reglas = new Reglas();
    private Expresion valorInicial;

    public DeclaracionVariable(Expresion valorInicial, String tipoDato, String id, int fila, int columna) {
        super(tipoDato, id, fila, columna);
        this.valorInicial = valorInicial;
    }

    public Expresion getValorInicial() {
        return valorInicial;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (valorInicial != null) {
            valorInicial.verificarSemantica(contexto);
        }
        Tipo tipo;
        if (tipoDato != null) {
            tipo = reglas.resolverTipo(contexto, tipoDato, fila, columna);
        } else {
            tipo = (valorInicial != null) ? valorInicial.getTipo() : null;
            if (tipo == null) {
                contexto.agregarError(fila, columna, id,
                        "No se puede inferir el tipo de '" + id + "'");
                return;
            }
        }
        if (reglas.esError(tipo)) {
            return;
        }
        SimboloVariable variable = reglas.registrarVariable(contexto, id, tipo, fila, columna);
        if (variable != null && valorInicial != null
                && !reglas.esAsignable(tipo, valorInicial.getTipo())) {
            contexto.agregarError(fila, columna, id,
                    "El valor inicial de '" + id + "' es incompatible con su tipo");
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        Tipo tipo = (tipoDato != null)
                ? reglas.resolverTipo(contexto, tipoDato, fila, columna)
                : ((valorInicial != null) ? valorInicial.getTipo() : null);
        cuartetas.registrarTipoVariable(id, tipo);
        if (tipo instanceof com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo) {
            cuartetas.registrarTipoArreglo(id,
                    ((com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo) tipo).getTipoBase());
        }
        if (valorInicial != null) {
            String dirValor = valorInicial.generarCuartetas(contexto, cuartetas);
            cuartetas.agregar(OperadorCuarteta.ASIGNACION, dirValor,
                    null, id, fila, columna);
        }
        return null;
    }

}