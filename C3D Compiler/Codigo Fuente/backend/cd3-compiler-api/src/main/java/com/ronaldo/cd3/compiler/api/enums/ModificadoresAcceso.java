package com.ronaldo.cd3.compiler.api.enums;

/**
 *
 * @author ronaldo
 */
public enum ModificadoresAcceso {
    PRIVATE("private"), 
    PUBLIC("public"), 
    PROTECTED("protected"), 
    DEFAULT("");
    
    private String texto;

    ModificadoresAcceso(String texto) {
        this.texto = texto;
    }
    
    public String getTexto(){
        return this.texto;
    }
    
}
