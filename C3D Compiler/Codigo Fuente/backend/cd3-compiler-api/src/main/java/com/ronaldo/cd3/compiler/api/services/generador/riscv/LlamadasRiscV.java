package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoParametro;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;

import java.util.List;

/**
 * 
 * @author ronaldo
 */
public class LlamadasRiscV {

    private static final int REGISTROS_ARGUMENTOS = 8;
    private static final int TAMAÑO_SLOT = FrameRiscV.TAMAÑO_SLOT;

    private final ListaCuartetas cuartetas;
    private final UnidadRiscV unidad;
    private final FrameRiscV frame;
    private final AccesoOperandosRiscV acceso;
    private final AritmeticaRiscV aritmetica;
    private final TiposRiscV tipos;

    private final List<Operando> parametrosPendientes;


    public LlamadasRiscV(ListaCuartetas cuartetas, UnidadRiscV unidad, FrameRiscV frame,
            AccesoOperandosRiscV acceso, AritmeticaRiscV aritmetica, TiposRiscV tipos,
            List<Operando> parametrosPendientes) {
        this.cuartetas = cuartetas;
        this.unidad = unidad;
        this.frame = frame;
        this.acceso = acceso;
        this.aritmetica = aritmetica;
        this.tipos = tipos;
        this.parametrosPendientes = parametrosPendientes;
    }

    public void emitirParametro(Operando operando) {
        parametrosPendientes.add(operando);
    }


    public void emitirLlamada(Operando funcion, Operando resultado) {
        int total = parametrosPendientes.size();
        int registros = Math.min(total, REGISTROS_ARGUMENTOS);
        int extra = total - registros;
        reservarPilaExtra(extra);

        String nombre = (funcion != null) ? funcion.getTexto() : null;
        int desfase = (nombre != null
                && (nombre.startsWith("metodo_") || nombre.startsWith("constructor_"))) ? 1 : 0;
        List<SimboloParametro> params
                = (nombre != null) ? cuartetas.parametrosDeFuncion(nombre) : null;

        for (int i = 0; i < total; i++) {
            Operando argumento = parametrosPendientes.get(i);
            if (tipos.esStructPorValor(argumento)) {
                if (argumento instanceof OperandoParametro) {
                    acceso.cargarValor(argumento, "a" + i);
                } else {
                    unidad.linea("addi a" + i + ", fp, " + frame.offset(argumento));
                }
            } else if (debePromover(params, i - desfase, argumento)) {
                acceso.cargarValor(argumento, "t1");
                aritmetica.promover("t1");
                colocarArgumento(i, registros, "t1");
            } else if (i < registros) {
                acceso.cargarValor(argumento, "a" + i);
            } else {
                acceso.cargarValor(argumento, "t1");
                unidad.linea("sw t1, " + ((i - registros) * TAMAÑO_SLOT) + "(sp)");
            }
        }
        if (nombre != null) {
            unidad.linea("call " + nombre);
        }
        liberarPilaExtra(extra);
        guardarResultado(resultado);
        parametrosPendientes.clear();
    }


    private boolean debePromover(List<SimboloParametro> params, int indice, Operando argumento) {
        if (params == null || indice < 0 || indice >= params.size()) {
            return false;
        }
        SimboloParametro param = params.get(indice);
        return param.getTipo() != null
                && param.getTipo().getTipoDato() == TipoDato.DECIMAL
                && argumento != null && argumento.getTipo() != null
                && argumento.getTipo().getTipoDato() != TipoDato.DECIMAL
                && tipos.esEntero(argumento);
    }

    private void colocarArgumento(int i, int registros, String origen) {
        if (i < registros) {
            unidad.linea("mv a" + i + ", " + origen);
        } else {
            unidad.linea("sw " + origen + ", " + ((i - registros) * TAMAÑO_SLOT) + "(sp)");
        }
    }


    public void emitirLlamadaVirtual(Operando receptor, int slot, Operando resultado) {
        int total = parametrosPendientes.size();
        int registros = Math.min(total, REGISTROS_ARGUMENTOS);
        int extra = total - registros;
        reservarPilaExtra(extra);

        for (int i = 0; i < total; i++) {
            Operando argumento = parametrosPendientes.get(i);
            if (tipos.esStructPorValor(argumento)) {
                unidad.linea("addi a" + i + ", fp, " + frame.offset(argumento));
            } else if (i < registros) {
                acceso.cargarValor(argumento, "a" + i);
            } else {
                acceso.cargarValor(argumento, "t1");
                unidad.linea("sw t1, " + ((i - registros) * TAMAÑO_SLOT) + "(sp)");
            }
        }
        if (total > 0) {
            unidad.linea("mv t0, a0");
        } else if (receptor != null) {
            acceso.cargarValor(receptor, "t0");
        }
        unidad.linea("lw t0, 0(t0)");
        unidad.linea("lw t0, " + (slot * TAMAÑO_SLOT) + "(t0)");
        unidad.linea("jalr t0");
        liberarPilaExtra(extra);
        guardarResultado(resultado);
        parametrosPendientes.clear();
    }


    public void emitirFijarVtable(Operando objeto, String nombreClase) {
        if (objeto == null || nombreClase == null) {
            return;
        }
        unidad.linea("lw t6, " + frame.offset(objeto) + "(fp)");
        unidad.linea("la t1, __vtable_" + nombreClase);
        unidad.linea("sw t1, 0(t6)");
    }

    public void emitirRetorno(Operando valor) {
        if (valor != null) {
            if (tipos.esStructPorValor(valor)) {
                if (valor instanceof OperandoParametro) {
                    acceso.cargarValor(valor, "a0");
                } else {
                    unidad.linea("addi a0, fp, " + frame.offset(valor));
                }
            } else if (tipos.esDecimalRetorno(unidad.getNombreEnCurso()) && tipos.esEntero(valor)) {
                acceso.cargarValor(valor, "t1");
                aritmetica.promover("t1");
                unidad.linea("mv a0, t1");
            } else {
                acceso.cargarValor(valor, "a0");
            }
        }
        String enCurso = unidad.getNombreEnCurso();
        if (enCurso != null) {
            if (enCurso.startsWith("__entrada_")) {
                unidad.linea("j z_exit");
            } else {
                unidad.linea("j __epilogo_" + enCurso);
            }
        }
    }


    private void reservarPilaExtra(int extra) {
        if (extra > 0) {
            unidad.linea("addi sp, sp, -" + (extra * TAMAÑO_SLOT));
        }
    }

    private void liberarPilaExtra(int extra) {
        if (extra > 0) {
            unidad.linea("addi sp, sp, " + (extra * TAMAÑO_SLOT));
        }
    }

    private void guardarResultado(Operando resultado) {
        if (resultado != null) {
            acceso.guardarValor(resultado, "a0");
        }
    }
}