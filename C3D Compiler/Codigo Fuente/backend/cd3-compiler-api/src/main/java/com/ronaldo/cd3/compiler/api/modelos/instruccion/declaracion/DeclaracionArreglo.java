package com.ronaldo.cd3.compiler.api.modelos.instruccion.declaracion;

import com.ronaldo.cd3.compiler.api.enums.ModificadoresAcceso;
import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
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
    private Expresion valorExpresion;

    public DeclaracionArreglo(List<Expresion> dimensiones, List<Expresion> valoresIniciales,
            String tipoDato, String id, int fila, int columna) {

        super(tipoDato, id, fila, columna);
        this.dimensiones = dimensiones;
        this.valoresIniciales = valoresIniciales;
    }

    public DeclaracionArreglo(List<Expresion> dimensiones, List<Expresion> valoresIniciales,
            ModificadoresAcceso modAcceso, String tipoDato, String id, int fila, int columna) {

        super(modAcceso, tipoDato, id, fila, columna);
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
        List<Integer> tamaños = verificarDimensiones(contexto);

        Tipo base = reglas.resolverTipo(contexto, tipoDato, fila, columna);
        if (reglas.esError(base)) {
            return;
        }
        if (tamaños.isEmpty()) {
            contexto.agregarError(fila, columna, id,
                    "El arreglo '" + id + "' debe declarar al menos una dimensión");
            return;
        }

        ajustarTamañoPorValoresIniciales(tamaños);

        Tipo tipoArreglo = contexto.getTablaTipos().getArreglo(base, tamaños);

        if (valorExpresion != null) {
            valorExpresion.verificarSemantica(contexto);
        }

        SimboloVariable variable = reglas.registrarVariable(contexto, id, tipoArreglo, fila, columna);
        if (variable == null) {
            return;
        }

        verificarValorExpresion(contexto, tipoArreglo);
        verificarValoresIniciales(contexto, base);
    }

    /**
     * Verifica cada dimension declarada y devuelve sus tamaños , 0 si no se conoce
     */
    private List<Integer> verificarDimensiones(Contexto contexto) {
        List<Integer> tamaños = new ArrayList<>();
        if (dimensiones == null) {
            return tamaños;
        }
        for (int i = 0; i < dimensiones.size(); i++) {
            tamaños.add(verificarDimension(contexto, dimensiones.get(i), i));
        }
        return tamaños;
    }

    /**
     * Devuelve el tamaño constante de la dimension, o 0 si no se conoce o es invalida
     */
    private int verificarDimension(Contexto contexto, Expresion dimension, int indice) {
        if (dimension == null) {
            return 0;
        }
        dimension.verificarSemantica(contexto);

        if (dimension.getTipo() == null
                || dimension.getTipo().getTipoDato() != TipoDato.ENTERO) {
            contexto.agregarError(dimension.getFila(), dimension.getColumna(),
                    id, "La dimensión " + (indice + 1) + " del arreglo '"
                    + id + "' debe ser un valor entero");
            return 0;
        }

        Integer tamaño = reglas.constanteEntera(dimension);
        if (tamaño != null && tamaño <= 0) {
            contexto.agregarError(dimension.getFila(), dimension.getColumna(),
                    id, "La dimensión " + (indice + 1) + " del arreglo '"
                    + id + "' debe ser mayor que 0");
            return 0;
        }
        return (tamaño != null) ? tamaño : 0;
    }

    /**
     * En int[] a = {1, 2, 3} el tamaño se toma de la cantidad de valores
     * iniciales.
     */
    private void ajustarTamañoPorValoresIniciales(List<Integer> tamaños) {
        boolean unaDimensionSinTamaño = tamaños.size() == 1 && tamaños.get(0) == 0;
        if (valoresIniciales != null && !valoresIniciales.isEmpty() && unaDimensionSinTamaño) {
            tamaños.set(0, valoresIniciales.size());
        }
    }

    /**
     * Verifica inicializadores como: new tipo[n] o una expresion cualquiera.
     */
    private void verificarValorExpresion(Contexto contexto, Tipo tipoArreglo) {
        if (valorExpresion == null) {
            return;
        }
        if (!reglas.esAsignable(tipoArreglo, valorExpresion.getTipo())) {
            contexto.agregarError(valorExpresion.getFila(), valorExpresion.getColumna(), id,
                    "No se puede asignar un valor de tipo '" + valorExpresion.getTipo()
                    + "' al arreglo '" + id + "' de tipo '" + tipoArreglo + "'");
        }
    }

    /**
     * Verifica inicializadores de la forma {a,b,c}
     */
    private void verificarValoresIniciales(Contexto contexto, Tipo base) {
        if (valoresIniciales == null) {
            return;
        }
        for (Expresion valor : valoresIniciales) {
            valor.verificarSemantica(contexto);
            if (!reglas.esAsignable(base, valor.getTipo())) {
                contexto.agregarError(fila, columna, id,
                        "Un valor inicial del arreglo '" + id + "' es incompatible con su tipo base");
            }
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        Tipo base = reglas.resolverTipo(contexto, tipoDato, fila, columna);
        cuartetas.registrarTipoArregloDeclarado(id, base);

        List<Integer> tamaños = new ArrayList<>();

        boolean tieneDimension = false;

        for (Expresion dim : dimensiones) {
            Integer tam = (dim != null) ? reglas.constanteEntera(dim) : null;
            tamaños.add((tam != null && tam > 0) ? tam : 0);
            if (tam == null && dim != null) {
                tieneDimension = true;
            }
        }

        Tipo tipoArreglo = contexto.getTablaTipos().getArreglo(base, tamaños);
        cuartetas.registrarTipoVariableDeclarada(id, tipoArreglo);

        if (tieneDimension) {
            StringBuilder expr = new StringBuilder();
            for (int i = 0; i < dimensiones.size(); i++) {
                Expresion dim = dimensiones.get(i);
                if (dim == null) {
                    expr.append("1");
                } else if (i > 0) {
                    expr.append(" * (");
                    String dirDim = dim.generarCuartetas(contexto, cuartetas);
                    expr.append(dirDim);
                    expr.append(")");
                } else {
                    String dirDim = dim.generarCuartetas(contexto, cuartetas);
                    expr.append(dirDim);
                }
            }
            cuartetas.registrarDimensionArreglo(id, expr.toString());
        }

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

    public Expresion getValorExpresion() {
        return valorExpresion;
    }

    public void setValorExpresion(Expresion valorExpresion) {
        this.valorExpresion = valorExpresion;
    }

}
