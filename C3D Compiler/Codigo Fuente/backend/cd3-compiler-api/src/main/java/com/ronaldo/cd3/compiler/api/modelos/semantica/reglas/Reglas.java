package com.ronaldo.cd3.compiler.api.modelos.semantica.reglas;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
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

    private final ReglasTipos tipos = new ReglasTipos();
    private final ReglasExpresiones expresiones = new ReglasExpresiones(tipos);
    private final ReglasFlujo flujo = new ReglasFlujo();
    private final ReglasSimbolos simbolos = new ReglasSimbolos(tipos);

    // ---- Tipos ----
    public Tipo resolverTipo(Contexto ctx, String nombreTipo, int fila, int columna) {
        return tipos.resolverTipo(ctx, nombreTipo, fila, columna);
    }

    public boolean esError(Tipo t) {
        return tipos.esError(t);
    }

    public boolean esNumerico(Tipo t) {
        return tipos.esNumerico(t);
    }

    public boolean esChar(Tipo t) {
        return tipos.esChar(t);
    }

    public boolean esBooleano(Tipo t) {
        return tipos.esBooleano(t);
    }

    public boolean esCadena(Tipo t) {
        return tipos.esCadena(t);
    }

    public boolean esCompatibleConString(Tipo t) {
        return tipos.esCompatibleConString(t);
    }

    public boolean esVoid(Tipo t) {
        return tipos.esVoid(t);
    }

    public boolean esAsignable(Tipo destino, Tipo fuente) {
        return tipos.esAsignable(destino, fuente);
    }

    public boolean comparables(Tipo a, Tipo b) {
        return tipos.comparables(a, b);
    }

    public Tipo numeroResultado(TablaTipos tt, Tipo a, Tipo b) {
        return tipos.numeroResultado(tt, a, b);
    }

    public Tipo tipoDeLiteral(Contexto ctx, TipoDato td, int fila, int columna) {
        return tipos.tipoDeLiteral(ctx, td, fila, columna);
    }

    // ---- Expresiones ----
    public Integer constanteEntera(Expresion e) {
        return expresiones.constanteEntera(e);
    }

    public boolean esCondicionValida(Contexto ctx, Expresion c) {
        return expresiones.esCondicionValida(ctx, c);
    }

    public boolean esLvalue(Expresion e) {
        return expresiones.esLvalue(e);
    }

    // ---- Flujo ----
    public void verificarInstrucciones(Contexto ctx, List<Instruccion> ins) {
        flujo.verificarInstrucciones(ctx, ins);
    }

    public boolean siempreRetorna(List<Instruccion> ins) {
        return flujo.siempreRetorna(ins);
    }

    public boolean siempreRetornaInstruccion(Instruccion i) {
        return flujo.siempreRetornaInstruccion(i);
    }

    // ---- Simbolos ----
    public SimboloVariable registrarVariable(Contexto ctx, String id, Tipo tipo, int fila, int columna) {
        return simbolos.registrarVariable(ctx, id, tipo, fila, columna);
    }

    public SimboloFuncion resolverFuncion(Contexto ctx, String nombre, List<Tipo> args) {
        return simbolos.resolverFuncion(ctx, nombre, args);
    }

    public SimboloFuncion resolverEntre(List<SimboloFuncion> sobrecargas, List<Tipo> args) {
        return simbolos.resolverEntre(sobrecargas, args);
    }
}
