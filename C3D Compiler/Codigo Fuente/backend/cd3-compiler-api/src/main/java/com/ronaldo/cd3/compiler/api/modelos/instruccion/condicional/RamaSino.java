package com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class RamaSino extends Nodo implements Verificable {

    private final Reglas reglas = new Reglas();
    private Expresion condicion;
    private List<Instruccion> instruccionesInternas;

    public RamaSino(Expresion condicion, List<Instruccion> instruccionesInternas, int fila, int columna) {
        super(fila, columna);
        this.condicion = condicion;
        this.instruccionesInternas = instruccionesInternas;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public List<Instruccion> getInstruccionesInternas() {
        return instruccionesInternas;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        reglas.esCondicionValida(contexto, condicion);
        TablaSimbolos anterior = contexto.nuevoAmbito("sino");
        reglas.verificarInstrucciones(contexto, instruccionesInternas);
        contexto.restaurarAmbito(anterior);
    }

}