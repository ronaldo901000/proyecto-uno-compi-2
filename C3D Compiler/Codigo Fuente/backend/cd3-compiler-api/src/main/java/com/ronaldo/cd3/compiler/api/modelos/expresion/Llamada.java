package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
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
        if (objetivo != null) {
            objetivo.verificarSemantica(contexto);
        }
        List<Tipo> tiposArgumentos = new ArrayList<>();
        if (argumentos != null) {
            for (Expresion argumento : argumentos) {
                argumento.verificarSemantica(contexto);
                tiposArgumentos.add(argumento.getTipo());
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
            setTipo(contexto.getTablaTipos().getError());
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
                            + " de la función '" + nombreFuncion + "' es incompatible con su parámetro");
                }
            }
        }
        setTipo(funcion.getTipoRetorno());
    }

}