package com.ronaldo.cd3.compiler.api.dtos.entrada;

/**
 *
 * @author ronaldo
 */
public class ArchivoDTO {

    private String ruta;
    private String nombre;
    private String contenido;
    private String extension;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}
