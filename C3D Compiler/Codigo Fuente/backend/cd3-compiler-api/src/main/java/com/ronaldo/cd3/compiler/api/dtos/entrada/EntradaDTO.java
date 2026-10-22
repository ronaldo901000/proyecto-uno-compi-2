package com.ronaldo.cd3.compiler.api.dtos.entrada;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class EntradaDTO {

    private List<ArchivoDTO> archivos;
    private String entradaEstandar;

    public List<ArchivoDTO> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<ArchivoDTO> archivos) {
        this.archivos = archivos;
    }

    public String getEntradaEstandar() {
        return entradaEstandar;
    }

    public void setEntradaEstandar(String entradaEstandar) {
        this.entradaEstandar = entradaEstandar;
    }

}
