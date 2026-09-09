package com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo;

import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.Ciclo;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CicloHacerMientras extends Ciclo {

    public CicloHacerMientras(List<Instruccion> instruccionesInternas,
            Expresion condicion, int fila, int columna) {

        super(instruccionesInternas, condicion, fila, columna);
    }

}
