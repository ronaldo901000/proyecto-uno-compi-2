package com.ronaldo.cd3.compiler.api.modelos.expresion;

/**
 *
 * @author ronaldo
 */
public class AccesoVariable extends Expresion {

    private String id;

    public AccesoVariable(String id, int fila, int columna) {
        super(fila, columna);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
