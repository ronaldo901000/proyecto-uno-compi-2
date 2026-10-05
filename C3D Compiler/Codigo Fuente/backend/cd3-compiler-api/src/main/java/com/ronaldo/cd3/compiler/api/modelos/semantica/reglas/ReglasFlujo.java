package com.ronaldo.cd3.compiler.api.modelos.semantica.reglas;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Continuar;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Retorno;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Romper;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.CicloHacerMientras;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional.InstSi;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional.RamaSino;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir.CasoSwitch;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir.InstElegir;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReglasFlujo {

    public void verificarInstrucciones(Contexto ctx, List<Instruccion> instrucciones) {
        if (instrucciones == null) {
            return;
        }
        boolean yaNoSeAceptanInstrucciones = false;

        for (Instruccion instruccion : instrucciones) {

            if (instruccion instanceof Nodo) {
                if (yaNoSeAceptanInstrucciones) {
                    ctx.agregarError(
                            ((Nodo) instruccion).getFila(),
                            ((Nodo) instruccion).getColumna(),
                            "",
                            "Ya no se aceptan mas instrucciones despues de un retornar, romper/break o continuar");
                }
            }

            if (instruccion instanceof Verificable) {

                ((Verificable) instruccion).verificarSemantica(ctx);

                if (instruccion instanceof Retorno
                        || instruccion instanceof Romper
                        || instruccion instanceof Continuar) {
                    yaNoSeAceptanInstrucciones = true;
                }
            }
        }
    }

    public boolean siempreRetorna(List<Instruccion> instrucciones) {
        if (instrucciones == null) {
            return false;
        }
        for (Instruccion instruccion : instrucciones) {
            if (siempreRetornaInstruccion(instruccion)) {
                return true;
            }
        }
        return false;
    }

    public boolean siempreRetornaInstruccion(Instruccion instruccion) {
        if (instruccion instanceof Retorno) {
            return true;
        }
        if (instruccion instanceof InstSi) {
            InstSi si = (InstSi) instruccion;
            return siempreRetorna(si.getInstruccionesInternasSi())
                    && todasRamasRetornan(si.getRamasSino())
                    && si.getInstruccionesInternasContrario() != null
                    && siempreRetorna(si.getInstruccionesInternasContrario());
        }
        if (instruccion instanceof InstElegir) {
            InstElegir elegir = (InstElegir) instruccion;
            boolean hayDefecto = false;
            if (elegir.getCasos() == null) {
                return false;
            }
            for (CasoSwitch caso : elegir.getCasos()) {
                if (caso.getValor() == null) {
                    hayDefecto = true;
                }
                if (!siempreRetorna(caso.getIntruccionesInternas())) {
                    return false;
                }
            }
            return hayDefecto;
        }
        if (instruccion instanceof CicloHacerMientras) {
            return siempreRetorna(((CicloHacerMientras) instruccion).getInstruccionesInternas());
        }
        return false;
    }

    private boolean todasRamasRetornan(List<RamaSino> ramas) {
        if (ramas == null) {
            return true;
        }
        for (RamaSino rama : ramas) {
            if (!siempreRetorna(rama.getInstruccionesInternas())) {
                return false;
            }
        }
        return true;
    }
}
