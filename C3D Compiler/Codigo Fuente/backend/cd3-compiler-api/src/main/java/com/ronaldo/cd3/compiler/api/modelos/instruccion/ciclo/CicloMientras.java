package com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CicloMientras extends Ciclo {

    private final Reglas reglas = new Reglas();

    public CicloMientras(List<Instruccion> instruccionesInternas, Expresion condicion, int fila, int columna) {
        super(instruccionesInternas, condicion, fila, columna);
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        reglas.esCondicionValida(contexto, condicion);
        TablaSimbolos anterior = contexto.nuevoAmbito("ciclo_mientras");
        contexto.entrarCiclo();
        reglas.verificarInstrucciones(contexto, instruccionesInternas);
        contexto.salirCiclo();
        contexto.restaurarAmbito(anterior);
    }

}