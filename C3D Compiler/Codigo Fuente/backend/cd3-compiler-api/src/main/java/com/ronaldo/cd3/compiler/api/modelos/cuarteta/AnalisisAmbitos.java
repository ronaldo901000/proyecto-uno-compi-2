package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ronaldo
 */
public class AnalisisAmbitos {

    private final ContextoTraduccion contexto;

    public AnalisisAmbitos(ContextoTraduccion contexto) {
        this.contexto = contexto;
    }


    public void analizar(List<Unidad> unidades) {
        for (Unidad unidad : unidades) {
            if (unidad.getNombre() != null) {
                contexto.getFunciones().add(unidad.getNombre());
            }
        }
        for (Unidad unidad : unidades) {
            String anterior = contexto.getUnidadActual();
            contexto.setUnidadActual(
                    (unidad.getNombre() != null) ? unidad.getNombre() : null);
            for (Cuarteta cuarteta : unidad.getCuartetas()) {
                if (cuarteta.getOperador() == OperadorCuarteta.LLAMADA) {
                    contexto.getFunciones().add(cuarteta.getArg1());
                }
                if (cuarteta.getOperador() == OperadorCuarteta.SUMA
                        && cuarteta.getResultado() != null
                        && contexto.esDeTipo(
                                contexto.tipoDeOperando(cuarteta.getResultado()),
                                TipoDato.CADENA)) {
                    contexto.setUsaConcatenacion(true);
                }
                if (cuarteta.getOperador() == OperadorCuarteta.IGUAL
                        || cuarteta.getOperador() == OperadorCuarteta.DISTINTO) {
                    Tipo izquierdo = contexto.tipoDeOperando(cuarteta.getArg1());
                    Tipo derecho = contexto.tipoDeOperando(cuarteta.getArg2());
                    if (contexto.esDeTipo(izquierdo, TipoDato.CADENA)
                            || contexto.esDeTipo(derecho, TipoDato.CADENA)) {
                        contexto.setUsaComparacionCadenas(true);
                    }
                }
                if (cuarteta.getOperador() == OperadorCuarteta.PUNTERO_INICIO
                        && cuarteta.getArg1() != null
                        && cuarteta.getResultado() != null) {
                    contexto.getArreglosNuevos().put(
                            cuarteta.getResultado(), cuarteta.getArg2());
                    analizarOperando(cuarteta.getArg2());
                    continue;
                }
                if (cuarteta.getOperador() == OperadorCuarteta.IMPRIMIR) {
                    analizarOperando(cuarteta.getArg1());
                    analizarOperando(cuarteta.getArg2());
                    continue;
                }
                analizarOperando(cuarteta.getArg1());
                analizarOperando(cuarteta.getArg2());
                analizarOperando(cuarteta.getResultado());
            }
            contexto.setUnidadActual(anterior);
        }
        for (Tipo tipo : contexto.getCuartetas().tiposRegistrados()) {
            recolectarEstructuras(tipo);
        }
        for (Tipo puntero : contexto.getPunterosArreglo().values()) {
            recolectarEstructuras(puntero);
        }
    }

    public void clasificarAmbitosVariables(List<Unidad> unidades) {
        Map<String, Set<String>> declaradas
                = contexto.getCuartetas().getNombresDeclaradosPorUnidad();
        Set<String> nombresDeclarados = new LinkedHashSet<>();
        for (Set<String> nombres : declaradas.values()) {
            nombresDeclarados.addAll(nombres);
        }

        for (Map.Entry<String, Set<String>> declaracion : declaradas.entrySet()) {
            String unidad = declaracion.getKey();
            for (String nombre : declaracion.getValue()) {
                if (nombre == null || nombre.isEmpty()) {
                    continue;
                }
                if (contexto.nombreMain(unidad)) {
                    contexto.getVariablesGlobales().add(nombre);
                } else {
                    contexto.getLocalesPorUnidad()
                            .computeIfAbsent(unidad, k -> new LinkedHashSet<>())
                            .add(nombre);
                }
            }
        }

        Map<String, Set<String>> usos = new LinkedHashMap<>();
        for (Unidad unidad : unidades) {
            String nombre = unidad.getNombre();
            if (nombre == null) {
                continue;
            }
            String clave = contexto.nombreMain(nombre) ? "main" : nombre;
            Set<String> parametros = parametrosDeUnidad(nombre);
            Set<String> campos = contexto.camposDeUnidad(nombre);
            for (Cuarteta cuarteta : unidad.getCuartetas()) {
                registrarUsoVariable(usos, cuarteta.getArg1(),
                        clave, parametros, campos);
                registrarUsoVariable(usos, cuarteta.getArg2(),
                        clave, parametros, campos);
                registrarUsoVariable(usos, cuarteta.getResultado(),
                        clave, parametros, campos);
            }
        }
        for (Map.Entry<String, Set<String>> uso : usos.entrySet()) {
            if (nombresDeclarados.contains(uso.getKey())) {
                continue;
            }
            Set<String> unidadesUso = uso.getValue();
            if (unidadesUso.contains("main") || unidadesUso.size() >= 2) {
                contexto.getVariablesGlobales().add(uso.getKey());
            } else {
                String unica = unidadesUso.iterator().next();
                contexto.getLocalesPorUnidad()
                        .computeIfAbsent(unica, k -> new LinkedHashSet<>())
                        .add(uso.getKey());
            }
        }
    }

