package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

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

    /**
     * Devuelve la direccion simbolica de una expresion que se usa como lvalue
     * 
     */
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
            return lvalue(indice.getArreglo(), contexto, cuartetas)
                    + "[" + indice.getIndice().generarCuartetas(contexto, cuartetas) + "]";
        }
        return expresion.generarCuartetas(contexto, cuartetas);
    }
}