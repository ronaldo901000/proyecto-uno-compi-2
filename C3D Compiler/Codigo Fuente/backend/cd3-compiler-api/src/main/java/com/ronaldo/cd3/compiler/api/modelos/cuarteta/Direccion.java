package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Acceso;
import com.ronaldo.cd3.compiler.api.modelos.expresion.AccesoVariable;
import com.ronaldo.cd3.compiler.api.modelos.expresion.ExpIndice;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;

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

        if (expresion instanceof Acceso) {
            Acceso acceso = (Acceso) expresion;
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
}
