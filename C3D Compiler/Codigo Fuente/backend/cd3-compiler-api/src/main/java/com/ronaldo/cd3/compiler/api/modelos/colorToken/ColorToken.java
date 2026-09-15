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
            case 1:  // PUBLIC
            case 2:  // CLASS
            case 3:  // VOID
            case 9:  // NEW
            case 10: // RETURN
            case 11: // IF
            case 12: // ELSE
            case 15: // SWITCH
            case 16: // CASE
            case 17: // BREAK
            case 18: // CONTINUE
            case 19: // DEFAULT
            case 20: // PRINTLN
            case 21: // PRINT
            case 22: // READLN
            case 23: // FOR
            case 24: // WHILE
            case 25: // DO
            case 26: // NULL
                this.color = "#C678DD";
                break;

            // TIPOS DE DATOS PRIMITIVOS (Cyan brillante)
            case 4: // INT
            case 5: // DOUBLE
            case 6: // STRING
            case 7: // CHAR
            case 8: // BOOLEAN
                this.color = "#56B6C2";
                break;

            // VALORES LITERALES Y CONSTANTES (Naranja)
            case 13: // TRUE
            case 14: // FALSE
            case 59: // ENTERO
            case 60: // DECIMAL
                this.color = "#D19A66";
                break;

            // CADENAS Y CARACTERES (Verde suave)
            case 61: // CADENA
            case 62: // LIT_CHAR
                this.color = "#98C379";
                break;

            // IDENTIFICADORES (Azul claro)
            case 58: // ID
                this.color = "#61AFEF";
                break;

            // COMENTARIOS (Gris oscuro)
            case 64: // COMENTARIO_LINEA
            case 65: // COMENTARIO_BLOQUE
                this.color = "#5C6370";
                break;

            // OPERADORES Y SÍMBOLOS DE PUNTUACIÓN (Amarillo suave)
            case 27: // MAS_EQ
            case 28: // MENOS_EQ
            case 29: // MULTI_EQ
            case 30: // MAS
            case 31: // MENOS
            case 32: // MULTI
            case 33: // DIV
            case 34: // MODULO
            case 35: // EQ
            case 36: // EQ_EQ
            case 37: // NO_EQ
            case 38: // MAYOR_Q
            case 39: // MAYOR_EQ_Q
            case 40: // MENOR_Q
            case 41: // MENOR_EQ_Q
            case 42: // AND
            case 43: // OR
            case 44: // NOT
            case 45: // MAS_MAS
            case 46: // MENOS_MENOS
            case 47: // PUNTO
            case 48: // COMA
            case 49: // DOS_P
            case 50: // P_COMA
            case 51: // LLAVE_A
            case 52: // LLAVE_C
            case 53: // CORCH_A
            case 54: // CORCH_C
            case 55: // PAR_A
            case 56: // PAR_C
            case 57: // INTERROGACION
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
            case 1:  // VARIABILES
            case 2:  // MAIOR
            case 3:  // IMPORT
            case 14: // SI
            case 15: // FINIS
            case 16: // FINIS_MAY
            case 17: // ALITER
            case 18: // DUM
            case 19: // FACERE
            case 20: // PER
            case 21: // PERGE
            case 22: // INTERRUMPE
            case 23: // NON
                this.color = "#C678DD";
                break;

            // DECLARACION Y TIPOS DE DATOS
            case 4:  // ESTO
            case 5:  // SERIES
            case 6:  // NOVUS
            case 7:  // NUMERUS
            case 8:  // DECIMALIS
            case 9:  // TEXTUM
            case 10: // LITTERA
            case 11: // BOOL
                this.color = "#56B6C2";
                break;

            // LITERALES NUMÉRICOS Y BOOLEANOS
            case 12: // VERUM
            case 13: // FALSUS
            case 50: // ENTERO
            case 51: // DECIMAL
                this.color = "#D19A66";
                break;

            // CADENAS Y CARACTERES
            case 52: // CADENA
            case 53: // CHAR
                this.color = "#98C379";
                break;

            // IDENTIFICADORES
            case 49: 
                this.color = "#61AFEF";
                break;

            // COMENTARIOS
            case 54: 
            case 55: 
                this.color = "#5C6370";
                break;

            // SÍMBOLOS Y OPERADORES 
            case 24:
            case 25:
            case 26:
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
                this.color = "#E5C07B";
                break;

            // ESPACIOS EN BLANCO (WS), ERRORES O TOKEN DESCONOCIDO
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
