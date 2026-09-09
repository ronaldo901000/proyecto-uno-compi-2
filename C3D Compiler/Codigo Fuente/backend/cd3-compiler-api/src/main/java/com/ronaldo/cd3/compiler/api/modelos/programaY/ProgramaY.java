package com.ronaldo.cd3.compiler.api.modelos.programaY;

import com.ronaldo.cd3.compiler.api.modelos.estructurasY.EstructuraDef;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.FuncionDef;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ProgramaY extends Nodo {

    private List<EstructuraDef> estructuras;
    private List<FuncionDef> funciones;

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

}
