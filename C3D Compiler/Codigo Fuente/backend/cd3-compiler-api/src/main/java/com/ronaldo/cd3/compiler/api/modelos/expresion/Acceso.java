package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;

/**
 *
 * @author ronaldo
 */
public class Acceso extends Expresion implements Verificable {

    private final Reglas reglas = new Reglas();
    private Expresion objeto;
    private String atributo;

    public Acceso(Expresion objeto, String atributo, int fila, int columna) {
        super(fila, columna);
        this.objeto = objeto;
        this.atributo = atributo;
    }

    public Expresion getObjeto() {
        return objeto;
    }

    public String getAtributo() {
        return atributo;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (objeto != null) {
            objeto.verificarSemantica(contexto);
        }
        Tipo tipoObjeto = (objeto != null) ? objeto.getTipo() : null;
        if (reglas.esError(tipoObjeto)) {
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        if (!(tipoObjeto instanceof TipoStructura)) {
            contexto.agregarError(fila, columna, atributo,
                    "No se puede acceder al atributo '" + atributo + "' de un valor no estructurado");
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        TipoStructura estructura = (TipoStructura) tipoObjeto;
        Tipo tipoAtributo = estructura.getTipoAtributo(atributo);
        if (tipoAtributo == null) {
            contexto.agregarError(fila, columna, atributo,
                    "La estructura '" + estructura.getNombreStruct()
                    + "' no tiene el atributo '" + atributo + "'");
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        setTipo(tipoAtributo);
    }

}