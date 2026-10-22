package com.ronaldo.cd3.compiler.api.modelos.ir.operando;

import com.ronaldo.cd3.compiler.api.enums.RolOperando;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

public class OperandoIndice extends Operando {

    private final Operando base;
    private final Operando indice;
    private final int tamañoElemento;

    public OperandoIndice(String texto, Tipo tipo, RolOperando rol,
            Operando base, Operando indice, int tamañoElemento) {
        super(texto, base.getCategoria(), tipo, rol);
        this.base = base;
        this.indice = indice;
        this.tamañoElemento = tamañoElemento;
    }

    public Operando getBase() {
        return base;
    }

    public Operando getIndice() {
        return indice;
    }

    public int getTamañoElemento() {
        return tamañoElemento;
    }
}
