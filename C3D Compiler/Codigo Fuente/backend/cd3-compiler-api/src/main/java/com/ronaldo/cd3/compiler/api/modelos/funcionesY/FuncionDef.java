package com.ronaldo.cd3.compiler.api.modelos.funcionesY;

import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class FuncionDef extends Nodo {

    private String nombre;
    private List<Parametro> parametros;
    private String tipoRetorno;
    private List<Instruccion> cuerpo;

    public FuncionDef(String nombre, List<Parametro> parametros,
            String tipoRetorno, List<Instruccion> cuerpo, int fila, int columna) {

        super(fila, columna);
        this.nombre = nombre;
        this.parametros = parametros;
        this.tipoRetorno = tipoRetorno;
        this.cuerpo = cuerpo;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public List<Instruccion> getCuerpo() {
        return cuerpo;
    }

}
