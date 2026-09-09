package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class DeclaracionEstructura extends Declaracion {

    private final Reglas reglas = new Reglas();
    private List<Expresion> valoresIniciales;
    private Expresion valorExpresion;

    public DeclaracionEstructura(List<Expresion> valoresIniciales, Expresion valorExpresion, String tipoDato, String id, int fila, int columna) {
        super(tipoDato, id, fila, columna);
        this.valoresIniciales = valoresIniciales;
        this.valorExpresion = valorExpresion;
    }

    public List<Expresion> getValoresIniciales() {
        return valoresIniciales;
    }

    public Expresion getValorExpresion() {
        return valorExpresion;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        Tipo tipo = reglas.resolverTipo(contexto, tipoDato, fila, columna);
        if (reglas.esError(tipo)) {
            return;
        }
        if (!(tipo instanceof TipoStructura)) {
            contexto.agregarError(fila, columna, tipoDato,
                    "El tipo '" + tipoDato + "' de la declaración '" + id + "' no es una estructura");
            return;
        }
        TipoStructura estructura = (TipoStructura) tipo;
        SimboloVariable variable = reglas.registrarVariable(contexto, id, tipo, fila, columna);
        if (variable == null) {
            return;
        }
        if (valorExpresion != null) {
            valorExpresion.verificarSemantica(contexto);
            if (!reglas.esAsignable(tipo, valorExpresion.getTipo())) {
                contexto.agregarError(fila, columna, id,
                        "La asignación a '" + id + "' es incompatible con el tipo " + tipoDato);
            }
        }
        if (valoresIniciales != null) {
            int contador = 0;
            for (Expresion valor : valoresIniciales) {
                valor.verificarSemantica(contexto);
                contador++;
            }
            if (contador > estructura.getAtributos().size()) {
                contexto.agregarError(fila, columna, id,
                        "Demasiados valores iniciales para la estructura '" + id + "'");
            }
        }
    }

}