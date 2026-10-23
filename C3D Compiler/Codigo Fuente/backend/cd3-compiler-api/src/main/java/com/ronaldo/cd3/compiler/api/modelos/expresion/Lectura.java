package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Direccion;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public class Lectura extends Expresion implements Instruccion {

    private final Direccion direccion = new Direccion();
    private Expresion argumento;
    // Tipo esperado por el contexto (declaracion, asignacion, argumento, retorno).
    // Si es null, se usa CADENA por defecto.
    private Tipo tipoEsperado;

    public Lectura(int fila, int columna) {
        super(fila, columna);
    }

    public Lectura(Expresion argumento, int fila, int columna) {
        super(fila, columna);
        this.argumento = argumento;
    }

    public Expresion getArgumento() {
        return argumento;
    }

    public Tipo getTipoEsperado() {
        return tipoEsperado;
    }

    public void setTipoEsperado(Tipo tipoEsperado) {
        this.tipoEsperado = tipoEsperado;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (tipoEsperado != null) {
            setTipo(tipoEsperado);
        } else {
            setTipo(contexto.getTablaTipos().getCadena());
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {

        if (argumento != null) {

            String dir = direccion.lvalue(argumento, contexto, cuartetas);
            cuartetas.agregar(OperadorCuarteta.LEER, null, null,
                    dir, fila, columna);

        } else {

            cuartetas.agregar(OperadorCuarteta.LEER, null, null,
                    null, fila, columna);

        }
        return null;
    }

}
