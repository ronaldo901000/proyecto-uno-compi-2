package com.ronaldo.cd3.compiler.api.modelos.tipos;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;

/**
 *
 * @author ronaldo
 */
public class TipoError extends Tipo {

    public TipoError() {
        super(TipoDato.ERROR);
    }
    
    @Override
    public boolean esIgual(Tipo otro) {
        return otro instanceof TipoError;
    }

    @Override
    public boolean esPorReferencia() {
        return false;
    }

    @Override
    public boolean esNumerico() {
        return false;
    }

    @Override
    public int tamanoBytes() {
        return 0;
    }

    @Override
    public String tipoC() {
        return "error";
    }

    @Override
    public String toString() {
        return "error";
    }
}
