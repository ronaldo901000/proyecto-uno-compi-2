package com.ronaldo.cd3.compiler.api.modelos.ir.operando;

import com.ronaldo.cd3.compiler.api.enums.RolOperando;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

public class OperandoTemporal extends Operando {

    public OperandoTemporal(String texto, Tipo tipo, RolOperando rol) {
        super(texto, TipoOperando.TEMPORAL, tipo, rol);
    }
}
