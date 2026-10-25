package com.ronaldo.cd3.compiler.api.services.ejecutor.riscv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * @author ronaldo
 */
public class GeneradorArchivo {

    private final Path directorio;

    public GeneradorArchivo(String directorioTrabajo) {
        this.directorio = Paths.get(directorioTrabajo);
    }

    public Path guardar(String nombre, String contenido) throws IOException {
        Files.createDirectories(directorio);
        Path archivo = directorio.resolve(nombre);
        Files.writeString(archivo, contenido);
        return archivo;
    }
}