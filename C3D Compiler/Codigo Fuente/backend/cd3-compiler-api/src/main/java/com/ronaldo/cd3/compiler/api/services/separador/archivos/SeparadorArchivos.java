package com.ronaldo.cd3.compiler.api.services.separador.archivos;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.enums.ExtensionArchivos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class SeparadorArchivos {

    private List<ArchivoDTO> archivosY;

    public SeparadorArchivos() {
        this.archivosY = new ArrayList<>();
    }

    public void separar(List<ArchivoDTO> lista) {
        for (ArchivoDTO archivo : lista) {
            if (archivo.getExtension().equals(ExtensionArchivos.Y.getTexto())) {
                archivosY.add(archivo);
            }
        }
    }

    public List<ArchivoDTO> getArchivosY() {
        return archivosY;
    }
    
    
}
