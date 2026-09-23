package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
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
        //agregar error a la lista de errores semanticos
        contexto.agregarError(fila, columna, String.valueOf(operador),
                "Tipos incompatibles en la operación '" + operador + "'");
        
        setTipo(tablaTipos.getError());
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        
        //generar operando izquierdo 
        String dirIzquierda = (izquierda != null)
                ? izquierda.generarCuartetas(contexto, cuartetas)
                : null;
        
        //generar operando derecho
        String dirDerecha = (derecha != null)
                ? derecha.generarCuartetas(contexto, cuartetas)
                : null;
        
        //generar operador
        OperadorCuarteta operadorC = operadorDeCuarteta(operador);
        
        //pedir el nombre del temporal
        String idTemporal = cuartetas.nuevoTemporal();
        
        //agregar la nueva cuarteta a la lista
        cuartetas.agregar(operadorC, dirIzquierda, dirDerecha,
                idTemporal, fila, columna);
        
        //registrar el tipo del temporal
        cuartetas.registrarTipoTemporal(idTemporal, getTipo());
        
        //retornar el id del temporal
        return idTemporal;
    }

    
    private OperadorCuarteta operadorDeCuarteta(Operador operador) {
        switch (operador) {
            case SUMA:
                return OperadorCuarteta.SUMA;
            case RESTA:
                return OperadorCuarteta.RESTA;
            case MULTIPLICACION:
                return OperadorCuarteta.MULTIPLICACION;
            case DIVISION:
                return OperadorCuarteta.DIVISION;
            case MODULO:
                return OperadorCuarteta.MODULO;
            case MENOR:
                return OperadorCuarteta.MENOR_Q;
            case MENOR_IGUAL:
                return OperadorCuarteta.MENOR_EQ_Q;
            case MAYOR:
                return OperadorCuarteta.MAYOR_Q;
            case MAYOR_IGUAL:
                return OperadorCuarteta.MAYOR_EQ_Q;
            case IGUAL:
                return OperadorCuarteta.IGUAL;
            case DISTINTO:
                return OperadorCuarteta.DISTINTO;
            case AND:
                return OperadorCuarteta.AND;
            case OR:
                return OperadorCuarteta.OR;
            default:
                return OperadorCuarteta.ASIGNACION;
        }
    }

}