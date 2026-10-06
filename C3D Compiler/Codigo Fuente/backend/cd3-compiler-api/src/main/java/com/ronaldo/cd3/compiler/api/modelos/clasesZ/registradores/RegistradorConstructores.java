package com.ronaldo.cd3.compiler.api.modelos.clasesZ.registradores;

import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ConstructorZ;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.funciones.Parametro;
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
public class RegistradorConstructores {

    private String nombreClase;
    private List<ConstructorZ> constructores;

    public RegistradorConstructores(String nombreClase, List<ConstructorZ> constructores) {
        this.nombreClase = nombreClase;
        this.constructores = constructores;
    }

    /**
     * Valida el nombre de cada constructor, crea su símbolo y verifica
     * contructores repetidos
     */
    public void registrarConstructores(Contexto contexto, SimboloClase simboloClase) {
        List<ConstructorZ> constructores = this.constructores;
        if (constructores == null) {
            return;
        }

        int contador = 0;
        Set<String> firmas = new HashSet<>();
        for (ConstructorZ constructor : constructores) {
            contador++;
            if (!constructor.getNombre().equals(nombreClase)) {
                contexto.agregarError(constructor.getFila(), constructor.getColumna(),
                        constructor.getNombre(),
                        "El constructor debe llamarse igual que la clase '" + nombreClase + "'");
                continue;
            }

            SimboloFuncion simboloConstructor = simboloDeMetodo(
                    contexto, constructor, "constructor_" + nombreClase + "_" + contador);
            if (simboloConstructor == null) {
                continue;
            }

            constructor.setSimbolo(simboloConstructor);
            String clave = claveDeFirma(nombreClase, simboloConstructor);
            if (!firmas.add(clave)) {
                contexto.agregarError(constructor.getFila(), constructor.getColumna(),
                        constructor.getNombre(),
                        "Ya existe un constructor de la clase '" + nombreClase
                        + "' con los mismos parámetros");
                continue;
            }
            simboloClase.agregarConstructor(simboloConstructor);
        }
    }

    private String claveDeFirma(String nombreFuncion, SimboloFuncion funcion) {
        List<Tipo> tipos = new ArrayList<>();
        for (SimboloParametro parametro : funcion.getParametros()) {
            tipos.add(parametro.getTipo());
        }
        return TablaSimbolos.claveFuncion(nombreFuncion, tipos);
    }

    private SimboloFuncion simboloDeMetodo(Contexto contexto, ConstructorZ constructor,
            String etiqueta) {
        List<SimboloParametro> simbolosParametros = new ArrayList<>();
        if (constructor.getParametros() != null) {
            for (Parametro parametro : constructor.getParametros()) {
                parametro.verificarSemantica(contexto);
                simbolosParametros.add(new SimboloParametro(
                        parametro.getNombre(), parametro.getTipo(), 0));
            }
        }
        SimboloFuncion simboloConstructor = new SimboloFuncion(constructor.getNombre(),
                contexto.getTablaTipos().getVoid(), simbolosParametros, 0, etiqueta);
        simboloConstructor.setModAcceso(constructor.getModAcceso());
        return simboloConstructor;
    }

}
