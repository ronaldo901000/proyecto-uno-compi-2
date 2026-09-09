package com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
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

}