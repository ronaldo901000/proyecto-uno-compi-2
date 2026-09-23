package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.Simbolo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;

/**
 *
 * @author ronaldo
 */
public class AccesoVariable extends Expresion implements Verificable {

    private String id;

    public AccesoVariable(String id, int fila, int columna) {
        super(fila, columna);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        Simbolo simbolo = contexto.getAmbito().buscar(id);
        if (simbolo instanceof SimboloVariable) {
            setTipo(((SimboloVariable) simbolo).getTipo());
            return;
        }
        if (simbolo instanceof SimboloParametro) {
            setTipo(((SimboloParametro) simbolo).getTipo());
            return;
        }
        contexto.agregarError(fila, columna, id,
                "La variable '" + id + "' no ha sido declarada");
        setTipo(contexto.getTablaTipos().getError());
    }

    
    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        return id;
    }

}