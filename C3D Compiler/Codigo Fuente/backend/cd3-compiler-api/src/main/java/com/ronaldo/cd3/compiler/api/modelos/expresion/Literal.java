package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;

/**
 *
 * @author ronaldo
 */
public class Literal extends Expresion implements Verificable {

    private final Reglas reglas = new Reglas();
    private Object contenido;

    public Literal(Object contenido, int fila, int columna) {
        super(fila, columna);
        this.contenido = contenido;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        String texto;
        if (contenido instanceof org.antlr.v4.runtime.Token) {
            texto = ((org.antlr.v4.runtime.Token) contenido).getText();
        } else if (contenido != null) {
            texto = String.valueOf(contenido);
        } else {
            texto = null;
        }
        setTipo(reglas.tipoDeLiteral(contexto, texto, fila, columna));
    }

}