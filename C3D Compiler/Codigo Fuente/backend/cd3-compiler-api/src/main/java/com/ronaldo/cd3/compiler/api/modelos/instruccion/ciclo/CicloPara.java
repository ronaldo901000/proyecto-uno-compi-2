package com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo;

import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.Ciclo;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CicloPara extends Ciclo {

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

}
