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
public class IncrementoDecremento extends Nodo implements Instruccion {

    private final Reglas reglas = new Reglas();
    private final Direccion direccion = new Direccion();
    private Expresion objetivo;
    private Operador operador;

    public IncrementoDecremento(Expresion objetivo, Operador operador, int fila, int columna) {
        super(fila, columna);
        this.objetivo = objetivo;
        this.operador = operador;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (objetivo == null) {
            return;
        }
        objetivo.verificarSemantica(contexto);
        if (!reglas.esLvalue(objetivo)) {
            contexto.agregarError(fila, columna, null,
                    "El objetivo del incremento/decremento no es un valor modificable");
        }
        if (!reglas.esNumerico(objetivo.getTipo())) {
            contexto.agregarError(fila, columna, String.valueOf(operador),
                    "No se puede aplicar '" + operador + "' a un valor no numérico");
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (objetivo == null) {
            return null;
        }
        String direccion = this.direccion.lvalue(objetivo, contexto, cuartetas);
        OperadorCuarteta operadorC = (operador == Operador.DECREMENTO)
                ? OperadorCuarteta.DECREMENTO
                : OperadorCuarteta.INCREMENTO;
        cuartetas.agregar(operadorC, direccion, null,
                direccion, fila, columna);
        return null;
    }

}