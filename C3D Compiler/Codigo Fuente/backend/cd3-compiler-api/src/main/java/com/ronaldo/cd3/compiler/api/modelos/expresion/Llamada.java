package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
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
                        "No se puede invocar al metodo '" + nombreFuncion
                        + "' sobre un valor que no es un objeto");
                setTipo(tablaTipos.getError());
                return;
            }

            SimboloClase clase = contexto.getTablaSimbolos().buscarClase(
                    ((TipoStructura) tipoObjeto).getNombreStruct());

            if (clase == null) {
                contexto.agregarError(fila, columna, nombreFuncion,
                        "No se puede invocar un metodo sobre un valor de la estructura '"
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
                            + "' no tiene un metodo '" + nombreFuncion + "'");
                } else {
                    contexto.agregarError(fila, columna, nombreFuncion,
                            "No existe una sobrecarga del metodo '" + nombreFuncion
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
                                + " del metodo '" + nombreFuncion
                                + "' es incompatible con su parametro");
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
                            "No existe una sobrecarga del metodo '" + nombreFuncion
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
                                    + " del metodo '" + nombreFuncion
                                    + "' es incompatible con su parametro");
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
                        "La funcion '" + nombreFuncion + "' no esta definida");
            } else {
                contexto.agregarError(fila, columna, nombreFuncion,
                        "No existe una sobrecarga de la funcion '" + nombreFuncion
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
                            + " de la funcion '" + nombreFuncion
                            + "' es incompatible con su parametro");
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

        List<String> temporalesArgumentos = new ArrayList<>();

        if (argumentos != null) {
            for (Expresion arg : argumentos) {
                temporalesArgumentos.add(arg.generarCuartetas(contexto, cuartetas));
            }
        }

        SimboloFuncion funcion = resolverFuncion(contexto);
        String etiqueta = (funcion != null)
                ? funcion.getEtiquetaInicio() : nombreFuncion;

        boolean esMetodo = funcion != null && funcion.getNombreClase() != null;
        if (esMetodo) {
            String receptor = (dirObjetivo != null) ? dirObjetivo : "this";
            cuartetas.agregar(OperadorCuarteta.PARAMETRO, receptor,
                    null, null, fila, columna);
        }

        for (String dir : temporalesArgumentos) {
            cuartetas.agregar(OperadorCuarteta.PARAMETRO, dir,
                    null, null, fila, columna);
        }

        //Llamada
        String temporal = cuartetas.nuevoTemporal();
        cuartetas.registrarTipoTemporal(temporal, getTipo());
        if (etiqueta != null) {
            cuartetas.registrarTipoFuncion(etiqueta, getTipo());
        }
        cuartetas.agregar(OperadorCuarteta.LLAMADA, etiqueta,
                null, temporal, fila, columna);

        return temporal;
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
            return resolverMetodo(clase.getMetodosPorNombre(nombreFuncion));
        }
        if (contexto.getClaseActual() != null
                && contexto.getClaseActual().getMetodosPorNombre(nombreFuncion) != null
                && !contexto.getClaseActual().getMetodosPorNombre(nombreFuncion).isEmpty()) {
            return resolverMetodo(
                    contexto.getClaseActual().getMetodosPorNombre(nombreFuncion));
        }
        return resolverFuncionLibre(contexto);
    }

    private SimboloFuncion resolverMetodo(
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
