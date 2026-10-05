package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.semantica.VerificadorAcceso;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Llamada extends Expresion implements Instruccion {

    private static final String METODO = "del metodo";
    private static final String FUNCION = "de la funcion";

    private final Reglas reglas = new Reglas();
    private final VerificadorAcceso verificadorAcceso = new VerificadorAcceso();
    private Expresion objetivo;
    private String nombreFuncion;
    private List<Expresion> argumentos;

    public Llamada(Expresion objetivo, String nombreFuncion,
            List<Expresion> argumentos, int fila, int columna) {
        super(fila, columna);
        this.objetivo = objetivo;
        this.nombreFuncion = nombreFuncion;
        this.argumentos = argumentos;
    }

    public Expresion getObjetivo() {
        return objetivo;
    }

    public String getNombreFuncion() {
        return nombreFuncion;
    }

    public List<Expresion> getArgumentos() {
        return argumentos;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (objetivo != null) {
            verificarMetodoDeObjeto(contexto);
            return;
        }

        List<Tipo> tiposArgumentos = verificarArgumentos(contexto);

        if (verificarMetodoDeClaseActual(contexto, tiposArgumentos)) {  
            return;
        }
        verificarFuncionLibre(contexto, tiposArgumentos);                
    }

    
    private void verificarMetodoDeObjeto(Contexto contexto) {
        objetivo.verificarSemantica(contexto);
        List<Tipo> tiposArgumentos = verificarArgumentos(contexto);
        Tipo tipoObjeto = objetivo.getTipo();

        if (reglas.esError(tipoObjeto)) {
            marcarError(contexto);
            return;
        }

        if (!(tipoObjeto instanceof TipoStructura)) {
            contexto.agregarError(fila, columna, nombreFuncion,
                    "No se puede invocar al metodo '" + nombreFuncion
                    + "' sobre un valor que no es un objeto");
            marcarError(contexto);
            return;
        }

        String nombreClase = ((TipoStructura) tipoObjeto).getNombreStruct();
        SimboloClase clase = contexto.getTablaSimbolos().buscarClase(nombreClase);

        if (clase == null) {
            contexto.agregarError(fila, columna, nombreFuncion,
                    "No se puede invocar un metodo sobre un valor de la estructura '"
                    + nombreClase + "'");
            marcarError(contexto);
            return;
        }

        List<SimboloFuncion> candidatas = clase.getMetodosPorNombre(nombreFuncion);
        SimboloFuncion metodo = reglas.resolverEntre(candidatas, tiposArgumentos);

        if (metodo == null) {
            if (candidatas.isEmpty()) {
                contexto.agregarError(fila, columna, nombreFuncion,
                        "El objeto de clase '" + clase.getId()
                        + "' no tiene un metodo '" + nombreFuncion + "'");
            } else {
                errorSobrecargaNoCompatible(contexto, "del metodo");
            }
            marcarError(contexto);
            return;
        }

        aceptarLlamada(contexto, metodo, METODO);
    }

    /**
     * retorna true solo si existe en la clase actual o en la clase padre recursivamente
     */
    private boolean verificarMetodoDeClaseActual(Contexto contexto, List<Tipo> tiposArgumentos) {
        SimboloClase claseActual = contexto.getClaseActual();
        if (claseActual == null) {
            return false;
        }

        List<SimboloFuncion> candidatas = claseActual.getMetodosPorNombre(nombreFuncion);
        if (candidatas.isEmpty()) {
            return false;
        }

        SimboloFuncion metodo = reglas.resolverEntre(candidatas, tiposArgumentos);

        if (metodo == null) {
            errorSobrecargaNoCompatible(contexto, "del metodo");
            marcarError(contexto);
            return true;
        }

        aceptarLlamada(contexto, metodo, METODO);
        return true;
    }

    /*funciones .y*/
    private void verificarFuncionLibre(Contexto contexto, List<Tipo> tiposArgumentos) {
        SimboloFuncion funcion = reglas.resolverFuncion(contexto, nombreFuncion, tiposArgumentos);

        if (funcion == null) {
            if (contexto.getAmbito().buscarSobrecargas(nombreFuncion).isEmpty()) {
                contexto.agregarError(fila, columna, nombreFuncion,
                        "La funcion '" + nombreFuncion + "' no esta definida");
            } else {
                errorSobrecargaNoCompatible(contexto, "de la funcion");
            }
            marcarError(contexto);
            return;
        }

        aceptarLlamada(contexto, funcion, FUNCION);
    }


    private void aceptarLlamada(Contexto contexto, SimboloFuncion destino, String descripcionDestino) {
        verificadorAcceso.verificar(contexto, fila, columna, nombreFuncion,
                destino.getModAcceso(), destino.getNombreClase());
        verificarCompatibilidadArgumentos(contexto, destino, descripcionDestino);
        setTipo(destino.getTipoRetorno());
    }

    private void verificarCompatibilidadArgumentos(Contexto contexto,
            SimboloFuncion destino, String descripcionDestino) {
        if (argumentos == null) {
            return;
        }
        List<SimboloParametro> parametros = destino.getParametros();
        for (int i = 0; i < argumentos.size(); i++) {
            Expresion argumento = argumentos.get(i);
            if (!reglas.esAsignable(parametros.get(i).getTipo(), argumento.getTipo())) {
                contexto.agregarError(argumento.getFila(), argumento.getColumna(),
                        nombreFuncion, "El argumento " + (i + 1)
                        + " " + descripcionDestino + " '" + nombreFuncion
                        + "' es incompatible con su parametro");
            }
        }
    }

    private void errorSobrecargaNoCompatible(Contexto contexto, String descripcionDestino) {
        contexto.agregarError(fila, columna, nombreFuncion,
                "No existe una sobrecarga " + descripcionDestino + " '" + nombreFuncion
                + "' compatible con los argumentos proporcionados");
    }

    private void marcarError(Contexto contexto) {
        setTipo(contexto.getTablaTipos().getError());
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

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String dirObjetivo = null;

        if (objetivo != null) {
            dirObjetivo = objetivo.generarCuartetas(contexto, cuartetas);
        }

        List<String> temporalesArgumentos = new ArrayList<>();

        if (argumentos != null) {
            for (Expresion arg : argumentos) {
                temporalesArgumentos.add(arg.generarCuartetas(contexto, cuartetas));
            }
        }

        SimboloFuncion funcion = resolverFuncion(contexto);
        String etiqueta = (funcion != null)
                ? funcion.getEtiquetaInicio() : nombreFuncion;

        boolean esMetodo = funcion != null && funcion.getNombreClase() != null;
        if (esMetodo) {
            String receptor = (dirObjetivo != null) ? dirObjetivo : "this";
            cuartetas.agregar(OperadorCuarteta.PARAMETRO, receptor,
                    null, null, fila, columna);
        }

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

    private SimboloFuncion resolverFuncion(Contexto contexto) {
        if (objetivo != null) {
            Tipo tipoObjeto = objetivo.getTipo();
            if (!(tipoObjeto instanceof TipoStructura)) {
                return null;
            }
            SimboloClase clase = contexto.getTablaSimbolos()
                    .buscarClase(((TipoStructura) tipoObjeto).getNombreStruct());
            if (clase == null) {
                return null;
            }
            return resolverMetodo(clase.getMetodosPorNombre(nombreFuncion));
        }

        SimboloClase claseActual = contexto.getClaseActual();
        if (claseActual != null) {
            List<SimboloFuncion> candidatas = claseActual.getMetodosPorNombre(nombreFuncion);
            if (!candidatas.isEmpty()) {
                return resolverMetodo(candidatas);
            }
        }
        return resolverFuncionLibre(contexto);
    }

    private SimboloFuncion resolverMetodo(List<SimboloFuncion> candidatas) {
        return reglas.resolverEntre(candidatas, tiposDeArgumentos());
    }

    private SimboloFuncion resolverFuncionLibre(Contexto contexto) {
        return reglas.resolverFuncion(contexto, nombreFuncion, tiposDeArgumentos());
    }

    private List<Tipo> tiposDeArgumentos() {
        List<Tipo> tipos = new ArrayList<>();
        if (argumentos != null) {
            for (Expresion arg : argumentos) {
                tipos.add(arg.getTipo());
            }
        }
        return tipos;
    }
}