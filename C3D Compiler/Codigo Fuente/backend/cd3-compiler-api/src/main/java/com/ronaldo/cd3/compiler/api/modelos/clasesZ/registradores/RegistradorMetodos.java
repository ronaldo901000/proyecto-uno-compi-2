package com.ronaldo.cd3.compiler.api.modelos.clasesZ.registradores;

import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ResolutorTipoRetorno;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.FuncionDef;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.Parametro;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ronaldo
 */
public class RegistradorMetodos {

    private String nombreClase;
    private List<FuncionDef> metodos;
    private Reglas reglas;

    public RegistradorMetodos(String nombreClase, List<FuncionDef> metodos, Reglas reglas) {
        this.nombreClase = nombreClase;
        this.metodos = metodos;
        this.reglas = reglas;
    }

    /**
     * Crea el símbolo de cada metodo y verifica metodos repetidos.
     */
    public void registrarMetodos(Contexto contexto, SimboloClase simboloClase) {

        if (metodos == null) {
            return;
        }

        int contador = 0;
        Set<String> firmas = new HashSet<>();
        for (FuncionDef metodo : metodos) {
            contador++;
            SimboloFuncion simboloMetodo = simboloDeMetodo(contexto, metodo,
                    "metodo_" + nombreClase + "_" + metodo.getNombre() + "_" + contador);
            if (simboloMetodo == null) {
                continue;
            }

            metodo.setSimbolo(simboloMetodo);
            String clave = claveDeFirma(metodo.getNombre(), simboloMetodo);
            if (!firmas.add(clave)) {
                contexto.agregarError(metodo.getFila(), metodo.getColumna(),
                        metodo.getNombre(),
                        "El método '" + metodo.getNombre() + "' ya está definido "
                        + "con los mismos parámetros en la clase '" + nombreClase + "'");
                continue;
            }
            simboloClase.agregarMetodo(simboloMetodo);
        }
    }

    private SimboloFuncion simboloDeMetodo(Contexto contexto, FuncionDef metodo,
            String etiqueta) {

        ResolutorTipoRetorno resolutorRetorno = new ResolutorTipoRetorno(reglas);

        Tipo tipoRetornoT = resolutorRetorno.resolver(contexto, metodo);

        if (reglas.esError(tipoRetornoT)) {
            return null;
        }

        List<SimboloParametro> simbolosParametros = new ArrayList<>();
        if (metodo.getParametros() != null) {
            for (Parametro parametro : metodo.getParametros()) {
                parametro.verificarSemantica(contexto);
                simbolosParametros.add(new SimboloParametro(
                        parametro.getNombre(), parametro.getTipo(), 0));
            }
        }
        SimboloFuncion simboloMetodo = new SimboloFuncion(metodo.getNombre(), tipoRetornoT,
                simbolosParametros, 0, etiqueta);
        simboloMetodo.setModAcceso(metodo.getModAcceso());
        return simboloMetodo;
    }

    private String claveDeFirma(String nombreFuncion, SimboloFuncion funcion) {
        List<Tipo> tipos = new ArrayList<>();
        for (SimboloParametro parametro : funcion.getParametros()) {
            tipos.add(parametro.getTipo());
        }
        return TablaSimbolos.claveFuncion(nombreFuncion, tipos);
    }
}
