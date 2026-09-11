package com.ronaldo.cd3.compiler.api.modelos.clasesZ;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.Parametro;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ConstructorZ extends Nodo implements Verificable {

    private String nombre;
    private List<Parametro> parametros;
    private List<Instruccion> cuerpo;

    public ConstructorZ(String nombre, List<Parametro> parametros,
            List<Instruccion> cuerpo, int fila, int columna) {
        super(fila, columna);
        this.nombre = nombre;
        this.parametros = parametros;
        this.cuerpo = cuerpo;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public List<Instruccion> getCuerpo() {
        return cuerpo;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
    }

}