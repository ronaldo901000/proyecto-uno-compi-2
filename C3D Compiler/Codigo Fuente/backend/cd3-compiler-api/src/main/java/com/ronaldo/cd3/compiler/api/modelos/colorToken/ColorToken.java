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
                
            // Rosa
            case 5:// OVERRIDE
            case 14:// RETURN
                this.color = "#FF79C6";
                break;

            // PALABRAS RESERVADAS DEL LENGUAJE Z (Magenta)
            case 4:  // EXTENDS
            case 6:  // CLASS
            case 7:  // VOID
            case 13: // NEW
            case 15: // IF
            case 16: // ELSE
            case 19: // SWITCH
            case 20: // CASE
            case 21: // BREAK
            case 22: // CONTINUE
            case 23: // DEFAULT
            case 24: // PRINTLN
            case 25: // PRINT
            case 26: // READLN
            case 27: // FOR
            case 28: // WHILE
            case 29: // DO
            case 30: // NULL
                this.color = "#C678DD";
                break;

            // TIPOS DE DATOS PRIMITIVOS (Cyan brillante)
            case 8:  // INT
            case 9:  // DOUBLE
            case 10: // STRING
            case 11: // CHAR
            case 12: // BOOLEAN
                this.color = "#56B6C2";
                break;

            // VALORES LITERALES Y CONSTANTES (Naranja)
            case 17: // TRUE
            case 18: // FALSE
            case 63: // ENTERO
            case 64: // DECIMAL
                this.color = "#D19A66";
                break;

            // CADENAS Y CARACTERES (Verde suave)
            case 65: // CADENA
            case 66: // LIT_CHAR
                this.color = "#98C379";
                break;

            // IDENTIFICADORES (Azul claro)
            case 62: // ID
                this.color = "#61AFEF";
                break;

            // COMENTARIOS (Gris oscuro)
            case 68: // COMENTARIO_LINEA
            case 69: // COMENTARIO_BLOQUE
                this.color = "#5C6370";
                break;

            // OPERADORES Y SÍMBOLOS DE PUNTUACIÓN (Amarillo suave)
            case 31: // MAS_EQ
            case 32: // MENOS_EQ
            case 33: // MULTI_EQ
            case 34: // MAS
            case 35: // MENOS
            case 36: // MULTI
            case 37: // DIV
            case 38: // MODULO
            case 39: // EQ
            case 40: // EQ_EQ
            case 41: // NO_EQ
            case 42: // MAYOR_Q
            case 43: // MAYOR_EQ_Q
            case 44: // MENOR_Q
            case 45: // MENOR_EQ_Q
            case 46: // AND
            case 47: // OR
            case 48: // NOT
            case 49: // MAS_MAS
            case 50: // MENOS_MENOS
            case 51: // PUNTO
            case 52: // COMA
            case 53: // DOS_P
            case 54: // P_COMA
            case 55: // LLAVE_A
            case 56: // LLAVE_C
            case 57: // CORCH_A
            case 58: // CORCH_C
            case 59: // PAR_A
            case 60: // PAR_C
            case 61: // INTERROGACION
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
