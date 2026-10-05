package com.ronaldo.cd3.compiler.api.modelos.semantica.reglas;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Acceso;
import com.ronaldo.cd3.compiler.api.modelos.expresion.AccesoVariable;
import com.ronaldo.cd3.compiler.api.modelos.expresion.ExpIndice;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Literal;
import com.ronaldo.cd3.compiler.api.modelos.expresion.LlamadaThis;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Operacion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Unario;

/**
 *
 * @author ronaldo
 */
public class ReglasExpresiones {

    private final ReglasTipos tipos;

    public ReglasExpresiones(ReglasTipos tipos) {
        this.tipos = tipos;
    }

    public Integer constanteEntera(Expresion expresion) {
        if (expresion == null) {
            return null;
        }
        if (expresion instanceof Literal) {
            Object contenido = ((Literal) expresion).getContenido();
            if (contenido == null) {
                return null;
            }
            String texto;
            if (contenido instanceof org.antlr.v4.runtime.Token) {
                texto = ((org.antlr.v4.runtime.Token) contenido).getText();
            } else {
                texto = String.valueOf(contenido);
            }
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        if (expresion instanceof Unario) {
            Unario unario = (Unario) expresion;
            Integer valor = constanteEntera(unario.getExp());
            if (valor == null) {
                return null;
            }
            if (unario.getOperador() == Operador.NEGATIVO_UNARIO) {
                return -valor;
            }
            if (unario.getOperador() == Operador.POSITIVO_UNARIO) {
                return valor;
            }
            return null;
        }
        if (expresion instanceof Operacion) {
            Operacion operacion = (Operacion) expresion;
            Integer izq = constanteEntera(operacion.getIzquierda());
            Integer der = constanteEntera(operacion.getDerecha());
            if (izq == null || der == null) {
                return null;
            }
            switch (operacion.getOperador()) {
                case SUMA:
                    return izq + der;
                case RESTA:
                    return izq - der;
                case MULTIPLICACION:
                    return izq * der;
                case DIVISION:
                    return der != 0 ? izq / der : null;
                case MODULO:
                    return der != 0 ? izq % der : null;
                default:
                    return null;
            }
        }
        return null;
    }

    public boolean esCondicionValida(Contexto ctx, Expresion condicion) {
        if (condicion == null) {
            return false;
        }
        condicion.verificarSemantica(ctx);
        if (!tipos.esBooleano(condicion.getTipo())) {
            ctx.agregarError(condicion.getFila(), condicion.getColumna(), null,
                    "La condicion debe ser de tipo booleano");
            return false;
        }
        return true;
    }

    public boolean esLvalue(Expresion expresion) {
        return expresion instanceof AccesoVariable
                || expresion instanceof Acceso
                || expresion instanceof ExpIndice
                || expresion instanceof LlamadaThis;
    }

}
