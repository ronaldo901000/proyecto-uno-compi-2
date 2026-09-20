package com.ronaldo.cd3.compiler.api.modelos.instruccion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public class Romper extends Nodo implements Instruccion {

    public Romper(int fila, int columna) {
        super(fila, columna);
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (!contexto.dentroCiclo() && !contexto.dentroDeSwitch()) {
            contexto.agregarError(fila, columna, "romper",
                    "La instrucción 'romper/break' solo se puede usar dentro de un ciclo o un switch");
        }

    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String etiqueta = contexto.getEtiquetaRomperActual();
        if (etiqueta == null) {
            etiqueta = "L_fin";
        }
        cuartetas.agregar(OperadorCuarteta.GOTO, etiqueta,
                null, null, fila, columna);
        return null;
    }

}
