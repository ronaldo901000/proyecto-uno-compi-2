package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ListaCuartetas {

    private final List<Cuarteta> cuartetas;
    private int contadorTemporales;
    private int contadorEtiquetas;

    public ListaCuartetas() {
        this.cuartetas = new ArrayList<>();
        this.contadorTemporales = 0;
        this.contadorEtiquetas = 0;
    }

    public String nuevoTemporal() {
        return "t" + (++contadorTemporales);
    }

    public String nuevaEtiqueta() {
        return "L" + (++contadorEtiquetas);
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
}