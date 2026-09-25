package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class DeclaracionArreglo extends Declaracion {

    private final Reglas reglas = new Reglas();
    private List<Expresion> dimensiones;
    private List<Expresion> valoresIniciales;

    public DeclaracionArreglo(List<Expresion> dimensiones, List<Expresion> valoresIniciales, String tipoDato, String id, int fila, int columna) {
        super(tipoDato, id, fila, columna);
        this.dimensiones = dimensiones;
        this.valoresIniciales = valoresIniciales;
    }

    public List<Expresion> getDimensiones() {
        return dimensiones;
    }

    public List<Expresion> getValoresIniciales() {
        return valoresIniciales;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        int numDimensiones = (dimensiones != null) ? dimensiones.size() : 0;
        List<Integer> tamaños = new ArrayList<>();
        for (int i = 0; i < numDimensiones; i++) {
            Expresion dimension = dimensiones.get(i);
            if (dimension == null) {
                tamaños.add(0);
                continue;
            }
            dimension.verificarSemantica(contexto);
            if (dimension.getTipo() == null
                    || dimension.getTipo().getTipoDato() != TipoDato.ENTERO) {
                contexto.agregarError(dimension.getFila(), dimension.getColumna(),
                        id, "La dimensión " + (i + 1) + " del arreglo '"
                        + id + "' debe ser un valor entero");
                tamaños.add(0);
                continue;
            }
            Integer tamaño = reglas.constanteEntera(dimension);
            if (tamaño != null && tamaño <= 0) {
                contexto.agregarError(dimension.getFila(), dimension.getColumna(),
                        id, "La dimensión " + (i + 1) + " del arreglo '"
                        + id + "' debe ser mayor que 0");
                tamaños.add(0);
                continue;
            }
            tamaños.add((tamaño != null) ? tamaño : 0);
        }
        Tipo base = reglas.resolverTipo(contexto, tipoDato, fila, columna);
        if (reglas.esError(base)) {
            return;
        }
        if (numDimensiones == 0) {
            contexto.agregarError(fila, columna, id,
                    "El arreglo '" + id + "' debe declarar al menos una dimensión");
            return;
        }
        if (valoresIniciales != null && !valoresIniciales.isEmpty()
                && numDimensiones == 1 && tamaños.get(0) == 0) {
            tamaños.set(0, valoresIniciales.size());
        }
        Tipo tipoArreglo = contexto.getTablaTipos().getArreglo(base, tamaños);
        SimboloVariable variable = reglas.registrarVariable(contexto, id, tipoArreglo, fila, columna);
        if (variable != null && valoresIniciales != null) {
            for (Expresion valor : valoresIniciales) {
                valor.verificarSemantica(contexto);
                if (!reglas.esAsignable(base, valor.getTipo())) {
                    contexto.agregarError(fila, columna, id,
                            "Un valor inicial del arreglo '" + id + "' es incompatible con su tipo base");
                }
            }
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        Tipo base = reglas.resolverTipo(contexto, tipoDato, fila, columna);
        cuartetas.registrarTipoArregloDeclarado(id, base);
        if (valoresIniciales != null) {
            for (int i = 0; i < valoresIniciales.size(); i++) {
                Expresion valor = valoresIniciales.get(i);
                String dirValor = valor.generarCuartetas(contexto, cuartetas);
                cuartetas.agregar(OperadorCuarteta.ASIGNACION, dirValor,
                        null, id + "[" + i + "]", fila, columna);
            }
        }
        return null;
    }

}