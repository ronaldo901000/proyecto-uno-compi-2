package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;

/**
 *
 * @author ronaldo
 */
public class ExpIndice extends Expresion implements Verificable {

    private final Reglas reglas = new Reglas();
    private Expresion arreglo;
    private Expresion indice;

    public ExpIndice(Expresion arreglo, Expresion indice, int fila, int columna) {
        super(fila, columna);
        this.arreglo = arreglo;
        this.indice = indice;
    }

    public Expresion getArreglo() {
        return arreglo;
    }

    public Expresion getIndice() {
        return indice;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (arreglo != null) {
            arreglo.verificarSemantica(contexto);
        }
        if (indice != null) {
            indice.verificarSemantica(contexto);
        }
        Tipo tipoArreglo = (arreglo != null) ? arreglo.getTipo() : null;
        if (reglas.esError(tipoArreglo)) {
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        if (indice != null && !reglas.esNumerico(indice.getTipo())) {
            contexto.agregarError(indice.getFila(), indice.getColumna(), indice.resultado.toString(),
                    "El índice de un arreglo debe ser numerico");
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        if (!(tipoArreglo instanceof TipoArreglo)) {
            contexto.agregarError(fila, columna, null,
                    "Se intentó indexar un valor que no es un arreglo");
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        setTipo(((TipoArreglo) tipoArreglo).getTipoBase());
    }

}