    private void registrarUsoVariable(Map<String, Set<String>> usos,
            String operando, String unidad,
            Set<String> parametros, Set<String> campos) {
        if (operando == null || "this".equals(operando)) {
            return;
        }
        TipoOperando cat = contexto.categoriaDe(operando);
        if (cat != null && cat != TipoOperando.VARIABLE) {
            return;
        }
        if (contexto.getFunciones().contains(operando)) {
            return;
        }
        String base = raizIdentificador(operando);
        if (base == null) {
            return;
        }
        if (contexto.esCategoria(base, TipoOperando.TEMPORAL)) {
            return;
        }
        if (parametros != null && parametros.contains(base)) {
            return;
        }
        if (campos != null && campos.contains(base)) {
            return;
        }
        usos.computeIfAbsent(base, k -> new LinkedHashSet<>()).add(unidad);
    }


    private void analizarOperando(String operando) {
        if (operando == null) {
            return;
        }
        if (contexto.esCategoria(operando, TipoOperando.TEMPORAL)) {
            contexto.getTemporales().add(operando);
            return;
        }
        if (contexto.esCategoria(operando, TipoOperando.ETIQUETA_INTERNA)
                || contexto.esConstante(operando)) {
            return;
        }
        if (contexto.getFunciones().contains(operando)) {
            return;
        }
        String baseRaiz = raizIdentificador(operando);
        if (baseRaiz != null && operando.indexOf('.') >= 0) {
            registrarEscalarRaiz(baseRaiz);
            return;
        }
        if (operando.indexOf('[') >= 0) {
            String base = operando.substring(0, operando.indexOf('['));
            String baseRaizIndice = raizIdentificador(base);
            if (baseRaizIndice != null && baseRaizIndice.indexOf('.') >= 0) {
                registrarEscalarRaiz(baseRaizIndice);
                return;
            }
            if (esArregloPasadoPorReferencia(base)) {
                registrarPunteroArreglo(base);
                return;
            }
            if (contexto.esCategoria(base, TipoOperando.TEMPORAL)) {
                contexto.getTemporalesPuntero().add(base);
            } else if (contexto.esNombreDeVariable(base)
                    && !contexto.getFunciones().contains(base)) {
                if (contexto.getArreglos().containsKey(base)) {
                    Integer maximo = maximoIndiceNumerico(operando);
                    int tamanio = (maximo != null) ? (maximo + 1) : 100;
                    contexto.getArreglos().put(base,
                            Math.max(contexto.getArreglos().getOrDefault(base, 0),
                                    tamanio));
                    contexto.getEscalares().remove(base);
                } else if (contexto.getCuartetas().getDimensionArreglo(base)
                        != null) {
                    contexto.getEscalares().remove(base);
                }
            }
            return;
        }
        if (contexto.esNombreDeVariable(operando)) {
            if (esArregloPasadoPorReferencia(operando)) {
                registrarPunteroArreglo(operando);
                return;
            }

            Tipo tipoVariable = contexto.tipoDeVariableDe(operando);
            if (contexto.esObjetoPorReferencia(tipoVariable)) {
                contexto.getPunterosEstructura().put(operando,
                        (TipoStructura) tipoVariable);
                contexto.getEscalares().remove(operando);
                return;
            }

            if (!(tipoVariable instanceof TipoArreglo)
                    && !contexto.getArreglos().containsKey(operando)) {
                contexto.getEscalares().add(operando);
            }
        }
    }

    private boolean esArregloPasadoPorReferencia(String nombre) {
        if (nombre == null) {
            return false;
        }
        return contexto.getCuartetas().tipoDeVariable(nombre) instanceof TipoArreglo
                && contexto.getCuartetas().tipoDeArreglo(nombre) == null;
    }

    private void registrarPunteroArreglo(String nombre) {
        Tipo tipo = contexto.getCuartetas().tipoDeVariable(nombre);
        if (tipo instanceof TipoArreglo) {
            contexto.getPunterosArreglo().put(nombre,
                    ((TipoArreglo) tipo).getTipoBase());
        }
        contexto.getEscalares().remove(nombre);
        contexto.getArreglos().remove(nombre);
    }

