package com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public class DeclaracionIterador extends Nodo implements Instruccion {

    private final Reglas reglas = new Reglas();
    private String tipoDato;
    private String id;
    private Expresion valorInicial;
    private Tipo tipo;

    public DeclaracionIterador(String tipoDato, String id, Expresion valorInicial, int fila, int columna) {
        super(fila, columna);
        this.tipoDato = tipoDato;
        this.id = id;
        this.valorInicial = valorInicial;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expresion getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(Expresion valorInicial) {
        this.valorInicial = valorInicial;
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (valorInicial != null) {
            valorInicial.verificarSemantica(contexto);
        }
        Tipo tipoIterador;
        if (tipoDato != null) {
            tipoIterador = reglas.resolverTipo(contexto, tipoDato, fila, columna);
            if (reglas.esError(tipoIterador)) {
                return;
            }
            if (valorInicial != null && !reglas.esAsignable(tipoIterador, valorInicial.getTipo())) {
                contexto.agregarError(fila, columna, id,
                        "El valor inicial del iterador '" + id + "' es incompatible con su tipo");
            }
        } else {
            tipoIterador = (valorInicial != null) ? valorInicial.getTipo() : null;
            if (tipoIterador == null) {
                contexto.agregarError(fila, columna, id,
                        "No se pudo determinar el tipo de '" + 
                                id + 
                                "': especifica su tipo o asígnale un valor inicial"
                );
                return;
            }
        }
        this.tipo = tipoIterador;
        reglas.registrarVariable(contexto, id, tipoIterador, fila, columna);
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        cuartetas.registrarTipoVariable(id, tipo);
        if (valorInicial != null) {
            String dirValor = valorInicial.generarCuartetas(contexto, cuartetas);
            cuartetas.agregar(OperadorCuarteta.ASIGNACION, dirValor,
                    null, id, fila, columna);
        }
        return null;
    }

}