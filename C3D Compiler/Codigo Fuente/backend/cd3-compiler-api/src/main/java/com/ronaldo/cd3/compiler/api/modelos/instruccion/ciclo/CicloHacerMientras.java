package com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CicloHacerMientras extends Ciclo {

    private final Reglas reglas = new Reglas();

    public CicloHacerMientras(List<Instruccion> instruccionesInternas,
            Expresion condicion, int fila, int columna) {

        super(instruccionesInternas, condicion, fila, columna);
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        reglas.esCondicionValida(contexto, condicion);
        TablaSimbolos anterior = contexto.nuevoAmbito("ciclo_hacer");
        contexto.entrarCiclo();
        reglas.verificarInstrucciones(contexto, instruccionesInternas);
        contexto.salirCiclo();
        contexto.restaurarAmbito(anterior);
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String etiquetaInicio = cuartetas.nuevaEtiqueta();
        String etiquetaFin = cuartetas.nuevaEtiqueta();
        contexto.entrarNivelGeneracionCiclo(etiquetaInicio, etiquetaFin);

        cuartetas.agregarEtiqueta(etiquetaInicio, fila, columna);
        generarInstruccionesInternas(contexto, cuartetas);
        String dirCondicion = condicion.generarCuartetas(contexto, cuartetas);
        cuartetas.agregar(OperadorCuarteta.IF_VERDADERO, dirCondicion,
                etiquetaInicio, null, fila, columna);
        cuartetas.agregarEtiqueta(etiquetaFin, fila, columna);

        contexto.salirNivelGeneracionCiclo();
        return null;
    }

}