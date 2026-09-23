package com.ronaldo.cd3.compiler.api.modelos.programaY;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.interfaces.Generable;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.estructurasY.EstructuraDef;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.FuncionDef;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ProgramaY extends Nodo implements Verificable, Generable {

    private List<EstructuraDef> estructuras;
    private List<FuncionDef> funciones;
    private ArchivoDTO archivo;

    public ProgramaY(List<EstructuraDef> estructuras, List<FuncionDef> funciones, int fila, int columna) {
        super(fila, columna);
        this.estructuras = estructuras;
        this.funciones = funciones;
    }

    public List<EstructuraDef> getEstructuras() {
        return estructuras;
    }

    public List<FuncionDef> getFunciones() {
        return funciones;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        registrarEstructurasYFunciones(contexto);
        verificarCuerpos(contexto);
    }

    public void registrarEstructurasYFunciones(Contexto contexto) {
        if (estructuras != null) {
            for (EstructuraDef estructura : estructuras) {
                estructura.verificarSemantica(contexto);
            }
        }
        if (funciones != null) {
            for (FuncionDef funcion : funciones) {
                funcion.declararFuncion(contexto);
            }
        }
    }

    public void verificarCuerpos(Contexto contexto) {
        if (funciones != null) {
            for (FuncionDef funcion : funciones) {
                funcion.verificarSemantica(contexto);
            }
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (funciones != null) {
            for (FuncionDef funcion : funciones) {
                funcion.generarCuartetas(contexto, cuartetas);
            }
        }
        return null;
    }

    public ArchivoDTO getArchivo() {
        return archivo;
    }

    public void setArchivo(ArchivoDTO archivo) {
        this.archivo = archivo;
    }

}
