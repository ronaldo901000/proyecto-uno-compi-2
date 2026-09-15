package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class LiteralStructura extends Expresion implements Verificable {

    private List<Expresion> valores;

    public LiteralStructura(List<Expresion> valores, int fila, int columna) {
        super(fila, columna);
        this.valores = valores;
    }

    public List<Expresion> getValores() {
        return valores;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (valores != null) {
            for (Expresion valor : valores) {
                valor.verificarSemantica(contexto);
            }
        }
    }
}