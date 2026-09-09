package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;

import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public class Operacion extends Expresion implements Verificable {

    private final Reglas reglas = new Reglas();
    private Expresion izquierda;
    private Expresion derecha;
    private Operador operador;

    public Operacion(Expresion izquierda, Expresion derecha, Operador operador, int fila, int columna) {
        super(fila, columna);
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.operador = operador;
    }

    public Expresion getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Operacion izquierda) {
        this.izquierda = izquierda;
    }

    public Expresion getDerecha() {
        return derecha;
    }

    public void setDerecha(Operacion derecha) {
        this.derecha = derecha;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (izquierda != null) {
            izquierda.verificarSemantica(contexto);
        }
        if (derecha != null) {
            derecha.verificarSemantica(contexto);
        }
        TablaTipos tablaTipos = contexto.getTablaTipos();
        Tipo iz = (izquierda != null) ? izquierda.getTipo() : null;
        Tipo der = (derecha != null) ? derecha.getTipo() : null;
        if (reglas.esError(iz) || reglas.esError(der)) {
            setTipo(tablaTipos.getError());
            return;
        }
        switch (operador) {
            case SUMA:
                if (reglas.esCadena(iz) && reglas.esCompatibleConString(der)) {
                    setTipo(tablaTipos.getCadena());
                    return;
                }
                if (reglas.esNumerico(iz) && reglas.esNumerico(der)) {
                    setTipo(reglas.numeroResultado(tablaTipos, iz, der));
                    return;
                }
                break;
            case RESTA:
            case MULTIPLICACION:
            case DIVISION:
                if (reglas.esNumerico(iz) && reglas.esNumerico(der)) {
                    setTipo(reglas.numeroResultado(tablaTipos, iz, der));
                    return;
                }
                break;
            case MODULO:
                if (iz != null && iz.getTipoDato() == TipoDato.ENTERO
                        && der != null && der.getTipoDato() == TipoDato.ENTERO) {
                    setTipo(tablaTipos.getEntero());
                    return;
                }
                break;
            case MENOR:
            case MENOR_IGUAL:
            case MAYOR:
            case MAYOR_IGUAL:
                if (reglas.esNumerico(iz) && reglas.esNumerico(der)) {
                    setTipo(tablaTipos.getBooleano());
                    return;
                }
                break;
            case IGUAL:
            case DISTINTO:
                if (reglas.comparables(iz, der)) {
                    setTipo(tablaTipos.getBooleano());
                    return;
                }
                break;
            case AND:
            case OR:
                if (reglas.esBooleano(iz) && reglas.esBooleano(der)) {
                    setTipo(tablaTipos.getBooleano());
                    return;
                }
                break;
            default:
                break;
        }
        contexto.agregarError(fila, columna, String.valueOf(operador),
                "Tipos incompatibles en la operación '" + operador + "'");
        setTipo(tablaTipos.getError());
    }

}