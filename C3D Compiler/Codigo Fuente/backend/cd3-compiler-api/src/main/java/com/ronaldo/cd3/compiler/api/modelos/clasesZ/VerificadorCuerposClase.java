package com.ronaldo.cd3.compiler.api.modelos.clasesZ;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.FuncionDef;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.Parametro;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
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

    public void verificar(Contexto contexto) {
        if (clase.getSimboloClase() == null) {
            return;
        }

        if (clase.getMetodos() != null) {
            for (FuncionDef metodo : clase.getMetodos()) {
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
    }

    private void verificarMetodo(Contexto contexto, FuncionDef metodo) {
        Tipo tipoRetorno = resolutorRetorno.resolver(contexto, metodo);

        verificarCuerpo(contexto, metodo.getNombre(), metodo.getParametros(),
                tipoRetorno, metodo.getCuerpo());

        if (!reglas.esVoid(tipoRetorno) && !reglas.siempreRetorna(metodo.getCuerpo())) {
            contexto.agregarError(metodo.getFila(), metodo.getColumna(), metodo.getNombre(),
                    "El metodo '" + metodo.getNombre() + "' de tipo " + tipoRetorno
                    + " no retorna en todos sus caminos de ejecución");
        }
    }

    private void verificarConstructor(Contexto contexto, ConstructorZ constructor) {
        verificarCuerpo(contexto, "constructor_" + constructor.getNombre(),
                constructor.getParametros(), contexto.getTablaTipos().getVoid(),
                constructor.getCuerpo());
    }

    private void verificarCuerpo(Contexto contexto, String nombreMiembro,
            List<Parametro> parametros, Tipo tipoRetorno, List<Instruccion> cuerpo) {
        SimboloClase simboloClase = clase.getSimboloClase();

        TablaSimbolos anterior = contexto.nuevoAmbito(nombreMiembro);
        registrarAtributosEnAmbito(contexto, simboloClase);

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

    private void registrarAtributosEnAmbito(Contexto contexto, SimboloClase simboloClase) {
        if (simboloClase == null) {
            return;
        }
        for (SimboloVariable atributo : simboloClase.getAtributos().values()) {
            contexto.getAmbito().agregar(new SimboloVariable(
                    atributo.getId(), atributo.getTipo(), atributo.getPosicion()));
        }
    }
}
