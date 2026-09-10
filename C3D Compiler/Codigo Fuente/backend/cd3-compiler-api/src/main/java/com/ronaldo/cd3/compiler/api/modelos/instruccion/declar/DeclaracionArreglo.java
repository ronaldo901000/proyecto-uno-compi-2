package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
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
        List<Integer> tamanos = new ArrayList<>();
        for (int i = 0; i < numDimensiones; i++) {
            Expresion dimension = dimensiones.get(i);
            dimension.verificarSemantica(contexto);
            if (dimension.getTipo() == null
                    || dimension.getTipo().getTipoDato() != TipoDato.ENTERO) {
                contexto.agregarError(dimension.getFila(), dimension.getColumna(),
                        id, "La dimensión " + (i + 1) + " del arreglo '"
                        + id + "' debe ser un valor entero");
                tamanos.add(0);
                continue;
            }
            Integer tamano = reglas.constanteEntera(dimension);
            if (tamano != null && tamano <= 0) {
                contexto.agregarError(dimension.getFila(), dimension.getColumna(),
                        id, "La dimensión " + (i + 1) + " del arreglo '"
                        + id + "' debe ser mayor que 0");
                tamanos.add(0);
                continue;
            }
            tamanos.add((tamano != null) ? tamano : 0);
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
        Tipo tipoArreglo = contexto.getTablaTipos().getArreglo(base, tamanos);
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

}