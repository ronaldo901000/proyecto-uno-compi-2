package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.modelos.ir.AmbienteIR;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;

/**
 * Control de flujo: etiquetas (incluido el inicio de una unidad nueva),
 * saltos incondicionales y saltos condicionales.
 */
public class ControlFlujoRiscV {

    private final AmbienteIR ambiente;
    private final UnidadRiscV unidad;
    private final AccesoOperandosRiscV acceso;

    public ControlFlujoRiscV(AmbienteIR ambiente, UnidadRiscV unidad,
            AccesoOperandosRiscV acceso) {
        this.ambiente = ambiente;
        this.unidad = unidad;
        this.acceso = acceso;
    }

    /**
     * Si la etiqueta es el inicio de una unidad nueva, cierra la actual y
     * abre la nueva; si no, es una etiqueta interna de la unidad en curso.
     */
    public void emitirEtiqueta(Operando etiqueta) {
        String texto = etiqueta.getTexto();
        String actual = ambiente.getUnidadActual();
        if (actual != null && actual.equals(texto)
                && !actual.equals(unidad.getNombreEnCurso())) {
            unidad.cerrar();
            unidad.abrir(actual);
        } else {
            unidad.asegurar();
            unidad.etiqueta(texto);
        }
    }

    public void emitirSalto(Operando etiqueta) {
        unidad.linea("j " + etiqueta.getTexto());
    }

    public void emitirCondicional(Operando condicion, Operando etiqueta) {
        acceso.cargarValor(condicion, "t1");
        unidad.linea("bnez t1, " + etiqueta.getTexto());
    }
}