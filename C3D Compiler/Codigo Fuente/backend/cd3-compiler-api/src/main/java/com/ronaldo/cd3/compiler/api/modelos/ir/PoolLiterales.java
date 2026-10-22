package com.ronaldo.cd3.compiler.api.modelos.ir;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PoolLiterales {

    private final Map<String, EntradaLiteral> porClave;
    private final Map<String, Integer> contadores;

    public PoolLiterales() {
        this.porClave = new LinkedHashMap<>();
        this.contadores = new HashMap<>();
    }

    public boolean requiereMemoria(Tipo tipo) {
        if (tipo == null) {
            return false;
        }
        TipoDato dato = tipo.getTipoDato();
        return dato == TipoDato.CADENA || dato == TipoDato.DECIMAL;
    }

    public EntradaLiteral registrar(Tipo tipo, String valor) {
        String prefijo = prefijoDe(tipo);
        String clave = prefijo + ':' + valor;
        EntradaLiteral existente = porClave.get(clave);
        if (existente != null) {
            return existente;
        }
        int indice = contadores.getOrDefault(prefijo, 0);
        contadores.put(prefijo, indice + 1);
        EntradaLiteral entrada = new EntradaLiteral(
                prefijo + '_' + indice, tipo, valor);
        porClave.put(clave, entrada);
        return entrada;
    }

    public List<EntradaLiteral> getEntradas() {
        return new ArrayList<>(porClave.values());
    }

    public boolean esVacio() {
        return porClave.isEmpty();
    }

    private String prefijoDe(Tipo tipo) {
        TipoDato dato = (tipo != null) ? tipo.getTipoDato() : null;
        if (dato == TipoDato.CADENA) {
            return "str";
        }
        if (dato == TipoDato.DECIMAL) {
            return "dbl";
        }
        return "lit";
    }

    public static class EntradaLiteral {

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
}
