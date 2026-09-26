package com.ronaldo.cd3.compiler.api.dtos.respuesta;

import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorAnalisis;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Cuarteta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class RespuestaDTO {

    private boolean hayErrores;
    private List<ErrorAnalisis> errores;
    private List<Cuarteta> cuartetas;
    private boolean codigoCGenerado;

    public RespuestaDTO() {
        codigoCGenerado = false;
        this.errores = new ArrayList<>();
        this.cuartetas = new ArrayList<>();
    }

    public void agregarListaErrores(List<ErrorAnalisis> lista) {
        for (ErrorAnalisis error : lista) {
            this.errores.add(error);
        }
    }

    public void agregarUnError(ErrorAnalisis error) {
        this.errores.add(error);
    }

    public boolean isHayErrores() {
        return hayErrores;
    }

    public void setHayErrores(boolean hayErrores) {
        this.hayErrores = hayErrores;
    }

    public List<ErrorAnalisis> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorAnalisis> errores) {
        this.errores = errores;
    }

    public List<Cuarteta> getCuartetas() {
        return cuartetas;
    }

    public void setCuartetas(List<Cuarteta> cuartetas) {
        this.cuartetas = cuartetas;
    }

    public boolean isCodigoCGenerado() {
        return codigoCGenerado;
    }

    public void setCodigoCGenerado(boolean codigoCGenerado) {
        this.codigoCGenerado = codigoCGenerado;
    }

}
