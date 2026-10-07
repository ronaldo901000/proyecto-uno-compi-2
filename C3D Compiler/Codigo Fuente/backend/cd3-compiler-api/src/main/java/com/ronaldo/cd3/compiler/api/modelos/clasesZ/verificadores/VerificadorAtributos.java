package com.ronaldo.cd3.compiler.api.modelos.clasesZ.verificadores;

import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ClaseZ;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declaracion.Declaracion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declaracion.DeclaracionArreglo;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declaracion.DeclaracionVariable;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;

/**
 * Verifica los atributos de una clase
 *
 * @author ronaldo
 */
public class VerificadorAtributos {

    private final ClaseZ clase;
    private final Reglas reglas;

    public VerificadorAtributos(ClaseZ clase, Reglas reglas) {
        this.clase = clase;
        this.reglas = reglas;
    }

    public void verificar(Contexto contexto) {
        if (clase.getAtributos() == null) {
            return;
        }
        SimboloClase simboloClase = clase.getSimboloClase();
        SimboloClase claseAnterior = contexto.getClaseActual();
        contexto.setClaseActual(simboloClase);

        for (Declaracion atributo : clase.getAtributos()) {
            SimboloVariable simbolo = simboloClase.getAtributo(atributo.getId());
            if (simbolo == null) {
                continue;
            }
            verificarAtributo(contexto, atributo, simbolo.getTipo());
        }

        contexto.setClaseActual(claseAnterior);
    }

    private void verificarAtributo(Contexto contexto, Declaracion atributo, Tipo tipo) {
        if (atributo instanceof DeclaracionVariable) {
            verificarValor(contexto, atributo, tipo,
                    ((DeclaracionVariable) atributo).getValorInicial());
        } else if (atributo instanceof DeclaracionArreglo) {
            verificarArreglo(contexto, (DeclaracionArreglo) atributo, tipo);
        }
    }

    private void verificarArreglo(Contexto contexto, DeclaracionArreglo arreglo, Tipo tipo) {
        verificarValor(contexto, arreglo, tipo, arreglo.getValorExpresion());
        verificarValoresIniciales(contexto, arreglo, tipo);
    }

    /**
     * Verifica inicializadores de la forma { v1, v2, ... }.
     */
    private void verificarValoresIniciales(Contexto contexto, DeclaracionArreglo arreglo, Tipo tipo) {
        if (arreglo.getValoresIniciales() == null || !(tipo instanceof TipoArreglo)) {
            return;
        }
        Tipo base = ((TipoArreglo) tipo).getTipoBase();
        for (Expresion valor : arreglo.getValoresIniciales()) {
            valor.verificarSemantica(contexto);
            if (!reglas.esAsignable(base, valor.getTipo())) {
                contexto.agregarError(arreglo.getFila(), arreglo.getColumna(),
                        arreglo.getId(), "Un valor inicial del arreglo '"
                        + arreglo.getId() + "' es incompatible con su tipo base");
            }
        }
    }

    /**
     * Verifica un valor inicial simple: int x = 5, new tipo[n], null, etc.
     */
    private void verificarValor(Contexto contexto, Declaracion atributo, Tipo tipo, Expresion valor) {
        if (valor == null) {
            return;
        }
        valor.verificarSemantica(contexto);
        if (!reglas.esAsignable(tipo, valor.getTipo())) {
            contexto.agregarError(valor.getFila(), valor.getColumna(), atributo.getId(),
                    "No se puede asignar un valor de tipo '" + valor.getTipo()
                    + "' al atributo '" + atributo.getId() + "' de tipo '" + tipo + "'");
        }
    }

}
