package com.ronaldo.cd3.compiler.api.modelos.funcionesY;

import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public class Parametro extends Nodo {

    private String tipoDato;
    private String nombre;
    private boolean esArreglo;
    private boolean esStruct;

    public Parametro(String tipoDato, String nombre, boolean esArreglo, 
            boolean esStruct, int fila, int columna) {
        
        super(fila, columna);
        this.tipoDato = tipoDato;
        this.nombre = nombre;
        this.esArreglo = esArreglo;
        this.esStruct = esStruct;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEsArreglo() {
        return esArreglo;
    }

    public boolean isEsStruct() {
        return esStruct;
    }

}
