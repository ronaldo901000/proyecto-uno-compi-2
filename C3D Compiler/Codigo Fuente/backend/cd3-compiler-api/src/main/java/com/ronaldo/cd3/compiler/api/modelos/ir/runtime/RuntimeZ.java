package com.ronaldo.cd3.compiler.api.modelos.ir.runtime;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

public final class RuntimeZ {

    private RuntimeZ() {
    }

    public static String imprimirEntero() {
        return "z_print_entero";
    }

    public static String imprimirDecimal() {
        return "z_print_decimal";
    }

    public static String imprimirCadena() {
        return "z_print_cadena";
    }

    public static String imprimirCaracter() {
        return "z_print_caracter";
    }

    public static String imprimirBooleano() {
        return "z_print_booleano";
    }

    public static String imprimirNulo() {
        return "z_print_nulo";
    }

    public static String imprimirSaltoDeLinea() {
        return "z_print_salto";
    }

    public static String leerEntero() {
        return "z_leer_entero";
    }

    public static String leerDecimal() {
        return "z_leer_decimal";
    }

    public static String leerCadena() {
        return "z_leer_cadena";
    }

    public static String leerCaracter() {
        return "z_leer_caracter";
    }

    public static String rutinaImpresion(Tipo tipo) {
        TipoDato dato = (tipo != null) ? tipo.getTipoDato() : null;
        if (dato == null) {
            return null;
        }
        switch (dato) {
            case ENTERO:
                return imprimirEntero();
            case DECIMAL:
                return imprimirDecimal();
            case CADENA:
                return imprimirCadena();
            case CHAR:
                return imprimirCaracter();
            case BOOLEAN:
                return imprimirBooleano();
            case NULO:
                return imprimirNulo();
            default:
                return null;
        }
    }

    public static String rutinaLectura(Tipo tipo) {
        TipoDato dato = (tipo != null) ? tipo.getTipoDato() : null;
        if (dato == null) {
            return null;
        }
        switch (dato) {
            case ENTERO:
                return leerEntero();
            case DECIMAL:
                return leerDecimal();
            case CADENA:
                return leerCadena();
            case CHAR:
                return leerCaracter();
            default:
                return null;
        }
    }
}
