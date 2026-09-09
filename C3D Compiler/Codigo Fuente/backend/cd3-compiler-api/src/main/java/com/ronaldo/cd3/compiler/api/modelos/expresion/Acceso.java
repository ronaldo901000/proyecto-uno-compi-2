package com.ronaldo.cd3.compiler.api.modelos.expresion;

/**
 *
 * @author ronaldo
 */
public class Acceso extends Expresion {

    private Expresion objeto;
    private String atributo;

    public Acceso(Expresion objeto, String atributo, int fila, int columna) {
        super(fila, columna);
        this.objeto = objeto;
        this.atributo = atributo;
    }

    public Expresion getObjeto() {
        return objeto;
    }

    public String getAtributo() {
        return atributo;
    }
}
