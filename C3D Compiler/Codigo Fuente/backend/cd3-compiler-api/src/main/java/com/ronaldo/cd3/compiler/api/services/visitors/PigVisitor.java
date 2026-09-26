package com.ronaldo.cd3.compiler.api.services.visitors;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.interfaces.Visitable;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Acceso;
import com.ronaldo.cd3.compiler.api.modelos.expresion.AccesoVariable;
import com.ronaldo.cd3.compiler.api.modelos.expresion.ExpIndice;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Lectura;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Literal;
import com.ronaldo.cd3.compiler.api.modelos.expresion.LiteralStructura;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Llamada;
import com.ronaldo.cd3.compiler.api.modelos.expresion.NewObjeto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Operacion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Unario;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Asignacion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Continuar;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Imprimir;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.IncrementoDecremento;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Romper;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.CicloHacerMientras;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.CicloMientras;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.CicloPara;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.DeclaracionIterador;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional.InstSi;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional.RamaSino;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.Declaracion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionArreglo;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionEstructura;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionVariable;
import com.ronaldo.cd3.compiler.api.modelos.programaPig.ImportacionPig;
import com.ronaldo.cd3.compiler.api.modelos.programaPig.ProgramaPig;
import com.ronaldo.cd3.compiler.api.pig.LenguajePigBaseVisitor;
import com.ronaldo.cd3.compiler.api.pig.LenguajePigParser;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class PigVisitor extends LenguajePigBaseVisitor<Visitable> {

    @Override
    public ProgramaPig visitPig(LenguajePigParser.PigContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        List<ImportacionPig> imports = new ArrayList<>();
        for (LenguajePigParser.ImportacionContext i : ctx.importacion()) {
            int filaImporte = i.importe().start.getLine();
            int columnaImporte = i.importe().start.getCharPositionInLine();
            imports.add(new ImportacionPig(i.importe().getText(), filaImporte, columnaImporte));
        }

        List<Instruccion> declaraciones = new ArrayList<>();
        if (ctx.bloque_variabile() != null) {
            for (LenguajePigParser.DeclaracionContext d : ctx.bloque_variabile().declaracion()) {
                Instruccion inst = (Instruccion) visit(d);
                if (inst != null) {
                    declaraciones.add(inst);
                }
            }
        }

        List<Instruccion> instrucciones = new ArrayList<>();
        if (ctx.bloque_maior() != null) {
            for (LenguajePigParser.InstruccionContext i : ctx.bloque_maior().instruccion()) {
                Instruccion inst = (Instruccion) visitInstruccion(i);
                if (inst != null) {
                    instrucciones.add(inst);
                }
            }
        }

        return new ProgramaPig(imports, declaraciones, instrucciones, fila, columna);
    }

    @Override
    public Visitable visitInstruccion(LenguajePigParser.InstruccionContext ctx) {
        if (ctx.condicional() != null) {
            return (InstSi) visit(ctx.condicional());
        }
        if (ctx.ciclo_simple() != null) {
            return (CicloMientras) visit(ctx.ciclo_simple());
        }
        if (ctx.ciclo_do_while() != null) {
            return (CicloHacerMientras) visit(ctx.ciclo_do_while());
        }
        if (ctx.ciclo_iterador() != null) {
            return (CicloPara) visit(ctx.ciclo_iterador());
        }
        if (ctx.operacion_abrev() != null) {
            return (IncrementoDecremento) visit(ctx.operacion_abrev());
        }
        if (ctx.declaracion() != null) {
            return visit(ctx.declaracion());
        }
        if (ctx.asignacion() != null) {
            return (Asignacion) visit(ctx.asignacion());
        }
        if (ctx.expresion() != null) {
            Visitable visitable = visit(ctx.expresion());
            return (visitable instanceof Instruccion) ? visitable : null;
        }
        if (ctx.fun_lectura() != null) {
            return (Lectura) visit(ctx.fun_lectura());
        }
        if (ctx.fun_lectura_guardado() != null) {
            return (Lectura) visit(ctx.fun_lectura_guardado());
        }
        if (ctx.fun_impresion() != null) {
            return (Imprimir) visit(ctx.fun_impresion());
        }
        if (ctx.PERGE() != null) {
            return new Continuar(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
        if (ctx.INTERRUMPE() != null) {
            return new Romper(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }

        return null;
    }

    @Override
    public Visitable visitDeclaracion(LenguajePigParser.DeclaracionContext ctx) {
        if (ctx.dec_var() != null) {
            return (Declaracion) visit(ctx.dec_var());
        }
        return visit(ctx.asignacion());
    }

    @Override
    public Declaracion visitDec_var(LenguajePigParser.Dec_varContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        if (ctx.SERIES() != null) {
            String tipoDato = extraerTipoDato(ctx);
            String nombre = ctx.ID(0).getText();

            List<Expresion> dimensiones = new ArrayList<>();
            dimensiones.add((Expresion) visit(ctx.expresion(0)));

            List<Expresion> valoresIniciales = null;
            if (ctx.expresion().size() > 1) {
                valoresIniciales = extraerValoresIniciales(ctx.expresion(1));
            }

            return new DeclaracionArreglo(dimensiones, valoresIniciales, tipoDato,
                    nombre, fila, columna);
        }

        String tipoDato = extraerTipoDato(ctx);
        String nombre = ctx.ID(0).getText();

        LenguajePigParser.ExpresionContext inicialCtx = (ctx.expresion().isEmpty())
                ? null : ctx.expresion(0);

        if (ctx.ID().size() > 1) {
            List<Expresion> valoresIniciales = (inicialCtx != null)
                    ? extraerValoresIniciales(inicialCtx) : null;
            Expresion valorExpresion = (valoresIniciales == null && inicialCtx != null)
                    ? (Expresion) visit(inicialCtx) : null;
            return new DeclaracionEstructura(valoresIniciales, valorExpresion, tipoDato,
                    nombre, fila, columna);
        }

        Expresion valorInicial = (inicialCtx != null) ? (Expresion) visit(inicialCtx) : null;
        return new DeclaracionVariable(valorInicial, tipoDato, nombre, fila, columna);
    }

    @Override
    public DeclaracionIterador visitDec_var_sin_pcoma(
            LenguajePigParser.Dec_var_sin_pcomaContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        String tipoDato = (ctx.tipo_dato() != null) ? ctx.tipo_dato().getText()
                : (ctx.ID().size() > 1 ? ctx.ID(1).getText() : null);
        String nombre = ctx.ID(0).getText();
        Expresion valorInicial = (ctx.expresion() != null)
                ? (Expresion) visit(ctx.expresion()) : null;

        return new DeclaracionIterador(tipoDato, nombre, valorInicial, fila, columna);
    }

    private String extraerTipoDato(LenguajePigParser.Dec_varContext ctx) {
        if (ctx.tipo_dato() != null) {
            return ctx.tipo_dato().getText();
        }
        return (ctx.ID().size() > 1) ? ctx.ID(1).getText() : null;
    }

    private List<Expresion> extraerValoresIniciales(
            LenguajePigParser.ExpresionContext ctx) {
        if (ctx instanceof LenguajePigParser.ExpArgStructContext) {
            return extraerArgumentos(
                    ((LenguajePigParser.ExpArgStructContext) ctx).argumentos());
        }
        List<Expresion> valorUnico = new ArrayList<>();
        valorUnico.add((Expresion) visit(ctx));
        return valorUnico;
    }

    @Override
    public Asignacion visitAsignacion(LenguajePigParser.AsignacionContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion objetivo = visitarLvalue(ctx.lvalue());
        Expresion valor = (Expresion) visit(ctx.expresion());

        return new Asignacion(objetivo, valor, fila, columna);
    }

    private Expresion visitarLvalue(LenguajePigParser.LvalueContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        if (ctx.lvalue() == null) {
            return new AccesoVariable(ctx.ID().getText(), fila, columna);
        }
        if (ctx.CORCH_A() != null) {
            Expresion arreglo = visitarLvalue(ctx.lvalue());
            Expresion indice = (Expresion) visit(ctx.expresion());
            return new ExpIndice(arreglo, indice, fila, columna);
        }

        Expresion objeto = visitarLvalue(ctx.lvalue());
        String atributo = ctx.ID().getText();
        return new Acceso(objeto, atributo, fila, columna);
    }

    @Override
    public InstSi visitCondicional(LenguajePigParser.CondicionalContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion condicion = (Expresion) visit(ctx.expresion());

        List<Instruccion> cuerpoSi = visitarInstrucciones(ctx.instruccion());

        List<RamaSino> ramasSino = new ArrayList<>();
        List<Instruccion> instruccionesContrario = null;

        for (LenguajePigParser.Rama_aliterContext rama : ctx.rama_aliter()) {
            List<Instruccion> cuerpo = visitarInstrucciones(rama.instruccion());
            if (rama.expresion() != null) {
                int filaRama = rama.start.getLine();
                int columnaRama = rama.start.getCharPositionInLine();
                Expresion condSino = (Expresion) visit(rama.expresion());
                ramasSino.add(new RamaSino(condSino, cuerpo, filaRama, columnaRama));
            } else {
                instruccionesContrario = cuerpo;
            }
        }

        return new InstSi(condicion, cuerpoSi, ramasSino, instruccionesContrario, fila, columna);
    }

    @Override
    public CicloMientras visitCiclo_simple(LenguajePigParser.Ciclo_simpleContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion condicion = (Expresion) visit(ctx.expresion());
        List<Instruccion> cuerpo = visitarInstrucciones(ctx.instruccion());

        return new CicloMientras(cuerpo, condicion, fila, columna);
    }

    @Override
    public CicloHacerMientras visitCiclo_do_while(LenguajePigParser.Ciclo_do_whileContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        List<Instruccion> cuerpo = visitarInstrucciones(ctx.instruccion());
        Expresion condicion = (Expresion) visit(ctx.expresion());

        return new CicloHacerMientras(cuerpo, condicion, fila, columna);
    }

    @Override
    public CicloPara visitCiclo_iterador(LenguajePigParser.Ciclo_iteradorContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        DeclaracionIterador iterador = visitDec_var_sin_pcoma(ctx.dec_var_sin_pcoma());
        Expresion condicion = (Expresion) visit(ctx.expresion());
        Instruccion actualizacion = (Instruccion) visit(ctx.expresion_iterador());
        List<Instruccion> cuerpo = visitarInstrucciones(ctx.instruccion());

        return new CicloPara(iterador, actualizacion, cuerpo, condicion, fila, columna);
    }

    @Override
    public Visitable visitExpresion_iterador(LenguajePigParser.Expresion_iteradorContext ctx) {
        if (ctx.operacion_abrev() != null) {
            return visit(ctx.operacion_abrev());
        }
        if (ctx.asignacion() != null) {
            return visit(ctx.asignacion());
        }
        Visitable visitable = visit(ctx.expresion());
        return (visitable instanceof Instruccion) ? visitable : null;
    }

    private List<Instruccion> visitarInstrucciones(
            List<LenguajePigParser.InstruccionContext> contextos) {
        List<Instruccion> resultado = new ArrayList<>();
        for (LenguajePigParser.InstruccionContext instCtx : contextos) {
            Instruccion inst = (Instruccion) visitInstruccion(instCtx);
            if (inst != null) {
                resultado.add(inst);
            }
        }
        return resultado;
    }

    @Override
    public IncrementoDecremento visitOperacion_abrev(LenguajePigParser.Operacion_abrevContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion objetivo = new AccesoVariable(ctx.ID().getText(), fila, columna);
        Operador operador = (ctx.MAS_MAS() != null) ? Operador.INCREMENTO : Operador.DECREMENTO;

        return new IncrementoDecremento(objetivo, operador, fila, columna);
    }

    @Override
    public Llamada visitLlamada_metodo(LenguajePigParser.Llamada_metodoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        String nombreFuncion = ctx.ID().getText();
        List<Expresion> argumentos = extraerArgumentos(ctx.argumentos());

        return new Llamada(null, nombreFuncion, argumentos, fila, columna);
    }

    @Override
    public Lectura visitFun_lectura(LenguajePigParser.Fun_lecturaContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Lectura(fila, columna);
    }

    @Override
    public Lectura visitFun_lectura_guardado(LenguajePigParser.Fun_lectura_guardadoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion argumento = visitarLvalue(ctx.lvalue());
        return new Lectura(argumento, fila, columna);
    }

    @Override
    public Imprimir visitFun_impresion(LenguajePigParser.Fun_impresionContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        List<Expresion> valores = new ArrayList<>();
        for (LenguajePigParser.ExpresionContext e : ctx.expresion()) {
            valores.add((Expresion) visit(e));
        }
        return new Imprimir(valores, true, fila, columna);
    }


    @Override
    public NewObjeto visitInstanciacion(LenguajePigParser.InstanciacionContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        String nombreClase = ctx.ID().getText();
        List<Expresion> argumentos = extraerArgumentos(ctx.argumentos());

        return new NewObjeto(nombreClase, argumentos, fila, columna);
    }

    private List<Expresion> extraerArgumentos(LenguajePigParser.ArgumentosContext ctx) {
        List<Expresion> argumentos = new ArrayList<>();
        if (ctx != null) {
            for (LenguajePigParser.ExpresionContext e : ctx.expresion()) {
                argumentos.add((Expresion) visit(e));
            }
        }
        return argumentos;
    }

    @Override
    public LiteralStructura visitExpArgStruct(LenguajePigParser.ExpArgStructContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new LiteralStructura(extraerArgumentos(ctx.argumentos()), fila, columna);
    }

    @Override
    public Llamada visitExpLlamada(LenguajePigParser.ExpLlamadaContext ctx) {
        return (Llamada) visit(ctx.llamada_metodo());
    }

    @Override
    public Llamada visitExpLlamadaMetodo(LenguajePigParser.ExpLlamadaMetodoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion objeto = (Expresion) visit(ctx.expresion());
        String nombreMetodo = ctx.llamada_metodo().ID().getText();
        List<Expresion> argumentos = extraerArgumentos(ctx.llamada_metodo().argumentos());

        return new Llamada(objeto, nombreMetodo, argumentos, fila, columna);
    }

    @Override
    public Acceso visitExpAcceso(LenguajePigParser.ExpAccesoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion objeto = (Expresion) visit(ctx.expresion());
        String atributo = ctx.ID().getText();

        return new Acceso(objeto, atributo, fila, columna);
    }

    @Override
    public ExpIndice visitExpIndice(LenguajePigParser.ExpIndiceContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion arreglo = (Expresion) visit(ctx.expresion(0));
        Expresion indice = (Expresion) visit(ctx.expresion(1));

        return new ExpIndice(arreglo, indice, fila, columna);
    }

    @Override
    public NewObjeto visitExpInstanciacion(LenguajePigParser.ExpInstanciacionContext ctx) {
        return visitInstanciacion(ctx.instanciacion());
    }

    @Override
    public Expresion visitExpParentesis(LenguajePigParser.ExpParentesisContext ctx) {
        return (Expresion) visit(ctx.expresion());
    }

    @Override
    public Unario visitExpNot(LenguajePigParser.ExpNotContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion exp = (Expresion) visit(ctx.expresion());
        return new Unario(exp, Operador.NOT, fila, columna);
    }

    @Override
    public Unario visitExpNegativo(LenguajePigParser.ExpNegativoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion exp = (Expresion) visit(ctx.expresion());
        return new Unario(exp, Operador.NEGATIVO_UNARIO, fila, columna);
    }

    @Override
    public Operacion visitExpMultiDiv(LenguajePigParser.ExpMultiDivContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));

        Operador op = (ctx.MULTI() != null) ? Operador.MULTIPLICACION : Operador.DIVISION;
        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public Operacion visitExpSumaResta(LenguajePigParser.ExpSumaRestaContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));

        Operador op = (ctx.MAS() != null) ? Operador.SUMA : Operador.RESTA;
        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public Operacion visitExpRelacional(LenguajePigParser.ExpRelacionalContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));

        Operador op;
        if (ctx.MAYOR_Q() != null) {
            op = Operador.MAYOR;
        } else if (ctx.MAYOR_EQ_Q() != null) {
            op = Operador.MAYOR_IGUAL;
        } else if (ctx.MENOR_Q() != null) {
            op = Operador.MENOR;
        } else {
            op = Operador.MENOR_IGUAL;
        }
        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public Operacion visitExpIgualdad(LenguajePigParser.ExpIgualdadContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));

        Operador op = (ctx.EQ_EQ() != null) ? Operador.IGUAL : Operador.DISTINTO;
        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public Operacion visitExpAnd(LenguajePigParser.ExpAndContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));

        return new Operacion(iz, der, Operador.AND, fila, columna);
    }

    @Override
    public Operacion visitExpOr(LenguajePigParser.ExpOrContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));

        return new Operacion(iz, der, Operador.OR, fila, columna);
    }

    @Override
    public Literal visitExpEntero(LenguajePigParser.ExpEnteroContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.ENTERO(), TipoDato.ENTERO, fila, columna);
    }

    @Override
    public Literal visitExpDecimal(LenguajePigParser.ExpDecimalContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.DECIMAL(), TipoDato.DECIMAL, fila, columna);
    }

    @Override
    public Literal visitExpCadena(LenguajePigParser.ExpCadenaContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.CADENA().getText(), TipoDato.CADENA, fila, columna);
    }

    @Override
    public Literal visitExpChar(LenguajePigParser.ExpCharContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.CHAR(), TipoDato.CHAR, fila, columna);
    }

    @Override
    public Literal visitExpVerdadero(LenguajePigParser.ExpVerdaderoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.VERUM().getText(), TipoDato.BOOLEAN, fila, columna);
    }

    @Override
    public Literal visitExpFalso(LenguajePigParser.ExpFalsoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.FALSUS().getText(), TipoDato.BOOLEAN, fila, columna);
    }

    @Override
    public AccesoVariable visitExpId(LenguajePigParser.ExpIdContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new AccesoVariable(ctx.ID().getText(), fila, columna);
    }
}