package com.ronaldo.cd3.compiler.api.modelos.ir.operando;

import com.ronaldo.cd3.compiler.api.enums.RolOperando;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

public class OperandoParametro extends Operando {

    public OperandoParametro(String texto, Tipo tipo, RolOperando rol) {
        super(texto, TipoOperando.VARIABLE, tipo, rol);
    }
}
