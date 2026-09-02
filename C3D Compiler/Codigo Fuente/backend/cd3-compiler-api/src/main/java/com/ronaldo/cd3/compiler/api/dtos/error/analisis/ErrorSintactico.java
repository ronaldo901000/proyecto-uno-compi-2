package com.ronaldo.cd3.compiler.api.dtos.error.analisis;

/**
 *
 * @author ronaldo
 */
public class ErrorSintactico extends ErrorAnalisis {

    public ErrorSintactico(int fila, int columna, String lexema, String descripcion, String ruta) {
        super(fila, columna, lexema, descripcion, ruta);
        this.tipo = "Sintactico";
    }

}
