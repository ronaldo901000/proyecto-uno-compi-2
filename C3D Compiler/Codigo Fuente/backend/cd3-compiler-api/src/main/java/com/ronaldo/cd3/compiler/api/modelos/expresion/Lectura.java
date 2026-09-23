package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Direccion;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;

/**
 *
 * @author ronaldo
 */
public class Lectura extends Expresion implements Instruccion {

    private final Direccion direccion = new Direccion();
    private Expresion argumento;

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

    @Override
    public void verificarSemantica(Contexto contexto) {
        setTipo(contexto.getTablaTipos().getCadena());
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
