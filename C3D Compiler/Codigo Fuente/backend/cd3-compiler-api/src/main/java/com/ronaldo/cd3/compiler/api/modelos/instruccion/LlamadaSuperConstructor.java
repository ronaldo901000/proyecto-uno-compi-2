package com.ronaldo.cd3.compiler.api.modelos.instruccion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.VerificadorAcceso;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ronaldo
 */
public class LlamadaSuperConstructor extends Nodo implements Instruccion {

    private final Reglas reglas = new Reglas();
    private final VerificadorAcceso verificadorAcceso = new VerificadorAcceso();
    private List<Expresion> argumentos;

    public LlamadaSuperConstructor(List<Expresion> argumentos, int fila, int columna) {
        super(fila, columna);
        this.argumentos = argumentos;
    }

    public List<Expresion> getArgumentos() {
        return argumentos;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        SimboloClase superclase = obtenerSuperclase(contexto);
        if (superclase == null) {
            return;
        }

        List<Tipo> tiposArgumentos = verificarArgumentos(contexto);

        List<SimboloFuncion> constructores = superclase.getConstructores();
        if (constructores == null || constructores.isEmpty()) {
            if (!tiposArgumentos.isEmpty()) {
                contexto.agregarError(fila, columna, "super",
                        "La superclase '" + superclase.getId()
                        + "' no tiene un constructor compatible con los argumentos proporcionados");
            }
            return;
        }

        SimboloFuncion constructor = reglas.resolverEntre(constructores, tiposArgumentos);

        if (constructor == null) {
            contexto.agregarError(fila, columna, "super",
                    "No existe un constructor de la superclase '" + superclase.getId()
                    + "' compatible con los argumentos proporcionados");
            return;
        }

        verificadorAcceso.verificar(contexto, fila, columna, superclase.getId(),
                constructor.getModAcceso(), constructor.getNombreClase());

        verificarCompatibilidadArgumentos(contexto, constructor);
    }

    /**
     * super(...) solo es valido dentro de un constructor cuya clase tenga
     * superclase
     */
    private SimboloClase obtenerSuperclase(Contexto contexto) {
        SimboloClase claseActual = contexto.getClaseActual();
        if (claseActual == null) {
            contexto.agregarError(fila, columna, "super",
                    "'super(...)' solo puede usarse dentro de un constructor");
            return null;
        }

        SimboloClase superclase = claseActual.getClasePadre();
        if (superclase == null) {
            contexto.agregarError(fila, columna, "super",
                    "La clase '" + claseActual.getId()
                    + "' no tiene superclase. No se puede llamar a super(...).");
            return null;
        }
        return superclase;
    }

    private List<Tipo> verificarArgumentos(Contexto contexto) {
        List<Tipo> tiposArgumentos = new ArrayList<>();
        if (argumentos != null) {
            for (Expresion argumento : argumentos) {
                argumento.verificarSemantica(contexto);
                tiposArgumentos.add(argumento.getTipo());
            }
        }
        return tiposArgumentos;
    }

    private void verificarCompatibilidadArgumentos(Contexto contexto,
            SimboloFuncion constructor) {
        if (argumentos == null) {
            return;
        }
        List<SimboloParametro> parametros = constructor.getParametros();
        for (int i = 0; i < argumentos.size(); i++) {
            Expresion argumento = argumentos.get(i);
            if (!reglas.esAsignable(parametros.get(i).getTipo(), argumento.getTipo())) {
                contexto.agregarError(argumento.getFila(), argumento.getColumna(),
                        "super", "El argumento " + (i + 1)
                        + " del constructor de la superclase es incompatible con su parametro");
            }
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        List<String> temporalesArgumentos = new ArrayList<>();
        if (argumentos != null) {
            for (Expresion argumento : argumentos) {
                temporalesArgumentos.add(argumento.generarCuartetas(contexto, cuartetas));
            }
        }

        SimboloFuncion constructor = resolverConstructor(contexto);
        if (constructor == null) {
            return null;
        }

        cuartetas.agregar(OperadorCuarteta.PARAMETRO, "this",
                null, null, fila, columna);
        for (String dir : temporalesArgumentos) {
            cuartetas.agregar(OperadorCuarteta.PARAMETRO, dir,
                    null, null, fila, columna);
        }

        cuartetas.agregar(OperadorCuarteta.LLAMADA, constructor.getEtiquetaInicio(),
                null, null, fila, columna);
        return null;
    }

    private SimboloFuncion resolverConstructor(Contexto contexto) {
        SimboloClase claseActual = contexto.getClaseActual();
        if (claseActual == null || claseActual.getClasePadre() == null) {
            return null;
        }
        List<SimboloFuncion> constructores = claseActual.getClasePadre().getConstructores();
        if (constructores == null || constructores.isEmpty()) {
            return null;
        }
        return reglas.resolverEntre(constructores, tiposDeArgumentos());
    }

    private List<Tipo> tiposDeArgumentos() {
        List<Tipo> tipos = new ArrayList<>();
        if (argumentos != null) {
            for (Expresion argumento : argumentos) {
                tipos.add(argumento.getTipo());
            }
        }
        return tipos;
    }

}
