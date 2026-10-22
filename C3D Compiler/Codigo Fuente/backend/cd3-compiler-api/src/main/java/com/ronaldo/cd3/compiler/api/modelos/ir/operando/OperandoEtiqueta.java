package com.ronaldo.cd3.compiler.api.modelos.ir.operando;

import com.ronaldo.cd3.compiler.api.enums.RolOperando;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;

public class OperandoEtiqueta extends Operando {

    public OperandoEtiqueta(String texto, RolOperando rol) {
        super(texto, TipoOperando.ETIQUETA_INTERNA, null, rol);
    }
}
