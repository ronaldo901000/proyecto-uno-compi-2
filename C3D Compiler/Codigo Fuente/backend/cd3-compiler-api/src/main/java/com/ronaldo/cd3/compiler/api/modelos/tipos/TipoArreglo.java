package com.ronaldo.cd3.compiler.api.modelos.tipos;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class TipoArreglo extends Tipo {

    private final Tipo tipoBase;
    private final List<Integer> dimensiones;

    public TipoArreglo(Tipo tipoBase, List<Integer> dimensiones) {
        super(TipoDato.ARREGLO);
        this.tipoBase = tipoBase;
        this.dimensiones = new ArrayList<>(dimensiones);
    }

    public TipoArreglo(Tipo tipoBase, int numeroDimensiones) {
        this(tipoBase, Collections.nCopies(numeroDimensiones, 0));
    }

    public Tipo getTipoBase() {
        return tipoBase;
    }

    public List<Integer> getDimensiones() {
        return Collections.unmodifiableList(dimensiones);
    }

    public int getNumeroDimensiones() {
        return dimensiones.size();
    }

    public void actualizarDimension(int indice, int extension) {
        if (indice < 0 || indice >= dimensiones.size()) {
            return;
        }
        dimensiones.set(indice, extension);
    }

    public int getTotalElementos() {
        int total = 1;
        for (Integer ext : dimensiones) {
            total *= (ext != null && ext > 0) ? ext : 1;
        }
        return total;
    }

    @Override
    public boolean esIgual(Tipo otro) {
        if (!(otro instanceof TipoArreglo)) {
            return false;
        }
        TipoArreglo otroArreglo = (TipoArreglo) otro;
        return this.dimensiones.equals(otroArreglo.getDimensiones())
                && this.tipoBase != null
                && this.tipoBase.esIgual(otroArreglo.getTipoBase());
    }

    @Override
    public boolean esPorReferencia() {
        return true;
    }

    @Override
    public boolean esNumerico() {
        return false;
    }

    @Override
    public int tamanoBytes() {
        return this.tipoBase.tamanoBytes() * this.getTotalElementos();
    }

    @Override
    public String tipoC() {
        return this.tipoBase.tipoC() + "*";
    }

    @Override
    public String toString() {
        return "["
                + this.tipoBase
                + " x"
                + this.dimensiones.size()
                + "]";
    }
}