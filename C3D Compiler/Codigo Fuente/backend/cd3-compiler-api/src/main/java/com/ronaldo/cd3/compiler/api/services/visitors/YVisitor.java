package com.ronaldo.cd3.compiler.api.services.visitors;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.interfaces.Visitable;
import com.ronaldo.cd3.compiler.api.modelos.estructurasY.AtributoEstructura;
import com.ronaldo.cd3.compiler.api.modelos.estructurasY.EstructuraDef;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Acceso;
import com.ronaldo.cd3.compiler.api.modelos.expresion.AccesoVariable;
import com.ronaldo.cd3.compiler.api.modelos.expresion.ExpIndice;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Lectura;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Literal;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Llamada;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Operacion;
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
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionEstructura;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionVariable;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir.CasoSwitch;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir.InstElegir;
import com.ronaldo.cd3.compiler.api.modelos.programaY.ProgramaY;
import com.ronaldo.cd3.compiler.api.y.LenguajeYBaseVisitor;
import com.ronaldo.cd3.compiler.api.y.LenguajeYParser;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class YVisitor extends LenguajeYBaseVisitor<Visitable> {

    @Override
    public ProgramaY visitLenguaje(LenguajeYParser.LenguajeContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        List<EstructuraDef> estructuras = new ArrayList<>();
        if (ctx.bloque_estructuras() != null) {
            for (LenguajeYParser.EstructuraContext e : ctx.bloque_estructuras().estructura()) {
                estructuras.add(visitEstructura(e));
            }
        }

        List<FuncionDef> funciones = new ArrayList<>();
        if (ctx.bloque_funciones() != null) {
            for (LenguajeYParser.FuncionContext f : ctx.bloque_funciones().funcion()) {
                funciones.add(visitFuncion(f));
            }
        }

        return new ProgramaY(estructuras, funciones, fila, columna);
    }

    @Override
    public EstructuraDef visitEstructura(LenguajeYParser.EstructuraContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String nombre = ctx.ID_STRUCTURA().getText();

        List<AtributoEstructura> atributos = new ArrayList<>();
        for (LenguajeYParser.Atributo_structContext a : ctx.atributo_struct()) {
            atributos.add(visitAtributo_struct(a));
        }

        return new EstructuraDef(nombre, atributos, fila, columna);
    }

    @Override
    public AtributoEstructura visitAtributo_struct(LenguajeYParser.Atributo_structContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String tipoDato = ctx.tipo_dato_general().getText();
        String nombre = ctx.ID().getText();
        Integer tamaño = (ctx.NUM_ENTERO() != null) ? Integer.parseInt(ctx.NUM_ENTERO().getText()) : null;

        return new AtributoEstructura(tipoDato, nombre, tamaño, fila, columna);
    }

    @Override
    public FuncionDef visitFuncion(LenguajeYParser.FuncionContext ctx) {
        if (ctx.funcion_void() != null) {
            return visitFuncion_void(ctx.funcion_void());
        }
        return visitFuncion_retorno(ctx.funcion_retorno());
    }

    @Override
    public FuncionDef visitFuncion_void(LenguajeYParser.Funcion_voidContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String nombre = ctx.ID().getText();
        List<Parametro> parametros = extraerParametros(ctx.params());
        List<Instruccion> cuerpo = visitarBloque(ctx.bloque());

        return new FuncionDef(nombre, parametros, null, cuerpo, fila, columna);
    }

    @Override
    public FuncionDef visitFuncion_retorno(LenguajeYParser.Funcion_retornoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String nombre = ctx.ID().getText();
        List<Parametro> parametros = extraerParametros(ctx.params());
        String tipoRetorno = ctx.tipo_dato_general().getText();
        List<Instruccion> cuerpo = visitarBloque(ctx.bloque());

        return new FuncionDef(nombre, parametros, tipoRetorno, cuerpo, fila, columna);
    }

    private List<Parametro> extraerParametros(LenguajeYParser.ParamsContext ctx) {
        List<Parametro> resultado = new ArrayList<>();
        if (ctx == null) {
            return resultado;
        }
        for (LenguajeYParser.ParamContext p : ctx.param()) {
            String tipoDato;
            String nombre;
            boolean esArreglo = false;
            boolean esStruct = false;

            if (p.tipo_dato_primitivo() != null && p.CORCH_A() == null) {
                tipoDato = p.tipo_dato_primitivo().getText();
                nombre = p.ID().getText();
            } else if (p.CORCH_A() != null) {
                tipoDato = p.tipo_dato_primitivo().getText();
                nombre = p.ID().getText();
                esArreglo = true;
            } else {
                tipoDato = p.ID_STRUCTURA().getText();
                nombre = p.ID().getText();
                esStruct = true;
            }

            resultado.add(new Parametro(tipoDato, nombre, esArreglo, esStruct, 0, 0));
        }
        return resultado;
    }

    @Override
    public Parametro visitParam(LenguajeYParser.ParamContext ctx) {
        String tipoDato;
        String nombre;
        boolean esArreglo = false;
        boolean esStruct = false;
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        if (ctx.tipo_dato_primitivo() != null && ctx.CORCH_A() == null) {
            // tipo_dato_primitivo ID
            tipoDato = ctx.tipo_dato_primitivo().getText();
            nombre = ctx.ID().getText();
        } else if (ctx.CORCH_A() != null) {
            // CORCH_A CORCH_C tipo_dato_primitivo ID
            tipoDato = ctx.tipo_dato_primitivo().getText();
            nombre = ctx.ID().getText();
            esArreglo = true;
        } else {
            tipoDato = ctx.ID_STRUCTURA().getText();
            nombre = ctx.ID().getText();
            esStruct = true;
        }

        return new Parametro(tipoDato, nombre, esArreglo, esStruct, fila, columna);
    }

    private List<Instruccion> visitarBloque(LenguajeYParser.BloqueContext ctx) {
        List<Instruccion> resultado = new ArrayList<>();
        for (LenguajeYParser.InstruccionContext instCtx : ctx.instruccion()) {
            Instruccion inst = (Instruccion) visitInstruccion(instCtx);
            if (inst != null) {
                resultado.add(inst);
            }
        }
        return resultado;
    }

    @Override
    public Visitable visitInstruccion(LenguajeYParser.InstruccionContext ctx) {
        if (ctx.declaracion() != null) {
            return (Declaracion) visit(ctx.declaracion());
        }
        if (ctx.fun_leer() != null) {
            return (Lectura) visit(ctx.fun_leer());
        }
        if (ctx.asignacion() != null) {
            return (Asignacion) visit(ctx.asignacion());
        }
        if (ctx.suma_resta_abrev() != null) {
            return (IncrementoDecremento) visit(ctx.suma_resta_abrev());
        }
        if (ctx.fun_imprimir() != null) {
            return (Imprimir) visit(ctx.fun_imprimir());
        }
        if (ctx.llamada_funcion() != null) {
            return (Llamada) visit(ctx.llamada_funcion());
        }
        if (ctx.retorno() != null) {
            return (Retorno) visit(ctx.retorno());
        }
        if (ctx.ROMPER() != null) {
            return new Romper(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
        if (ctx.CONTINUAR() != null) {
            return new Continuar(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
        if (ctx.inst_si() != null) {
            return (InstSi) visit(ctx.inst_si());
        }
        if (ctx.inst_elegir() != null) {
            return (InstElegir) visit(ctx.inst_elegir());
        }
        if (ctx.ciclo_para() != null) {
            return (CicloPara) visit(ctx.ciclo_para());
        }
        if (ctx.ciclo_mientras() != null) {
            return (CicloMientras) visit(ctx.ciclo_mientras());
        }
        if (ctx.ciclo_hacer() != null) {
            return (CicloHacerMientras) visit(ctx.ciclo_hacer());
        }

        return null;
    }

    @Override
    public Declaracion visitDeclaracion(LenguajeYParser.DeclaracionContext ctx) {
        if (ctx.dec_var_simple() != null) {
            return (DeclaracionVariable) visit(ctx.dec_var_simple());
        }
        if (ctx.dec_array() != null) {
            return (DeclaracionArreglo) visit(ctx.dec_array());
        }
        return (DeclaracionEstructura) visit(ctx.dec_estruct());
    }

    @Override
    public DeclaracionVariable visitDec_var_simple(LenguajeYParser.Dec_var_simpleContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String tipoDato = ctx.tipo_dato_primitivo().getText();
        String nombre = ctx.ID().getText();
        Expresion valorInicial = (ctx.expresion() != null) ? (Expresion) visit(ctx.expresion()) : null;
        return new DeclaracionVariable(valorInicial, tipoDato, nombre, fila, columna);
    }

    @Override
    public DeclaracionArreglo visitDec_array(LenguajeYParser.Dec_arrayContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String tipoDato = ctx.tipo_dato_primitivo().getText();
        String nombre = ctx.ID().getText();

        List<Expresion> dimensiones = new ArrayList<>();
        for (LenguajeYParser.ExpresionContext e : ctx.expresion()) {
            dimensiones.add((Expresion) visit(e));
        }

        List<Expresion> valoresIniciales = null;
        if (ctx.valores_iniciales() != null) {
            valoresIniciales = new ArrayList<>();
            for (LenguajeYParser.ExpresionContext e : ctx.valores_iniciales().expresion()) {
                valoresIniciales.add((Expresion) visit(e));
            }
        }

        return new DeclaracionArreglo(dimensiones, valoresIniciales, tipoDato, nombre, fila, columna);
    }

    @Override
    public DeclaracionEstructura visitDec_estruct(LenguajeYParser.Dec_estructContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        String tipoStruct = ctx.ID_STRUCTURA().getText();
        String nombre = ctx.ID().getText();

        List<Expresion> valoresIniciales = null;
        Expresion valorExpresion = null;

        if (ctx.valores_iniciales() != null) {
            valoresIniciales = new ArrayList<>();
            for (LenguajeYParser.ExpresionContext e : ctx.valores_iniciales().expresion()) {
                valoresIniciales.add((Expresion) visit(e));
            }
        } else if (ctx.expresion() != null) {
            valorExpresion = (Expresion) visit(ctx.expresion());
        }

        return new DeclaracionEstructura(valoresIniciales, valorExpresion, tipoStruct, nombre, fila, columna);
    }

    private Expresion visitarLvalue(LenguajeYParser.LvalueContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        if (ctx.lvalue() == null) {
            // caso base: ID
            return new AccesoVariable(ctx.ID().getText(), fila, columna);
        }
        if (ctx.CORCH_A() != null) {

            // lvalue [ expresion ]
            Expresion arreglo = visitarLvalue(ctx.lvalue());
            Expresion indice = (Expresion) visit(ctx.expresion());
            return new ExpIndice(arreglo, indice, fila, columna);
        }

        Expresion objeto = visitarLvalue(ctx.lvalue());
        String atributo = ctx.ID().getText();
        return new Acceso(objeto, atributo, fila, columna);
    }

    @Override
    public Asignacion visitAsignacion(LenguajeYParser.AsignacionContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion objetivo = visitarLvalue(ctx.lvalue());
        Expresion valor = (Expresion) visit(ctx.expresion());

        return new Asignacion(objetivo, valor, fila, columna);
    }

    @Override
    public InstSi visitInst_si(LenguajeYParser.Inst_siContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion condicion = (Expresion) visit(ctx.expresion());
        List<Instruccion> cuerpoSi = visitarBloque(ctx.bloque());

        List<RamaSino> ramasSino = new ArrayList<>();
        List<Instruccion> instruccionesInternasContrario = null;

        LenguajeYParser.BifurcacionContext actual = ctx.bifurcacion();
        while (actual != null) {
            if (actual.SINO() != null) {
                int filaSino = actual.start.getLine();
                int columnaSino = actual.start.getCharPositionInLine();
                Expresion condSino = (Expresion) visit(actual.expresion());
                List<Instruccion> cuerpoSino = visitarBloque(actual.bloque());
                ramasSino.add(new RamaSino(condSino, cuerpoSino, filaSino, columnaSino));
                actual = actual.bifurcacion();
            } else {
                instruccionesInternasContrario = visitarBloque(actual.bloque());
                actual = null;
            }
        }
        return new InstSi(condicion, cuerpoSi, ramasSino, instruccionesInternasContrario, fila, columna);
    }

    @Override
    public InstElegir visitInst_elegir(LenguajeYParser.Inst_elegirContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion valorEvaluado = (Expresion) visit(ctx.expresion());

        List<CasoSwitch> casos = new ArrayList<>();
        for (LenguajeYParser.CasoContext casoCtx : ctx.casos().caso()) {
            casos.add((CasoSwitch) visit(casoCtx));
        }

        return new InstElegir(valorEvaluado, casos, fila, columna);
    }

    @Override
    public CasoSwitch visitCaso(LenguajeYParser.CasoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        if (ctx.CASO() != null) {
            Expresion valor = (Expresion) visit(ctx.expresion());
            List<Instruccion> cuerpo = visitarBloque(ctx.bloque());
            return new CasoSwitch(valor, cuerpo, fila, columna);
        } else {
            List<Instruccion> cuerpo = visitarBloque(ctx.bloque());
            return new CasoSwitch(null, cuerpo, fila, columna);
        }
    }

    @Override
    public CicloPara visitCiclo_para(LenguajeYParser.Ciclo_paraContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        DeclaracionIterador iterador = (DeclaracionIterador) visit(ctx.dec_iterador());
        Expresion condicion = (Expresion) visit(ctx.expresion());

        Instruccion actualizacion;
        if (ctx.suma_resta_abrev() != null) {
            actualizacion = (Instruccion) visit(ctx.suma_resta_abrev());
        } else {
            actualizacion = (Instruccion) visit(ctx.asignacion());
        }

        List<Instruccion> instruccionesInternas = (List<Instruccion>) visitarBloque(ctx.bloque());

        return new CicloPara(
                iterador, actualizacion, instruccionesInternas,
                condicion, fila, columna);
    }

    @Override
    public DeclaracionIterador visitDec_iterador(LenguajeYParser.Dec_iteradorContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        String tipoDato = (ctx.tipo_dato_primitivo() != null)
                ? ctx.tipo_dato_primitivo().getText()
                : null;
        String nombre = ctx.ID().getText();
        Expresion valorInicial = (Expresion) visit(ctx.expresion());

        return new DeclaracionIterador(tipoDato, nombre, valorInicial, fila, columna);
    }

    @Override
    public CicloMientras visitCiclo_mientras(LenguajeYParser.Ciclo_mientrasContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion condicion = (Expresion) visit(ctx.expresion());
        List<Instruccion> cuerpo = (List<Instruccion>) visitarBloque(ctx.bloque());

        return new CicloMientras(cuerpo, condicion, fila, columna);
    }

    @Override
    public CicloHacerMientras visitCiclo_hacer(LenguajeYParser.Ciclo_hacerContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        List<Instruccion> instruccionesInternas = (List<Instruccion>) visitarBloque(ctx.bloque());
        Expresion condicion = (Expresion) visit(ctx.expresion());

        return new CicloHacerMientras(instruccionesInternas, condicion, fila, columna);
    }

    @Override
    public Imprimir visitFun_imprimir(LenguajeYParser.Fun_imprimirContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion valor = (Expresion) visit(ctx.expresion());
        return new Imprimir(valor, fila, columna);
    }

    @Override
    public Lectura visitFun_leer(LenguajeYParser.Fun_leerContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Lectura(fila, columna);
    }

    @Override
    public Llamada visitLlamada_funcion(LenguajeYParser.Llamada_funcionContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        String nombreFuncion = ctx.ID().getText();

        List<Expresion> argumentos = new ArrayList<>();
        if (ctx.argumentos() != null) {
            for (LenguajeYParser.ExpresionContext e : ctx.argumentos().expresion()) {
                argumentos.add((Expresion) visit(e));
            }
        }

        return new Llamada(null, nombreFuncion, argumentos, fila, columna);
    }

    @Override
    public IncrementoDecremento visitSuma_resta_abrev(LenguajeYParser.Suma_resta_abrevContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion objetivo = (Expresion) visit(ctx.lvalue());
        Operador operador = (ctx.MAS_MAS() != null) ? Operador.INCREMENTO : Operador.DECREMENTO;

        return new IncrementoDecremento(objetivo, operador, fila, columna);
    }

    @Override
    public Retorno visitRetorno(LenguajeYParser.RetornoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion expresion = (ctx.expresion() != null)
                ? (Expresion) visit(ctx.expresion()) : null;
        return new Retorno(expresion, fila, columna);
    }

    @Override
    public Literal visitExpDecimal(LenguajeYParser.ExpDecimalContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.NUM_DECIMAL(), fila, columna);
    }

    @Override
    public Operacion visitExpIgualdad(LenguajeYParser.ExpIgualdadContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));

        Operador op;
        if (ctx.EQ_EQ() != null) {
            op = Operador.IGUAL;
        } else {
            op = Operador.DISTINTO;
        }

        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public Unario visitExpNegativo(LenguajeYParser.ExpNegativoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion exp = (Expresion) visit(ctx.expresion());
        Operador op = Operador.NEGATIVO_UNARIO;
        return new Unario(exp, op, fila, columna);
    }

    @Override
    public Operacion visitExpRelacional(LenguajeYParser.ExpRelacionalContext ctx) {
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
    public Operacion visitExpOr(LenguajeYParser.ExpOrContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));
        Operador op = Operador.OR;
        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public Literal visitExpCadena(LenguajeYParser.ExpCadenaContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.LIT_CADENA().getText(), fila, columna);
    }

    @Override
    public Acceso visitExpAcceso(LenguajeYParser.ExpAccesoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion objeto = (Expresion) visit(ctx.expresion());
        String atributo = ctx.ID().getText();

        return new Acceso(objeto, atributo, fila, columna);
    }

    @Override
    public Literal visitExpVerdadero(LenguajeYParser.ExpVerdaderoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.VERDADERO().getText(), fila, columna);
    }

    @Override
    public Operacion visitExpAnd(LenguajeYParser.ExpAndContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));
        Operador op = Operador.AND;
        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public Llamada visitExpLlamada(LenguajeYParser.ExpLlamadaContext ctx) {
        return (Llamada) visit(ctx.llamada_funcion());
    }

    @Override
    public Expresion visitExpParentesis(LenguajeYParser.ExpParentesisContext ctx) {
        return (Expresion) visit(ctx.expresion());
    }

    @Override
    public Lectura visitExpFunLeer(LenguajeYParser.ExpFunLeerContext ctx) {
        return (Lectura) visit(ctx.fun_leer());
    }

    @Override
    public Literal visitExpChar(LenguajeYParser.ExpCharContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.CHAR(), fila, columna);
    }

    @Override
    public Literal visitExpFalso(LenguajeYParser.ExpFalsoContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.FALSO().getText(), fila, columna);
    }

    @Override
    public Operacion visitExpMultDiv(LenguajeYParser.ExpMultDivContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));
        Operador op;
        if (ctx.MULTI() != null) {
            op = Operador.MULTIPLICACION;
        } else {
            op = Operador.DIVISION;
        }
        return new Operacion(iz, der, op, fila, columna);
    }

    @Override
    public ExpIndice visitExpIndice(LenguajeYParser.ExpIndiceContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        Expresion arreglo = (Expresion) visit(ctx.expresion(0));
        Expresion indice = (Expresion) visit(ctx.expresion(1));

        return new ExpIndice(arreglo, indice, fila, columna);
    }

    @Override
    public Literal visitExpEntero(LenguajeYParser.ExpEnteroContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        return new Literal(ctx.NUM_ENTERO(), fila, columna);
    }

    @Override
    public Unario visitExpNot(LenguajeYParser.ExpNotContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion exp = (Expresion) visit(ctx.expresion());

        return new Unario(exp, Operador.NOT, fila, columna);
    }

    @Override
    public AccesoVariable visitExpId(LenguajeYParser.ExpIdContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();

        return new AccesoVariable(ctx.ID().getText(), fila, columna);
    }

    @Override
    public Operacion visitExpSuma(LenguajeYParser.ExpSumaContext ctx) {
        int fila = ctx.start.getLine();
        int columna = ctx.start.getCharPositionInLine();
        Expresion iz = (Expresion) visit(ctx.expresion(0));
        Expresion der = (Expresion) visit(ctx.expresion(1));
        Operador op;
        if (ctx.MAS() != null) {
            op = Operador.SUMA;
        } else {
            op = Operador.RESTA;
        }
        return new Operacion(iz, der, op, fila, columna);
    }
    
}
