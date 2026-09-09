package com.ronaldo.cd3.compiler.api.modelos.estructurasY;

import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public class AtributoEstructura extends Nodo {

    private String tipoDato;
    private String nombre;
    private Integer tamañoArreglo;

    public AtributoEstructura(String tipoDato, String nombre, 
            Integer tamañoArreglo, int fila, int columna) {
        
        super(fila, columna);
        this.tipoDato = tipoDato;
        this.nombre = nombre;
        this.tamañoArreglo = tamañoArreglo;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTamañoArreglo() {
        return tamañoArreglo;
    }

    public void setTamañoArreglo(Integer tamañoArreglo) {
        this.tamañoArreglo = tamañoArreglo;
    }

}
