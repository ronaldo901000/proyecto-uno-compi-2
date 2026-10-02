package com.ronaldo.cd3.compiler.api.modelos.clasesZ;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.FuncionDef;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.Parametro;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.Declaracion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionArreglo;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionVariable;
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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Registra una clase Z en la tabla de simbolos: atributos, metodos y sus constructores
 */
public class RegistradorClase {

    private final ClaseZ clase;
    private final String nombre;
    private final Reglas reglas;
    private final ResolutorTipoRetorno resolutorRetorno;

    public RegistradorClase(ClaseZ clase, Reglas reglas, ResolutorTipoRetorno resolutorRetorno) {
        this.clase = clase;
        this.nombre = clase.getNombre();
        this.reglas = reglas;
        this.resolutorRetorno = resolutorRetorno;
    }

    /**
     * @return el símbolo registrado, o null si hubo un error.
     */
    public SimboloClase registrar(Contexto contexto) {
        if (existeConflictoDeNombre(contexto)) {
            return null;
        }

        TipoStructura tipoClase = contexto.getTablaTipos().registrarClase(nombre);
        Map<String, SimboloVariable> atributosSimbolo = registrarAtributos(contexto, tipoClase);

        SimboloClase nuevaClase = new SimboloClase(nombre, calcularTamañoHeap(atributosSimbolo));
        nuevaClase.setTipo(tipoClase);
        for (SimboloVariable atributo : atributosSimbolo.values()) {
            nuevaClase.agregarAtributo(atributo);
        }

        registrarMetodos(contexto, nuevaClase);
        registrarConstructores(contexto, nuevaClase);

        if (!contexto.getTablaSimbolos().agregar(nuevaClase)) {
            contexto.agregarError(clase.getFila(), clase.getColumna(), nombre,
                    "Ya existe una clase llamada '" + nombre + "'");
            return null;
        }
        return nuevaClase;
    }


    /**
     * Verifica si existe conflicto de nombre con otras clases, atributo 
     */
    private boolean existeConflictoDeNombre(Contexto contexto) {
        Simbolo existente = contexto.getTablaSimbolos().buscarOtroSimbolo(nombre);

        if (existente == null || existente instanceof SimboloEstructura) {
            return false;
        }

        String mensaje = (existente instanceof SimboloClase)
                ? "Ya existe una clase llamada '" + nombre + "'"
                : "Ya existe un identificador llamado '" + nombre + "'";
        contexto.agregarError(clase.getFila(), clase.getColumna(), nombre, mensaje);
        return true;
    }

    /**
     * Resuelve el tipo de cada atributo, detecta duplicados y calcula su
     * posición en el heap.
     */
    private Map<String, SimboloVariable> registrarAtributos(Contexto contexto, TipoStructura tipoClase) {
        Map<String, SimboloVariable> atributosSimbolo = new LinkedHashMap<>();
        List<Declaracion> atributos = clase.getAtributos();
        if (atributos == null) {
            return atributosSimbolo;
        }

        int posicion = 0;
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
            atributosSimbolo.put(atributo.getId(),
                    new SimboloVariable(atributo.getId(), tipoAtributo, posicion, true));
            posicion += tipoAtributo.tamañoBytes();
        }
        return atributosSimbolo;
    }

    private int calcularTamañoHeap(Map<String, SimboloVariable> atributosSimbolo) {
        int total = 0;
        for (SimboloVariable atributo : atributosSimbolo.values()) {
            total += atributo.getTipo().tamañoBytes();
        }
        return total;
    }

    /**
     * Crea el símbolo de cada metodo y verifica metodos repetidos.
     */
    private void registrarMetodos(Contexto contexto, SimboloClase simboloClase) {
        List<FuncionDef> metodos = clase.getMetodos();
        if (metodos == null) {
            return;
        }

        int contador = 0;
        Set<String> firmas = new HashSet<>();
        for (FuncionDef metodo : metodos) {
            contador++;
            SimboloFuncion simboloMetodo = simboloDeMetodo(contexto, metodo,
                    "metodo_" + nombre + "_" + metodo.getNombre() + "_" + contador);
            if (simboloMetodo == null) {
                continue;
            }

            metodo.setSimbolo(simboloMetodo);
            String clave = claveDeFirma(metodo.getNombre(), simboloMetodo);
            if (!firmas.add(clave)) {
                contexto.agregarError(metodo.getFila(), metodo.getColumna(),
                        metodo.getNombre(),
                        "El método '" + metodo.getNombre() + "' ya está definido "
                        + "con los mismos parámetros en la clase '" + nombre + "'");
                continue;
            }
            simboloClase.agregarMetodo(simboloMetodo);
        }
    }

    /**
     * Valida el nombre de cada constructor, crea su símbolo y verifica contructores repetidos
     */
    private void registrarConstructores(Contexto contexto, SimboloClase simboloClase) {
        List<ConstructorZ> constructores = clase.getConstructores();
        if (constructores == null) {
            return;
        }

        int contador = 0;
        Set<String> firmas = new HashSet<>();
        for (ConstructorZ constructor : constructores) {
            contador++;
            if (!constructor.getNombre().equals(nombre)) {
                contexto.agregarError(constructor.getFila(), constructor.getColumna(),
                        constructor.getNombre(),
                        "El constructor debe llamarse igual que la clase '" + nombre + "'");
                continue;
            }

            SimboloFuncion simboloConstructor = simboloDeMetodo(
                    contexto, constructor, "constructor_" + nombre + "_" + contador);
            if (simboloConstructor == null) {
                continue;
            }

            constructor.setSimbolo(simboloConstructor);
            String clave = claveDeFirma(nombre, simboloConstructor);
            if (!firmas.add(clave)) {
                contexto.agregarError(constructor.getFila(), constructor.getColumna(),
                        constructor.getNombre(),
                        "Ya existe un constructor de la clase '" + nombre
                        + "' con los mismos parámetros");
                continue;
            }
            simboloClase.agregarConstructor(simboloConstructor);
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
        Tipo tipoRetornoT = resolutorRetorno.resolver(contexto, metodo);
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
            List<Integer> tamaños = new ArrayList<>();
            if (arreglo.getDimensiones() != null) {
                for (Expresion dimension : arreglo.getDimensiones()) {
                    if (dimension == null) {
                        tamaños.add(0);
                        continue;
                    }
                    Integer tamaño = reglas.constanteEntera(dimension);
                    tamaños.add((tamaño != null && tamaño > 0) ? tamaño : 0);
                }
            }
            Tipo base = reglas.resolverTipo(contexto, arreglo.getTipoDato(),
                    arreglo.getFila(), arreglo.getColumna());
            if (reglas.esError(base)) {
                return base;
            }
            return contexto.getTablaTipos().getArreglo(base, tamaños);
        }
        return contexto.getTablaTipos().getError();
    }
}
