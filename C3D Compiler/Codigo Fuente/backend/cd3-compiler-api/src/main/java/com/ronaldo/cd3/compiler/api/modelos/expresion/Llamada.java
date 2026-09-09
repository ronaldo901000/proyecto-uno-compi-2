package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Llamada extends Expresion implements Instruccion{

    private Expresion objetivo;        
    private String nombreFuncion;
    private List<Expresion> argumentos;

    public Llamada(Expresion objetivo, String nombreFuncion,
            List<Expresion> argumentos, int fila, int columna) {
        super(fila, columna);
        this.objetivo = objetivo;
        this.nombreFuncion = nombreFuncion;
        this.argumentos = argumentos;
    }

    public Expresion getObjetivo() {
        return objetivo;
    }

    public String getNombreFuncion() {
        return nombreFuncion;
    }

    public List<Expresion> getArgumentos() {
        return argumentos;
    }
}
