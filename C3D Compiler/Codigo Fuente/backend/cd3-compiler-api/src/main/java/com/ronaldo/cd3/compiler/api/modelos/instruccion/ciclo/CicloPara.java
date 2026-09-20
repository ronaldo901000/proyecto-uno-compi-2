package com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
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
public class CicloPara extends Ciclo {

    private final Reglas reglas = new Reglas();
    private DeclaracionIterador iterador;
    private Instruccion actualizacion;

    public CicloPara(DeclaracionIterador iterador,
            Instruccion actualizacion, List<Instruccion> instruccionesInternas,
            Expresion condicion, int fila, int columna) {

        super(instruccionesInternas, condicion, fila, columna);
        this.iterador = iterador;
        this.actualizacion = actualizacion;
    }

    public DeclaracionIterador getIterador() {
        return iterador;
    }

    public void setIterador(DeclaracionIterador iterador) {
        this.iterador = iterador;
    }

    public Instruccion getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Instruccion actualizacion) {
        this.actualizacion = actualizacion;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        TablaSimbolos anterior = contexto.nuevoAmbito("ciclo_para");
        contexto.entrarCiclo();
        if (iterador != null) {
            iterador.verificarSemantica(contexto);
        }
        reglas.esCondicionValida(contexto, condicion);
        if (actualizacion instanceof Verificable) {
            ((Verificable) actualizacion).verificarSemantica(contexto);
        }
        reglas.verificarInstrucciones(contexto, instruccionesInternas);
        contexto.salirCiclo();
        contexto.restaurarAmbito(anterior);
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (iterador != null) {
            iterador.generarCuartetas(contexto, cuartetas);
        }
        String etiquetaCondicion = cuartetas.nuevaEtiqueta();
        String etiquetaContinuar = cuartetas.nuevaEtiqueta();
        String etiquetaFin = cuartetas.nuevaEtiqueta();
        contexto.entrarNivelGeneracionCiclo(etiquetaContinuar, etiquetaFin);

        cuartetas.agregarEtiqueta(etiquetaCondicion, fila, columna);
        if (condicion != null) {
            String dirCondicion = condicion.generarCuartetas(contexto, cuartetas);
            cuartetas.agregar(OperadorCuarteta.IF_FALSO, dirCondicion,
                    etiquetaFin, null, fila, columna);
        }
        generarInstruccionesInternas(contexto, cuartetas);
        cuartetas.agregarEtiqueta(etiquetaContinuar, fila, columna);
        if (actualizacion != null) {
            actualizacion.generarCuartetas(contexto, cuartetas);
        }
        cuartetas.agregar(OperadorCuarteta.GOTO, etiquetaCondicion,
                null, null, fila, columna);
        cuartetas.agregarEtiqueta(etiquetaFin, fila, columna);

        contexto.salirNivelGeneracionCiclo();
        return null;
    }

}