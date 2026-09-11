package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Llamada extends Expresion implements Instruccion {

    private final Reglas reglas = new Reglas();
    private Expresion objetivo;
    private String nombreFuncion;
    private List<Expresion> argumentos;

    public Llamada(Expresion objetivo, String nombreFuncion,
            List<Expresion> argumentos, int fila, int columna) {
        super(fila, columna);
        this.objetivo = objetivo;
        this.nombreFuncion = nombreFuncion;
        this.argumentos = argumentos;
    }

    public Expresion getObjetivo() {
        return objetivo;
    }

    public String getNombreFuncion() {
        return nombreFuncion;
    }

    public List<Expresion> getArgumentos() {
        return argumentos;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        TablaTipos tablaTipos = contexto.getTablaTipos();
        if (objetivo != null) {
            objetivo.verificarSemantica(contexto);
            List<Tipo> tiposArgumentos = verificarArgumentos(contexto);
            Tipo tipoObjeto = objetivo.getTipo();
            if (reglas.esError(tipoObjeto)) {
                setTipo(tablaTipos.getError());
                return;
            }
            if (!(tipoObjeto instanceof TipoStructura)) {
                contexto.agregarError(fila, columna, nombreFuncion,
                        "No se puede invocar al método '" + nombreFuncion
                        + "' sobre un valor que no es un objeto");
                setTipo(tablaTipos.getError());
                return;
            }
            SimboloClase clase = contexto.getTablaSimbolos().buscarClase(
                    ((TipoStructura) tipoObjeto).getNombreStruct());
            if (clase == null) {
                contexto.agregarError(fila, columna, nombreFuncion,
                        "No se puede invocar un método sobre un valor de la estructura '"
                        + ((TipoStructura) tipoObjeto).getNombreStruct() + "'");
                setTipo(tablaTipos.getError());
                return;
            }
            List<SimboloFuncion> candidatas = clase.getMetodosPorNombre(nombreFuncion);
            SimboloFuncion metodo = reglas.resolverEntre(candidatas, tiposArgumentos);
            if (metodo == null) {
                if (candidatas.isEmpty()) {
                    contexto.agregarError(fila, columna, nombreFuncion,
                            "El objeto de clase '" + clase.getId()
                            + "' no tiene un método '" + nombreFuncion + "'");
                } else {
                    contexto.agregarError(fila, columna, nombreFuncion,
                            "No existe una sobrecarga del método '" + nombreFuncion
                            + "' compatible con los argumentos proporcionados");
                }
                setTipo(tablaTipos.getError());
                return;
            }
            List<SimboloParametro> parametros = metodo.getParametros();
            if (argumentos != null) {
                for (int i = 0; i < argumentos.size(); i++) {
                    Expresion argumento = argumentos.get(i);
                    SimboloParametro parametro = parametros.get(i);
                    if (!reglas.esAsignable(parametro.getTipo(), argumento.getTipo())) {
                        contexto.agregarError(argumento.getFila(), argumento.getColumna(),
                                nombreFuncion, "El argumento " + (i + 1)
                                + " del método '" + nombreFuncion
                                + "' es incompatible con su parámetro");
                    }
                }
            }
            setTipo(metodo.getTipoRetorno());
            return;
        }
        List<Tipo> tiposArgumentos = verificarArgumentos(contexto);
        if (contexto.getClaseActual() != null) {
            List<SimboloFuncion> metodosClase = contexto.getClaseActual()
                    .getMetodosPorNombre(nombreFuncion);
            if (!metodosClase.isEmpty()) {
                SimboloFuncion metodo = reglas.resolverEntre(metodosClase, tiposArgumentos);
                if (metodo == null) {
                    contexto.agregarError(fila, columna, nombreFuncion,
                            "No existe una sobrecarga del método '" + nombreFuncion
                            + "' compatible con los argumentos proporcionados");
                    setTipo(tablaTipos.getError());
                    return;
                }
                List<SimboloParametro> parametros = metodo.getParametros();
                if (argumentos != null) {
                    for (int i = 0; i < argumentos.size(); i++) {
                        Expresion argumento = argumentos.get(i);
                        SimboloParametro parametro = parametros.get(i);
                        if (!reglas.esAsignable(parametro.getTipo(), argumento.getTipo())) {
                            contexto.agregarError(argumento.getFila(), argumento.getColumna(),
                                    nombreFuncion, "El argumento " + (i + 1)
                                    + " del método '" + nombreFuncion
                                    + "' es incompatible con su parámetro");
                        }
                    }
                }
                setTipo(metodo.getTipoRetorno());
                return;
            }
        }
        SimboloFuncion funcion = reglas.resolverFuncion(contexto, nombreFuncion, tiposArgumentos);
        if (funcion == null) {
            if (contexto.getAmbito().buscarSobrecargas(nombreFuncion).isEmpty()) {
                contexto.agregarError(fila, columna, nombreFuncion,
                        "La función '" + nombreFuncion + "' no está definida");
            } else {
                contexto.agregarError(fila, columna, nombreFuncion,
                        "No existe una sobrecarga de la función '" + nombreFuncion
                        + "' compatible con los argumentos proporcionados");
            }
            setTipo(tablaTipos.getError());
            return;
        }
        List<SimboloParametro> parametros = funcion.getParametros();
        if (argumentos != null) {
            for (int i = 0; i < argumentos.size(); i++) {
                Expresion argumento = argumentos.get(i);
                SimboloParametro parametro = parametros.get(i);
                if (!reglas.esAsignable(parametro.getTipo(), argumento.getTipo())) {
                    contexto.agregarError(argumento.getFila(), argumento.getColumna(),
                            nombreFuncion, "El argumento " + (i + 1)
                            + " de la función '" + nombreFuncion
                            + "' es incompatible con su parámetro");
                }
            }
        }
        setTipo(funcion.getTipoRetorno());
    }

    private List<Tipo> verificarArgumentos(Contexto contexto) {
        List<Tipo> tiposArgumentos = new ArrayList<>();
        if (argumentos != null) {
            for (Expresion argumento : argumentos) {
                argumento.verificarSemantica(contexto);
                tiposArgumentos.add(argumento.getTipo());
            }
        }
        return tiposArgumentos;
    }

}