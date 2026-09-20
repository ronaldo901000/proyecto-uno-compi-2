package com.ronaldo.cd3.compiler.api.modelos.clasesZ;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.interfaces.Generable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.FuncionDef;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.Parametro;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.Declaracion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionArreglo;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionVariable;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.Simbolo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloEstructura;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class ClaseZ extends Nodo implements Verificable, Generable {

    private final Reglas reglas = new Reglas();
    private String nombre;
    private List<Declaracion> atributos;
    private List<ConstructorZ> constructores;
    private List<FuncionDef> metodos;
    private SimboloClase simboloClase;
    private ArchivoDTO archivo;

    public ClaseZ(String nombre, List<Declaracion> atributos,
            List<ConstructorZ> constructores, List<FuncionDef> metodos,
            int fila, int columna) {
        super(fila, columna);
        this.nombre = nombre;
        this.atributos = atributos;
        this.constructores = constructores;
        this.metodos = metodos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Declaracion> getAtributos() {
        return atributos;
    }

    public List<ConstructorZ> getConstructores() {
        return constructores;
    }

    public List<FuncionDef> getMetodos() {
        return metodos;
    }

    public SimboloClase getSimboloClase() {
        return simboloClase;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        registrarEstructuraYFirmas(contexto);
        verificarCuerpos(contexto);
    }

    public void registrarEstructuraYFirmas(Contexto contexto) {
        Simbolo existente = contexto.getTablaSimbolos().buscarOtroSimbolo(nombre);
        if (existente != null && !(existente instanceof SimboloEstructura)) {
            String mensajeIdentificador;
            if (existente instanceof SimboloClase) {
                mensajeIdentificador = "Ya existe una clase llamada '" + nombre + "'";
            } else {
                mensajeIdentificador = "Ya existe un identificador llamado '" + nombre + "'";
            }
            contexto.agregarError(fila, columna, nombre, mensajeIdentificador);
            this.simboloClase = null;
            return;
        }
        TipoStructura tipoClase = contexto.getTablaTipos().registrarClase(nombre);

        Map<String, SimboloVariable> atributosSimbolo = new LinkedHashMap<>();
        int tamanoHeap = 0;
        if (atributos != null) {
            for (Declaracion atributo : atributos) {
                Tipo tipoAtributo = tipoDeAtributo(contexto, atributo);
                if (reglas.esError(tipoAtributo)) {
                    continue;
                }
                if (atributosSimbolo.containsKey(atributo.getId())) {
                    contexto.agregarError(atributo.getFila(), atributo.getColumna(),
                            atributo.getId(), "El atributo '" + atributo.getId()
                            + "' ya fue declarado en la clase '" + nombre + "'");
                    continue;
                }
                tipoClase.agregarAtributo(atributo.getId(), tipoAtributo);
                SimboloVariable simboloAtributo = new SimboloVariable(
                        atributo.getId(), tipoAtributo, tamanoHeap, true);
                atributosSimbolo.put(atributo.getId(), simboloAtributo);
                tamanoHeap += tipoAtributo.tamanoBytes();
            }
        }

        SimboloClase simboloClase = new SimboloClase(nombre, tamanoHeap);
        simboloClase.setTipo(tipoClase);
        for (SimboloVariable atributo : atributosSimbolo.values()) {
            simboloClase.agregarAtributo(atributo);
        }

        if (metodos != null) {
            int contador = 0;
            Map<String, Boolean> firmasMetodos = new LinkedHashMap<>();
            for (FuncionDef metodo : metodos) {
                contador++;
                SimboloFuncion simboloMetodo = simboloDeMetodo(contexto, metodo,
                        "met_" + nombre + "_" + metodo.getNombre() + "_" + contador);
                if (simboloMetodo != null) {
                    metodo.setSimbolo(simboloMetodo);
                    String clave = claveDeFirma(metodo.getNombre(), simboloMetodo);
                    if (firmasMetodos.containsKey(clave)) {
                        contexto.agregarError(metodo.getFila(), metodo.getColumna(),
                                metodo.getNombre(),
                                "El método '" + metodo.getNombre() + "' ya está definido "
                                + "con los mismos parámetros en la clase '" + nombre + "'");
                        continue;
                    }
                    firmasMetodos.put(clave, Boolean.TRUE);
                    simboloClase.agregarMetodo(simboloMetodo);
                }
            }
        }

        if (constructores != null) {
            int contador = 0;
            Map<String, Boolean> firmasConstructores = new LinkedHashMap<>();
            for (ConstructorZ constructor : constructores) {
                contador++;
                if (!constructor.getNombre().equals(nombre)) {
                    contexto.agregarError(constructor.getFila(), constructor.getColumna(),
                            constructor.getNombre(),
                            "El constructor debe llamarse igual que la clase '" + nombre + "'");
                    continue;
                }
                SimboloFuncion simboloConstructor = simboloDeMetodo(
                        contexto, constructor, "ctor_" + nombre + "_" + contador);
                if (simboloConstructor != null) {
                    constructor.setSimbolo(simboloConstructor);
                    String clave = claveDeFirma(nombre, simboloConstructor);
                    if (firmasConstructores.containsKey(clave)) {
                        contexto.agregarError(constructor.getFila(), constructor.getColumna(),
                                constructor.getNombre(),
                                "Ya existe un constructor de la clase '" + nombre
                                + "' con los mismos parámetros");
                        continue;
                    }
                    firmasConstructores.put(clave, Boolean.TRUE);
                    simboloClase.agregarConstructor(simboloConstructor);
                }
            }
        }

        if (!contexto.getTablaSimbolos().agregar(simboloClase)) {
            contexto.agregarError(fila, columna, nombre,
                    "Ya existe una clase llamada '" + nombre + "'");
            this.simboloClase = null;
            return;
        }
        this.simboloClase = simboloClase;
    }

    public void verificarCuerpos(Contexto contexto) {
        if (simboloClase == null) {
            return;
        }
        if (metodos != null) {
            for (FuncionDef metodo : metodos) {
                verificarCuerpoMetodo(contexto, metodo);
            }
        }
        if (constructores != null) {
            for (ConstructorZ constructor : constructores) {
                if (constructor.getNombre().equals(nombre)) {
                    verificarCuerpoConstructor(contexto, constructor);
                }
            }
        }
    }

    private void verificarCuerpoMetodo(Contexto contexto, FuncionDef metodo) {
        Tipo tipoRetornoT;
        if (metodo.getTipoRetorno() == null) {
            tipoRetornoT = contexto.getTablaTipos().getVoid();
        } else {
            tipoRetornoT = reglas.resolverTipo(
                    contexto, metodo.getTipoRetorno(), metodo.getFila(), metodo.getColumna());
        }
        verificarCuerpo(contexto, metodo.getNombre(), metodo.getParametros(),
                tipoRetornoT, metodo.getCuerpo(), metodo.getFila(), metodo.getColumna());
        if (!reglas.esVoid(tipoRetornoT) && !reglas.siempreRetorna(metodo.getCuerpo())) {
            contexto.agregarError(metodo.getFila(), metodo.getColumna(), metodo.getNombre(),
                    "El método '" + metodo.getNombre() + "' de tipo " + tipoRetornoT
                    + " no retorna en todos sus caminos de ejecución");
        }
    }

    private void verificarCuerpoConstructor(Contexto contexto, ConstructorZ constructor) {
        verificarCuerpo(contexto, "constructor_" + constructor.getNombre(),
                constructor.getParametros(), contexto.getTablaTipos().getVoid(),
                constructor.getCuerpo(), constructor.getFila(), constructor.getColumna());
    }

    private void verificarCuerpo(Contexto contexto, String nombreMiembro,
            List<Parametro> parametros, Tipo tipoRetorno, List<Instruccion> cuerpo,
            int fila, int columna) {
        TablaSimbolos anterior = contexto.nuevoAmbito(nombreMiembro);
        sembrarAtributos(contexto, simboloClase);
        int posicion = 0;
        if (parametros != null) {
            for (Parametro parametro : parametros) {
                if (parametro.getTipo() == null) {
                    continue;
                }
                SimboloParametro simboloParametro = new SimboloParametro(
                        parametro.getNombre(), parametro.getTipo(), posicion);
                contexto.getAmbito().agregar(simboloParametro);
                posicion += parametro.getTipo().tamanoBytes();
            }
        }
        contexto.getAmbito().setSiguientePosicion(posicion);
        Tipo retornoAnterior = contexto.getTipoRetornoActual();
        SimboloClase claseAnterior = contexto.getClaseActual();
        contexto.setTipoRetornoActual(tipoRetorno);
        contexto.setClaseActual(simboloClase);
        reglas.verificarInstrucciones(contexto, cuerpo);
        contexto.setClaseActual(claseAnterior);
        contexto.setTipoRetornoActual(retornoAnterior);
        contexto.restaurarAmbito(anterior);
    }

    private void sembrarAtributos(Contexto contexto, SimboloClase clase) {
        if (clase == null) {
            return;
        }
        for (SimboloVariable atributo : clase.getAtributos().values()) {
            contexto.getAmbito().agregar(new SimboloVariable(
                    atributo.getId(), atributo.getTipo(), atributo.getPosicion()));
        }
    }

    private String claveDeFirma(String nombreFuncion, SimboloFuncion funcion) {
        List<Tipo> tipos = new ArrayList<>();
        for (SimboloParametro parametro : funcion.getParametros()) {
            tipos.add(parametro.getTipo());
        }
        return TablaSimbolos.claveFuncion(nombreFuncion, tipos);
    }

    private SimboloFuncion simboloDeMetodo(Contexto contexto, FuncionDef metodo,
            String etiqueta) {
        Tipo tipoRetornoT;
        if (metodo.getTipoRetorno() == null) {
            tipoRetornoT = contexto.getTablaTipos().getVoid();
        } else {
            tipoRetornoT = reglas.resolverTipo(
                    contexto, metodo.getTipoRetorno(), metodo.getFila(), metodo.getColumna());
        }
        if (reglas.esError(tipoRetornoT)) {
            return null;
        }
        List<SimboloParametro> simbolosParametros = new ArrayList<>();
        if (metodo.getParametros() != null) {
            for (Parametro parametro : metodo.getParametros()) {
                parametro.verificarSemantica(contexto);
                simbolosParametros.add(new SimboloParametro(
                        parametro.getNombre(), parametro.getTipo(), 0));
            }
        }
        return new SimboloFuncion(metodo.getNombre(), tipoRetornoT,
                simbolosParametros, 0, etiqueta);
    }

    private SimboloFuncion simboloDeMetodo(Contexto contexto, ConstructorZ constructor,
            String etiqueta) {
        List<SimboloParametro> simbolosParametros = new ArrayList<>();
        if (constructor.getParametros() != null) {
            for (Parametro parametro : constructor.getParametros()) {
                parametro.verificarSemantica(contexto);
                simbolosParametros.add(new SimboloParametro(
                        parametro.getNombre(), parametro.getTipo(), 0));
            }
        }
        return new SimboloFuncion(constructor.getNombre(),
                contexto.getTablaTipos().getVoid(), simbolosParametros, 0, etiqueta);
    }

    private Tipo tipoDeAtributo(Contexto contexto, Declaracion atributo) {
        if (atributo instanceof DeclaracionVariable) {
            return reglas.resolverTipo(contexto, atributo.getTipoDato(),
                    atributo.getFila(), atributo.getColumna());
        }
        if (atributo instanceof DeclaracionArreglo) {
            DeclaracionArreglo arreglo = (DeclaracionArreglo) atributo;
            List<Integer> tamanos = new ArrayList<>();
            if (arreglo.getDimensiones() != null) {
                for (Expresion dimension : arreglo.getDimensiones()) {
                    if (dimension == null) {
                        tamanos.add(0);
                        continue;
                    }
                    Integer tamano = reglas.constanteEntera(dimension);
                    tamanos.add((tamano != null && tamano > 0) ? tamano : 0);
                }
            }
            Tipo base = reglas.resolverTipo(contexto, arreglo.getTipoDato(),
                    arreglo.getFila(), arreglo.getColumna());
            if (reglas.esError(base)) {
                return base;
            }
            return contexto.getTablaTipos().getArreglo(base, tamanos);
        }
        return contexto.getTablaTipos().getError();
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (simboloClase == null) {
            return null;
        }
        SimboloClase claseAnterior = contexto.getClaseActual();
        contexto.setClaseActual(simboloClase);
        if (simboloClase.getAtributos() != null) {
            for (SimboloVariable atributo : simboloClase.getAtributos().values()) {
                cuartetas.registrarTipoVariable(atributo.getId(), atributo.getTipo());
                if (atributo.getTipo() instanceof com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo) {
                    cuartetas.registrarTipoArreglo(atributo.getId(),
                            ((com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo) atributo.getTipo()).getTipoBase());
                }
            }
        }
        if (metodos != null) {
            for (FuncionDef metodo : metodos) {
                metodo.generarCuartetas(contexto, cuartetas);
            }
        }
        if (constructores != null) {
            for (ConstructorZ constructor : constructores) {
                if (constructor.getNombre().equals(nombre)) {
                    constructor.generarCuartetas(contexto, cuartetas);
                }
            }
        }
        contexto.setClaseActual(claseAnterior);
        return null;
    }

    public ArchivoDTO getArchivo() {
        return archivo;
    }

    public void setArchivo(ArchivoDTO archivo) {
        this.archivo = archivo;
    }

}
