package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final Map<String, TipoOperando> categoriasOperandos;
    private int contadorTemporales;
    private int contadorEtiquetas;

    public ListaCuartetas() {
        this.cuartetas = new ArrayList<>();
        this.tiposTemporales = new HashMap<>();
        this.tiposVariables = new HashMap<>();
        this.tiposArreglos = new HashMap<>();
        this.tiposFunciones = new HashMap<>();
        this.categoriasOperandos = new HashMap<>();
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

    public void agregar(OperadorCuarteta operador, String arg1, String arg2, String resultado,
            int fila, int columna) {
        this.cuartetas.add(new Cuarteta(operador, arg1, arg2, resultado, fila, columna));
    }

    public void agregarEtiqueta(String etiqueta) {
        this.agregar(OperadorCuarteta.ETIQUETA, etiqueta, null, null, 0, 0);
    }

    public void agregarEtiqueta(String etiqueta, int fila, int columna) {
        this.agregar(OperadorCuarteta.ETIQUETA, etiqueta, null, null, fila, columna);
    }

    public Cuarteta get(int indice) {
        return this.cuartetas.get(indice);
    }

    public int getTamanio() {
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

    public void registrarTipoArreglo(String nombre, Tipo tipoElemento) {
        if (nombre != null && tipoElemento != null) {
            this.tiposArreglos.put(nombre, tipoElemento);
        }
    }

    public void registrarTipoFuncion(String nombre, Tipo tipoRetorno) {
        if (nombre != null && tipoRetorno != null) {
            this.tiposFunciones.put(nombre, tipoRetorno);
        }
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

    public Tipo tipoDeFuncion(String nombre) {
        return this.tiposFunciones.get(nombre);
    }

    public List<Tipo> tiposRegistrados() {
        List<Tipo> tipos = new ArrayList<>();
        tipos.addAll(this.tiposVariables.values());
        tipos.addAll(this.tiposTemporales.values());
        tipos.addAll(this.tiposFunciones.values());
        tipos.addAll(this.tiposArreglos.values());
        return tipos;
    }
}