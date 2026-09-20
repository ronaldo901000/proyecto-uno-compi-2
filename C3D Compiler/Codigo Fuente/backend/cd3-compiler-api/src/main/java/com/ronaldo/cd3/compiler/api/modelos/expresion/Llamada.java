package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoPrimitivo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String dirObjetivo = null;
        if (objetivo != null) {
            dirObjetivo = objetivo.generarCuartetas(contexto, cuartetas);
        }
        List<String> dirsArgumentos = new ArrayList<>();
        if (argumentos != null) {
            for (Expresion arg : argumentos) {
                dirsArgumentos.add(arg.generarCuartetas(contexto, cuartetas));
            }
        }
        SimboloFuncion funcion = resolverFuncion(contexto);
        String etiqueta = (funcion != null)
                ? funcion.getEtiquetaInicio() : nombreFuncion;
        List<SimboloParametro> parametros = (funcion != null)
                ? funcion.getParametros() : null;

        List<String> camposActivos = camposDeObjeto(funcion, contexto);
        if (camposActivos != null && dirObjetivo != null) {
            for (String campo : camposActivos) {
                String destinoCampo = dirObjetivo + "." + campo;
                Tipo tipoCampo = tipoDeCampo(funcion, contexto, campo);
                cuartetas.registrarTipoVariable(destinoCampo, tipoCampo);
                if (tipoCampo instanceof TipoArreglo) {
                    cuartetas.registrarTipoArreglo(destinoCampo,
                            ((TipoArreglo) tipoCampo).getTipoBase());
                    cuartetas.agregar(OperadorCuarteta.COPIAR, destinoCampo,
                            null, campo, fila, columna);
                } else {
                    cuartetas.agregar(OperadorCuarteta.ASIGNACION, destinoCampo,
                            null, campo, fila, columna);
                }
            }
        }

        List<Integer> indicesPasables = new ArrayList<>();
        if (parametros != null) {
            for (int i = 0; i < parametros.size(); i++) {
                if (esPasablePorValor(parametros.get(i).getTipo())) {
                    indicesPasables.add(i);
                }
            }
        }
        Map<Integer, String> guardados = new HashMap<>();
        if (parametros != null && parametros.size() == dirsArgumentos.size()) {
            for (int i : indicesPasables) {
                SimboloParametro parametro = parametros.get(i);
                cuartetas.registrarTipoVariable(parametro.getId(), parametro.getTipo());
                String guardado = cuartetas.nuevoTemporal();
                guardados.put(i, guardado);
                cuartetas.registrarTipoTemporal(guardado, parametro.getTipo());
                cuartetas.agregar(OperadorCuarteta.ASIGNACION, parametros.get(i).getId(),
                        null, guardado, fila, columna);
            }
            for (int i : indicesPasables) {
                cuartetas.agregar(OperadorCuarteta.ASIGNACION, dirsArgumentos.get(i),
                        null, parametros.get(i).getId(), fila, columna);
            }
        } else {
            for (String dir : dirsArgumentos) {
                cuartetas.agregar(OperadorCuarteta.PARAMETRO, dir,
                        null, null, fila, columna);
            }
        }
        String temporal = cuartetas.nuevoTemporal();
        cuartetas.registrarTipoTemporal(temporal, getTipo());
        if (etiqueta != null) {
            cuartetas.registrarTipoFuncion(etiqueta, getTipo());
        }
        cuartetas.agregar(OperadorCuarteta.LLAMADA, etiqueta,
                null, temporal, fila, columna);
        if (parametros != null && parametros.size() == dirsArgumentos.size()) {
            for (int i : indicesPasables) {
                cuartetas.agregar(OperadorCuarteta.ASIGNACION, guardados.get(i),
                        null, parametros.get(i).getId(), fila, columna);
            }
        }
        if (camposActivos != null && dirObjetivo != null) {
            for (String campo : camposActivos) {
                if (tipoDeCampo(funcion, contexto, campo) instanceof TipoArreglo) {
                    cuartetas.agregar(OperadorCuarteta.COPIAR, campo,
                            null, dirObjetivo + "." + campo, fila, columna);
                } else {
                    cuartetas.agregar(OperadorCuarteta.ASIGNACION, campo,
                            null, dirObjetivo + "." + campo, fila, columna);
                }
            }
        }
        return temporal;
    }

    private List<String> camposDeObjeto(SimboloFuncion funcion, Contexto contexto) {
        if (funcion == null || objetivo == null) {
            return null;
        }
        SimboloClase clase = claseDeFuncion(funcion, contexto);
        if (clase == null
                || !(clase.getTipo() instanceof TipoStructura)
                || ((TipoStructura) clase.getTipo()).getAtributos().isEmpty()) {
            return null;
        }
        TipoStructura tipoClase = (TipoStructura) clase.getTipo();
        List<String> campos = new ArrayList<>();
        for (Map.Entry<String, Tipo> atributo : tipoClase.getAtributos().entrySet()) {
            campos.add(atributo.getKey());
        }
        return campos.isEmpty() ? null : campos;
    }

    private Tipo tipoDeCampo(SimboloFuncion funcion, Contexto contexto, String campo) {
        SimboloClase clase = claseDeFuncion(funcion, contexto);
        if (clase == null || !(clase.getTipo() instanceof TipoStructura)) {
            return null;
        }
        return ((TipoStructura) clase.getTipo()).getTipoAtributo(campo);
    }

    private SimboloClase claseDeFuncion(SimboloFuncion funcion, Contexto contexto) {
        String nombreClase = (funcion != null) ? funcion.getNombreClase() : null;
        if (nombreClase == null) {
            return null;
        }
        return contexto.getTablaSimbolos().buscarClase(nombreClase);
    }

    private boolean esPasablePorValor(Tipo tipo) {
        if (tipo == null) {
            return true;
        }
        if (tipo instanceof TipoPrimitivo) {
            return tipo.getTipoDato() != TipoDato.NULO;
        }
        return true;
    }

    private SimboloFuncion resolverFuncion(Contexto contexto) {
        if (objetivo != null) {
            Tipo tipoObjeto = objetivo.getTipo();
            if (!(tipoObjeto instanceof TipoStructura)) {
                return null;
            }
            SimboloClase clase = contexto.getTablaSimbolos()
                    .buscarClase(((TipoStructura) tipoObjeto).getNombreStruct());
            if (clase == null) {
                return null;
            }
            return resolverMetodo(clase, clase.getMetodosPorNombre(nombreFuncion));
        }
        if (contexto.getClaseActual() != null
                && contexto.getClaseActual().getMetodosPorNombre(nombreFuncion) != null
                && !contexto.getClaseActual().getMetodosPorNombre(nombreFuncion).isEmpty()) {
            return resolverMetodo(contexto.getClaseActual(),
                    contexto.getClaseActual().getMetodosPorNombre(nombreFuncion));
        }
        return resolverFuncionLibre(contexto);
    }

    private SimboloFuncion resolverMetodo(SimboloClase clase,
            List<SimboloFuncion> candidatas) {
        
        List<Tipo> tipos = tiposDeArgumentos();
        return reglas.resolverEntre(candidatas, tipos);
        
    }

    private SimboloFuncion resolverFuncionLibre(Contexto contexto) {
        List<Tipo> tipos = tiposDeArgumentos();
        return reglas.resolverFuncion(contexto, nombreFuncion, tipos);
    }

    private List<Tipo> tiposDeArgumentos() {
        List<Tipo> tipos = new ArrayList<>();
        if (argumentos != null) {
            for (Expresion arg : argumentos) {
                tipos.add(arg.getTipo());
            }
        }
        return tipos;
    }

}
