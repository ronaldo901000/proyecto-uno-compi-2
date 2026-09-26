package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ronaldo
 */
public class ListaCuartetas {

    private final List<Cuarteta> cuartetas;
    private final Map<String, Tipo> tiposTemporales;
    private final Map<String, Tipo> tiposVariables;
    private final Map<String, Tipo> tiposArreglos;
    private final Map<String, Tipo> tiposFunciones;
    private final Map<String, List<SimboloParametro>> parametrosFunciones;
    private final Map<String, TipoOperando> categoriasOperandos;
    private final Map<String, Map<String, Tipo>> variablesDeclaradasPorUnidad;
    private final Map<String, Map<String, Tipo>> arreglosDeclaradosPorUnidad;
    private final Map<String, String> dimensionesArreglo;
    private final Map<String, Tipo> tiposEstructuras;
    private String unidadActual;
    private int contadorTemporales;
    private int contadorEtiquetas;

    public ListaCuartetas() {
        this.cuartetas = new ArrayList<>();
        this.tiposTemporales = new HashMap<>();
        this.tiposVariables = new HashMap<>();
        this.tiposArreglos = new HashMap<>();
        this.tiposFunciones = new HashMap<>();
        this.parametrosFunciones = new HashMap<>();
        this.categoriasOperandos = new HashMap<>();
        this.variablesDeclaradasPorUnidad = new HashMap<>();
        this.arreglosDeclaradosPorUnidad = new HashMap<>();
        this.dimensionesArreglo = new HashMap<>();
        this.tiposEstructuras = new HashMap<>();
        this.unidadActual = null;
        this.contadorTemporales = 0;
        this.contadorEtiquetas = 0;
    }

    public String nuevoTemporal() {
        String temporal = "t" + (++contadorTemporales);
        registrarCategoria(temporal, TipoOperando.TEMPORAL);
        return temporal;
    }

    public String nuevaEtiqueta() {
        String etiqueta = "L" + (++contadorEtiquetas);
        registrarCategoria(etiqueta, TipoOperando.ETIQUETA_INTERNA);
        return etiqueta;
    }

    public void registrarCategoria(String operando, TipoOperando categoria) {
        if (operando != null && categoria != null) {
            this.categoriasOperandos.put(operando, categoria);
        }
    }

    public TipoOperando categoriaDe(String operando) {
        if (operando == null) {
            return null;
        }
        return this.categoriasOperandos.get(operando);
    }

    public void agregar(Cuarteta cuarteta) {
        this.cuartetas.add(cuarteta);
    }

