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

        String etiquetaSalida = cuartetas.nuevaEtiqueta();

        boolean hayElseIf = ramasSino != null && !ramasSino.isEmpty();

        boolean hayElse = instruccionesInternasContrario != null
                && !instruccionesInternasContrario.isEmpty();
        

        boolean haySiguienteAl_if = hayElseIf || hayElse;

        //pedir generar cuarteta de la condicion
        String dirCondicion = condicion.generarCuartetas(contexto, cuartetas);

        if (haySiguienteAl_if) {

            String etiquetaSiguiente = cuartetas.nuevaEtiqueta();
            String etiquetaVerdadero = cuartetas.nuevaEtiqueta();

            cuartetas.agregar(
                    OperadorCuarteta.IF_VERDADERO,
                    dirCondicion,
                    etiquetaVerdadero,
                    null,
                    fila, columna
            );

            cuartetas.agregar(OperadorCuarteta.GOTO,
                    etiquetaSiguiente,
                    null,
                    null,
                    fila, columna
            );

            cuartetas.agregarEtiqueta(
                    etiquetaVerdadero,
                    fila, columna);

            generarInstrucciones(
                    contexto,
                    cuartetas,
                    instruccionesInternasSi
            );

            cuartetas.agregar(OperadorCuarteta.GOTO,
                    etiquetaSalida,
                    null,
                    null,
                    fila, columna
            );

            cuartetas.agregarEtiqueta(
                    etiquetaSiguiente,
                    fila, columna);

        } else {

            String etiquetaVerdadero = cuartetas.nuevaEtiqueta();

            cuartetas.agregar(
                    OperadorCuarteta.IF_VERDADERO,
                    dirCondicion,
                    etiquetaVerdadero,
                    null,
                    fila, columna
            );

            cuartetas.agregar(OperadorCuarteta.GOTO,
                    etiquetaSalida,
                    null,
                    null,
                    fila, columna
            );

            cuartetas.agregarEtiqueta(
                    etiquetaVerdadero,
                    fila, columna);

            generarInstrucciones(contexto, cuartetas, instruccionesInternasSi);

        }

        if (ramasSino != null) {

            for (int i = 0; i < ramasSino.size(); i++) {

                RamaSino rama = ramasSino.get(i);


                String dirCondicionRama = rama.getCondicion()
                        .generarCuartetas(contexto, cuartetas);

                String etiquetaSiguiente = cuartetas.nuevaEtiqueta();
                String etiquetaVerdadero = cuartetas.nuevaEtiqueta();

                cuartetas.agregar(OperadorCuarteta.IF_VERDADERO, dirCondicionRama,
                        etiquetaVerdadero, null, fila, columna);

                cuartetas.agregar(OperadorCuarteta.GOTO, etiquetaSiguiente,
                        null, null, fila, columna);

                cuartetas.agregarEtiqueta(etiquetaVerdadero, fila, columna);

                generarInstrucciones(contexto, cuartetas,
                        rama.getInstruccionesInternas());

                cuartetas.agregar(OperadorCuarteta.GOTO, etiquetaSalida,
                        null, null, fila, columna);

                cuartetas.agregarEtiqueta(etiquetaSiguiente, fila, columna);

            }
        }

        if (instruccionesInternasContrario != null) {

            generarInstrucciones(contexto, cuartetas, instruccionesInternasContrario);

        }

        cuartetas.agregarEtiqueta(etiquetaSalida, fila, columna);
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
