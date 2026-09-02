package com.ronaldo.cd3.compiler.api.dtos.entrada;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class EntradaDTO {

    private List<ArchivoDTO> archivos;

    public List<ArchivoDTO> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<ArchivoDTO> archivos) {
        this.archivos = archivos;
    }

}
