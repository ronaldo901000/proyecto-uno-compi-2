package com.ronaldo.cd3.compiler.api.modelos.clasesZ.verificadores;

import com.ronaldo.cd3.compiler.api.enums.ModificadoresAcceso;
import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ClaseZ;
import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ResolutorTipoRetorno;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.funciones.Funcion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;

/**
 * Verifica los metodos de una clase: override, cuerpo/contenido y retorno.
 *
 * @author ronaldo
 */
public class VerificadorMetodos {

    private final ClaseZ clase;
    private final Reglas reglas;
    private final ResolutorTipoRetorno resolutorRetorno;
    private final VerificadorContenidoMiembro verificadorCuerpo;

    public VerificadorMetodos(ClaseZ clase, Reglas reglas,
            ResolutorTipoRetorno resolutorRetorno, VerificadorContenidoMiembro verificadorCuerpo) {
        this.clase = clase;
        this.reglas = reglas;
        this.resolutorRetorno = resolutorRetorno;
        this.verificadorCuerpo = verificadorCuerpo;
    }

    public void verificar(Contexto contexto) {
        if (clase.getMetodos() == null) {
            return;
        }
        for (Funcion metodo : clase.getMetodos()) {
            verificarMetodo(contexto, metodo);
        }
    }

    private void verificarMetodo(Contexto contexto, Funcion metodo) {
        Tipo tipoRetorno = resolutorRetorno.resolver(contexto, metodo);

        verificarOverride(contexto, metodo, tipoRetorno);

        verificadorCuerpo.verificar(contexto, metodo.getNombre(), metodo.getParametros(),
                tipoRetorno, metodo.getCuerpo());

        if (!reglas.esVoid(tipoRetorno) && !reglas.siempreRetorna(metodo.getCuerpo())) {
            contexto.agregarError(metodo.getFila(), metodo.getColumna(), metodo.getNombre(),
                    "El metodo '" + metodo.getNombre() + "' de tipo " + tipoRetorno
                    + " no retorna en todos sus caminos de ejecución");
        }
    }

    private void verificarOverride(Contexto contexto, Funcion metodo, Tipo tipoRetorno) {
        SimboloFuncion simbolo = metodo.getSimbolo();
        if (simbolo == null || reglas.esError(tipoRetorno)) {
            return;
        }

        SimboloClase simboloClase = clase.getSimboloClase();

        if (clase.getNombrePadre() != null && simboloClase.getClasePadre() == null) {
            return;
        }

        SimboloFuncion sobreescrito = simboloClase
                .buscarMetodoEnPadre(metodo.getNombre(), simbolo.getParametros());

        if (metodo.tieneOverride()) {
            verificarMetodoConOverride(contexto, metodo, simbolo, tipoRetorno, sobreescrito);
        } else if (sobreescrito != null) {
            contexto.agregarError(metodo.getFila(), metodo.getColumna(), metodo.getNombre(),
                    "El método '" + metodo.getNombre() + "' sobreescribe el método de la clase '"
                    + sobreescrito.getNombreClase() + "' y debe llevar @Override.");
        }
    }

    private void verificarMetodoConOverride(Contexto contexto, Funcion metodo,
            SimboloFuncion simbolo, Tipo tipoRetorno, SimboloFuncion sobreescrito) {

        if (sobreescrito == null) {
            
            contexto.agregarError(metodo.getFila(), metodo.getColumna(), "@Override",
                    "El método '" + metodo.getNombre()
                    + "' no sobreescribe ningún método de la clase padre.");
            
        } else if (!retornoCompatible(tipoRetorno, sobreescrito.getTipoRetorno())) {
            
            contexto.agregarError(metodo.getFila(), metodo.getColumna(), "@Override",
                    "El método '" + metodo.getNombre() + "' retorna " + tipoRetorno
                    + " pero el método sobreescrito en '" + sobreescrito.getNombreClase()
                    + "' retorna " + sobreescrito.getTipoRetorno() + ".");
            
        } else if (nivelAcceso(simbolo.getModAcceso()) < nivelAcceso(sobreescrito.getModAcceso())) {
            
            contexto.agregarError(metodo.getFila(), metodo.getColumna(), "@Override",
                    "El método '" + metodo.getNombre() + "' no puede reducir la visibilidad del "
                    + "método sobreescrito en '" + sobreescrito.getNombreClase() + "'.");
            
        }
    }

    private boolean retornoCompatible(Tipo retornoHijo, Tipo retornoPadre) {
        if (retornoHijo instanceof TipoStructura && retornoPadre instanceof TipoStructura) {
            return ((TipoStructura) retornoHijo).esSubtipoDe(retornoPadre);
        }
        return retornoHijo.esIgual(retornoPadre);
    }

    private int nivelAcceso(ModificadoresAcceso mod) {
        if (mod == null) {
            return 1;
        }
        switch (mod) {
            case PRIVATE:
                return 0;
            case PROTECTED:
                return 2;
            case PUBLIC:
                return 3;
            default:
                return 1;
        }
    }

}
