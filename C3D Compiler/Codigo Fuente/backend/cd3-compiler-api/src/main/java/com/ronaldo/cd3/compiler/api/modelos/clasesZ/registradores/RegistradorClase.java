package com.ronaldo.cd3.compiler.api.modelos.clasesZ.registradores;

import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ClaseZ;
import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ResolutorTipoRetorno;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.Simbolo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloEstructura;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class RegistradorClase {

    private final ClaseZ clase;
    private final String nombre;
    private final Reglas reglas;

    public RegistradorClase(ClaseZ clase, Reglas reglas, ResolutorTipoRetorno resolutorRetorno) {
        this.clase = clase;
        this.nombre = clase.getNombre();
        this.reglas = reglas;
    }

    public SimboloClase registrar(Contexto contexto) {
        if (existeConflictoDeNombre(contexto)) {
            return null;
        }

        TipoStructura tipoClase = contexto.getTablaTipos().registrarClase(nombre);

        RegistradorAtributos registradorAtributos = new RegistradorAtributos(
                clase.getAtributos(), nombre, reglas
        );
        Map<String, SimboloVariable> atributosSimbolo = registradorAtributos.registrarAtributos(
                contexto,
                tipoClase
        );

        SimboloClase nuevaClase = new SimboloClase(nombre, calcularTamañoHeap(atributosSimbolo));

        nuevaClase.setTipo(tipoClase);
        for (SimboloVariable atributo : atributosSimbolo.values()) {
            nuevaClase.agregarAtributo(atributo);
        }

        //registro de metodos
        RegistradorMetodos registradorMetodos = new RegistradorMetodos(nombre, clase.getMetodos(), reglas);
        registradorMetodos.registrarMetodos(contexto, nuevaClase);

        //Registro de constructores en la tabla de simbolos
        RegistradorConstructores registradorConstructores = new RegistradorConstructores(
                nombre,
                clase.getConstructores()
        );
        registradorConstructores.registrarConstructores(contexto, nuevaClase);

        if (!contexto.getTablaSimbolos().agregar(nuevaClase)) {
            contexto.agregarError(clase.getFila(), clase.getColumna(), nombre,
                    "Ya existe una clase llamada '" + nombre + "'");
            return null;
        }
        return nuevaClase;
    }

    /**
     * Verifica si existe conflicto de nombre con otras clases, id
     */
    private boolean existeConflictoDeNombre(Contexto contexto) {
        Simbolo existente = contexto.getTablaSimbolos().buscarOtroSimbolo(nombre);

        if (existente == null || existente instanceof SimboloEstructura) {
            return false;
        }

        String mensaje = (existente instanceof SimboloClase)
                ? "Ya existe una clase llamada '" + nombre + "'"
                : "Ya existe un identificador llamado '" + nombre + "'";
        contexto.agregarError(clase.getFila(), clase.getColumna(), nombre, mensaje);
        return true;
    }

    private int calcularTamañoHeap(Map<String, SimboloVariable> atributosSimbolo) {
        int total = 0;
        for (SimboloVariable atributo : atributosSimbolo.values()) {
            total += atributo.getTipo().tamañoBytes();
        }
        return total;
    }

}
