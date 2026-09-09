package com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class InstSi extends Nodo implements Instruccion {

    private final Reglas reglas = new Reglas();
    private Expresion condicion;
    private List<Instruccion> instruccionesInternasSi;
    private List<RamaSino> ramasSino;
    private List<Instruccion> instruccionesInternasContrario;

    public InstSi(Expresion condicion, List<Instruccion> instruccionesInternasSi,
            List<RamaSino> ramasSino, List<Instruccion> instruccionesInternasContrario,
            int fila, int columna) {

        super(fila, columna);
        this.condicion = condicion;
        this.instruccionesInternasSi = instruccionesInternasSi;
        this.ramasSino = ramasSino;
        this.instruccionesInternasContrario = instruccionesInternasContrario;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public List<Instruccion> getInstruccionesInternasSi() {
        return instruccionesInternasSi;
    }

    public List<RamaSino> getRamasSino() {
        return ramasSino;
    }

    public List<Instruccion> getInstruccionesInternasContrario() {
        return instruccionesInternasContrario;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        reglas.esCondicionValida(contexto, condicion);
        TablaSimbolos anterior = contexto.nuevoAmbito("si");
        reglas.verificarInstrucciones(contexto, instruccionesInternasSi);
        contexto.restaurarAmbito(anterior);
        if (ramasSino != null) {
            for (RamaSino rama : ramasSino) {
                rama.verificarSemantica(contexto);
            }
        }
        anterior = contexto.nuevoAmbito("contrario");
        reglas.verificarInstrucciones(contexto, instruccionesInternasContrario);
        contexto.restaurarAmbito(anterior);
    }

}