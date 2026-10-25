package com.ronaldo.cd3.compiler.api.modelos.ir;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public class EntradaLiteral {

    private final String etiqueta;
    private final Tipo tipo;
    private final String valor;

    public EntradaLiteral(String etiqueta, Tipo tipo, String valor) {
        this.etiqueta = etiqueta;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public int getTamaño() {
        if (tipo != null && tipo.getTipoDato() == TipoDato.CADENA) {
            return longitudCadena() + 1;
        }
        return (tipo != null) ? tipo.tamañoBytes() : 0;
    }

    public int getAlineacion() {
        return (tipo != null) ? tipo.alineacion() : 1;
    }

    private int longitudCadena() {
        if (valor == null) {
            return 0;
        }
        int inicio = valor.startsWith("\"") ? 1 : 0;
        boolean cierra = valor.length() > 1 && valor.endsWith("\"");
        int fin = valor.length() - (cierra ? 1 : 0);
        return Math.max(0, fin - inicio);
    }
}
