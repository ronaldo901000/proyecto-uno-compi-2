package com.ronaldo.cd3.compiler.api.modelos.funciones;

import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Parametro extends Nodo implements Verificable {

    private final Reglas reglas = new Reglas();
    private String tipoDato;
    private String nombre;
    private boolean esArreglo;
    private int dimensiones;
    private boolean esStruct;
    private Tipo tipo;

    public Parametro(String tipoDato, String nombre, boolean esArreglo,
            boolean esStruct, int fila, int columna) {
        this(tipoDato, nombre, esArreglo, esArreglo ? 1 : 0, esStruct, fila, columna);
    }

    public Parametro(String tipoDato, String nombre, boolean esArreglo, int dimensiones,
            boolean esStruct, int fila, int columna) {
        super(fila, columna);
        this.tipoDato = tipoDato;
        this.nombre = nombre;
        this.esArreglo = esArreglo;
        this.dimensiones = dimensiones;
        this.esStruct = esStruct;
    }

    public int getDimensiones() {
        return dimensiones;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEsArreglo() {
        return esArreglo;
    }

    public boolean isEsStruct() {
        return esStruct;
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        Tipo base = reglas.resolverTipo(contexto, tipoDato, fila, columna);
        if (reglas.esError(base)) {
            this.tipo = base;
        } else if (esArreglo) {
            List<Integer> dims = new ArrayList<>();
            for (int i = 0; i < dimensiones; i++) {
                dims.add(0);
            }
            this.tipo = contexto.getTablaTipos().getArreglo(base, dims);
        } else {
            this.tipo = base;
        }
    }

}