    public void agregar(OperadorCuarteta operador, String arg1, String arg2,
            String resultado, int fila, int columna) {
        Cuarteta cuarteta;
        switch (operador) {
            case ASIGNACION:
                cuarteta = new CuartetaAsignacion(arg1, arg2, resultado,
                        fila, columna);
                break;
            case SUMA: case RESTA: case MULTIPLICACION:
            case DIVISION: case MODULO:
                cuarteta = new CuartetaAritmetica(operador, arg1, arg2,
                        resultado, fila, columna);
                break;
            case MENOR_Q: case MENOR_EQ_Q:
            case MAYOR_Q: case MAYOR_EQ_Q:
                cuarteta = new CuartetaComparacion(operador, arg1, arg2,
                        resultado, fila, columna);
                break;
            case IGUAL: case DISTINTO:
                cuarteta = new CuartetaIgualdad(operador, arg1, arg2,
                        resultado, fila, columna);
                break;
            case AND: case OR:
                cuarteta = new CuartetaLogicaBinaria(operador, arg1, arg2,
                        resultado, fila, columna);
                break;
            case NEGATIVO_UNARIO: case NOT:
                cuarteta = new CuartetaNegacion(operador, arg1, arg2,
                        resultado, fila, columna);
                break;
            case INCREMENTO: case DECREMENTO:
                cuarteta = new CuartetaIncrementoDecremento(operador, arg1,
                        arg2, resultado, fila, columna);
                break;
            case ETIQUETA:
                cuarteta = new CuartetaEtiqueta(arg1, arg2, resultado,
                        fila, columna);
                break;
            case GOTO:
                cuarteta = new CuartetaSalto(arg1, arg2, resultado,
                        fila, columna);
                break;
            case IF_FALSO: case IF_VERDADERO:
                cuarteta = new CuartetaCondicional(operador, arg1, arg2,
                        resultado, fila, columna);
                break;
            case IMPRIMIR:
                cuarteta = new CuartetaImprimir(arg1, arg2, resultado,
                        fila, columna);
                break;
            case LEER:
                cuarteta = new CuartetaLeer(arg1, arg2, resultado,
                        fila, columna);
                break;
            case PUNTERO_INICIO:
                cuarteta = new CuartetaPunteroInicio(arg1, arg2, resultado,
                        fila, columna);
                break;
            case PUNTERO_FINAL:
                cuarteta = new CuartetaPunteroFinal(arg1, arg2, resultado,
                        fila, columna);
                break;
            case COPIAR:
                cuarteta = new CuartetaCopiar(arg1, arg2, resultado,
                        fila, columna);
                break;
            case ACCESO_INDICE:
                cuarteta = new CuartetaAccesoIndice(arg1, arg2, resultado,
                        fila, columna);
                break;
            case ACCESO_ATRIBUTO:
                cuarteta = new CuartetaAccesoAtributo(arg1, arg2, resultado,
                        fila, columna);
                break;
            case PARAMETRO:
                cuarteta = new CuartetaParametro(arg1, arg2, resultado,
                        fila, columna);
                break;
            case LLAMADA:
                cuarteta = new CuartetaLlamada(arg1, arg2, resultado,
                        fila, columna);
                break;
            case RETORNO:
                cuarteta = new CuartetaRetorno(arg1, arg2, resultado,
                        fila, columna);
                break;
            default:
                cuarteta = new CuartetaAsignacion(arg1, arg2, resultado,
                        fila, columna);
                break;
        }
        this.cuartetas.add(cuarteta);
    }

    public void agregarEtiqueta(String etiqueta) {
        this.agregar(OperadorCuarteta.ETIQUETA, etiqueta, null, null, 0, 0);
        this.actualizarUnidadActual(etiqueta);
    }

    public void agregarEtiqueta(String etiqueta, int fila, int columna) {
        this.agregar(OperadorCuarteta.ETIQUETA, etiqueta, null, null, fila, columna);
        this.actualizarUnidadActual(etiqueta);
    }


    private void actualizarUnidadActual(String etiqueta) {
        if (etiqueta != null
                && !TipoOperando.ETIQUETA_INTERNA.equals(this.categoriasOperandos.get(etiqueta))) {
            this.unidadActual = etiqueta;
        }
    }

    public Cuarteta get(int indice) {
        return this.cuartetas.get(indice);
    }

    public int getTamaño() {
        return this.cuartetas.size();
    }

    public List<Cuarteta> getCuartetas() {
        return cuartetas;
    }

    public int getContadorTemporales() {
        return contadorTemporales;
    }

    public int getContadorEtiquetas() {
        return contadorEtiquetas;
    }

    public void setContadorTemporales(int contadorTemporales) {
        this.contadorTemporales = contadorTemporales;
    }

    public void setContadorEtiquetas(int contadorEtiquetas) {
        this.contadorEtiquetas = contadorEtiquetas;
    }

    public void registrarTipoTemporal(String nombre, Tipo tipo) {
        if (nombre != null && tipo != null) {
            this.tiposTemporales.put(nombre, tipo);
        }
    }

    public void registrarTipoVariable(String nombre, Tipo tipo) {
        if (nombre != null && tipo != null) {
            this.tiposVariables.put(nombre, tipo);
        }
    }


