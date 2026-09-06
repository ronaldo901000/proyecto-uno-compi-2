package com.ronaldo.cd3.compiler.api.modelos.colorToken;

import com.ronaldo.cd3.compiler.api.enums.ExtensionArchivos;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author ronaldo
 */
public class ColorToken {

    private int inicio;
    private int fin;
    private int id;
    private String color;

    public ColorToken(int inicio, int fin, int id, String opcion) {
        this.inicio = inicio;
        this.fin = fin;
        this.id = id;

        if (opcion.equals(ExtensionArchivos.Y.getTexto())) {
            definirColorY();
        } else if (opcion.equals(ExtensionArchivos.Z.getTexto())) {
            definirColorZ();
        } else if (opcion.equals(ExtensionArchivos.PIG.getTexto())) {
            definirColorPig();
        }

    }

    public void definirColorY() {
        switch (this.id) {

            case Token.INVALID_TYPE:
                this.color = "#E06C75";
                break;
            //PALABRAS RESERVADAS
            case 1:
            case 2:
            case 3:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                this.color = "#C678DD"; // Magenta 
                break;

            // --- TIPOS DE DATOS PRIMITIVOS 
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                this.color = "#56B6C2"; // Cyan brillante
                break;

            // --- VALORES BOOLEANOS
            case 9:
            case 10:
                this.color = "#D19A66"; // Naranja
                break;

            // CONSTANTES
            case 56:
            case 57:
                this.color = "#D19A66"; // Naranja claro
                break;

            case 58: // LIT_CADENA
            case 59: // CHAR
                this.color = "#98C379"; // Verde suave 
                break;

            // --- IDENTIFICADORES 
            case 54:
                this.color = "#E5C07B"; // Dorado
                break;

            case 55: // ID
                this.color = "#61AFEF"; // Azul claro
                break;

            // --- COMENTARIOS
            case 62: // COMENTARIO_LINEA
            case 63: // COMENTARIO_BLOQUE
                this.color = "#5C6370";
                break;

            //OPERADORES Y SIMBOLOS (Gris claro / Blanco)
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
                this.color = "#E5C07B";
                break;

            default:
                this.color = "#E06C75";
                break;
        }
    }

    public void definirColorZ() {
        switch (this.id) {

            // PALABRAS RESERVADAS DEL LENGUAJE Z (Magenta)
            case 1:
            case 2:
            case 3:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                this.color = "#C678DD";
                break;

            // TIPOS DE DATOS PRIMITIVOS (Cyan brillante)
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                this.color = "#56B6C2";
                break;

            // VALORES LITERALES Y CONSTANTES (Naranja)
            case 14:
            case 15:
            case 27:
            case 60: // ENTERO
            case 61: // DECIMAL
                this.color = "#D19A66";
                break;

            // CADENAS Y CARACTERES (Verde suave)
            case 62: // CADENA
            case 63: // LIT_CHAR
                this.color = "#98C379";
                break;

            // IDENTIFICADORES (Azul claro)
            case 59: // ID
                this.color = "#61AFEF";
                break;

            // COMENTARIOS (Gris oscuro)
            case 65: // COMENTARIO_LINEA
            case 66: // COMENTARIO_BLOQUE
                this.color = "#5C6370";
                break;

            // OPERADORES Y SÍMBOLOS DE PUNTUACIÓN (Amarillo suave)
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
                this.color = "#E5C07B";
                break;

            // TOKEN DESCONOCIDO O ERROR DE SINTAXIS (Rojo)
            default:
                this.color = "#E06C75";
                break;
        }
    }

    public void definirColorPig() {
        switch (this.id) {

            // PALABRAS RESERVADAS Y ESTRUCTURA DE CONTROL
            case 1:
            case 2:
            case 3:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                this.color = "#C678DD";
                break;

            // DECLARACION Y TIPOS DE DATOS
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                this.color = "#56B6C2";
                break;

            // LITERALES NUMERICOS Y BOOLEANOS
            case 12:
            case 13:
            case 54:
            case 55:
                this.color = "#D19A66";
                break;

            // CADENAS Y CARACTERES
            case 56:
            case 57:
                this.color = "#98C379";
                break;

            // IDENTIFICADORES
            case 53:
                this.color = "#61AFEF";
                break;

            // COMENTARIOS
            case 58:
            case 59:
                this.color = "#5C6370";
                break;

            // SIMBOLOS Y OPERADORES
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                this.color = "#E5C07B";
                break;

            // ERROR O TOKEN DESCONOCIDO
            default:
                this.color = "#E06C75";
                break;
        }
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
