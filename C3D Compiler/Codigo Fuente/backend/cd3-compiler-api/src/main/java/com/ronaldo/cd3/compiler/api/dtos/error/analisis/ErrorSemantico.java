package com.ronaldo.cd3.compiler.api.dtos.error.analisis;

/**
 *
 * @author ronaldo
 */
public class ErrorSemantico extends ErrorAnalisis {

    public ErrorSemantico(int fila, int columna, String lexema, String descripcion, String ruta) {
        super(fila, columna, lexema, descripcion, ruta);
        this.tipo = "Semantico";
    }

}