    public void registrarTipoVariableDeclarada(String nombre, Tipo tipo) {
        this.registrarTipoVariable(nombre, tipo);
        TipoOperando cat = categoriasOperandos.get(nombre);
        if (cat == TipoOperando.TEMPORAL) {
            categoriasOperandos.put(nombre, TipoOperando.VARIABLE);
        }
        if (nombre != null && tipo != null && this.unidadActual != null) {
            this.variablesDeclaradasPorUnidad
                    .computeIfAbsent(this.unidadActual, k -> new HashMap<>())
                    .put(nombre, tipo);
        }
    }

    public void registrarTipoArreglo(String nombre, Tipo tipoElemento) {
        if (nombre != null && tipoElemento != null) {
            this.tiposArreglos.put(nombre, tipoElemento);
        }
    }

    public void registrarTipoArregloDeclarado(String nombre, Tipo tipoElemento) {
        this.registrarTipoArreglo(nombre, tipoElemento);
        if (nombre != null && tipoElemento != null && this.unidadActual != null) {
            this.arreglosDeclaradosPorUnidad
                    .computeIfAbsent(this.unidadActual, k -> new HashMap<>())
                    .put(nombre, tipoElemento);
        }
    }

    public void registrarDimensionArreglo(String nombre, String expresionTamano) {
        if (nombre != null && expresionTamano != null) {
            this.dimensionesArreglo.put(nombre, expresionTamano);
        }
    }

    public String getDimensionArreglo(String nombre) {
        return this.dimensionesArreglo.get(nombre);
    }

    public void registrarTipoEstructura(String nombre, Tipo tipo) {
        if (nombre != null && tipo != null) {
            this.tiposEstructuras.put(nombre, tipo);
        }
    }

    public void registrarTipoFuncion(String nombre, Tipo tipoRetorno) {
        if (nombre != null && tipoRetorno != null) {
            this.tiposFunciones.put(nombre, tipoRetorno);
        }
    }

    public void registrarParametrosFuncion(String etiqueta,
            List<SimboloParametro> parametros) {
        if (etiqueta != null && parametros != null) {
            this.parametrosFunciones.put(etiqueta, parametros);
        }
    }

    public List<SimboloParametro> parametrosDeFuncion(String etiqueta) {
        if (etiqueta == null) {
            return null;
        }
        return this.parametrosFunciones.get(etiqueta);
    }

    public Tipo tipoDeTemporal(String nombre) {
        return this.tiposTemporales.get(nombre);
    }

    public Tipo tipoDeVariable(String nombre) {
        return this.tiposVariables.get(nombre);
    }

    public Tipo tipoDeArreglo(String nombre) {
        return this.tiposArreglos.get(nombre);
    }

    public Map<String, Map<String, Tipo>> getVariablesDeclaradasPorUnidad() {
        return this.variablesDeclaradasPorUnidad;
    }

    public Map<String, Map<String, Tipo>> getArreglosDeclaradosPorUnidad() {
        return this.arreglosDeclaradosPorUnidad;
    }

    public Map<String, Set<String>> getNombresDeclaradosPorUnidad() {
        Map<String, Set<String>> nombres = new HashMap<>();
        for (Map.Entry<String, Map<String, Tipo>> entrada : this.variablesDeclaradasPorUnidad.entrySet()) {
            nombres.computeIfAbsent(entrada.getKey(), k -> new LinkedHashSet<>())
                    .addAll(entrada.getValue().keySet());
        }
        for (Map.Entry<String, Map<String, Tipo>> entrada : this.arreglosDeclaradosPorUnidad.entrySet()) {
            nombres.computeIfAbsent(entrada.getKey(), k -> new LinkedHashSet<>())
                    .addAll(entrada.getValue().keySet());
        }
        return nombres;
    }

    public Tipo tipoDeFuncion(String nombre) {
        return this.tiposFunciones.get(nombre);
    }

    public List<Tipo> tiposRegistrados() {
        List<Tipo> tipos = new ArrayList<>();
        tipos.addAll(this.tiposVariables.values());
        tipos.addAll(this.tiposTemporales.values());
        tipos.addAll(this.tiposFunciones.values());
        tipos.addAll(this.tiposArreglos.values());
        tipos.addAll(this.tiposEstructuras.values());
        return tipos;
    }
}