package com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class InstElegir extends Nodo implements Instruccion {

    private Expresion valorEvaluado;
    private List<CasoSwitch> casos;

    public InstElegir(Expresion valorEvaluado, List<CasoSwitch> casos, int fila, int columna) {
        super(fila, columna);
        this.valorEvaluado = valorEvaluado;
        this.casos = casos;
    }

    public Expresion getValorEvaluado() {
        return valorEvaluado;
    }

    public void setValorEvaluado(Expresion valorEvaluado) {
        this.valorEvaluado = valorEvaluado;
    }

    public List<CasoSwitch> getCasos() {
        return casos;
    }

    public void setCasos(List<CasoSwitch> casos) {
        this.casos = casos;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (valorEvaluado != null) {
            valorEvaluado.verificarSemantica(contexto);
        }
        Tipo tipoEvaluado = (valorEvaluado != null) ? valorEvaluado.getTipo() : null;
        
        contexto.setdentroDeSwitch(true);
        if (casos != null) {
            for (CasoSwitch caso : casos) {
                caso.verificarSemantica(contexto, tipoEvaluado);
            }
        }
        contexto.setdentroDeSwitch(false);
    }

}