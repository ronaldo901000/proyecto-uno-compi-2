package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoThis;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author ronaldo
 */
public class FrameRiscV {

    public static final int TAMAÑO_SLOT = 4;
    public static final int TAMAÑO_ENCABEZADO = 8;

    private final Map<String, Integer> ranuras = new LinkedHashMap<>();
    private int siguienteOffset;


    public void reiniciar() {
        ranuras.clear();
        siguienteOffset = 0;
    }

 
    public int tamañoTotal() {
        return alinear4(TAMAÑO_ENCABEZADO + siguienteOffset);
    }

    public String claveParametro(String nombre) {
        return "OperandoParametro:" + nombre;
    }

    public String clave(Operando operando) {
        if (operando instanceof OperandoThis) {
            return "=this";
        }
        return operando.getClass().getSimpleName() + ":" + operando.getTexto();
    }

    public int asignarRanura(String clave) {
        return asignarRanura(clave, TAMAÑO_SLOT);
    }

    public int asignarRanura(String clave, int tamaño) {
        Integer existente = ranuras.get(clave);
        if (existente != null) {
            return existente;
        }
        int reservado = (tamaño > TAMAÑO_SLOT) ? alinear4(tamaño) : TAMAÑO_SLOT;
        siguienteOffset += reservado;
        ranuras.put(clave, siguienteOffset);
        return siguienteOffset;
    }


    public String offsetDeClave(String clave) {
        int fin = asignarRanura(clave);
        return String.valueOf(-TAMAÑO_ENCABEZADO - fin);
    }


    public String offset(Operando operando) {
        int fin = asignarRanura(clave(operando), tamañoDeOperando(operando));
        return String.valueOf(-TAMAÑO_ENCABEZADO - fin);
    }

    private int tamañoDeOperando(Operando operando) {
        Tipo tipo = (operando != null) ? operando.getTipo() : null;
        if (tipo instanceof TipoStructura && !((TipoStructura) tipo).esPorReferencia()) {
            return Math.max(TAMAÑO_SLOT, ((TipoStructura) tipo).getTamañoHeap());
        }
        return TAMAÑO_SLOT;
    }

    private int alinear4(int valor) {
        return (valor + 3) & ~3;
    }
}