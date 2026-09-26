package com.ronaldo.cd3.compiler.api.services.visitors;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.interfaces.Visitable;
import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ClaseZ;
import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ConstructorZ;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Acceso;
import com.ronaldo.cd3.compiler.api.modelos.expresion.AccesoVariable;
import com.ronaldo.cd3.compiler.api.modelos.expresion.ExpIndice;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Lectura;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Literal;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Llamada;
import com.ronaldo.cd3.compiler.api.modelos.expresion.NewObjeto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.NewArreglo;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Operacion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Ternaria;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Unario;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.FuncionDef;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.Parametro;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Asignacion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Continuar;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Imprimir;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.IncrementoDecremento;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Retorno;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Romper;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.CicloHacerMientras;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.CicloMientras;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.CicloPara;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.DeclaracionIterador;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional.InstSi;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional.RamaSino;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.Declaracion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionArreglo;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionVariable;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir.CasoSwitch;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir.InstElegir;
import com.ronaldo.cd3.compiler.api.zetariano.LenguajeZBaseVisitor;
import com.ronaldo.cd3.compiler.api.zetariano.LenguajeZParser.ExpresionContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import com.ronaldo.cd3.compiler.api.zetariano.LenguajeZParser;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ZVisitor extends LenguajeZBaseVisitor<Visitable> {

    @Override
    public ClaseZ visitClase(LenguajeZParser.ClaseContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String nombre = ctx.ID().getText();

        List<Declaracion> atributos = new ArrayList<>();
        for (LenguajeZParser.AtributoContext a : ctx.contenido().atributo()) {
            atributos.add(visitAtributo(a));
        }

        List<ConstructorZ> constructores = new ArrayList<>();
        for (LenguajeZParser.ConstructorContext c : ctx.contenido().constructor()) {
            constructores.add(visitConstructor(c));
        }

        List<FuncionDef> metodos = new ArrayList<>();
        for (LenguajeZParser.MetodoContext m : ctx.contenido().metodo()) {
            metodos.add(visitMetodo(m));
        }

        return new ClaseZ(nombre, atributos, constructores, metodos, fila, columna);
    }

    @Override
    public Declaracion visitAtributo(LenguajeZParser.AtributoContext ctx) {
        return visitDeclaracion(ctx.declaracion());
    }

    @Override
    public Declaracion visitDeclaracion(LenguajeZParser.DeclaracionContext ctx) {
        if (ctx.dec_var_simple() != null) {
            return visitDec_var_simple(ctx.dec_var_simple());
        }
        return visitDec_array(ctx.dec_array());
    }

    @Override
    public DeclaracionVariable visitDec_var_simple(LenguajeZParser.Dec_var_simpleContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String tipoDato = ctx.tipo_dato_general().getText();
        String nombre = ctx.ID().getText();
        Expresion valorInicial = (ctx.expresion() != null)
                ? (Expresion) visit(ctx.expresion()) : null;
        return new DeclaracionVariable(valorInicial, tipoDato, nombre, fila, columna);
    }

    @Override
    public DeclaracionArreglo visitDec_array(LenguajeZParser.Dec_arrayContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String tipoDato = ctx.tipo_dato_general(0).getText();
        String nombre = ctx.ID().getText();

        List<Expresion> dimensiones = extraerDimensionesArreglo(ctx);

        List<Expresion> valoresIniciales = null;
        if (ctx.valores_iniciales() != null) {
            valoresIniciales = new ArrayList<>();
            for (ExpresionContext e : ctx.valores_iniciales().expresion()) {
                valoresIniciales.add((Expresion) visit(e));
            }
        }

        return new DeclaracionArreglo(dimensiones, valoresIniciales, tipoDato, nombre, fila, columna);
    }

    private List<Expresion> extraerDimensionesArreglo(LenguajeZParser.Dec_arrayContext ctx) {
        List<Expresion> dimensiones = new ArrayList<>();
        boolean corchetePendiente = false;
        boolean trasId = false;
        for (Object hijo : ctx.children) {
            if (hijo instanceof TerminalNode) {
                TerminalNode terminal = (TerminalNode) hijo;
                int tipoToken = terminal.getSymbol().getType();
                if (tipoToken == LenguajeZParser.ID) {
                    trasId = true;
                    break;
                }
                if (tipoToken == LenguajeZParser.CORCH_A && !trasId) {
                    corchetePendiente = true;
                } else if (tipoToken == LenguajeZParser.CORCH_C && !trasId) {
                    if (corchetePendiente) {
                        dimensiones.add(null);
                    }
                    corchetePendiente = false;
                }
                continue;
            }
            if (hijo instanceof ExpresionContext && !trasId) {
                if (corchetePendiente) {
                    dimensiones.add((Expresion) visit((ExpresionContext) hijo));
                    corchetePendiente = false;
                }
            }
        }
        return dimensiones;
    }

    @Override
    public ConstructorZ visitConstructor(LenguajeZParser.ConstructorContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String nombre = ctx.ID().getText();
        List<Parametro> parametros = extraerParametros(ctx.parametros());
        List<Instruccion> cuerpo = visitarInstrucciones(ctx.instruccion());
        return new ConstructorZ(nombre, parametros, cuerpo, fila, columna);
    }

    @Override
    public Visitable visitInstruccion(LenguajeZParser.InstruccionContext ctx) {
        if (ctx.declaracion() != null) {
            return visitDeclaracion(ctx.declaracion());
        }
        if (ctx.asignacion() != null) {
            return visitAsignacion(ctx.asignacion());
        }
        if (ctx.suma_resta_abrev() != null) {
            return visitSuma_resta_abrev(ctx.suma_resta_abrev());
        }
        if (ctx.llamada_metodo_objeto() != null) {
            return visitLlamada_metodo_objeto(ctx.llamada_metodo_objeto());
        }
        if (ctx.llamada_metodo() != null) {
            return visitLlamada_metodo(ctx.llamada_metodo());
        }
        if (ctx.funcion_especial() != null) {
            return visitFuncion_especial(ctx.funcion_especial());
        }
        if (ctx.BREAK() != null) {
            return new Romper(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
        if (ctx.CONTINUE() != null) {
            return new Continuar(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
        if (ctx.return_() != null) {
            return visitReturn(ctx.return_());
        }
        if (ctx.inst_if() != null) {
            return visitInst_if(ctx.inst_if());
        }
        if (ctx.inst_switch() != null) {
            return visitInst_switch(ctx.inst_switch());
        }
        if (ctx.ciclo_for() != null) {
            return visitCiclo_for(ctx.ciclo_for());
        }
        if (ctx.ciclo_while() != null) {
            return visitCiclo_while(ctx.ciclo_while());
        }
        if (ctx.ciclo_do_while() != null) {
            return visitCiclo_do_while(ctx.ciclo_do_while());
        }
        return null;
    }

    @Override
    public Asignacion visitAsignacion(LenguajeZParser.AsignacionContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion objetivo = visitarLvalue(ctx.lvalue());
        Expresion valor = (Expresion) visit(ctx.expresion());
        Operador op;
        if (ctx.MAS_EQ() != null) {
            op = Operador.MAS_IGUAL;
        } else if (ctx.MENOS_EQ() != null) {
            op = Operador.MENOS_IGUAL;
        } else if (ctx.MULTI_EQ() != null) {
            op = Operador.MULTI_IGUAL;
        } else {
            op = Operador.IGUAL;
        }

        return new Asignacion(objetivo, valor, op, fila, columna);
    }

    private Expresion visitarLvalue(LenguajeZParser.LvalueContext ctx) {
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
    public Retorno visitReturn(LenguajeZParser.ReturnContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion expresion = (ctx.expresion() != null)
                ? (Expresion) visit(ctx.expresion()) : null;
        return new Retorno(expresion, fila, columna);
    }

    @Override
    public Llamada visitLlamada_metodo_objeto(LenguajeZParser.Llamada_metodo_objetoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion objeto = (Expresion) visit(ctx.expresion());
        Llamada llamada = visitLlamada_metodo(ctx.llamada_metodo());
        return new Llamada(objeto, llamada.getNombreFuncion(), llamada.getArgumentos(), fila, columna);
    }

    @Override
    public InstSi visitInst_if(LenguajeZParser.Inst_ifContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion condicion = (Expresion) visit(ctx.expresion());
        List<Instruccion> cuerpoSi = visitarInstrucciones(ctx.cuerpo_if().instruccion());

        List<RamaSino> ramasSino = new ArrayList<>();
        List<Instruccion> instruccionesContrario = null;

        LenguajeZParser.BifurcacionContext actual = ctx.bifurcacion();
        while (actual != null) {
            if (actual.IF() != null) {
                int filaSino = actual.start.getLine();
                int columnaSino = actual.start.getCharPositionInLine();
                Expresion condSino = (Expresion) visit(actual.expresion());
                List<Instruccion> cuerpoSino = visitarInstrucciones(actual.cuerpo_if().instruccion());
                ramasSino.add(new RamaSino(condSino, cuerpoSino, filaSino, columnaSino));
                actual = actual.bifurcacion();
            } else {
                instruccionesContrario = visitarInstrucciones(actual.cuerpo_if().instruccion());
                actual = null;
            }
        }
        return new InstSi(condicion, cuerpoSi, ramasSino, instruccionesContrario, fila, columna);
    }

    @Override
    public InstElegir visitInst_switch(LenguajeZParser.Inst_switchContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion valorEvaluado = (Expresion) visit(ctx.expresion());
        List<CasoSwitch> casos = visitarCasos(ctx.casos());
        return new InstElegir(valorEvaluado, casos, fila, columna);
    }

    private List<CasoSwitch> visitarCasos(LenguajeZParser.CasosContext ctx) {
        List<CasoSwitch> resultado = new ArrayList<>();
        if (ctx == null) {
            return resultado;
        }
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        List<Instruccion> cuerpo = visitarInstrucciones(ctx.instruccion());
        if (ctx.CASE() != null) {
            Expresion valor = (Expresion) visit(ctx.expresion());
            resultado.add(new CasoSwitch(valor, cuerpo, fila, columna));
        } else {
            resultado.add(new CasoSwitch(null, cuerpo, fila, columna));
        }
        if (ctx.casos() != null) {
            resultado.addAll(visitarCasos(ctx.casos()));
        }
        return resultado;
    }

    @Override
    public CicloPara visitCiclo_for(LenguajeZParser.Ciclo_forContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        DeclaracionIterador iterador = (ctx.dec_iterador() != null)
                ? visitDec_iterador(ctx.dec_iterador()) : null;
        Expresion condicion = (ctx.expresion() != null)
                ? (Expresion) visit(ctx.expresion()) : null;
        Instruccion actualizacion = null;
        if (ctx.suma_resta_abrev() != null) {
            actualizacion = visitSuma_resta_abrev(ctx.suma_resta_abrev());
        } else if (ctx.asignacion() != null) {
            actualizacion = visitAsignacion(ctx.asignacion());
        }

        List<Instruccion> cuerpo = visitarInstrucciones(ctx.instruccion());

        return new CicloPara(iterador, actualizacion, cuerpo, condicion, fila, columna);
    }

    @Override
    public DeclaracionIterador visitDec_iterador(LenguajeZParser.Dec_iteradorContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        if (ctx.declaracion() != null) {
            DeclaracionVariable variable = visitDec_var_simple(ctx.declaracion().dec_var_simple());
            return new DeclaracionIterador(variable.getTipoDato(), variable.getId(),
                    variable.getValorInicial(), fila, columna);
        }
        if (ctx.asignacion() != null) {
            Expresion valorInicial = (Expresion) visit(ctx.asignacion().expresion());
            String nombre = ctx.asignacion().lvalue().ID().getText();
            return new DeclaracionIterador(null, nombre, valorInicial, fila, columna);
        }
        return new DeclaracionIterador(null, null, null, fila, columna);
    }

    @Override
    public CicloMientras visitCiclo_while(LenguajeZParser.Ciclo_whileContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion condicion = (Expresion) visit(ctx.expresion());
        List<Instruccion> cuerpo = visitarInstrucciones(ctx.instruccion());
        return new CicloMientras(cuerpo, condicion, fila, columna);
    }

    @Override
    public CicloHacerMientras visitCiclo_do_while(LenguajeZParser.Ciclo_do_whileContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        List<Instruccion> cuerpo = visitarInstrucciones(ctx.instruccion());
        Expresion condicion = (Expresion) visit(ctx.expresion());
        return new CicloHacerMientras(cuerpo, condicion, fila, columna);
    }

    @Override
    public FuncionDef visitMetodo(LenguajeZParser.MetodoContext ctx) {
        if (ctx.funcion() != null) {
            return visitFuncion(ctx.funcion());
        }
        return visitProcedimiento(ctx.procedimiento());
    }

    @Override
    public FuncionDef visitProcedimiento(LenguajeZParser.ProcedimientoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String nombre = ctx.ID().getText();
        List<Parametro> parametros = extraerParametros(ctx.parametros());
        List<Instruccion> cuerpo = visitarInstrucciones(ctx.instruccion());
        return new FuncionDef(nombre, parametros, null, cuerpo, fila, columna);
    }

    @Override
    public FuncionDef visitFuncion(LenguajeZParser.FuncionContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String nombre = ctx.ID().getText();
        String tipoRetorno = ctx.tipo_dato_general().getText();
        int dimensionesRetorno = ctx.CORCH_A().size();
        List<Parametro> parametros = extraerParametros(ctx.parametros());
        List<Instruccion> cuerpo = visitarInstrucciones(ctx.instruccion());
        return new FuncionDef(nombre, parametros, tipoRetorno, dimensionesRetorno,
                cuerpo, fila, columna);
    }

    private List<Parametro> extraerParametros(LenguajeZParser.ParametrosContext ctx) {
        List<Parametro> resultado = new ArrayList<>();
        if (ctx == null) {
            return resultado;
        }
        for (LenguajeZParser.ParametroContext p : ctx.parametro()) {
            resultado.add(visitParametro(p));
        }
        return resultado;
    }

    @Override
    public Parametro visitParametro(LenguajeZParser.ParametroContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String tipoDato = ctx.tipo_dato_general().getText();
        String nombre = ctx.ID().getText();
        boolean esArreglo = !ctx.CORCH_A().isEmpty();
        boolean esStruct = (ctx.tipo_dato_general().ID() != null);
        return new Parametro(tipoDato, nombre, esArreglo, esStruct, fila, columna);
    }

    @Override
    public Llamada visitLlamada_metodo(LenguajeZParser.Llamada_metodoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        String nombreFuncion = ctx.ID().getText();

        List<Expresion> argumentos = new ArrayList<>();
        if (ctx.argumentos() != null) {
            for (LenguajeZParser.ExpresionContext e : ctx.argumentos().expresion()) {
                argumentos.add((Expresion) visit(e));
            }
        }

        return new Llamada(null, nombreFuncion, argumentos, fila, columna);
    }

    @Override
    public Visitable visitFuncion_especial(LenguajeZParser.Funcion_especialContext ctx) {
        if (ctx.fun_leer() != null) {
            return visitFun_leer(ctx.fun_leer());
        }
        if (ctx.fun_imprimir_con_ln() != null) {
            return visitFun_imprimir_con_ln(ctx.fun_imprimir_con_ln());
        }
        return visitFun_imprimir_sin_ln(ctx.fun_imprimir_sin_ln());
    }

    @Override
    public Lectura visitFun_leer(LenguajeZParser.Fun_leerContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion argumento = (ctx.expresion() != null)
                ? (Expresion) visit(ctx.expresion()) : null;
        return new Lectura(argumento, fila, columna);
    }

    @Override
    public Imprimir visitFun_imprimir_con_ln(LenguajeZParser.Fun_imprimir_con_lnContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion valor = (ctx.expresion() != null)
                ? (Expresion) visit(ctx.expresion()) : null;
        return new Imprimir(valor, true, fila, columna);
    }

    @Override
    public Imprimir visitFun_imprimir_sin_ln(LenguajeZParser.Fun_imprimir_sin_lnContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion valor = (Expresion) visit(ctx.expresion());
        return new Imprimir(valor, false, fila, columna);
    }

    @Override
    public IncrementoDecremento visitSuma_resta_abrev(LenguajeZParser.Suma_resta_abrevContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion objetivo = visitarLvalue(ctx.lvalue());
        Operador operador = (ctx.MAS_MAS() != null) ? Operador.INCREMENTO : Operador.DECREMENTO;

        return new IncrementoDecremento(objetivo, operador, fila, columna);
    }

    @Override
    public Literal visitExpDecimal(LenguajeZParser.ExpDecimalContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.DECIMAL(), TipoDato.DECIMAL, fila, columna);
    }

    @Override
    public Operacion visitExpMultDivMod(LenguajeZParser.ExpMultDivModContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expr_base(0));
        Expresion der = (Expresion) visit(ctx.expr_base(1));
        Operador op;
        if (ctx.MULTI() != null) {
            op = Operador.MULTIPLICACION;
        } else if (ctx.DIV() != null) {
            op = Operador.DIVISION;
        } else {
            op = Operador.MODULO;
        }
        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public Unario visitExpPositivo(LenguajeZParser.ExpPositivoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion exp = (Expresion) visit(ctx.expr_base());
        return new Unario(exp, Operador.POSITIVO_UNARIO, fila, columna);
    }

    @Override
    public Unario visitExpNegativo(LenguajeZParser.ExpNegativoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion exp = (Expresion) visit(ctx.expr_base());
        return new Unario(exp, Operador.NEGATIVO_UNARIO, fila, columna);
    }

    @Override
    public Acceso visitExpAcceso(LenguajeZParser.ExpAccesoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion objeto = (Expresion) visit(ctx.expr_base());
        String atributo = ctx.ID().getText();

        return new Acceso(objeto, atributo, fila, columna);
    }

    @Override
    public Operacion visitExpAnd(LenguajeZParser.ExpAndContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expr_base(0));
        Expresion der = (Expresion) visit(ctx.expr_base(1));
        return new Operacion(iz, der, Operador.AND, fila, columna);
    }

    @Override
    public Operacion visitExpOr(LenguajeZParser.ExpOrContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expr_base(0));
        Expresion der = (Expresion) visit(ctx.expr_base(1));
        return new Operacion(iz, der, Operador.OR, fila, columna);
    }

    @Override
    public Llamada visitExpLlamada(LenguajeZParser.ExpLlamadaContext ctx) {
        return visitLlamada_metodo(ctx.llamada_metodo());
    }

    @Override
    public Llamada visitExpLlamadaEncadenada(LenguajeZParser.ExpLlamadaEncadenadaContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion objeto = (Expresion) visit(ctx.expr_base());
        Llamada llamada = visitLlamada_metodo(ctx.llamada_metodo());
        return new Llamada(objeto, llamada.getNombreFuncion(), llamada.getArgumentos(), fila, columna);
    }

    @Override
    public ExpIndice visitExpIndice(LenguajeZParser.ExpIndiceContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion arreglo = (Expresion) visit(ctx.expr_base());
        Expresion indice = (Expresion) visit(ctx.expresion());

        return new ExpIndice(arreglo, indice, fila, columna);
    }

    @Override
    public Literal visitExpEntero(LenguajeZParser.ExpEnteroContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        return new Literal(ctx.ENTERO(), TipoDato.ENTERO, fila, columna);
    }

    @Override
    public Unario visitExpNot(LenguajeZParser.ExpNotContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion exp = (Expresion) visit(ctx.expr_base());

        return new Unario(exp, Operador.NOT, fila, columna);
    }

    @Override
    public AccesoVariable visitExpId(LenguajeZParser.ExpIdContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        return new AccesoVariable(ctx.ID().getText(), fila, columna);
    }

    @Override
    public Operacion visitExpSumaResta(LenguajeZParser.ExpSumaRestaContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expr_base(0));
        Expresion der = (Expresion) visit(ctx.expr_base(1));
        Operador op;
        if (ctx.MAS() != null) {
            op = Operador.SUMA;
        } else {
            op = Operador.RESTA;
        }
        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public Operacion visitExpIgualdad(LenguajeZParser.ExpIgualdadContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expr_base(0));
        Expresion der = (Expresion) visit(ctx.expr_base(1));

        Operador op;
        if (ctx.EQ_EQ() != null) {
            op = Operador.IGUAL;
        } else {
            op = Operador.DISTINTO;
        }

        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public Operacion visitExpRelacional(LenguajeZParser.ExpRelacionalContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expr_base(0));
        Expresion der = (Expresion) visit(ctx.expr_base(1));

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
    public Ternaria visitExpTernaria(LenguajeZParser.ExpTernariaContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion condicion = (Expresion) visit(ctx.expr_base());
        Expresion verdadero = (Expresion) visit(ctx.expresion(0));
        Expresion falso = (Expresion) visit(ctx.expresion(1));
        return new Ternaria(condicion, verdadero, falso, fila, columna);
    }

    @Override
    public NewObjeto visitExpNewObjeto(LenguajeZParser.ExpNewObjetoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String nombreClase = ctx.tipo_dato_general().getText();

        List<Expresion> argumentos = new ArrayList<>();
        if (ctx.argumentos() != null) {
            for (LenguajeZParser.ExpresionContext e : ctx.argumentos().expresion()) {
                argumentos.add((Expresion) visit(e));
            }
        }

        return new NewObjeto(nombreClase, argumentos, fila, columna);
    }

    @Override
    public NewArreglo visitExpNewArreglo(LenguajeZParser.ExpNewArregloContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String tipoBase = ctx.tipo_dato_general().getText();

        List<Expresion> dimensiones = new ArrayList<>();
        for (LenguajeZParser.ExpresionContext e : ctx.expresion()) {
            dimensiones.add((Expresion) visit(e));
        }

        return new NewArreglo(tipoBase, dimensiones, fila, columna);
    }

    @Override
    public Literal visitExpCadena(LenguajeZParser.ExpCadenaContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.CADENA().getText(), TipoDato.CADENA, fila, columna);
    }

    @Override
    public Literal visitExpNull(LenguajeZParser.ExpNullContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.NULL().getText(), TipoDato.NULO, fila, columna);
    }

    @Override
    public Literal visitExpVerdadero(LenguajeZParser.ExpVerdaderoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.TRUE().getText(), TipoDato.BOOLEAN, fila, columna);
    }

    @Override
    public Expresion visitExpParentesis(LenguajeZParser.ExpParentesisContext ctx) {
        return (Expresion) visit(ctx.expresion());
    }

    @Override
    public Lectura visitExpFunLeer(LenguajeZParser.ExpFunLeerContext ctx) {
        return visitFun_leer(ctx.fun_leer());
    }

    @Override
    public Literal visitExpChar(LenguajeZParser.ExpCharContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.LIT_CHAR(), TipoDato.CHAR, fila, columna);
    }

    @Override
    public Literal visitExpFalso(LenguajeZParser.ExpFalsoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.FALSE().getText(), TipoDato.BOOLEAN, fila, columna);
    }

    private List<Instruccion> visitarInstrucciones(List<LenguajeZParser.InstruccionContext> ctxs) {
        List<Instruccion> resultado = new ArrayList<>();
        for (LenguajeZParser.InstruccionContext instCtx : ctxs) {
            Instruccion inst = (Instruccion) visit(instCtx);
            if (inst != null) {
                resultado.add(inst);
            }
        }
        return resultado;
    }

}
