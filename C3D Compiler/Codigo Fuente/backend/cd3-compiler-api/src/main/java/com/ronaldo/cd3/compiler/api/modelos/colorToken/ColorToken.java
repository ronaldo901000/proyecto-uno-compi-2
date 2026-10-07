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

            // MODIFICADORES DE ACCESO (Morado)
            case 1:  // PUBLIC
            case 2:  // PRIVATE
            case 3:  // PROTECTED
                this.color = "#9577f4";
                break;

            // ROSA
            case 5:  // OVERRIDE
            case 15: // RETURN
                this.color = "#FF79C6";
                break;

            // PALABRAS RESERVADAS DEL LENGUAJE Z (Magenta)
            case 4:  // EXTENDS
            case 6:  // THIS
            case 7:  // CLASS
            case 8:  // VOID
            case 14: // NEW
            case 16: // IF
            case 17: // ELSE
            case 20: // SWITCH
            case 21: // CASE
            case 22: // BREAK
            case 23: // CONTINUE
            case 24: // DEFAULT
            case 25: // PRINTLN
            case 26: // PRINT
            case 27: // READLN
            case 28: // FOR
            case 29: // WHILE
            case 30: // DO
            case 31: // NULL
                this.color = "#C678DD";
                break;

            // TIPOS DE DATOS PRIMITIVOS (Cyan brillante)
            case 9:  // INT
            case 10: // DOUBLE
            case 11: // STRING
            case 12: // CHAR
            case 13: // BOOLEAN
                this.color = "#56B6C2";
                break;

            // VALORES LITERALES Y CONSTANTES (Naranja)
            case 18: // TRUE
            case 19: // FALSE
            case 64: // ENTERO
            case 65: // DECIMAL
                this.color = "#D19A66";
                break;

            // CADENAS Y CARACTERES (Verde suave)
            case 66: // CADENA
            case 67: // LIT_CHAR
                this.color = "#98C379";
                break;

            // IDENTIFICADORES (Azul claro)
            case 63: // ID
                this.color = "#61AFEF";
                break;

            // COMENTARIOS (Gris oscuro)
            case 69: // COMENTARIO_LINEA
            case 70: // COMENTARIO_BLOQUE
                this.color = "#5C6370";
                break;

            // OPERADORES Y SÍMBOLOS DE PUNTUACIÓN (Amarillo suave)
            case 32: // MAS_EQ
            case 33: // MENOS_EQ
            case 34: // MULTI_EQ
            case 35: // MAS
            case 36: // MENOS
            case 37: // MULTI
            case 38: // DIV
            case 39: // MODULO
            case 40: // EQ
            case 41: // EQ_EQ
            case 42: // NO_EQ
            case 43: // MAYOR_Q
            case 44: // MAYOR_EQ_Q
            case 45: // MENOR_Q
            case 46: // MENOR_EQ_Q
            case 47: // AND
            case 48: // OR
            case 49: // NOT
            case 50: // MAS_MAS
            case 51: // MENOS_MENOS
            case 52: // PUNTO
            case 53: // COMA
            case 54: // DOS_P
            case 55: // P_COMA
            case 56: // LLAVE_A
            case 57: // LLAVE_C
            case 58: // CORCH_A
            case 59: // CORCH_C
            case 60: // PAR_A
            case 61: // PAR_C
            case 62: // INTERROGACION
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

            // PALABRAS RESERVADAS Y ESTRUCTURA DE CONTROL (Magenta)
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
            case 24: // NULL
                this.color = "#C678DD";
                break;

            // DECLARACION Y TIPOS DE DATOS (Cyan brillante)
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

            // LITERALES NUMÉRICOS Y BOOLEANOS (Naranja)
            case 12: // VERUM
            case 13: // FALSUS
            case 51: // ENTERO
            case 52: // DECIMAL
                this.color = "#D19A66";
                break;

            // CADENAS Y CARACTERES (Verde suave)
            case 53: // CADENA
            case 54: // CHAR
                this.color = "#98C379";
                break;

            // IDENTIFICADORES (Azul claro)
            case 50: // ID
                this.color = "#61AFEF";
                break;

            // COMENTARIOS (Gris oscuro)
            case 55: // COMENTARIO_LINEA
            case 56: // COMENTARIO_BLOQUE
                this.color = "#5C6370";
                break;

            // SÍMBOLOS Y OPERADORES (Amarillo suave)
            case 25: // MAS
            case 26: // MENOS
            case 27: // MULTI
            case 28: // DIV
            case 29: // EQ
            case 30: // EQ_EQ
            case 31: // NO_EQ
            case 32: // MAYOR_Q
            case 33: // MAYOR_EQ_Q
            case 34: // MENOR_Q
            case 35: // MENOR_EQ_Q
            case 36: // AND
            case 37: // OR
            case 38: // MAS_MAS
            case 39: // MENOS_MENOS
            case 40: // PUNTO
            case 41: // COMA
            case 42: // DOS_P
            case 43: // P_COMA
            case 44: // LLAVE_A
            case 45: // LLAVE_C
            case 46: // CORCH_A
            case 47: // CORCH_C
            case 48: // PAR_A
            case 49: // PAR_C
                this.color = "#E5C07B";
                break;

            //ERRORES O TOKEN DESCONOCIDO (Rojo)
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
