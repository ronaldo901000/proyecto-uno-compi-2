package com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
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

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String etiquetaFin = cuartetas.nuevaEtiqueta();
        boolean hayElseIf = ramasSino != null && !ramasSino.isEmpty();
        boolean haySino = instruccionesInternasContrario != null
                && !instruccionesInternasContrario.isEmpty();
        boolean haySiguienteAlSi = hayElseIf || haySino;

        String dirCondicion = condicion.generarCuartetas(contexto, cuartetas);
        if (haySiguienteAlSi) {
            String etiquetaSiguiente = cuartetas.nuevaEtiqueta();
            cuartetas.agregar(OperadorCuarteta.IF_FALSO, dirCondicion,
                    etiquetaSiguiente, null, fila, columna);
            generarInstrucciones(contexto, cuartetas, instruccionesInternasSi);
            cuartetas.agregar(OperadorCuarteta.GOTO, etiquetaFin,
                    null, null, fila, columna);
            cuartetas.agregarEtiqueta(etiquetaSiguiente, fila, columna);
        } else {
            cuartetas.agregar(OperadorCuarteta.IF_FALSO, dirCondicion,
                    etiquetaFin, null, fila, columna);
            generarInstrucciones(contexto, cuartetas, instruccionesInternasSi);
        }

        if (ramasSino != null) {
            for (int i = 0; i < ramasSino.size(); i++) {
                RamaSino rama = ramasSino.get(i);
                boolean ultimaRama = (i == ramasSino.size() - 1);
                boolean hayAlgoDespues = (!ultimaRama) || haySino;
                String dirCondicionRama = rama.getCondicion()
                        .generarCuartetas(contexto, cuartetas);
                if (hayAlgoDespues) {
                    String etiquetaSiguiente = cuartetas.nuevaEtiqueta();
                    cuartetas.agregar(OperadorCuarteta.IF_FALSO, dirCondicionRama,
                            etiquetaSiguiente, null, fila, columna);
                    generarInstrucciones(contexto, cuartetas,
                            rama.getInstruccionesInternas());
                    cuartetas.agregar(OperadorCuarteta.GOTO, etiquetaFin,
                            null, null, fila, columna);
                    cuartetas.agregarEtiqueta(etiquetaSiguiente, fila, columna);
                } else {
                    cuartetas.agregar(OperadorCuarteta.IF_FALSO, dirCondicionRama,
                            etiquetaFin, null, fila, columna);
                    generarInstrucciones(contexto, cuartetas,
                            rama.getInstruccionesInternas());
                    cuartetas.agregar(OperadorCuarteta.GOTO, etiquetaFin,
                            null, null, fila, columna);
                }
            }
        }

        if (instruccionesInternasContrario != null) {
            generarInstrucciones(contexto, cuartetas, instruccionesInternasContrario);
        }
        cuartetas.agregarEtiqueta(etiquetaFin, fila, columna);
        return null;
    }

    private void generarInstrucciones(Contexto contexto, ListaCuartetas cuartetas,
            List<Instruccion> instrucciones) {
        if (instrucciones == null) {
            return;
        }
        for (Instruccion instruccion : instrucciones) {
            instruccion.generarCuartetas(contexto, cuartetas);
        }
    }

}