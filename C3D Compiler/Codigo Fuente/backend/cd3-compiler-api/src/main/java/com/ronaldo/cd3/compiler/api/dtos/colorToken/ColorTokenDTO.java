package com.ronaldo.cd3.compiler.api.dtos.colorToken;

/**
 *
 * @author ronaldo
 */
public class ColorTokenDTO {
    
    private int inicio;
    private int fin;
    private String color;

    public ColorTokenDTO(int inicio, int fin, String color) {
        this.inicio = inicio;
        this.fin = fin;
        this.color = color;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
}
