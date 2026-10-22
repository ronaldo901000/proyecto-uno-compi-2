package com.ronaldo.cd3.compiler.api.modelos.ir.operando;

import com.ronaldo.cd3.compiler.api.enums.RolOperando;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

public class OperandoAtributo extends Operando {

    private final Operando base;
    private final String nombreAtributo;
    private final int offset;

    public OperandoAtributo(String texto, Tipo tipo, RolOperando rol,
            Operando base, String nombreAtributo, int offset) {
        super(texto, base.getCategoria(), tipo, rol);
        this.base = base;
        this.nombreAtributo = nombreAtributo;
        this.offset = offset;
    }

    public Operando getBase() {
        return base;
    }

    public String getNombreAtributo() {
        return nombreAtributo;
    }

    public int getOffset() {
        return offset;
    }
}
