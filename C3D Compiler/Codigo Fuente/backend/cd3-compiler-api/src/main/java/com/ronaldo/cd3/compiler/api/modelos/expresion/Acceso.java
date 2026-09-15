package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
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
        
        // verificar si el objeto es de tipo primitivo, indicar que no tiene acceso .algo
        if (!(tipoObjeto instanceof TipoStructura)) {
            contexto.agregarError(fila, columna, atributo,
                    "No se puede acceder al atributo '" + atributo
                    + "' porque el valor es de tipo primitivo '" + tipoObjeto
                    + "', el cual no tiene atributos");
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        
        TipoStructura estructura = (TipoStructura) tipoObjeto;
        SimboloClase clase = contexto.getTablaSimbolos().buscarClase(
                estructura.getNombreStruct());
        if (clase != null) {
            SimboloVariable atributoSimbolo = clase.getAtributo(atributo);
            if (atributoSimbolo == null) {
                contexto.agregarError(fila, columna, atributo,
                        "El atributo '" + atributo + "' no existe en la clase '"
                        + clase.getId() + "'");
                setTipo(contexto.getTablaTipos().getError());
                return;
            }
            setTipo(atributoSimbolo.getTipo());
            return;
        }
        Tipo tipoAtributo = estructura.getTipoAtributo(atributo);
        if (tipoAtributo == null) {
            contexto.agregarError(fila, columna, atributo,
                    "El atributo '" + atributo + "' no está declarado en la estructura '"
                    + estructura.getNombreStruct() + "'");
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        setTipo(tipoAtributo);
    }

}