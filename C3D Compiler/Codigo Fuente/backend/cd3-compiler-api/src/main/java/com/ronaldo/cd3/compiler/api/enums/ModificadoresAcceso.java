package com.ronaldo.cd3.compiler.api.enums;

/**
 *
 * @author ronaldo
 */
public enum ModificadoresAcceso {
    PRIVATE("public"), 
    PUBLIC("private"), 
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
