package com.ronaldo.cd3.compiler.api.modelos.ir.operando;

import com.ronaldo.cd3.compiler.api.enums.RolOperando;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

public abstract class Operando {

    private final String texto;
    private final TipoOperando categoria;
    private final Tipo tipo;
    private final RolOperando rol;

    protected Operando(String texto, TipoOperando categoria, Tipo tipo,
            RolOperando rol) {
        this.texto = texto;
        this.categoria = categoria;
        this.tipo = tipo;
        this.rol = rol;
    }

    public String getTexto() {
        return texto;
    }

    public TipoOperando getCategoria() {
        return categoria;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public RolOperando getRol() {
        return rol;
    }

    public boolean esValor() {
        return rol == RolOperando.VALOR;
    }

    public boolean esDireccion() {
        return rol == RolOperando.DIRECCION;
    }

    public boolean esTemporal() {
        return categoria == TipoOperando.TEMPORAL;
    }

    public boolean esVariable() {
        return categoria == TipoOperando.VARIABLE;
    }

    public boolean esEtiqueta() {
        return categoria == TipoOperando.ETIQUETA_INTERNA;
    }

    public boolean esFuncion() {
        return categoria == TipoOperando.FUNCION;
    }

    public boolean esConstante() {
        return categoria == TipoOperando.CONSTANTE_ENTERA
                || categoria == TipoOperando.CONSTANTE_DECIMAL
                || categoria == TipoOperando.CONSTANTE_CADENA
                || categoria == TipoOperando.CONSTANTE_CARACTER
                || categoria == TipoOperando.BOOLEANO_VERDADERO
                || categoria == TipoOperando.BOOLEANO_FALSO
                || categoria == TipoOperando.NULO;
    }

    public boolean esPorReferencia() {
        return tipo != null && tipo.esPorReferencia();
    }

    @Override
    public String toString() {
        return texto;
    }
}
