package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class NewArreglo extends Expresion implements Verificable {

    private final Reglas reglas = new Reglas();
    private String tipoBase;
    private List<Expresion> dimensiones;

    public NewArreglo(String tipoBase, List<Expresion> dimensiones,
            int fila, int columna) {
        super(fila, columna);
        this.tipoBase = tipoBase;
        this.dimensiones = dimensiones;
    }

    public String getTipoBase() {
        return tipoBase;
    }

    public List<Expresion> getDimensiones() {
        return dimensiones;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        int numDimensiones = (dimensiones != null) ? dimensiones.size() : 0;
        if (numDimensiones == 0) {
            contexto.agregarError(fila, columna, tipoBase,
                    "La creación de arreglos debe indicar al menos una dimensión");
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        List<Integer> tamanos = new ArrayList<>();
        for (int i = 0; i < numDimensiones; i++) {
            Expresion dimension = dimensiones.get(i);
            dimension.verificarSemantica(contexto);
            if (dimension.getTipo() == null
                    || dimension.getTipo().getTipoDato() != TipoDato.ENTERO) {
                contexto.agregarError(dimension.getFila(), dimension.getColumna(),
                        tipoBase, "La dimensión " + (i + 1) + " del arreglo "
                        + "creado con 'new' debe ser un valor entero");
                tamanos.add(0);
                continue;
            }
            Integer tamano = reglas.constanteEntera(dimension);
            if (tamano != null && tamano <= 0) {
                contexto.agregarError(dimension.getFila(), dimension.getColumna(),
                        tipoBase, "La dimensión " + (i + 1) + " del arreglo "
                        + "creado con 'new' debe ser mayor que 0");
                tamanos.add(0);
                continue;
            }
            tamanos.add((tamano != null) ? tamano : 0);
        }
        Tipo base = reglas.resolverTipo(contexto, tipoBase, fila, columna);
        if (reglas.esError(base)) {
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        setTipo(contexto.getTablaTipos().getArreglo(base, tamanos));
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String temporalPtr = cuartetas.nuevoTemporal();
        String dirDimension = null;
        if (dimensiones != null && !dimensiones.isEmpty()) {
            dirDimension = dimensiones.get(0).generarCuartetas(contexto, cuartetas);
        }
        cuartetas.agregar(OperadorCuarteta.PUNTERO_INICIO, tipoBase,
                dirDimension, temporalPtr, fila, columna);
        return temporalPtr;
    }

}