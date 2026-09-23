package com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CasoSwitch extends Nodo implements Verificable {

    private final Reglas reglas = new Reglas();
    private Expresion valor;
    private List<Instruccion> intruccionesInternas;

    public CasoSwitch(Expresion valor, List<Instruccion> intruccionesInternas, int fila, int columna) {
        super(fila, columna);
        this.valor = valor;
        this.intruccionesInternas = intruccionesInternas;
    }

    public Expresion getValor() {
        return valor;
    }

    public List<Instruccion> getIntruccionesInternas() {
        return intruccionesInternas;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        verificarSemantica(contexto, null);
    }

    
    public void verificarSemantica(Contexto contexto, Tipo tipoEvaluado) {
        
        if (valor != null) {
            
            valor.verificarSemantica(contexto);
            
            if (tipoEvaluado != null && !reglas.comparables(tipoEvaluado, valor.getTipo())) {
                contexto.agregarError(fila, columna, null,
                        "El valor del caso es incompatible con el valor evaluado");
            }
            
        }
        
        TablaSimbolos anterior = contexto.nuevoAmbito("caso");
        reglas.verificarInstrucciones(contexto, intruccionesInternas);
        contexto.restaurarAmbito(anterior);
    }

}