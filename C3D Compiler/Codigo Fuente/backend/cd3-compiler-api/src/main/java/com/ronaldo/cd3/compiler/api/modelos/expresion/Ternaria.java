package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public class Ternaria extends Expresion implements Verificable {

    private final Reglas reglas = new Reglas();
    private Expresion condicion;
    private Expresion verdadero;
    private Expresion falso;

    public Ternaria(Expresion condicion, Expresion verdadero,
            Expresion falso, int fila, int columna) {
        super(fila, columna);
        this.condicion = condicion;
        this.verdadero = verdadero;
        this.falso = falso;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public Expresion getVerdadero() {
        return verdadero;
    }

    public Expresion getFalso() {
        return falso;
    }

    
    @Override
    public void verificarSemantica(Contexto contexto) {

        if (condicion != null) {
            condicion.verificarSemantica(contexto);
        }

        if (verdadero != null) {
            verdadero.verificarSemantica(contexto);
        }

        if (falso != null) {
            falso.verificarSemantica(contexto);
        }

        TablaTipos tablaTipos = contexto.getTablaTipos();

        if (condicion == null || !reglas.esBooleano(condicion.getTipo())) {
            contexto.agregarError(fila, columna, null,
                    "La condición de la expresión ternaria debe ser booleana");
            setTipo(tablaTipos.getError());
            return;
        }

        Tipo tipoVerdadero = (verdadero != null) ? verdadero.getTipo() : null;

        Tipo tipoFalso = (falso != null) ? falso.getTipo() : null;

        if (tipoVerdadero == null || tipoFalso == null) {
            setTipo(tablaTipos.getError());
            return;
        }

        if (reglas.comparables(tipoVerdadero, tipoFalso)
                || reglas.esAsignable(tipoVerdadero, tipoFalso)) {

            setTipo(tipoVerdadero);
            return;
        }

        if (reglas.esAsignable(tipoFalso, tipoVerdadero)) {
            setTipo(tipoFalso);
            return;
        }

        contexto.agregarError(fila, columna, null,
                "Las ramas de la expresión ternaria son incompatibles");
        setTipo(tablaTipos.getError());
    }

    /**
     * t1 = z == 1
     *
     * if_true t1 goto et1 
     * goto et2 
     * 
     * et1: a = v 
     *      goto salida 
     * 
     * et2: a = f 
     *      goto salida 
     * 
     * salida : 
     * @param contexto
     * @param cuartetas
     * @return 
     */
    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {

        String temporal = cuartetas.nuevoTemporal();
        cuartetas.registrarTipoTemporal(temporal, this.tipo);

        String salida = cuartetas.nuevaEtiqueta();

        String t1 = (condicion != null)
                ? condicion.generarCuartetas(contexto, cuartetas)
                : null;

        String et1 = cuartetas.nuevaEtiqueta();
        String et2 = cuartetas.nuevaEtiqueta();

        cuartetas.agregar(OperadorCuarteta.IF_VERDADERO, t1, null, et1, fila, columna);
        cuartetas.agregar(OperadorCuarteta.GOTO, null, null, et2, fila, columna);

        // rama verdadera
        cuartetas.agregarEtiqueta(et1);
        String idVerdadero = (verdadero != null)
                ? verdadero.generarCuartetas(contexto, cuartetas)
                : null;
        cuartetas.agregar(OperadorCuarteta.ASIGNACION, idVerdadero, null, temporal, fila, columna);
        cuartetas.agregar(OperadorCuarteta.GOTO, null, null, salida, fila, columna);

        // rama falsa
        cuartetas.agregarEtiqueta(et2);
        String idFalso = (falso != null)
                ? falso.generarCuartetas(contexto, cuartetas)
                : null;
        cuartetas.agregar(OperadorCuarteta.ASIGNACION, idFalso, null, temporal, fila, columna);
        cuartetas.agregar(OperadorCuarteta.GOTO, null, null, salida, fila, columna);

        //salida
        cuartetas.agregarEtiqueta(salida);

        return temporal;
    }

}
