package com.ronaldo.cd3.compiler.api.modelos.ir;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Cuarteta;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;

public class MotorIR {

    private final ListaCuartetas cuartetas;
    private final AmbienteIR ambiente;

    public MotorIR(ListaCuartetas cuartetas) {
        this.cuartetas = cuartetas;
        this.ambiente = new AmbienteIR(cuartetas);
    }

    public AmbienteIR getAmbiente() {
        return ambiente;
    }

    public void recorrer(EmisorIR emisor) {
        for (Cuarteta cuarteta : cuartetas.getCuartetas()) {
            actualizarUnidadActual(cuarteta);
            cuarteta.aceptar(emisor);
        }
    }

    private void actualizarUnidadActual(Cuarteta cuarteta) {
        if (cuarteta.getOperador() != OperadorCuarteta.ETIQUETA) {
            return;
        }
        String etiqueta = cuarteta.getArg1();
        if (etiqueta != null
                && cuartetas.categoriaDe(etiqueta) != TipoOperando.ETIQUETA_INTERNA) {
            ambiente.setUnidadActual(etiqueta);
        }
    }
}
