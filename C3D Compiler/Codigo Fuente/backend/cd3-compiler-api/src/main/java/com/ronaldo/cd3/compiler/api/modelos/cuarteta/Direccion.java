package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Acceso;
import com.ronaldo.cd3.compiler.api.modelos.expresion.AccesoVariable;
import com.ronaldo.cd3.compiler.api.modelos.expresion.ExpIndice;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.LlamadaThis;

/**
 *
 * @author ronaldo
 */
public class Direccion {

    public String lvalue(Expresion expresion, Contexto contexto,
            ListaCuartetas cuartetas) {

        if (expresion instanceof AccesoVariable) {
            return ((AccesoVariable) expresion).getId();
        }

        if (expresion instanceof LlamadaThis) {
            LlamadaThis llamadaThis = (LlamadaThis) expresion;
            if (!llamadaThis.esMetodo() && llamadaThis.getVariable() != null) {
                return "this." + llamadaThis.getVariable().getId();
            }
        }

        if (expresion instanceof Acceso) {
            Acceso acceso = (Acceso) expresion;
            if (llevaIndice(acceso.getObjeto())) {
                return acceso.generarCuartetas(contexto, cuartetas);
            }
            return lvalue(acceso.getObjeto(), contexto, cuartetas)
                    + "." + acceso.getAtributo();
        }

        if (expresion instanceof ExpIndice) {
            ExpIndice indice = (ExpIndice) expresion;

            String dirArreglo = lvalue(indice.getArreglo(), contexto, cuartetas);
            String dirIndice = indice.getIndice().generarCuartetas(contexto, cuartetas);
            String destino = dirArreglo + "[" + dirIndice + "]";

            cuartetas.agregar(OperadorCuarteta.ACCESO_INDICE, dirArreglo,
                    dirIndice, destino, indice.getFila(), indice.getColumna());

            return destino;
        }

        return expresion.generarCuartetas(contexto, cuartetas);
    }

    private boolean llevaIndice(Expresion expresion) {
        if (expresion instanceof ExpIndice) {
            return true;
        }
        if (expresion instanceof Acceso) {
            return llevaIndice(((Acceso) expresion).getObjeto());
        }
        return false;
    }
}
