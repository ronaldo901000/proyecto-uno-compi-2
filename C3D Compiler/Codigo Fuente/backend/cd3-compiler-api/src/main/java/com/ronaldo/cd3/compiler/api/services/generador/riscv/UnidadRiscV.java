package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ronaldo
 */
public class UnidadRiscV {

    private static final int LIMITE_PARAMETROS = 8;

    private final ListaCuartetas cuartetas;
    private final FrameRiscV frame;
    private final List<Operando> parametrosPendientes;

    private final StringBuilder cuerpo = new StringBuilder();
    private final StringBuilder unidad = new StringBuilder();
    private final List<String> parametros = new ArrayList<>();

    private String unidadEnCurso;
    private boolean metodoEnCurso;

    public UnidadRiscV(ListaCuartetas cuartetas, FrameRiscV frame,
            List<Operando> parametrosPendientes) {
        this.cuartetas = cuartetas;
        this.frame = frame;
        this.parametrosPendientes = parametrosPendientes;
    }

    public String getCuerpo() {
        return cuerpo.toString();
    }

    public String getNombreEnCurso() {
        return unidadEnCurso;
    }


    public void linea(String instruccion) {
        unidad.append("    ").append(instruccion).append("\n");
    }


    public void etiqueta(String nombre) {
        unidad.append(nombre).append(":\n");
    }


    public void asegurar() {
        if (unidadEnCurso == null) {
            abrir("__global");
        }
    }

    
    public void abrir(String nombre) {
        unidadEnCurso = nombre;
        unidad.setLength(0);
        frame.reiniciar();
        parametros.clear();
        parametrosPendientes.clear();
        metodoEnCurso = nombre != null
                && (nombre.startsWith("metodo_") || nombre.startsWith("constructor_"));
        if (metodoEnCurso) {
            frame.asignarRanura("=this");
        }
        if (cuartetas != null && nombre != null) {
            List<SimboloParametro> lista = cuartetas.parametrosDeFuncion(nombre);
            if (lista != null) {
                for (SimboloParametro p : lista) {
                    parametros.add(p.getId());
                    frame.asignarRanura(frame.claveParametro(p.getId()));
                }
            }
        }
    }


    public void cerrar() {
        if (unidadEnCurso == null) {
            return;
        }
        int tamaño = frame.tamañoTotal();

        // Prologo
        cuerpo.append(unidadEnCurso).append(":\n");
        cuerpo.append("    addi sp, sp, -").append(tamaño).append("\n");
        cuerpo.append("    sw ra, ").append(tamaño - 4).append("(sp)\n");
        cuerpo.append("    sw fp, ").append(tamaño - 8).append("(sp)\n");
        cuerpo.append("    addi fp, sp, ").append(tamaño).append("\n");
        if (metodoEnCurso) {
            cuerpo.append("    sw a0, ").append(frame.offsetDeClave("=this")).append("(fp)\n");
        }

        // Parametros: de registros (a0-a7) o de la pila hacia sus ranuras
        for (int i = 0; i < parametros.size(); i++) {
            int registro = i + (metodoEnCurso ? 1 : 0);
            String slot = frame.offsetDeClave(frame.claveParametro(parametros.get(i)));
            if (registro < LIMITE_PARAMETROS) {
                cuerpo.append("    sw a").append(registro).append(", ")
                        .append(slot).append("(fp)\n");
            } else {
                cuerpo.append("    lw t1, ")
                        .append((registro - LIMITE_PARAMETROS) * 4).append("(fp)\n");
                cuerpo.append("    sw t1, ").append(slot).append("(fp)\n");
            }
        }

        // Cuerpo
        cuerpo.append(unidad);

        // Epilogo
        cuerpo.append("__epilogo_").append(unidadEnCurso).append(":\n");
        if (unidadEnCurso.startsWith("constructor_")) {
            cuerpo.append("    lw a0, ").append(frame.offsetDeClave("=this")).append("(fp)\n");
        }
        cuerpo.append("    lw t0, -8(fp)\n");
        cuerpo.append("    lw ra, -4(fp)\n");
        cuerpo.append("    addi sp, sp, ").append(tamaño).append("\n");
        cuerpo.append("    mv fp, t0\n");
        if (unidadEnCurso.startsWith("__entrada_")) {
            cuerpo.append("    j z_exit\n\n");
        } else {
            cuerpo.append("    ret\n\n");
        }
        unidadEnCurso = null;
    }
}