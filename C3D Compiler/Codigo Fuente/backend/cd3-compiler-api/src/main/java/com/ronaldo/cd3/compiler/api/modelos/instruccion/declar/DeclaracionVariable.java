package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
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
        Tipo tipo = reglas.resolverTipo(contexto, tipoDato, fila, columna);
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

}