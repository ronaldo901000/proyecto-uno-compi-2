package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author ronaldo
 */
public class Literal extends Expresion implements Verificable {

    private final Reglas reglas = new Reglas();
    private Object contenido;

    public Literal(Object contenido, TipoDato resultado, int fila, int columna) {
        super(fila, columna);
        this.contenido = contenido;
        this.resultado = resultado;
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
        if (contenido instanceof Token) {
            texto = ((Token) contenido).getText();
        } else if (contenido != null) {
            texto = String.valueOf(contenido);
        } else {
            texto = null;
        }
        setTipo(reglas.tipoDeLiteral(contexto, resultado, fila, columna));
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String texto;
        if (contenido instanceof Token) {
            texto = ((Token) contenido).getText();
        } else if (contenido != null) {
            texto = String.valueOf(contenido);
        } else {
            texto = "nulo";
        }
        cuartetas.registrarCategoria(texto, categoriaDe(resultado, texto));
        return texto;
    }

    private TipoOperando categoriaDe(TipoDato tipoDato, String texto) {
        if (tipoDato == null) {
            return null;
        }
        switch (tipoDato) {
            case ENTERO:
                return TipoOperando.CONSTANTE_ENTERA;
            case DECIMAL:
                return TipoOperando.CONSTANTE_DECIMAL;
            case CADENA:
                return TipoOperando.CONSTANTE_CADENA;
            case CHAR:
                return TipoOperando.CONSTANTE_CARACTER;
            case BOOLEAN:
                return esVerdadero(texto)
                        ? TipoOperando.BOOLEANO_VERDADERO
                        : TipoOperando.BOOLEANO_FALSO;
            case NULO:
                return TipoOperando.NULO;
            default:
                return null;
        }
    }

    private boolean esVerdadero(String texto) {
        if (texto == null) {
            return false;
        }
        switch (texto.toLowerCase()) {
            case "true":
            case "verum":
            case "verdadero":
            case "1":
                return true;
            default:
                return false;
        }
    }

}
