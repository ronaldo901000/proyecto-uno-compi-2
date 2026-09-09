package com.ronaldo.cd3.compiler.api.dtos.respuesta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorAnalisis;
import com.ronaldo.cd3.compiler.api.modelos.programaY.ProgramaY;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class RespuestaDTO {

    private boolean hayErrores;
    private List<ErrorAnalisis> errores;
    @JsonIgnore
    private List<ProgramaY> programasY;

    public RespuestaDTO() {
        this.errores = new ArrayList<>();
        this.programasY = new ArrayList<>();
    }

    public void agregarListaErrores(List<ErrorAnalisis> lista) {
        for (ErrorAnalisis error : lista) {
            this.errores.add(error);
        }
    }
    
    public void agregarUnError(ErrorAnalisis error){
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

    public List<ProgramaY> getProgramasY() {
        return programasY;
    }

    public void setProgramasY(List<ProgramaY> programasY) {
        this.programasY = programasY;
    }

    public void agregarProgramaY(ProgramaY programa) {
        this.programasY.add(programa);
    }

}
