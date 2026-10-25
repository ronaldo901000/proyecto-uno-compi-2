package com.ronaldo.cd3.compiler.api.dtos.respuesta;

import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorAnalisis;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Cuarteta;
import java.util.List;

/**
 * Respuesta de operaciones de sesion interactiva de Venus.
 */
public class EjecutarSesionRespuestaDTO {

    private boolean exito;
    private String sessionId;
    private String salida;
    private List<ErrorAnalisis> errores;
    private String codigoRiscV;
    private boolean terminado;
    private List<Cuarteta> cuartetas;

    public EjecutarSesionRespuestaDTO() {
    }

    public EjecutarSesionRespuestaDTO(boolean exito, String sessionId,
            String salida, List<ErrorAnalisis> errores, String codigoRiscV, List<Cuarteta> cuartetas) {
        this.exito = exito;
        this.sessionId = sessionId;
        this.salida = salida;
        this.errores = errores;
        this.codigoRiscV = codigoRiscV;
        this.cuartetas = cuartetas;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public List<ErrorAnalisis> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorAnalisis> errores) {
        this.errores = errores;
    }

    public String getCodigoRiscV() {
        return codigoRiscV;
    }

    public void setCodigoRiscV(String codigoRiscV) {
        this.codigoRiscV = codigoRiscV;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public List<Cuarteta> getCuartetas() {
        return cuartetas;
    }

    public void setCuartetas(List<Cuarteta> cuartetas) {
        this.cuartetas = cuartetas;
    }

}
