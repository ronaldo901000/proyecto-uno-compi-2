package com.ronaldo.cd3.compiler.api.modelos.semantica;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Acceso;
import com.ronaldo.cd3.compiler.api.modelos.expresion.AccesoVariable;
import com.ronaldo.cd3.compiler.api.modelos.expresion.ExpIndice;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Literal;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Operacion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Unario;
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
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import java.util.ArrayList;
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
        Tipo tipo = ctx.esLenguajeZ()
                ? ctx.getTablaTipos().resolverZ(nombreTipo)
                : ctx.getTablaTipos().resolver(nombreTipo);
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
        if (destino instanceof TipoArreglo && fuente instanceof TipoArreglo) {
            return arreglosCompatibles((TipoArreglo) destino, (TipoArreglo) fuente);
        }
        if (destino.esIgual(fuente)) {
            return true;
        }
        if (destino.esNumerico() && fuente.esNumerico()) {
            return destino.getTipoDato() == TipoDato.DECIMAL;
        }
        return false;
    }

    private boolean arreglosCompatibles(TipoArreglo destino, TipoArreglo fuente) {
        if (!destino.getTipoBase().esIgual(fuente.getTipoBase())) {
            return false;
        }
        int numDimensiones = destino.getNumeroDimensiones();
        if (numDimensiones != fuente.getNumeroDimensiones()) {
            return false;
        }
        List<Integer> dimsDestino = destino.getDimensiones();
        List<Integer> dimsFuente = fuente.getDimensiones();
        for (int i = 0; i < numDimensiones; i++) {
            int dimDestino = dimsDestino.get(i);
            int dimFuente = dimsFuente.get(i);
            if (dimDestino > 0 && dimFuente > 0 && dimDestino != dimFuente) {
                return false;
            }
        }
        return true;
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
        if (a.getTipoDato() == TipoDato.NULO || b.getTipoDato() == TipoDato.NULO) {
            return a.esPorReferencia() && b.esPorReferencia();
        }
        return false;
    }

    public Tipo numeroResultado(TablaTipos tablaTipos, Tipo a, Tipo b) {
        if (a.getTipoDato() == TipoDato.DECIMAL || b.getTipoDato() == TipoDato.DECIMAL) {
            return tablaTipos.getDecimal();
        }
        return tablaTipos.getEntero();
    }

    public Tipo tipoDeLiteral(Contexto ctx, TipoDato tipoDato, int fila, int columna) {
        if (tipoDato == null) {
            ctx.agregarError(fila, columna, null, "Literal vacio");
            return ctx.getTablaTipos().getError();
        }

        switch (tipoDato) {
            case CHAR:
                return ctx.getTablaTipos().getCaracter();
            case CADENA:
                return ctx.getTablaTipos().getCadena();
            case BOOLEAN:
                return ctx.getTablaTipos().getBooleano();
            case DECIMAL:
                return ctx.getTablaTipos().getDecimal();
            case ENTERO:
                return ctx.getTablaTipos().getEntero();
            case NULO:
                return ctx.getTablaTipos().getNulo();
            default:
                break;
        }
        return null;
    }

    public Integer constanteEntera(Expresion expresion) {
        if (expresion == null) {
            return null;
        }
        if (expresion instanceof Literal) {
            Object contenido = ((Literal) expresion).getContenido();
            if (contenido == null) {
                return null;
            }
            String texto;
            if (contenido instanceof org.antlr.v4.runtime.Token) {
                texto = ((org.antlr.v4.runtime.Token) contenido).getText();
            } else {
                texto = String.valueOf(contenido);
            }
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        if (expresion instanceof Unario) {
            Unario unario = (Unario) expresion;
            Integer valor = constanteEntera(unario.getExp());
            if (valor == null) {
                return null;
            }
            if (unario.getOperador() == Operador.NEGATIVO_UNARIO) {
                return -valor;
            }
            if (unario.getOperador() == Operador.POSITIVO_UNARIO) {
                return valor;
            }
            return null;
        }
        if (expresion instanceof Operacion) {
            Operacion operacion = (Operacion) expresion;
            Integer izq = constanteEntera(operacion.getIzquierda());
            Integer der = constanteEntera(operacion.getDerecha());
            if (izq == null || der == null) {
                return null;
            }
            switch (operacion.getOperador()) {
                case SUMA:
                    return izq + der;
                case RESTA:
                    return izq - der;
                case MULTIPLICACION:
                    return izq * der;
                case DIVISION:
                    return der != 0 ? izq / der : null;
                case MODULO:
                    return der != 0 ? izq % der : null;
                default:
                    return null;
            }
        }
        return null;
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

    public SimboloVariable registrarVariable(Contexto ctx, String id, Tipo tipo,
            int fila, int columna) {
        TablaSimbolos ambito = ctx.getAmbito();
        if (ambito.existeLocal(id) || ambito.hayFuncion(id)) {
            ctx.agregarError(fila, columna, id,
                    "Ya existe una variable llamada '" + id + "' en este ambito");
            return null;
        }
        SimboloVariable variable = new SimboloVariable(id, tipo, 0);
        ambito.asignarPosicion(variable);
        ambito.agregar(variable);
        return variable;
    }

    public SimboloFuncion resolverFuncion(Contexto ctx, String nombre,
            List<Tipo> tiposArgumentos) {
        List<SimboloFuncion> sobrecargas = ctx.getAmbito().buscarSobrecargas(nombre);
        if (sobrecargas.isEmpty()) {
            return null;
        }
        return resolverEntre(sobrecargas, tiposArgumentos);
    }

    public SimboloFuncion resolverEntre(List<SimboloFuncion> sobrecargas,
            List<Tipo> tiposArgumentos) {
        
        if (sobrecargas == null || sobrecargas.isEmpty()) {
            return null;
        }
        
        int numeroArgumentos = (tiposArgumentos != null) ? tiposArgumentos.size() : 0;
        
        List<SimboloFuncion> mismaArity = new ArrayList<>();
        
        for (SimboloFuncion sobrecarga : sobrecargas) {
            if (sobrecarga.getParametros().size() == numeroArgumentos) {
                mismaArity.add(sobrecarga);
            }
        }
        
        if (mismaArity.isEmpty()) {
            return null;
        }
        SimboloFuncion exacta = null;
        SimboloFuncion candidata = null;
        for (SimboloFuncion sobrecarga : mismaArity) {
            boolean coincideExacto = true;
            boolean coincideAsignable = true;
            List<SimboloParametro> parametros = sobrecarga.getParametros();
            for (int i = 0; i < parametros.size(); i++) {
                Tipo tipoParametro = parametros.get(i).getTipo();
                Tipo tipoArgumento = tiposArgumentos.get(i);
                if (!tiposCoincidenExacto(tipoParametro, tipoArgumento)) {
                    coincideExacto = false;
                }
                if (!esAsignable(tipoParametro, tipoArgumento)) {
                    coincideAsignable = false;
                }
            }
            if (coincideExacto) {
                if (exacta != null) {
                    return null;
                }
                exacta = sobrecarga;
            } else if (coincideAsignable) {
                if (candidata != null) {
                    return null;
                }
                candidata = sobrecarga;
            }
        }
        return (exacta != null) ? exacta : candidata;
    }

    private boolean tiposCoincidenExacto(Tipo parametro, Tipo argumento) {
        return parametro != null && argumento != null && parametro.esIgual(argumento);
    }

    public boolean esLvalue(Expresion expresion) {
        return expresion instanceof AccesoVariable
                || expresion instanceof Acceso
                || expresion instanceof ExpIndice;
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
