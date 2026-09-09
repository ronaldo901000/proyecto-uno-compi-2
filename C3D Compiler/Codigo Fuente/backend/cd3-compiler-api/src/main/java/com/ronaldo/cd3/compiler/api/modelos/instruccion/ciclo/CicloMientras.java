package com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo;

import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.Ciclo;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CicloMientras extends Ciclo {

    public CicloMientras(List<Instruccion> instruccionesInternas, Expresion condicion, int fila, int columna) {
        super(instruccionesInternas, condicion, fila, columna);
    }

}
