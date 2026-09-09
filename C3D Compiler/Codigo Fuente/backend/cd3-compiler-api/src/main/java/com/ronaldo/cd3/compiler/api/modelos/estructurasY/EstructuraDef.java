package com.ronaldo.cd3.compiler.api.modelos.estructurasY;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloEstructura;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class EstructuraDef extends Nodo implements Verificable {

    private final Reglas reglas = new Reglas();
    private String nombre;
    private List<AtributoEstructura> atributos;

    public EstructuraDef(String nombre, List<AtributoEstructura> atributos, int fila, int columna) {
        super(fila, columna);
        this.nombre = nombre;
        this.atributos = atributos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<AtributoEstructura> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<AtributoEstructura> atributos) {
        this.atributos = atributos;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (contexto.getTablaSimbolos().existeLocal(nombre)) {
            contexto.agregarError(fila, columna, nombre,
                    "La estructura '" + nombre + "' ya fue declarada");
            return;
        }
        TipoStructura tipoStructura = contexto.getTablaTipos().registrarEstructura(nombre, "global");
        Map<String, SimboloVariable> atributosSimbolo = new LinkedHashMap<>();
        int posicion = 0;
        if (atributos != null) {
            for (AtributoEstructura atributo : atributos) {
                atributo.verificarSemantica(contexto);
                Tipo tipoAtributo = atributo.getTipo();
                if (reglas.esError(tipoAtributo)) {
                    continue;
                }
                if (atributosSimbolo.containsKey(atributo.getNombre())) {
                    contexto.agregarError(atributo.getFila(), atributo.getColumna(),
                            atributo.getNombre(), "Atributo duplicado '" + atributo.getNombre()
                            + "' en la estructura '" + nombre + "'");
                    continue;
                }
                tipoStructura.agregarAtributo(atributo.getNombre(), tipoAtributo);
                SimboloVariable simboloAtributo = new SimboloVariable(
                        atributo.getNombre(), tipoAtributo, posicion, true);
                atributosSimbolo.put(atributo.getNombre(), simboloAtributo);
                posicion += tipoAtributo.tamanoBytes();
            }
        }
        contexto.getTablaSimbolos().agregar(
                new SimboloEstructura(nombre, atributosSimbolo, posicion));
    }

}