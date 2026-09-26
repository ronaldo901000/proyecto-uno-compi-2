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
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class FuncionDef extends Nodo implements Verificable, Generable {

    private final Reglas reglas = new Reglas();
    private String nombre;
    private List<Parametro> parametros;
    private String tipoRetorno;
    private int dimensionesRetorno;
    private List<Instruccion> cuerpo;
    private SimboloFuncion simbolo;

    public FuncionDef(String nombre, List<Parametro> parametros,
            String tipoRetorno, List<Instruccion> cuerpo, int fila, int columna) {
        this(nombre, parametros, tipoRetorno, 0, cuerpo, fila, columna);
    }

    public FuncionDef(String nombre, List<Parametro> parametros,
            String tipoRetorno, int dimensionesRetorno,
            List<Instruccion> cuerpo, int fila, int columna) {

        super(fila, columna);
        this.nombre = nombre;
        this.parametros = parametros;
        this.tipoRetorno = tipoRetorno;
        this.dimensionesRetorno = dimensionesRetorno;
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

    public int getDimensionesRetorno() {
        return dimensionesRetorno;
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
            if (dimensionesRetorno > 0 && !reglas.esError(tipoRetornoT)) {
                List<Integer> dims = new ArrayList<>();
                for (int i = 0; i < dimensionesRetorno; i++) {
                    dims.add(0);
                }
                tipoRetornoT = contexto.getTablaTipos().getArreglo(tipoRetornoT, dims);
            }
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
        cuartetas.registrarParametrosFuncion(simbolo.getEtiquetaInicio(),
                simbolo.getParametros());
        
        if (cuerpo != null) {
            for (Instruccion instruccion : cuerpo) {
                instruccion.generarCuartetas(contexto, cuartetas);
            }
        }

        propagarDimensionesRetorno(cuartetas);

        cuartetas.agregar(OperadorCuarteta.RETORNO, null,
                null, null, fila, columna);
        return null;
    }

    private void propagarDimensionesRetorno(ListaCuartetas cuartetas) {
        if (simbolo == null || !(simbolo.getTipoRetorno() instanceof TipoArreglo)) {
            return;
        }
        TipoArreglo tipoRetorno = (TipoArreglo) simbolo.getTipoRetorno();
        boolean todasDesconocidas = true;
        for (Integer d : tipoRetorno.getDimensiones()) {
            if (d != null && d > 0) {
                todasDesconocidas = false;
                break;
            }
        }
        if (!todasDesconocidas) {
            return;
        }
        String varRetorno = null;
        for (int i = cuartetas.getCuartetas().size() - 1; i >= 0; i--) {
            var c = cuartetas.getCuartetas().get(i);
            if (c.getOperador() == OperadorCuarteta.RETORNO
                    && c.getArg1() != null) {
                varRetorno = c.getArg1();
                break;
            }
        }
        if (varRetorno == null) {
            return;
        }
        Map<String, Tipo> vars = cuartetas
                .getVariablesDeclaradasPorUnidad()
                .get(simbolo.getEtiquetaInicio());
        Tipo tipoVar = (vars != null) ? vars.get(varRetorno) : null;
        if (tipoVar == null) {
            tipoVar = cuartetas.tipoDeTemporal(varRetorno);
        }
        if (!(tipoVar instanceof TipoArreglo)) {
            return;
        }
        TipoArreglo tipoArrVar = (TipoArreglo) tipoVar;
        boolean dimsReales = false;
        for (Integer d : tipoArrVar.getDimensiones()) {
            if (d != null && d > 0) {
                dimsReales = true;
                break;
            }
        }
        if (!dimsReales) {
            return;
        }
        for (int i = 0; i < tipoRetorno.getNumeroDimensiones()
                && i < tipoArrVar.getNumeroDimensiones(); i++) {
            tipoRetorno.actualizarDimension(i,
                    tipoArrVar.getDimensiones().get(i));
        }
    }

}
