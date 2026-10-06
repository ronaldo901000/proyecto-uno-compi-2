package com.ronaldo.cd3.compiler.api.modelos.clasesZ;

import com.ronaldo.cd3.compiler.api.enums.ModificadoresAcceso;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.funciones.Funcion;
import com.ronaldo.cd3.compiler.api.modelos.funciones.Parametro;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.List;

/*
 *
 * @author ronaldo
 */
public class VerificadorCuerposClase {

    private final ClaseZ clase;
    private final Reglas reglas;
    private final ResolutorTipoRetorno resolutorRetorno;

    public VerificadorCuerposClase(ClaseZ clase, Reglas reglas, ResolutorTipoRetorno resolutorRetorno) {
        this.clase = clase;
        this.reglas = reglas;
        this.resolutorRetorno = resolutorRetorno;
    }

    /**
     *
     * @param contexto
     */
    public void verificar(Contexto contexto) {
        SimboloClase simboloClase = clase.getSimboloClase();
        if (simboloClase == null) {
            return;
        }

        TablaSimbolos ambitoPrevio = contexto.getAmbito();
        contexto.setAmbito(simboloClase.getAmbito());

        if (clase.getMetodos() != null) {
            for (Funcion metodo : clase.getMetodos()) {
                verificarMetodo(contexto, metodo);
            }
        }

        if (clase.getConstructores() != null) {
            for (ConstructorZ constructor : clase.getConstructores()) {
                if (constructor.getNombre().equals(clase.getNombre())) {
                    verificarConstructor(contexto, constructor);
                }
            }
        }

        contexto.setAmbito(ambitoPrevio);
    }

    /**
     *
     * @param contexto
     * @param metodo
     */
    private void verificarMetodo(Contexto contexto, Funcion metodo) {
        Tipo tipoRetorno = resolutorRetorno.resolver(contexto, metodo);

        verificarOverride(contexto, metodo, tipoRetorno);

        verificarCuerpo(contexto, metodo.getNombre(), metodo.getParametros(),
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
        } else if (sobreescrito != null) {
            contexto.agregarError(metodo.getFila(), metodo.getColumna(), metodo.getNombre(),
                    "El método '" + metodo.getNombre() + "' sobreescribe el método de la clase '"
                    + sobreescrito.getNombreClase() + "' y debe llevar @Override.");
        }
    }

    private boolean retornoCompatible(Tipo retornoHijo, Tipo retornoPadre) {
        if (retornoHijo instanceof TipoStructura && retornoPadre instanceof TipoStructura) {
            return ((TipoStructura) retornoHijo).esSubtipoDe(retornoPadre);
        }
        return retornoHijo.esIgual(retornoPadre);
    }

    /**
     *
     * @param contexto
     * @param constructor
     */
    private void verificarConstructor(Contexto contexto, ConstructorZ constructor) {
        verificarCuerpo(contexto, "constructor_" + constructor.getNombre(),
                constructor.getParametros(), contexto.getTablaTipos().getVoid(),
                constructor.getCuerpo());
    }

    /**
     *
     * @param contexto
     * @param nombreMiembro
     * @param parametros
     * @param tipoRetorno
     * @param cuerpo
     */
    private void verificarCuerpo(Contexto contexto, String nombreMiembro,
            List<Parametro> parametros, Tipo tipoRetorno, List<Instruccion> cuerpo) {
        SimboloClase simboloClase = clase.getSimboloClase();

        TablaSimbolos anterior = contexto.nuevoAmbito(nombreMiembro);

        int posicion = 0;
        if (parametros != null) {
            for (Parametro parametro : parametros) {
                if (parametro.getTipo() == null) {
                    continue;
                }
                SimboloParametro simboloParametro = new SimboloParametro(
                        parametro.getNombre(), parametro.getTipo(), posicion);
                contexto.getAmbito().agregar(simboloParametro);
                posicion += parametro.getTipo().tamañoBytes();
            }
        }
        contexto.getAmbito().setSiguientePosicion(posicion);

        Tipo retornoAnterior = contexto.getTipoRetornoActual();
        SimboloClase claseAnterior = contexto.getClaseActual();
        contexto.setTipoRetornoActual(tipoRetorno);
        contexto.setClaseActual(simboloClase);

        reglas.verificarInstrucciones(contexto, cuerpo);

        contexto.setClaseActual(claseAnterior);
        contexto.setTipoRetornoActual(retornoAnterior);
        contexto.restaurarAmbito(anterior);
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
