package com.ronaldo.cd3.compiler.api.modelos.estructurasY;

import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class EstructuraDef extends Nodo {

    private String nombre;
    private List<AtributoEstructura> atributos;

    public EstructuraDef(String nombre, List<AtributoEstructura> atributos, int fila, int columna) {
        super(fila, columna);
        this.nombre = nombre;
        this.atributos = atributos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<AtributoEstructura> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<AtributoEstructura> atributos) {
        this.atributos = atributos;
    }

}
