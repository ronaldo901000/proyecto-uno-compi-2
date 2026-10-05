package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.enums.ModificadoresAcceso;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;

/**
 *
 * @author ronaldo
 */
public abstract class Declaracion extends Nodo implements Instruccion {

    protected ModificadoresAcceso modAcceso;
    protected String tipoDato;
    protected String id;

    public Declaracion(String tipoDato, String id, int fila, int columna) {
        super(fila, columna);
        this.tipoDato = tipoDato;
        this.id = id;
    }

    public Declaracion(ModificadoresAcceso modAcceso, String tipoDato, String id, int fila, int columna) {
        super(fila, columna);
        this.tipoDato = tipoDato;
        this.id = id;
        this.modAcceso = modAcceso;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public String getId() {
        return id;
    }

    public ModificadoresAcceso getModAcceso() {
        return modAcceso;
    }

    public void setModAcceso(ModificadoresAcceso modAcceso) {
        this.modAcceso = modAcceso;
    }
    
    

}
