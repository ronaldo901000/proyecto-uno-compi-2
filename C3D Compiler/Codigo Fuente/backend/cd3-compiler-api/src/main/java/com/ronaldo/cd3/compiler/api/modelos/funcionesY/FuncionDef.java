package com.ronaldo.cd3.compiler.api.modelos.funcionesY;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.interfaces.Generable;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class FuncionDef extends Nodo implements Verificable, Generable {

    private final Reglas reglas = new Reglas();
    private String nombre;
    private List<Parametro> parametros;
    private String tipoRetorno;
    private List<Instruccion> cuerpo;
    private SimboloFuncion simbolo;

    public FuncionDef(String nombre, List<Parametro> parametros,
            String tipoRetorno, List<Instruccion> cuerpo, int fila, int columna) {

        super(fila, columna);
        this.nombre = nombre;
        this.parametros = parametros;
        this.tipoRetorno = tipoRetorno;
        this.cuerpo = cuerpo;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public List<Instruccion> getCuerpo() {
        return cuerpo;
    }

    public SimboloFuncion getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(SimboloFuncion simbolo) {
        this.simbolo = simbolo;
    }

    public void declararFuncion(Contexto contexto) {
        List<Tipo> tiposParametros = new ArrayList<>();
        List<SimboloParametro> simbolosParametros = new ArrayList<>();
        if (parametros != null) {

            for (Parametro parametro : parametros) {

                parametro.verificarSemantica(contexto);
                tiposParametros.add(parametro.getTipo());
                simbolosParametros.add(new SimboloParametro(
                        parametro.getNombre(), parametro.getTipo(), 0));
            }
        }
        if (contexto.getTablaSimbolos().existeOtroSimbolo(nombre)
                || contexto.getTablaSimbolos().existeFuncion(nombre, tiposParametros)) {

            contexto.agregarError(fila, columna, nombre,
                    "Ya existe una función '" + nombre
                    + "' con el mismo nombre o un identificador con el mismo nombre");

            return;
        }
        Tipo tipoRetornoT;
        if (tipoRetorno == null) {
            tipoRetornoT = contexto.getTablaTipos().getVoid();
        } else {
            tipoRetornoT = reglas.resolverTipo(contexto, tipoRetorno, fila, columna);
        }
        String etiqueta = contexto.getTablaSimbolos().generarEtiquetaFuncion(nombre);
        SimboloFuncion nuevoSimbolo = new SimboloFuncion(nombre, tipoRetornoT,
                simbolosParametros, 0, etiqueta);
        contexto.getTablaSimbolos().agregar(nuevoSimbolo);
        this.simbolo = nuevoSimbolo;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (simbolo == null) {
            return;
        }

        TablaSimbolos anterior = contexto.nuevoAmbito(nombre);
        int posicion = 0;

        for (SimboloParametro parametro : simbolo.getParametros()) {
            parametro.setPosicion(posicion);
            posicion += parametro.getTipo().tamañoBytes();
            contexto.getAmbito().agregar(parametro);
        }

        contexto.getAmbito().setSiguientePosicion(posicion);

        Tipo retornoAnterior = contexto.getTipoRetornoActual();

        contexto.setTipoRetornoActual(simbolo.getTipoRetorno());

        reglas.verificarInstrucciones(contexto, cuerpo);
        simbolo.setTamañoFrame(contexto.getAmbito().getSiguientePosicion());
        contexto.setTipoRetornoActual(retornoAnterior);
        contexto.restaurarAmbito(anterior);

        if (!reglas.esVoid(simbolo.getTipoRetorno())
                && !reglas.siempreRetorna(cuerpo)) {
            contexto.agregarError(fila, columna, nombre,
                    "La funcion '" + nombre + "' de tipo " + simbolo.getTipoRetorno()
                    + " no retorna en todos sus caminos de ejecución");
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (simbolo == null) {
            return null;
        }
        
        cuartetas.registrarTipoFuncion(simbolo.getEtiquetaInicio(),
                simbolo.getTipoRetorno());
        
        cuartetas.agregarEtiqueta(simbolo.getEtiquetaInicio(), fila, columna);
        
        for (SimboloParametro parametro : simbolo.getParametros()) {
            cuartetas.registrarTipoVariable(parametro.getId(), parametro.getTipo());
        }
        
        if (cuerpo != null) {
            for (Instruccion instruccion : cuerpo) {
                instruccion.generarCuartetas(contexto, cuartetas);
            }
        }
        
        cuartetas.agregar(OperadorCuarteta.RETORNO, null,
                null, null, fila, columna);
        return null;
    }

}