    private void registrarEscalarRaiz(String nombre) {
        if (contexto.esCategoria(nombre, TipoOperando.TEMPORAL)
                || contexto.getFunciones().contains(nombre)) {
            return;
        }
        if (esArregloPasadoPorReferencia(nombre)) {
            registrarPunteroArreglo(nombre);
            return;
        }
        if (!contexto.esNombreDeVariable(nombre)) {
            return;
        }
        Tipo tipoVariable = contexto.tipoDeVariableDe(nombre);
        if (contexto.esObjetoPorReferencia(tipoVariable)) {
            contexto.getPunterosEstructura().put(nombre,
                    (TipoStructura) tipoVariable);
            contexto.getEscalares().remove(nombre);
            return;
        }
        if (!(tipoVariable instanceof TipoArreglo)) {
            contexto.getEscalares().add(nombre);
        }
    }


    public String raizIdentificador(String operando) {
        if (operando == null || operando.isEmpty()) {
            return null;
        }
        String base = operando;
        int corchete = operando.indexOf('[');
        if (corchete >= 0) {
            base = operando.substring(0, corchete);
        }
        int punto = base.indexOf('.');
        int flecha = base.indexOf("->");
        int corte = -1;
        if (punto >= 0 && flecha >= 0) {
            corte = Math.min(punto, flecha);
        } else if (punto >= 0) {
            corte = punto;
        } else if (flecha >= 0) {
            corte = flecha;
        }
        if (corte >= 0) {
            base = base.substring(0, corte);
        }
        return base;
    }

    private Integer maximoIndiceNumerico(String operando) {
        Integer maximo = null;
        if (operando == null) {
            return null;
        }
        int i = 0;
        while (i < operando.length()) {
            if (operando.charAt(i) == '[') {
                int j = i + 1;
                int inicio = j;
                while (j < operando.length()
                        && Character.isDigit(operando.charAt(j))) {
                    j++;
                }
                if (j > inicio && j < operando.length()
                        && operando.charAt(j) == ']') {
                    int indice = Integer.parseInt(
                            operando.substring(inicio, j));
                    maximo = (maximo == null)
                            ? indice : Math.max(maximo, indice);
                }
                i = j;
            } else {
                i++;
            }
        }
        return maximo;
    }

    private void recolectarEstructuras(Tipo tipo) {
        if (tipo == null) {
            return;
        }
        if (tipo instanceof TipoStructura) {
            TipoStructura estructura = (TipoStructura) tipo;
            if (!contexto.getEstructuras().containsKey(
                    estructura.getNombreStruct())) {
                contexto.getEstructuras().put(
                        estructura.getNombreStruct(), estructura);
                for (Tipo atributo : estructura.getAtributos().values()) {
                    recolectarEstructuras(atributo);
                }
            }
            return;
        }
        if (tipo instanceof TipoArreglo) {
            recolectarEstructuras(((TipoArreglo) tipo).getTipoBase());
        }
    }

    private boolean dependenciasListas(TipoStructura estructura,
            Set<String> emitidas) {
        for (Tipo atributo : estructura.getAtributos().values()) {
            if (atributo instanceof TipoStructura) {
                String nombre = ((TipoStructura) atributo).getNombreStruct();
                if (!nombre.equals(estructura.getNombreStruct())
                        && !emitidas.contains(nombre)) {
                    return false;
                }
            }
        }
        return true;
    }


    public List<TipoStructura> listaEstructuras() {
        List<TipoStructura> pendientes
                = new java.util.ArrayList<>(contexto.getEstructuras().values());
        List<TipoStructura> ordenadas = new java.util.ArrayList<>();
        Set<String> emitidas = new LinkedHashSet<>();
        while (!pendientes.isEmpty()) {
            boolean progreso = false;
            for (int i = pendientes.size() - 1; i >= 0; i--) {
                TipoStructura estructura = pendientes.get(i);
                if (dependenciasListas(estructura, emitidas)) {
                    ordenadas.add(estructura);
                    emitidas.add(estructura.getNombreStruct());
                    pendientes.remove(i);
                    progreso = true;
                }
            }
            if (!progreso) {
                for (TipoStructura estructura : pendientes) {
                    ordenadas.add(estructura);
                    emitidas.add(estructura.getNombreStruct());
                }
                pendientes.clear();
            }
        }
        return ordenadas;
    }


    private Set<String> parametrosDeUnidad(String nombre) {
        List<SimboloParametro> parametros
                = contexto.getCuartetas().parametrosDeFuncion(nombre);
        if (parametros == null || parametros.isEmpty()) {
            return null;
        }
        Set<String> nombreParametros = new LinkedHashSet<>();
        for (SimboloParametro parametro : parametros) {
            nombreParametros.add(parametro.getId());
        }
        return nombreParametros;
    }
}
