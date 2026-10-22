package com.ronaldo.cd3.compiler.api.modelos.ir.operando;

import com.ronaldo.cd3.compiler.api.enums.RolOperando;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

public class OperandoLiteral extends Operando {

    private final String etiqueta;

    public OperandoLiteral(String texto, TipoOperando categoria, Tipo tipo,
            RolOperando rol) {
        this(texto, categoria, tipo, rol, null);
    }

    public OperandoLiteral(String texto, TipoOperando categoria, Tipo tipo,
            RolOperando rol, String etiqueta) {
        super(texto, categoria, tipo, rol);
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public boolean esEnMemoria() {
        return etiqueta != null;
    }
}
