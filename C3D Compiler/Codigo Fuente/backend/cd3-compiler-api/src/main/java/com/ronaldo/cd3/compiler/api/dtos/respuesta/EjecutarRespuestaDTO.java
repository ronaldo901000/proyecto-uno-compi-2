package com.ronaldo.cd3.compiler.api.dtos.respuesta;

/**
 * 
 * @author ronaldo
 */
public class EjecutarRespuestaDTO {

    private boolean exito;
    private String salida;
    private String error;
    private String codigoRiscV;

    public EjecutarRespuestaDTO() {
    }

    public EjecutarRespuestaDTO(boolean exito, String salida, String error, String codigoRiscV) {
        this.exito = exito;
        this.salida = salida;
        this.error = error;
        this.codigoRiscV = codigoRiscV;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCodigoRiscV() {
        return codigoRiscV;
    }

    public void setCodigoRiscV(String codigoRiscV) {
        this.codigoRiscV = codigoRiscV;
    }
}
