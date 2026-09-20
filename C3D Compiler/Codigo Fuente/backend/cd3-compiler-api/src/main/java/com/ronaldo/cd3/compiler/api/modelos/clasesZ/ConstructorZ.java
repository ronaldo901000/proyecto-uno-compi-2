package com.ronaldo.cd3.compiler.api.modelos.clasesZ;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.interfaces.Generable;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.Parametro;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ConstructorZ extends Nodo implements Verificable, Generable {

    private String nombre;
    private List<Parametro> parametros;
    private List<Instruccion> cuerpo;
    private SimboloFuncion simbolo;

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

    public SimboloFuncion getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(SimboloFuncion simbolo) {
        this.simbolo = simbolo;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (simbolo == null) {
            return null;
        }
        cuartetas.registrarTipoFuncion(simbolo.getEtiquetaInicio(),
                simbolo.getTipoRetorno());
        cuartetas.agregarEtiqueta(simbolo.getEtiquetaInicio(), fila, columna);
        for (SimboloParametro parametro : simbolo.getParametros()) {
            cuartetas.registrarTipoVariable(parametro.getId(), parametro.getTipo());
        }
        if (cuerpo != null) {
            for (Instruccion instruccion : cuerpo) {
                instruccion.generarCuartetas(contexto, cuartetas);
            }
        }
        cuartetas.agregar(OperadorCuarteta.RETORNO, null,
                null, null, fila, columna);
        return null;
    }

}