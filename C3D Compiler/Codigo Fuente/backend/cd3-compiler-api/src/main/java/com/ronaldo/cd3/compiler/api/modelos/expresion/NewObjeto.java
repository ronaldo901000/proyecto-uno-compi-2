package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class NewObjeto extends Expresion implements Verificable {

    private final Reglas reglas = new Reglas();
    private String nombreClase;
    private List<Expresion> argumentos;

    public NewObjeto(String nombreClase, List<Expresion> argumentos, int fila, int columna) {
        super(fila, columna);
        this.nombreClase = nombreClase;
        this.argumentos = argumentos;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public List<Expresion> getArgumentos() {
        return argumentos;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {

        TablaTipos tablaTipos = contexto.getTablaTipos();

        List<Tipo> tiposArgumentos = new ArrayList<>();

        if (argumentos != null) {

            for (Expresion argumento : argumentos) {
                argumento.verificarSemantica(contexto);
                tiposArgumentos.add(argumento.getTipo());
            }

        }

        SimboloClase clase = contexto.getTablaSimbolos().buscarClase(nombreClase);

        if (clase == null) {

            contexto.agregarError(fila, columna, nombreClase,
                    "La clase '" + nombreClase + "' no está definida.");
            setTipo(tablaTipos.getError());
            return;

        }

        if (clase.getConstructores().isEmpty()) {

            if (tiposArgumentos.isEmpty()) {
                setTipo(clase.getTipo());
                return;
            }

            contexto.agregarError(fila, columna, nombreClase,
                    "La clase '" + nombreClase + "' no tiene un constructor compatible "
                    + "con los argumentos proporcionados");

            setTipo(tablaTipos.getError());
            return;
        }

        SimboloFuncion constructor = reglas.resolverEntre(
                clase.getConstructores(), tiposArgumentos);

        if (constructor == null) {

            contexto.agregarError(fila, columna, nombreClase,
                    "No existe un constructor de la clase '" + nombreClase
                    + "' compatible con los argumentos proporcionados");
            setTipo(tablaTipos.getError());
            return;

        }

        List<SimboloParametro> parametros = constructor.getParametros();

        if (argumentos != null) {
            for (int i = 0; i < argumentos.size(); i++) {

                Expresion argumento = argumentos.get(i);
                SimboloParametro parametro = parametros.get(i);

                if (!reglas.esAsignable(parametro.getTipo(), argumento.getTipo())) {

                    contexto.agregarError(argumento.getFila(), argumento.getColumna(),
                            nombreClase, "El argumento " + (i + 1)
                            + " del constructor de la clase '" + nombreClase
                            + "' es incompatible con su parametro.");

                }
            }
        }
        setTipo(clase.getTipo());
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {

        String temporalPtr = cuartetas.nuevoTemporal();
        SimboloClase clase = contexto.getTablaSimbolos().buscarClase(nombreClase);

        if (clase != null && clase.getTipo() != null) {
            cuartetas.registrarTipoTemporal(temporalPtr, clase.getTipo());
        }

        cuartetas.agregar(OperadorCuarteta.PUNTERO_INICIO, null,
                null, temporalPtr, fila, columna);

        List<String> dirsArgumentos = new ArrayList<>();

        if (argumentos != null) {
            for (Expresion arg : argumentos) {
                dirsArgumentos.add(arg.generarCuartetas(contexto, cuartetas));
            }
        }

        SimboloFuncion constructor = resolverConstructor(contexto);

        String etiquetaConstructor = (constructor != null)
                ? constructor.getEtiquetaInicio()
                : "constructor_" + nombreClase;

        if (constructor != null) {
            cuartetas.agregar(OperadorCuarteta.PARAMETRO, temporalPtr,
                    null, null, fila, columna);
            if (argumentos != null) {
                for (String dir : dirsArgumentos) {
                    cuartetas.agregar(OperadorCuarteta.PARAMETRO, dir,
                            null, null, fila, columna);
                }
            }

            cuartetas.agregar(OperadorCuarteta.LLAMADA, etiquetaConstructor,
                    null, temporalPtr, fila, columna);
        }

        return temporalPtr;
    }

    private SimboloFuncion resolverConstructor(Contexto contexto) {
        SimboloClase clase = contexto.getTablaSimbolos().buscarClase(nombreClase);
        if (clase == null || clase.getConstructores().isEmpty()) {
            return null;
        }
        List<Tipo> tipos = new ArrayList<>();
        if (argumentos != null) {
            for (Expresion arg : argumentos) {
                tipos.add(arg.getTipo());
            }
        }
        return reglas.resolverEntre(clase.getConstructores(), tipos);
    }

}
