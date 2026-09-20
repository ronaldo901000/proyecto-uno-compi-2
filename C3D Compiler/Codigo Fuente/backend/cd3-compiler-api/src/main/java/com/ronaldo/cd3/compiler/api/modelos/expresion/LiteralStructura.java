package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class LiteralStructura extends Expresion implements Verificable {

    private List<Expresion> valores;

    public LiteralStructura(List<Expresion> valores, int fila, int columna) {
        super(fila, columna);
        this.valores = valores;
    }

    public List<Expresion> getValores() {
        return valores;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (valores != null) {
            for (Expresion valor : valores) {
                valor.verificarSemantica(contexto);
            }
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String temporalPtr = cuartetas.nuevoTemporal();
        cuartetas.agregar(OperadorCuarteta.PUNTERO_INICIO, null,
                null, temporalPtr, fila, columna);
        if (valores != null) {
            for (Expresion valor : valores) {
                String dirValor = valor.generarCuartetas(contexto, cuartetas);
                cuartetas.agregar(OperadorCuarteta.ASIGNACION, dirValor,
                        null, temporalPtr, fila, columna);
            }
        }
        return temporalPtr;
    }
}