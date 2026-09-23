package com.ronaldo.cd3.compiler.api.modelos.instruccion;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Direccion;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;

/**
 *
 * @author ronaldo
 */
public class Asignacion extends Nodo implements Instruccion {

    private final Reglas reglas = new Reglas();
    private final Direccion direccion = new Direccion();
    private Expresion objetivo;
    private Expresion valor;
    private Operador operador;

    /*constructor para el lenguaje Y*/
    public Asignacion(Expresion objetivo, Expresion valor, int fila, int columna) {
        super(fila, columna);
        this.objetivo = objetivo;
        this.valor = valor;
    }

    /*constructor para el lenguaje Z: == *= -=  */
    public Asignacion(Expresion objetivo, Expresion valor, Operador operador, int fila, int columna) {
        super(fila, columna);
        this.objetivo = objetivo;
        this.valor = valor;
        this.operador = operador;
    }

    public Expresion getObjetivo() {
        return objetivo;
    }

    public Expresion getValor() {
        return valor;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (objetivo != null) {
            objetivo.verificarSemantica(contexto);
        }
        if (valor != null) {
            valor.verificarSemantica(contexto);
        }
        if (objetivo == null || valor == null) {
            return;
        }
        if (!reglas.esLvalue(objetivo)) {
            contexto.agregarError(fila, columna, null,
                    "No se puede asignar un valor aqui: "
                    + "el lado izquierdo debe ser una variable, "
                    + "un campo o una posición de arreglo"
            );
        }
        if (!reglas.esAsignable(objetivo.getTipo(), valor.getTipo())) {
            contexto.agregarError(fila, columna, null,
                    "No se puede asignar un valor de tipo "
                    + "'" + valor.getTipo()
                    + "' a una variable de tipo '"
                    + objetivo.getTipo() + "'"
            );
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (objetivo == null || valor == null) {
            return null;
        }
        String dirValor = valor.generarCuartetas(contexto, cuartetas);
        String dirObjetivo = direccion.lvalue(objetivo, contexto, cuartetas);

        if (operador == Operador.MAS_IGUAL) {
            temporalAsignacionCompuesta(cuartetas,
                    dirObjetivo, dirValor, OperadorCuarteta.SUMA);

        } else if (operador == Operador.MENOS_IGUAL) {
            temporalAsignacionCompuesta(cuartetas,
                    dirObjetivo, dirValor, OperadorCuarteta.RESTA);

        } else if (operador == Operador.MULTI_IGUAL) {
            temporalAsignacionCompuesta(cuartetas,
                    dirObjetivo, dirValor, OperadorCuarteta.MULTIPLICACION);

        } else {
            cuartetas.agregar(OperadorCuarteta.ASIGNACION, dirValor,
                    null, dirObjetivo, fila, columna);

        }
        return null;
    }

    private void temporalAsignacionCompuesta(ListaCuartetas cuartetas,
            String dirObjetivo, String dirValor, OperadorCuarteta operadorC) {

        String temporal = cuartetas.nuevoTemporal();

        cuartetas.agregar(operadorC, dirObjetivo, dirValor,
                temporal, fila, columna);

        cuartetas.agregar(OperadorCuarteta.ASIGNACION, temporal,
                null, dirObjetivo, fila, columna);

    }
}
