package com.ronaldo.cd3.compiler.api.modelos.instruccion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Imprimir extends Nodo implements Instruccion {

    private final List<Expresion> valores;
    private boolean conSaltoLinea;

    public Imprimir(Expresion valor, int fila, int columna) {
        this(valor, true, fila, columna);
    }

    public Imprimir(Expresion valor, boolean conSaltoLinea, int fila, int columna) {
        super(fila, columna);
        this.valores = new ArrayList<>();
        if (valor != null) {
            this.valores.add(valor);
        }
        this.conSaltoLinea = conSaltoLinea;
    }

    public Imprimir(List<Expresion> valores, boolean conSaltoLinea, int fila, int columna) {
        super(fila, columna);
        this.valores = (valores != null) ? new ArrayList<>(valores) : new ArrayList<>();
        this.conSaltoLinea = conSaltoLinea;
    }

    public List<Expresion> getValores() {
        return Collections.unmodifiableList(valores);
    }

    public boolean isConSaltoLinea() {
        return conSaltoLinea;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        for (Expresion valor : valores) {
            if (valor != null) {
                valor.verificarSemantica(contexto);
            }
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (valores.isEmpty()) {
            cuartetas.agregar(OperadorCuarteta.IMPRIMIR, "\"\"",
                    null, String.valueOf(conSaltoLinea), fila, columna);
            return null;
        }
        for (int i = 0; i < valores.size(); i++) {

            Expresion valor = valores.get(i);
            String dirValor = (valor != null)
                    ? valor.generarCuartetas(contexto, cuartetas)
                    : "\"\"";
            boolean esUltimo = (i == valores.size() - 1);

            cuartetas.agregar(OperadorCuarteta.IMPRIMIR, dirValor,
                    null, String.valueOf(esUltimo && conSaltoLinea), fila, columna);
        }
        return null;
    }

}
