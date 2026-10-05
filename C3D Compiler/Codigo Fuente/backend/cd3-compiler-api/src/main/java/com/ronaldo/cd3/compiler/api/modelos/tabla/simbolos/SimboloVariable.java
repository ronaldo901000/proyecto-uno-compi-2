package com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos;

import com.ronaldo.cd3.compiler.api.enums.ModificadoresAcceso;
import com.ronaldo.cd3.compiler.api.enums.RolSimbolo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public class SimboloVariable extends Simbolo {

    private int posicion;
    private boolean esGlobal;
    private ModificadoresAcceso modAcceso = ModificadoresAcceso.DEFAULT;
    private String nombreClase;

    public SimboloVariable(String id, Tipo tipo, int posicion, boolean esGlobal) {
        super(id, tipo, RolSimbolo.VARIABLE);
        this.posicion = posicion;
        this.esGlobal = esGlobal;
    }

    public SimboloVariable(String id, Tipo tipo, int posicion) {
        this(id, tipo, posicion, false);
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public boolean isEsGlobal() {
        return esGlobal;
    }

    public void setEsGlobal(boolean esGlobal) {
        this.esGlobal = esGlobal;
    }

    public ModificadoresAcceso getModAcceso() {
        return modAcceso;
    }

    public void setModAcceso(ModificadoresAcceso modAcceso) {
        this.modAcceso = modAcceso;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

}
