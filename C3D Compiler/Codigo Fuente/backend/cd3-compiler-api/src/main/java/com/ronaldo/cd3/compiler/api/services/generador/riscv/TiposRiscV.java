package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoIndice;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;

/**
 * 
 * @author ronaldo
 */
public class TiposRiscV {

    private final ListaCuartetas cuartetas;

    public TiposRiscV(ListaCuartetas cuartetas) {
        this.cuartetas = cuartetas;
    }

    public boolean esDecimal(Operando operando) {
        return operando != null && operando.getTipo() != null
                && operando.getTipo().getTipoDato() == TipoDato.DECIMAL;
    }

    public boolean esEntero(Operando operando) {
        if (operando == null || operando.getTipo() == null) {
            return false;
        }
        TipoDato dato = operando.getTipo().getTipoDato();
        return dato == TipoDato.ENTERO || dato == TipoDato.CHAR || dato == TipoDato.BOOLEAN;
    }

    public boolean esCadena(Operando operando) {
        if (operando == null || operando.getTipo() == null) {
            return false;
        }
        return operando.getTipo().getTipoDato() == TipoDato.CADENA;
    }

    public boolean esCaracter(Operando operando) {
        return operando != null && operando.getTipo() != null
                && operando.getTipo().getTipoDato() == TipoDato.CHAR;
    }


    
    public boolean esStructPorValor(Operando operando) {
        Tipo tipo = (operando != null) ? operando.getTipo() : null;
        return tipo instanceof TipoStructura && !((TipoStructura) tipo).esPorReferencia();
    }

    /**
     * Indica si la funcion de la unidad devuelve un decimal.
     */
    public boolean esDecimalRetorno(String nombreUnidad) {
        if (nombreUnidad == null) {
            return false;
        }
        Tipo tipo = cuartetas.tipoDeFuncion(nombreUnidad);
        return tipo != null && tipo.getTipoDato() == TipoDato.DECIMAL;
    }
   
    public int tamañoAlmacen(Operando operando) {
        if (operando instanceof OperandoIndice) {
            return tamañoElementoIndice((OperandoIndice) operando);
        }
        if (operando == null || operando.getTipo() == null) {
            return 4;
        }
        if (operando.getTipo().esPorReferencia()) {
            return 4;
        }
        int tamaño = operando.getTipo().tamañoBytes();
        return (tamaño <= 1) ? 1 : 4;
    }

    public int tamañoElemento(Operando destino) {
        if (destino instanceof OperandoIndice) {
            return tamañoElementoIndice((OperandoIndice) destino);
        }
        return tamañoAlmacen(destino);
    }

    /**
     * Tamano del elemento al que apunta un acceso por indice: 4 si es una
     * fila (puntero) o struct, 1 si la hoja es char
     */
    public int tamañoElementoIndice(OperandoIndice indice) {
        int nivel = 1;
        Operando raiz = indice.getBase();
        while (raiz instanceof OperandoIndice) {
            raiz = ((OperandoIndice) raiz).getBase();
            nivel++;
        }
        if (raiz != null && raiz.getTipo() instanceof TipoArreglo) {
            TipoArreglo arr = (TipoArreglo) raiz.getTipo();
            if (nivel < rangoArreglo(arr)) {
                return 4;
            }
            Tipo hoja = arr;
            while (hoja instanceof TipoArreglo) {
                hoja = ((TipoArreglo) hoja).getTipoBase();
            }
            if (hoja == null || hoja instanceof TipoStructura) {
                return 4;
            }
            return (hoja.tamañoBytes() <= 1) ? 1 : 4;
        }
        int tamaño = indice.getTamañoElemento();
        return tamaño > 0 ? tamaño : 4;
    }

    /**
     * Numero de dimensiones del arreglo.
     */
    public int rangoArreglo(TipoArreglo arr) {
        int rango = 1;
        Tipo t = arr.getTipoBase();
        while (t instanceof TipoArreglo) {
            rango++;
            t = ((TipoArreglo) t).getTipoBase();
        }
        return rango;
    }

    /**
     * Tamaño en bytes del elemento hoja de un arreglo (1 para char, 4 el resto).
     */
    public int tamañoBaseHoja(Tipo t) {
        while (t instanceof TipoArreglo) {
            t = ((TipoArreglo) t).getTipoBase();
        }
        if (t instanceof TipoStructura) {
            return 4;
        }
        return (t != null && t.tamañoBytes() <= 1) ? 1 : 4;
    }
}