package com.ronaldo.cd3.compiler.api.modelos.semantica;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Acceso;
import com.ronaldo.cd3.compiler.api.modelos.expresion.AccesoVariable;
import com.ronaldo.cd3.compiler.api.modelos.expresion.ExpIndice;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Continuar;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Romper;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.List;

/**
 * Reglas y utilidades para el analisis semantico.
 *
 * @author ronaldo
 */
public class Reglas {

    public Tipo resolverTipo(Contexto ctx, String nombreTipo, int fila, int columna) {
        if (nombreTipo == null) {
            ctx.agregarError(fila, columna, null, "Falta el tipo de dato");
            return ctx.getTablaTipos().getError();
        }
        Tipo tipo = ctx.getTablaTipos().resolver(nombreTipo);
        if (tipo == null) {
            ctx.agregarError(fila, columna, nombreTipo, "Tipo desconocido: " + nombreTipo);
            return ctx.getTablaTipos().getError();
        }
        return tipo;
    }

    public boolean esError(Tipo tipo) {
        return tipo == null || tipo.getTipoDato() == TipoDato.ERROR;
    }

    public boolean esNumerico(Tipo tipo) {
        return tipo != null && tipo.esNumerico();
    }

    public boolean esChar(Tipo tipo) {
        return tipo != null
                && tipo.getTipoDato() != null
                && tipo.getTipoDato() == TipoDato.CHAR;
    }

    public boolean esBooleano(Tipo tipo) {
        return tipo != null && tipo.getTipoDato() == TipoDato.BOOLEAN;
    }

    public boolean esCadena(Tipo tipo) {
        return tipo != null && tipo.getTipoDato() == TipoDato.CADENA;
    }

    public boolean esCompatibleConString(Tipo tipo) {
        return esCadena(tipo) || esNumerico(tipo) || esChar(tipo) || esBooleano(tipo);
    }

    public boolean esVoid(Tipo tipo) {
        return tipo != null && tipo.getTipoDato() == TipoDato.VOID;
    }

    public boolean esAsignable(Tipo destino, Tipo fuente) {
        if (destino == null || fuente == null) {
            return false;
        }
        if (esError(destino) || esError(fuente)) {
            return true;
        }
        if (fuente.getTipoDato() == TipoDato.NULO) {
            return destino.esPorReferencia();
        }
        if (destino.esIgual(fuente)) {
            return true;
        }
        if (destino.esNumerico() && fuente.esNumerico()) {
            return destino.getTipoDato() == TipoDato.DECIMAL;
        }
        return false;
    }

    public boolean comparables(Tipo a, Tipo b) {
        if (a == null || b == null) {
            return false;
        }
        if (esError(a) || esError(b)) {
            return true;
        }
        if (a.esIgual(b)) {
            return true;
        }
        if (a.esNumerico() && b.esNumerico()) {
            return true;
        }
        return false;
    }

    public Tipo numeroResultado(TablaTipos tablaTipos, Tipo a, Tipo b) {
        if (a.getTipoDato() == TipoDato.DECIMAL || b.getTipoDato() == TipoDato.DECIMAL) {
            return tablaTipos.getDecimal();
        }
        return tablaTipos.getEntero();
    }

    public Tipo tipoDeLiteral(Contexto ctx, String texto, int fila, int columna) {
        if (texto == null) {
            ctx.agregarError(fila, columna, null, "Literal vacio");
            return ctx.getTablaTipos().getError();
        }
        String valor = texto.trim();
        if (valor.matches("'-?[^']'")) {
            return ctx.getTablaTipos().getCaracter();
        }
        if (valor.startsWith("\"") && valor.endsWith("\"") && valor.length() >= 2) {
            return ctx.getTablaTipos().getCadena();
        }
        if (valor.equals("verdadero") || valor.equals("falso")) {
            return ctx.getTablaTipos().getBooleano();
        }
        if (valor.matches("-?[0-9]+\\.[0-9]+")) {
            return ctx.getTablaTipos().getDecimal();
        }
        if (valor.matches("-?[0-9]+")) {
            return ctx.getTablaTipos().getEntero();
        }
        ctx.agregarError(fila, columna, texto, "Literal no reconocido: " + texto);
        return ctx.getTablaTipos().getError();
    }

    public boolean esCondicionValida(Contexto ctx, Expresion condicion) {
        if (condicion == null) {
            return false;
        }
        condicion.verificarSemantica(ctx);
        if (!esBooleano(condicion.getTipo())) {
            ctx.agregarError(condicion.getFila(), condicion.getColumna(), null,
                    "La condicion debe ser de tipo booleano");
            return false;
        }
        return true;
    }

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
                            "Ya no se aceptan mas instrucciones despues de un break/romper o continue");
                }
            }

            if (instruccion instanceof Verificable) {

                ((Verificable) instruccion).verificarSemantica(ctx);

                if (instruccion instanceof Romper || instruccion instanceof Continuar) {
                    yaNoSeAceptanInstrucciones = true;
                }

            }
        }
    }

    public SimboloVariable registrarVariable(Contexto ctx, String id, Tipo tipo,
            int fila, int columna) {
        TablaSimbolos ambito = ctx.getAmbito();
        if (ambito.existeLocal(id)) {
            ctx.agregarError(fila, columna, id,
                    "Ya existe una variable llamada '" + id + "' en este ambito");
            return null;
        }
        SimboloVariable variable = new SimboloVariable(id, tipo, 0);
        ambito.asignarPosicion(variable);
        ambito.agregar(variable);
        return variable;
    }

    public boolean esLvalue(Expresion expresion) {
        return expresion instanceof AccesoVariable
                || expresion instanceof Acceso
                || expresion instanceof ExpIndice;
    }
}
