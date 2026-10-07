package com.ronaldo.cd3.compiler.api.modelos.semantica.reglas;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReglasTipos {

    public Tipo resolverTipo(Contexto ctx, String nombreTipo, int fila, int columna) {
        if (nombreTipo == null) {
            ctx.agregarError(fila, columna, null, "Falta el tipo de dato");
            return ctx.getTablaTipos().getError();
        }
        Tipo tipo = ctx.esLenguajeZ()
                ? ctx.getTablaTipos().resolverZ(nombreTipo)
                : ctx.getTablaTipos().resolver(nombreTipo);
        if (tipo == null) {
            ctx.agregarError(fila, columna, nombreTipo, "Tipo desconocido: " + nombreTipo);
            return ctx.getTablaTipos().getError();
        }
        return tipo;
    }

    public boolean esError(Tipo tipo) {
        return tipo == null || tipo.getTipoDato() == TipoDato.ERROR;
    }

    public boolean esNumerico(Tipo tipo) {
        return tipo != null && tipo.esNumerico();
    }

    public boolean esChar(Tipo tipo) {
        return tipo != null
                && tipo.getTipoDato() != null
                && tipo.getTipoDato() == TipoDato.CHAR;
    }

    public boolean esBooleano(Tipo tipo) {
        return tipo != null && tipo.getTipoDato() == TipoDato.BOOLEAN;
    }

    public boolean esCadena(Tipo tipo) {
        return tipo != null && tipo.getTipoDato() == TipoDato.CADENA;
    }

    public boolean esCompatibleConString(Tipo tipo) {
        return esCadena(tipo) || esNumerico(tipo) || esChar(tipo) || esBooleano(tipo);
    }

    public boolean esVoid(Tipo tipo) {
        return tipo != null && tipo.getTipoDato() == TipoDato.VOID;
    }

    public boolean esAsignable(Tipo destino, Tipo fuente) {
        if (destino == null || fuente == null) {
            return false;
        }
        if (esError(destino) || esError(fuente)) {
            return true;
        }
        if (fuente.getTipoDato() == TipoDato.NULO) {
            return destino.esPorReferencia();
        }
        if (destino instanceof TipoArreglo && fuente instanceof TipoArreglo) {
            return arreglosCompatibles((TipoArreglo) destino, (TipoArreglo) fuente);
        }
        if (destino instanceof TipoStructura && fuente instanceof TipoStructura
                && ((TipoStructura) fuente).esSubtipoDe(destino)) {
            return true;
        }
        if (destino.esIgual(fuente)) {
            return true;
        }
        if (destino.esNumerico() && fuente.esNumerico()) {
            return destino.getTipoDato() == TipoDato.DECIMAL;
        }
        return false;
    }

    private boolean arreglosCompatibles(TipoArreglo destino, TipoArreglo fuente) {
        if (!basesCompatibles(destino.getTipoBase(), fuente.getTipoBase())) {
            return false;
        }
        int numDimensiones = destino.getNumeroDimensiones();
        if (numDimensiones != fuente.getNumeroDimensiones()) {
            return false;
        }
        List<Integer> dimsDestino = destino.getDimensiones();
        List<Integer> dimsFuente = fuente.getDimensiones();
        for (int i = 0; i < numDimensiones; i++) {
            int dimDestino = dimsDestino.get(i);
            int dimFuente = dimsFuente.get(i);
            if (dimDestino > 0 && dimFuente > 0 && dimDestino != dimFuente) {
                return false;
            }
        }
        return true;
    }

    /**
     * Solo las clases aceptan subclases
     */
    private boolean basesCompatibles(Tipo destino, Tipo fuente) {
        if (destino instanceof TipoStructura && fuente instanceof TipoStructura) {
            return ((TipoStructura) fuente).esSubtipoDe(destino);
        }
        return destino.esIgual(fuente);
    }

    public boolean comparables(Tipo a, Tipo b) {
        if (a == null || b == null) {
            return false;
        }
        if (esError(a) || esError(b)) {
            return true;
        }
        if (a.esIgual(b)) {
            return true;
        }
        if (a.esNumerico() && b.esNumerico()) {
            return true;
        }
        if (a.getTipoDato() == TipoDato.NULO || b.getTipoDato() == TipoDato.NULO) {
            return a.esPorReferencia() && b.esPorReferencia();
        }
        return false;
    }

    public Tipo numeroResultado(TablaTipos tablaTipos, Tipo a, Tipo b) {
        if (a.getTipoDato() == TipoDato.DECIMAL || b.getTipoDato() == TipoDato.DECIMAL) {
            return tablaTipos.getDecimal();
        }
        return tablaTipos.getEntero();
    }

    public Tipo tipoDeLiteral(Contexto ctx, TipoDato tipoDato, int fila, int columna) {
        if (tipoDato == null) {
            ctx.agregarError(fila, columna, null, "Literal vacio");
            return ctx.getTablaTipos().getError();
        }
        switch (tipoDato) {
            case CHAR:
                return ctx.getTablaTipos().getCaracter();
            case CADENA:
                return ctx.getTablaTipos().getCadena();
            case BOOLEAN:
                return ctx.getTablaTipos().getBooleano();
            case DECIMAL:
                return ctx.getTablaTipos().getDecimal();
            case ENTERO:
                return ctx.getTablaTipos().getEntero();
            case NULO:
                return ctx.getTablaTipos().getNulo();
            default:
                break;
        }
        return null;
    }
}
