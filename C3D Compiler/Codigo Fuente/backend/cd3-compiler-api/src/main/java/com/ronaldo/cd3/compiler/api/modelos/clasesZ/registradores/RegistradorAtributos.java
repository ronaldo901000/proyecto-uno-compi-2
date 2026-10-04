package com.ronaldo.cd3.compiler.api.modelos.clasesZ.registradores;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.Declaracion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionArreglo;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionVariable;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class RegistradorAtributos {

    private List<Declaracion> atributos;
    private String nombreClase;
    private final Reglas reglas;

    public RegistradorAtributos(List<Declaracion> atributos, String nombreClase, Reglas reglas) {
        this.atributos = atributos;
        this.nombreClase = nombreClase;
        this.reglas = reglas;

    }

    public Map<String, SimboloVariable> registrarAtributos(Contexto contexto, TipoStructura tipoClase) {
        Map<String, SimboloVariable> atributosSimbolo = new LinkedHashMap<>();
        if (atributos == null) {
            return atributosSimbolo;
        }

        int posicion = 0;
        for (Declaracion atributo : atributos) {
            Tipo tipoAtributo = tipoDeAtributo(contexto, atributo);
            if (reglas.esError(tipoAtributo)) {
                continue;
            }

            if (atributosSimbolo.containsKey(atributo.getId())) {
                contexto.agregarError(atributo.getFila(), atributo.getColumna(),
                        atributo.getId(), "El atributo '" + atributo.getId()
                        + "' ya fue declarado en la clase '" + nombreClase + "'");
                continue;
            }

            tipoClase.agregarAtributo(atributo.getId(), tipoAtributo);
            atributosSimbolo.put(atributo.getId(),
                    new SimboloVariable(atributo.getId(), tipoAtributo, posicion, true));
            posicion += tipoAtributo.tamañoBytes();
        }
        return atributosSimbolo;
    }

    private Tipo tipoDeAtributo(Contexto contexto, Declaracion atributo) {
        if (atributo instanceof DeclaracionVariable) {
            return reglas.resolverTipo(contexto, atributo.getTipoDato(),
                    atributo.getFila(), atributo.getColumna());
        }
        if (atributo instanceof DeclaracionArreglo) {
            DeclaracionArreglo arreglo = (DeclaracionArreglo) atributo;
            List<Integer> tamaños = new ArrayList<>();
            if (arreglo.getDimensiones() != null) {
                for (Expresion dimension : arreglo.getDimensiones()) {
                    if (dimension == null) {
                        tamaños.add(0);
                        continue;
                    }
                    Integer tamaño = reglas.constanteEntera(dimension);
                    tamaños.add((tamaño != null && tamaño > 0) ? tamaño : 0);
                }
            }
            Tipo base = reglas.resolverTipo(contexto, arreglo.getTipoDato(),
                    arreglo.getFila(), arreglo.getColumna());
            if (reglas.esError(base)) {
                return base;
            }
            return contexto.getTablaTipos().getArreglo(base, tamaños);
        }
        return contexto.getTablaTipos().getError();
    }
}
