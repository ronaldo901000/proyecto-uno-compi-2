package com.ronaldo.cd3.compiler.api.dtos.respuesta;

/**
 * DTO para enviar texto a una sesion de Venus viva.
 */
public class EjecutarSesionEnviarDTO {

    private String sessionId;
    private String texto;

    public EjecutarSesionEnviarDTO() {
    }

    public EjecutarSesionEnviarDTO(String sessionId, String texto) {
        this.sessionId = sessionId;
        this.texto = texto;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
