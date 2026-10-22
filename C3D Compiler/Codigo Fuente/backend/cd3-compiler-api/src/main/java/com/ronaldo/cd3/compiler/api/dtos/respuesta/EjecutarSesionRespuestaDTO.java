package com.ronaldo.cd3.compiler.api.dtos.respuesta;

/**
 * Respuesta de operaciones de sesion interactiva de Venus.
 */
public class EjecutarSesionRespuestaDTO {

    private boolean exito;
    private String sessionId;
    private String salida;
    private String error;
    private String codigoRiscV;

    public EjecutarSesionRespuestaDTO() {
    }

    public EjecutarSesionRespuestaDTO(boolean exito, String sessionId,
                                      String salida, String error, String codigoRiscV) {
        this.exito = exito;
        this.sessionId = sessionId;
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
