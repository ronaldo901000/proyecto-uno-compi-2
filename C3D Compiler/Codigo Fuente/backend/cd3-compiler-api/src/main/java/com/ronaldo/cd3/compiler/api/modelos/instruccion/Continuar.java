package com.ronaldo.cd3.compiler.api.modelos.instruccion;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public class Continuar extends Nodo implements Instruccion {

    public Continuar(int fila, int columna) {
        super(fila, columna);
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (!contexto.dentroCiclo()) {
            contexto.agregarError(fila, columna, "continuar",
                    "La instrucción 'continuar' solo se puede usar dentro de un ciclo");
        }
    }

}