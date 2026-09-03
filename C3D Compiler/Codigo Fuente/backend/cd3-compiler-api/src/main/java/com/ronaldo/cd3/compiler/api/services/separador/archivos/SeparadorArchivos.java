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
    private List<ArchivoDTO> archivosZ;

    public SeparadorArchivos() {
        this.archivosY = new ArrayList<>();
        this.archivosZ = new ArrayList<>();
    }

    public void separar(List<ArchivoDTO> lista) {
        for (ArchivoDTO archivo : lista) {
            if (archivo.getExtension().equals(ExtensionArchivos.Y.getTexto())) {
                archivosY.add(archivo);
            } else if (archivo.getExtension().equals(ExtensionArchivos.Z.getTexto())) {
                archivosZ.add(archivo);
            }
        }
    }

    public List<ArchivoDTO> getArchivosY() {
        return archivosY;
    }

    public List<ArchivoDTO> getArchivosZ() {
        return archivosZ;
    }

}
