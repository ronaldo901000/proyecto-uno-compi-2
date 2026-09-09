package com.ronaldo.cd3.compiler.api.modelos.estructurasY;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.Collections;

/**
 *
 * @author ronaldo
 */
public class AtributoEstructura extends Nodo implements Verificable {

    private final Reglas reglas = new Reglas();
    private String tipoDato;
    private String nombre;
    private Integer tamañoArreglo;
    private Tipo tipo;

    public AtributoEstructura(String tipoDato, String nombre, 
            Integer tamañoArreglo, int fila, int columna) {
        
        super(fila, columna);
        this.tipoDato = tipoDato;
        this.nombre = nombre;
        this.tamañoArreglo = tamañoArreglo;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTamañoArreglo() {
        return tamañoArreglo;
    }

    public void setTamañoArreglo(Integer tamañoArreglo) {
        this.tamañoArreglo = tamañoArreglo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        Tipo base = reglas.resolverTipo(contexto, tipoDato, fila, columna);
        if (reglas.esError(base)) {
            this.tipo = base;
        } else if (tamañoArreglo != null) {
            this.tipo = contexto.getTablaTipos().getArreglo(
                    base, Collections.singletonList(tamañoArreglo));
        } else {
            this.tipo = base;
        }
    }

}