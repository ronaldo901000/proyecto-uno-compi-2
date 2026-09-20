package com.ronaldo.cd3.compiler.api.modelos.instruccion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public class Retorno extends Nodo implements Instruccion {

    private final Reglas reglas = new Reglas();
    private Expresion expresion;

    public Retorno(Expresion expresion, int fila, int columna) {
        super(fila, columna);
        this.expresion = expresion;
    }

    public Expresion getExpresion() {
        return expresion;
    }

    public void setExpresion(Expresion expresion) {
        this.expresion = expresion;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (expresion != null) {
            expresion.verificarSemantica(contexto);
        }
        Tipo tipoRetorno = contexto.getTipoRetornoActual();
        if (tipoRetorno == null) {
            contexto.agregarError(fila, columna, "retornar",
                    "La instrucción 'retornar' solo se puede usar dentro de una función");
            return;
        }
        if (expresion == null) {
            if (!reglas.esVoid(tipoRetorno)) {
                contexto.agregarError(fila, columna, "retornar",
                        "La función debe retornar un valor de tipo " + tipoRetorno);
            }
            return;
        }
        if (!reglas.esAsignable(tipoRetorno, expresion.getTipo())) {
            contexto.agregarError(fila, columna, "retornar",
                    "El valor retornado es incompatible con el tipo de retorno " + tipoRetorno);
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String dirExpresion = (expresion != null)
                ? expresion.generarCuartetas(contexto, cuartetas)
                : null;
        cuartetas.agregar(OperadorCuarteta.RETORNO, dirExpresion,
                null, null, fila, columna);
        return null;
    }

}