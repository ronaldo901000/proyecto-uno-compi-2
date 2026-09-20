package com.ronaldo.cd3.compiler.api.modelos.instruccion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
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

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String etiqueta = contexto.getEtiquetaContinuarActual();
        if (etiqueta == null) {
            etiqueta = "L_con";
        }
        cuartetas.agregar(OperadorCuarteta.GOTO, etiqueta,
                null, null, fila, columna);
        return null;
    }

}