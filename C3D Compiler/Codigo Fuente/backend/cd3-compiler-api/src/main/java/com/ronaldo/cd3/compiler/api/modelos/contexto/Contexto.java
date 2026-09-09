package com.ronaldo.cd3.compiler.api.modelos.contexto;

import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorSemantico;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Contexto {

    private TablaTipos tablaTipos;
    private TablaSimbolos tablaSimbolos;
    private TablaSimbolos ambito;
    private List<ErrorSemantico> errores;
    private String ruta;
    private Tipo tipoRetornoActual;
    private int contadorCiclos;
    private boolean dentroSwitch;

    public Contexto(TablaTipos tablaTipos, TablaSimbolos tablaSimbolos,
            TablaSimbolos ambito, String ruta) {
        this.tablaTipos = tablaTipos;
        this.tablaSimbolos = tablaSimbolos;
        this.ambito = (ambito != null) ? ambito : tablaSimbolos;
        this.errores = new ArrayList<>();
        this.ruta = ruta;
    }

    public TablaTipos getTablaTipos() {
        return tablaTipos;
    }

    public void setTablaTipos(TablaTipos tablaTipos) {
        this.tablaTipos = tablaTipos;
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }

    public void setTablaSimbolos(TablaSimbolos tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos;
    }

    public TablaSimbolos getAmbito() {
        return ambito;
    }

    public void setAmbito(TablaSimbolos ambito) {
        this.ambito = ambito;
    }

    public TablaSimbolos nuevoAmbito(String nombre) {
        TablaSimbolos anterior = this.ambito;
        this.ambito = this.ambito.nuevoAmbito(nombre);
        return anterior;
    }

    public void restaurarAmbito(TablaSimbolos anterior) {
        this.ambito = anterior;
    }

    public List<ErrorSemantico> getErrores() {
        return errores;
    }

    public boolean hayErrores() {
        return !errores.isEmpty();
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void agregarError(int fila, int columna, String lexema, String descripcion) {
        this.errores.add(new ErrorSemantico(fila, columna, lexema, descripcion, this.ruta));
    }

    public Tipo getTipoRetornoActual() {
        return tipoRetornoActual;
    }

    public void setTipoRetornoActual(Tipo tipoRetornoActual) {
        this.tipoRetornoActual = tipoRetornoActual;
    }

    public void entrarCiclo() {
        this.contadorCiclos++;
    }

    public void salirCiclo() {
        if (this.contadorCiclos > 0) {
            this.contadorCiclos--;
        }
    }

    public boolean dentroCiclo() {
        return this.contadorCiclos > 0;
    }

    public boolean dentroDeSwitch() {
        return dentroSwitch;
    }

    public void setdentroDeSwitch(boolean estaDentroElegir) {
        this.dentroSwitch = estaDentroElegir;
    }
    
    
    
    
    
}