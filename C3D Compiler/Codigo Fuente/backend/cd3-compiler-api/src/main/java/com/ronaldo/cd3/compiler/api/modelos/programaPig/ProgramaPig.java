package com.ronaldo.cd3.compiler.api.modelos.programaPig;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.interfaces.Generable;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ProgramaPig extends Nodo implements Verificable, Generable {

    private List<ImportacionPig> imports;
    private List<Instruccion> declaraciones;
    private List<Instruccion> bloqueMaior;

    private ArchivoDTO archivo;

    public ProgramaPig(List<ImportacionPig> imports, List<Instruccion> declaraciones,
            List<Instruccion> instrucciones, int fila, int columna) {
        super(fila, columna);
        this.imports = imports;
        this.declaraciones = declaraciones;
        this.bloqueMaior = instrucciones;
    }

    public void iniciarVerificacionSemantica(Contexto contexto) {

        //verificar declaraciones
        if (declaraciones != null) {
            for (Instruccion inst : declaraciones) {
                inst.verificarSemantica(contexto);
            }
        }

        //verificar el bloque maior
        if (bloqueMaior != null) {
            for (Instruccion inst : bloqueMaior) {
                inst.verificarSemantica(contexto);
            }
        }
    }

    public List<ImportacionPig> getImports() {
        return imports;
    }

    public List<Instruccion> getDeclaraciones() {
        return declaraciones;
    }

    public List<Instruccion> getBloqueMaior() {
        return bloqueMaior;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        Reglas reglas = new Reglas();
        reglas.verificarInstrucciones(contexto, declaraciones);
        reglas.verificarInstrucciones(contexto, bloqueMaior);
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (declaraciones != null) {
            for (Instruccion inst : declaraciones) {
                inst.generarCuartetas(contexto, cuartetas);
            }
        }
        if (bloqueMaior != null) {
            for (Instruccion inst : bloqueMaior) {
                inst.generarCuartetas(contexto, cuartetas);
            }
        }
        return null;
    }

    public ArchivoDTO getArchivo() {
        return archivo;
    }

    public void setArchivo(ArchivoDTO archivo) {
        this.archivo = archivo;
    }
}
