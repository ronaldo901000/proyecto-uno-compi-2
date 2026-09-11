package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;

/**
 *
 * @author ronaldo
 */
public class Lectura extends Expresion implements Instruccion {

    private Expresion argumento;

    public Lectura(int fila, int columna) {
        super(fila, columna);
    }

    public Lectura(Expresion argumento, int fila, int columna) {
        super(fila, columna);
        this.argumento = argumento;
    }

    public Expresion getArgumento() {
        return argumento;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        setTipo(contexto.getTablaTipos().getCadena());
    }

}