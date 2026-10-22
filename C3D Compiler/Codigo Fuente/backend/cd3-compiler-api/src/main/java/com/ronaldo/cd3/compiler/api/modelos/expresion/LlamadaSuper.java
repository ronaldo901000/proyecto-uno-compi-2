package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.VerificadorAcceso;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ronaldo
 */
public class LlamadaSuper extends Expresion implements Instruccion {

    private static final String METODO = "del metodo";

    private final Reglas reglas = new Reglas();
    private final VerificadorAcceso verificadorAcceso = new VerificadorAcceso();
    private AccesoVariable variable;
    private Llamada llamada;

    public LlamadaSuper(AccesoVariable variable, Llamada llamada, int fila, int columna) {
        super(fila, columna);
        this.variable = variable;
        this.llamada = llamada;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        SimboloClase superclase = obtenerSuperclase(contexto);
        if (superclase == null) {
            marcarError(contexto);
            return;
        }

        if (esMetodo()) {
            verificarMetodoEnSuperclase(contexto, superclase);
        } else {
            verificarAtributoEnSuperclase(contexto, superclase);
        }
    }

    /**
     * super solo es valido dentro de un metodo o constructor de una clase que
     * tenga superclase
     */
    private SimboloClase obtenerSuperclase(Contexto contexto) {
        SimboloClase claseActual = contexto.getClaseActual();
        if (claseActual == null) {
            contexto.agregarError(fila, columna, "super",
                    "'super' solo puede usarse dentro de un metodo o constructor");
            return null;
        }

        SimboloClase superclase = claseActual.getClasePadre();
        if (superclase == null) {
            contexto.agregarError(fila, columna, "super",
                    "La clase '" + claseActual.getId()
                    + "' no tiene superclase. No se puede usar super.");
            return null;
        }
        return superclase;
    }

    /**
     * el metodo se busca a partir de la superclase, por lo que no se
     * sobreescrito por la clase actual
     */
    private void verificarMetodoEnSuperclase(Contexto contexto, SimboloClase superclase) {
        List<Tipo> tiposArgumentos = verificarArgumentos(contexto);

        List<SimboloFuncion> candidatas
                = superclase.getMetodosPorNombre(llamada.getNombreFuncion());
        SimboloFuncion metodo = reglas.resolverEntre(candidatas, tiposArgumentos);

        if (metodo == null) {
            if (candidatas.isEmpty()) {
                contexto.agregarError(fila, columna, llamada.getNombreFuncion(),
                        "La superclase '" + superclase.getId()
                        + "' no tiene un metodo '" + llamada.getNombreFuncion() + "'");
            } else {
                errorSobrecargaNoCompatible(contexto);
            }
            marcarError(contexto);
            return;
        }

        verificadorAcceso.verificar(contexto, fila, columna, llamada.getNombreFuncion(),
                metodo.getModAcceso(), metodo.getNombreClase());
        setTipo(metodo.getTipoRetorno());
    }

    /**
     * el atributo se busca en el ambito de la superclase, la cadena de ambitos
     * (padre-hijo) hace que la busqueda suba por la jerarquia de herencia
     */
    private void verificarAtributoEnSuperclase(Contexto contexto, SimboloClase superclase) {
        TablaSimbolos ambitoPrevio = contexto.getAmbito();
        if (superclase.getAmbito() != null) {
            contexto.setAmbito(superclase.getAmbito());
        }
        variable.verificarSemantica(contexto);
        contexto.setAmbito(ambitoPrevio);
        setTipo(variable.getTipo());
    }

    private List<Tipo> verificarArgumentos(Contexto contexto) {
        List<Tipo> tiposArgumentos = new ArrayList<>();
        if (llamada.getArgumentos() != null) {
            for (Expresion argumento : llamada.getArgumentos()) {
                argumento.verificarSemantica(contexto);
                tiposArgumentos.add(argumento.getTipo());
            }
        }
        return tiposArgumentos;
    }

    private void errorSobrecargaNoCompatible(Contexto contexto) {
        contexto.agregarError(fila, columna, llamada.getNombreFuncion(),
                "No existe una sobrecarga " + METODO + " '" + llamada.getNombreFuncion()
                + "' compatible con los argumentos proporcionados");
    }

    private void marcarError(Contexto contexto) {
        setTipo(contexto.getTablaTipos().getError());
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (esMetodo()) {
            return generarCuartetasMetodo(contexto, cuartetas);
        }
        return variable.generarCuartetas(contexto, cuartetas);
    }

    /**
     * super se enlaza estaticamente con el metodo de la superclase, nunca con
     * la version sobreescrita en la clase actual
     */
    private String generarCuartetasMetodo(Contexto contexto, ListaCuartetas cuartetas) {
        List<String> temporalesArgumentos = new ArrayList<>();
        if (llamada.getArgumentos() != null) {
            for (Expresion argumento : llamada.getArgumentos()) {
                temporalesArgumentos.add(argumento.generarCuartetas(contexto, cuartetas));
            }
        }

        SimboloFuncion metodo = resolverMetodoEnSuperclase(contexto);
        String etiqueta = (metodo != null)
                ? metodo.getEtiquetaInicio() : llamada.getNombreFuncion();

        cuartetas.agregar(OperadorCuarteta.PARAMETRO, "this",
                null, null, fila, columna);
        for (String dir : temporalesArgumentos) {
            cuartetas.agregar(OperadorCuarteta.PARAMETRO, dir,
                    null, null, fila, columna);
        }

        String temporal = cuartetas.nuevoTemporal();
        cuartetas.registrarTipoTemporal(temporal, getTipo());
        if (etiqueta != null) {
            cuartetas.registrarTipoFuncion(etiqueta, getTipo());
        }
        cuartetas.agregar(OperadorCuarteta.LLAMADA, etiqueta,
                null, temporal, fila, columna);

        return temporal;
    }

    private SimboloFuncion resolverMetodoEnSuperclase(Contexto contexto) {
        SimboloClase claseActual = contexto.getClaseActual();
        if (claseActual == null || claseActual.getClasePadre() == null) {
            return null;
        }
        List<SimboloFuncion> candidatas
                = claseActual.getClasePadre().getMetodosPorNombre(llamada.getNombreFuncion());
        return reglas.resolverEntre(candidatas, tiposDeArgumentos());
    }

    private List<Tipo> tiposDeArgumentos() {
        List<Tipo> tipos = new ArrayList<>();
        if (llamada.getArgumentos() != null) {
            for (Expresion argumento : llamada.getArgumentos()) {
                tipos.add(argumento.getTipo());
            }
        }
        return tipos;
    }

    private boolean esMetodo() {
        return llamada != null;
    }

}
