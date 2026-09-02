package com.ronaldo.cd3.compiler.api.dtos.error.analisis;

/**
 *
 * @author ronaldo
 */
public class ErrorLexico extends ErrorAnalisis{
    
    public ErrorLexico(int fila, int columna, String lexema, String descripcion, String ruta) {
        super(fila, columna, lexema, descripcion, ruta);
        this.tipo = "Lexico";
    }
    
    
    
}
