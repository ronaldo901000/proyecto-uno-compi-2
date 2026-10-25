package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoIndice;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoLiteral;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;

/**
 * Memoria: reserva de objetos y arreglos en el heap, acceso por indice y
 * asignacion (incluida la copia de structs por valor).
 * @author ronaldo
 */
public class MemoriaRiscV {

    private final UnidadRiscV unidad;
    private final AccesoOperandosRiscV acceso;
    private final AritmeticaRiscV aritmetica;
    private final TiposRiscV tipos;

    private int etiquetaNew = 0;

    public MemoriaRiscV(UnidadRiscV unidad, AccesoOperandosRiscV acceso,
            AritmeticaRiscV aritmetica, TiposRiscV tipos) {
        this.unidad = unidad;
        this.acceso = acceso;
        this.aritmetica = aritmetica;
        this.tipos = tipos;
    }


    public void emitirAsignacion(Operando destino, Operando origen) {
        if (tipos.esStructPorValor(destino) && tipos.esStructPorValor(origen)) {
            copiarStruct(destino, origen);
            return;
        }
        // Coercion: destino decimal y origen entero -> promover (x1000)
        if (tipos.esDecimal(destino) && !tipos.esDecimal(origen) && tipos.esEntero(origen)) {
            acceso.cargarValor(origen, "t1");
            aritmetica.promover("t1");
            acceso.guardarValor(destino, "t1");
            return;
        }
        acceso.cargarValor(origen, "t1");
        acceso.guardarValor(destino, "t1");
    }


    private void copiarStruct(Operando destino, Operando origen) {
        int tamaño = ((TipoStructura) destino.getTipo()).getTamañoHeap();
        int palabras = Math.min(tamaño / 4 + ((tamaño % 4 != 0) ? 1 : 0), 4);
        acceso.cargarDireccion(destino, "t2");
        acceso.cargarDireccion(origen, "t3");
        for (int i = 0; i < palabras; i++) {
            unidad.linea("lw t1, " + (i * 4) + "(t3)");
            unidad.linea("sw t1, " + (i * 4) + "(t2)");
        }
    }

    
    public void emitirPunteroInicio(Operando destino, Operando cantidad) {
        // Arreglo 1D con tamaño NO constante: calcular bytes en ejecucion
        if (destino != null && destino.getTipo() instanceof TipoArreglo
                && cantidad != null && !(cantidad instanceof OperandoLiteral)) {
            Tipo base = ((TipoArreglo) destino.getTipo()).getTipoBase();
            int tamañoBase = (base instanceof TipoStructura || base instanceof TipoArreglo)
                    ? 4
                    : Math.max(1, base.tamañoBytes());
            acceso.cargarValor(cantidad, "t1");
            unidad.linea("li t2, " + tamañoBase);
            unidad.linea("mul a0, t1, t2");
            unidad.linea("addi a0, a0, 3");
            unidad.linea("andi a0, a0, -4");
            unidad.linea("call z_alloc");
            acceso.guardarValor(destino, "a0");
            return;
        }
        unidad.linea("li a0, " + bytesDeObjeto(destino, cantidad));
        unidad.linea("call z_alloc");
        acceso.guardarValor(destino, "a0");
    }

    /**
     * Bytes a reservar para un objeto o arreglo de tamaño conocido.
     */
    private int bytesDeObjeto(Operando destino, Operando cantidad) {
        if (destino == null || destino.getTipo() == null) {
            return 4;
        }
        if (destino.getTipo() instanceof TipoArreglo) {
            TipoArreglo arreglo = (TipoArreglo) destino.getTipo();
            Tipo base = arreglo.getTipoBase();
            int tamañoBase = (base instanceof TipoStructura || base instanceof TipoArreglo)
                    ? 4
                    : base.tamañoBytes();
            int cuenta = arreglo.getTotalElementos();
            if (cantidad != null && cantidad.getTipo() != null
                    && cantidad.getTipo().getTipoDato() == TipoDato.ENTERO
                    && cantidad instanceof OperandoLiteral) {
                cuenta = acceso.valorDeLiteral((OperandoLiteral) cantidad);
            }
            return Math.max(1, cuenta) * tamañoBase;
        }
        if (destino.getTipo() instanceof TipoStructura) {
            return ((TipoStructura) destino.getTipo()).getTamañoHeap();
        }
        return 4;
    }

    
    public void emitirNuevoArreglo2D(Operando destino, Operando filas, Operando columnas) {
        int tam = tipos.tamañoBaseHoja(destino.getTipo());
        int n = etiquetaNew++;
        acceso.cargarValor(filas, "t1");
        acceso.cargarValor(columnas, "t2");
        unidad.linea("addi sp, sp, -16");
        unidad.linea("sw t1, 0(sp)");
        unidad.linea("sw t2, 4(sp)");
        unidad.linea("slli a0, t1, 2");
        unidad.linea("call z_alloc");
        unidad.linea("sw a0, 8(sp)");
        unidad.linea("sw zero, 12(sp)");
        unidad.etiqueta("Lnew_" + n + "_loop");
        unidad.linea("lw t0, 12(sp)");
        unidad.linea("lw t1, 0(sp)");
        unidad.linea("bge t0, t1, Lnew_" + n + "_fin");
        unidad.linea("lw a0, 4(sp)");
        unidad.linea("li t2, " + tam);
        unidad.linea("mul a0, a0, t2");
        unidad.linea("addi a0, a0, 3");
        unidad.linea("andi a0, a0, -4");
        unidad.linea("call z_alloc");
        unidad.linea("lw t3, 8(sp)");
        unidad.linea("lw t0, 12(sp)");
        unidad.linea("slli t4, t0, 2");
        unidad.linea("add t3, t3, t4");
        unidad.linea("sw a0, 0(t3)");
        unidad.linea("addi t0, t0, 1");
        unidad.linea("sw t0, 12(sp)");
        unidad.linea("j Lnew_" + n + "_loop");
        unidad.etiqueta("Lnew_" + n + "_fin");
        unidad.linea("lw a0, 8(sp)");
        unidad.linea("addi sp, sp, 16");
        acceso.guardarValor(destino, "a0");
    }


    public void emitirAccesoIndice(Operando base, Operando indice, Operando destino) {
        acceso.cargarValor(indice, "t2");
        acceso.cargarValor(base, "t1");
        unidad.linea("li t3, " + tipos.tamañoElemento(destino));
        unidad.linea("mul t2, t2, t3");
        unidad.linea("add t1, t1, t2");
        if (destino instanceof OperandoIndice) {
            return;
        }
        acceso.cargarDesdeDireccion("t3", "t1", tipos.tamañoAlmacen(destino));
        acceso.guardarValor(destino, "t3");
    }
}