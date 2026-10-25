package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.ir.runtime.RuntimeZ;

/**
 * Cadenas y entrada/salida: concatenacion, conversion de valores a texto,
 * impresion y lectura.
 */
public class CadenasRiscV {

    private static final int TAMAÑO_BUFFER_CADENA = 256;
    private final UnidadRiscV unidad;
    private final AccesoOperandosRiscV acceso;
    private final TiposRiscV tipos;

    public CadenasRiscV(UnidadRiscV unidad, AccesoOperandosRiscV acceso, TiposRiscV tipos) {
        this.unidad = unidad;
        this.acceso = acceso;
        this.tipos = tipos;
    }


    public void emitirConcatena(Operando izquierda, Operando derecha, Operando resultado) {
        acceso.cargarValor(izquierda, "t1");
        acceso.cargarValor(derecha, "t2");
        unidad.linea("addi sp, sp, -12");
        unidad.linea("sw t1, 0(sp)");
        unidad.linea("sw t2, 4(sp)");
        unidad.linea("sw zero, 8(sp)");

        unidad.linea("lw a0, 0(sp)");
        convertirACadena(izquierda);
        unidad.linea("sw a0, 8(sp)");

        unidad.linea("lw a0, 4(sp)");
        convertirACadena(derecha);
        unidad.linea("mv a1, a0");
        unidad.linea("lw a0, 8(sp)"); 
        unidad.linea("call z_concat");

        unidad.linea("addi sp, sp, 12");
        acceso.guardarValor(resultado, "a0");
    }


    private void convertirACadena(Operando operando) {
        if (tipos.esCadena(operando)) {
            return;
        }
        if (tipos.esDecimal(operando)) {
            unidad.linea("call z_ftoa");
        } else if (tipos.esCaracter(operando)) {
            unidad.linea("call z_chr");
        } else {
            unidad.linea("call z_itoa");
        }
    }


    public void emitirImprimir(Operando valor, boolean conSaltoLinea) {
        if (valor != null) {
            String rutina = RuntimeZ.rutinaImpresion(valor.getTipo());
            if (rutina != null) {
                acceso.cargarValor(valor, "a0");
                unidad.linea("call " + rutina);
            }
        }
        if (conSaltoLinea) {
            unidad.linea("call " + RuntimeZ.imprimirSaltoDeLinea());
        }
    }

    public void emitirLeer(Operando destino) {
        if (destino == null || destino.getTipo() == null) {
            return;
        }
        if (destino.getTipo().getTipoDato() == TipoDato.CADENA) {
            unidad.linea("li a0, " + TAMAÑO_BUFFER_CADENA);
            unidad.linea("call z_alloc");
            unidad.linea("mv t0, a0");
            unidad.linea("li a1, " + TAMAÑO_BUFFER_CADENA);
            unidad.linea("call z_leer_cadena");
            unidad.linea("mv a0, t0");
            acceso.guardarValor(destino, "a0");
            return;
        }
        String rutina = RuntimeZ.rutinaLectura(destino.getTipo());
        if (rutina == null) {
            return;
        }
        unidad.linea("call " + rutina);
        acceso.guardarValor(destino, "a0");
    }
}