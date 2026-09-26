package com.ronaldo.cd3.compiler.api.services.traduccion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Cuarteta;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ContextoTraduccion;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Unidad;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 
 * @author ronaldo
 */
public class TraductorC {

    public String traducir(ListaCuartetas cuartetas) {
        if (cuartetas == null || cuartetas.getCuartetas() == null
                || cuartetas.getCuartetas().isEmpty()) {
            return "";
        }

        ContextoTraduccion ctx = new ContextoTraduccion(cuartetas);
        List<Cuarteta> lista = cuartetas.getCuartetas();
        List<Unidad> unidades = separarUnidades(lista, cuartetas);
        ctx.analizar(unidades);
        ctx.clasificarAmbitosVariables(unidades);
        sembrarArreglosSinCuartetas(ctx);
        corregirDimensionesArreglos(unidades, ctx);

        StringBuilder sb = new StringBuilder();
        encabezado(sb);
        emitirEstructuras(sb, ctx);
        sb.append('\n');

        boolean hayMain = false;
        for (Unidad unidad : unidades) {
            if (ctx.nombreMain(unidad.getNombre())) {
                hayMain = true;
                continue;
            }
            if (unidad.getNombre() != null) {
                sb.append(ctx.tipoCDeFuncion(unidad.getNombre())).append(' ')
                        .append(unidad.getNombre())
                        .append(ctx.firmaParametros(unidad.getNombre()))
                        .append(";\n");
            }
        }
        if (!hayMain) {
            sb.append("int main();\n");
        }
        sb.append('\n');

        emitirVariablesGlobales(sb, ctx);
        if (ctx.usaAyudasCadenas()) {
            emitirAyudasCadenas(sb);
        }
        sb.append('\n');

        for (Unidad unidad : unidades) {
            emitirUnidad(sb, unidad, ctx);
        }
        return sb.toString();
    }

    private void emitirVariablesGlobales(StringBuilder sb,
            ContextoTraduccion ctx) {
        Set<String> escalares = ctx.getEscalares();
        for (String nombre : escalares) {
            if (ctx.esPunteroArreglo(nombre)
                    || ctx.esPunteroEstructura(nombre)) {
                continue;
            }
            if (!ctx.esVariableGlobal(nombre)) {
                continue;
            }
            sb.append(ctx.tipoCDeVariable(nombre)).append(' ')
                    .append(nombre).append(";\n");
        }
        for (Map.Entry<String, Integer> arreglo : ctx.getArreglos().entrySet()) {
            if (!ctx.esVariableGlobal(arreglo.getKey())) {
                continue;
            }
            sb.append(ctx.tipoCDeElementoArreglo(arreglo.getKey()))
                    .append(' ').append(arreglo.getKey())
                    .append('[').append(arreglo.getValue()).append("];\n");
        }
        for (Map.Entry<String, Tipo> puntero
                : ctx.getPunterosArreglo().entrySet()) {
            if (!ctx.esVariableGlobal(puntero.getKey())) {
                continue;
            }
            sb.append(ctx.tipoCDe(puntero.getValue())).append("* ")
                    .append(puntero.getKey()).append(";\n");
        }
        for (Map.Entry<String, TipoStructura> puntero
                : ctx.getPunterosEstructura().entrySet()) {
            if (!ctx.esVariableGlobal(puntero.getKey())) {
                continue;
            }
            sb.append(puntero.getValue().tipoC()).append("* ")
                    .append(puntero.getKey()).append(";\n");
        }
        for (String puntero : ctx.getTemporalesPuntero()) {
            sb.append("double* ").append(puntero).append(";\n");
        }
    }

    private List<Unidad> separarUnidades(List<Cuarteta> lista,
            ListaCuartetas cuartetas) {
        List<Unidad> unidades = new ArrayList<>();
        Unidad actual = new Unidad(null);
        for (Cuarteta cuarteta : lista) {
            if (cuarteta.getOperador() == OperadorCuarteta.ETIQUETA
                    && esEntradaDeFuncion(cuarteta.getArg1(), cuartetas)) {
                unidades.add(actual);
                actual = new Unidad(cuarteta.getArg1());
            } else {
                actual.getCuartetas().add(cuarteta);
            }
        }
        unidades.add(actual);
        return unidades;
    }

    private boolean esEntradaDeFuncion(String etiqueta,
            ListaCuartetas cuartetas) {
        return etiqueta != null
                && !TipoOperando.ETIQUETA_INTERNA.equals(
                        cuartetas.categoriaDe(etiqueta));
    }

    private void encabezado(StringBuilder sb) {
        sb.append("/* Generado automaticamente a partir de cuartetas */\n");
        sb.append("#include <stdio.h>\n");
        sb.append("#include <stdlib.h>\n");
        sb.append("#include <string.h>\n");
        sb.append("#include <stdbool.h>\n");
    }

    private void emitirEstructuras(StringBuilder sb, ContextoTraduccion ctx) {
        for (TipoStructura estructura : ctx.listaEstructuras()) {
            sb.append("struct ").append(estructura.getNombreStruct())
                    .append(" {\n");
            for (Map.Entry<String, Tipo> atributo
                    : estructura.getAtributos().entrySet()) {
                String campo = ctx.getFormateo().tipoCampoEstructura(
                        atributo.getValue(), atributo.getKey());
                if (campo == null) {
                    continue;
                }
                sb.append("\t").append(campo).append(";\n");
            }
            sb.append("};\n");
        }
    }

    private void emitirAyudasCadenas(StringBuilder sb) {
        sb.append("char* cad_concat(char* izquierdo, char* derecho) {\n");
        sb.append("\tif (izquierdo == NULL) izquierdo = \"\";\n");
        sb.append("\tif (derecho == NULL) derecho = \"\";\n");
        sb.append("\tchar* resultado = (char*)malloc("
                + "strlen(izquierdo) + strlen(derecho) + 1);\n");
        sb.append("\tif (resultado == NULL) return NULL;\n");
        sb.append("\tstrcpy(resultado, izquierdo);\n");
        sb.append("\tstrcat(resultado, derecho);\n");
        sb.append("\treturn resultado;\n");
        sb.append("}\n");
        sb.append("char* cad_numero(double valor) {\n");
        sb.append("\tchar buffer[64];\n");
        sb.append("\tsnprintf(buffer, sizeof(buffer), \"%g\", valor);\n");
        sb.append("\tchar* resultado = (char*)malloc(strlen(buffer) + 1);\n");
        sb.append("\tif (resultado == NULL) return NULL;\n");
        sb.append("\tstrcpy(resultado, buffer);\n");
        sb.append("\treturn resultado;\n");
        sb.append("}\n");
        sb.append("int cad_igual(char* izquierdo, char* derecho) {\n");
        sb.append("\tif (izquierdo == NULL || derecho == NULL) "
                + "return izquierdo == derecho;\n");
        sb.append("\treturn strcmp(izquierdo, derecho) == 0;\n");
        sb.append("}\n");
        sb.append("int cad_distinto(char* izquierdo, char* derecho) {\n");
        sb.append("\tif (izquierdo == NULL || derecho == NULL) "
                + "return izquierdo != derecho;\n");
        sb.append("\treturn strcmp(izquierdo, derecho) != 0;\n");
        sb.append("}\n\n");
    }

    private void emitirUnidad(StringBuilder sb, Unidad unidad,
            ContextoTraduccion ctx) {
        if (unidad.getNombre() == null) {
            return;
        }
        ctx.setUnidadActual(unidad.getNombre());
        boolean esMain = ctx.nombreMain(unidad.getNombre());
        boolean esVoid = !esMain
                && ctx.esTipoVoid(ctx.tipoDeFuncionDe(unidad.getNombre()));

        if (esMain) {
            sb.append("int main() {\n");
        } else {
            sb.append(ctx.tipoCDeFuncion(unidad.getNombre())).append(' ')
                    .append(unidad.getNombre())
                    .append(ctx.firmaParametros(unidad.getNombre()))
                    .append(" {\n");
        }

        Set<String> temporalesUnidad = temporalesDeUnidad(
                unidad.getCuartetas(), ctx.getCuartetas());
        for (String temporal : temporalesUnidad) {
            if (ctx.esTemporalPuntero(temporal)) {
                continue;
            }
            if (ctx.contieneArregloNuevo(temporal)) {
                continue;
            }
            if (ctx.esTipoVoid(ctx.tipoDeTemporal(temporal))) {
                continue;
            }
            linea(sb, ctx.tipoCDeTemporal(temporal) + " " + temporal + ";");
        }

        Set<String> localesUnidad = ctx.getLocalesDeUnidad(unidad.getNombre());
        Tipo tipoRetornoFuncion = ctx.tipoDeFuncionDe(unidad.getNombre());
        boolean funcionRetornaArreglo = (tipoRetornoFuncion instanceof TipoArreglo);
        if (localesUnidad != null) {
            for (String nombre : localesUnidad) {
                if (esNombreReservadoOTipo(nombre, ctx)) {
                    continue;
                }
                if (ctx.esPunteroArreglo(nombre)) {
                    linea(sb, ctx.tipoCDe(ctx.tipoPunteroArreglo(nombre))
                            + "* " + nombre + ";");
                } else if (ctx.getCuartetas().getDimensionArreglo(nombre)
                        != null) {
                    String tipoElemento = ctx.tipoCDeElementoArreglo(nombre);
                    String exprTamano = ctx.getCuartetas()
                            .getDimensionArreglo(nombre);
                    linea(sb, tipoElemento + "* " + nombre
                            + " = (" + tipoElemento + "*) calloc("
                            + exprTamano + ", sizeof(" + tipoElemento + "));");
                } else if (ctx.getArreglos().containsKey(nombre)
                        && ctx.esArregloEnUnidad(nombre,
                                unidad.getNombre())) {
                    if (funcionRetornaArreglo) {
                        String tipoElemento = ctx.tipoCDeElementoArreglo(nombre);
                        int tamaño = ctx.tamañoArreglo(nombre);
                        linea(sb, tipoElemento + "* " + nombre
                                + " = (" + tipoElemento + "*) calloc("
                                + tamaño + ", sizeof(" + tipoElemento + "));");
                    } else {
                        linea(sb, ctx.tipoCDeElementoArreglo(nombre) + " "
                                + nombre + "[" + ctx.tamañoArreglo(nombre)
                                + "];");
                    }
                } else {
                    linea(sb, ctx.tipoCDeVariable(nombre) + " "
                            + nombre + ";");
                }
            }
        }
        if (!temporalesUnidad.isEmpty()
                || (localesUnidad != null && !localesUnidad.isEmpty())) {
            linea(sb, "");
        }

        ctx.setUnidadEsVoid(esVoid);
        ctx.setTerminoConRetorno(false);
        for (Cuarteta cuarteta : unidad.getCuartetas()) {
            if (!OperadorCuarteta.RETORNO.equals(cuarteta.getOperador())) {
                ctx.setTerminoConRetorno(false);
            }
            cuarteta.aCodigoC(sb, ctx);
        }
        if (!ctx.terminoConRetorno() && !esVoid) {
            linea(sb, "return 0;");
        }
        sb.append("}\n\n");
    }

    private Set<String> temporalesDeUnidad(List<Cuarteta> cuartetasUnidad,
            ListaCuartetas cuartetas) {
        Set<String> locales = new LinkedHashSet<>();
        for (Cuarteta cuarteta : cuartetasUnidad) {
            agregarTemporalSiAplica(locales, cuarteta.getArg1(), cuartetas);
            agregarTemporalSiAplica(locales, cuarteta.getArg2(), cuartetas);
            agregarTemporalSiAplica(locales, cuarteta.getResultado(), cuartetas);
        }
        return locales;
    }

    private void agregarTemporalSiAplica(Set<String> locales,
            String operando, ListaCuartetas cuartetas) {
        if (operando != null
                && TipoOperando.TEMPORAL.equals(
                        cuartetas.categoriaDe(operando))) {
            locales.add(operando);
        }
    }

    private static final Set<String> RESERVADAS_C
            = Set.of("int", "double", "float", "char", "void",
                    "long", "short", "unsigned", "signed", "struct",
                    "if", "else", "while", "for", "return", "sizeof",
                    "NULL", "break", "continue", "switch", "case",
                    "default", "do", "typedef", "enum", "union",
                    "const", "volatile", "static", "extern", "register",
                    "auto");

    private boolean esNombreReservadoOTipo(String nombre,
            ContextoTraduccion contexto) {
        if (nombre == null) {
            return true;
        }
        if (RESERVADAS_C.contains(nombre)) {
            return true;
        }
        for (String clase : contexto.getEstructuras().keySet()) {
            if (nombre.equals(clase)) {
                return true;
            }
        }
        return false;
    }

    private void linea(StringBuilder sb, String texto) {
        sb.append("\t").append(texto).append('\n');
    }

    private void sembrarArreglosSinCuartetas(ContextoTraduccion ctx) {
        for (Map.Entry<String, Map<String, Tipo>> entrada
                : ctx.getCuartetas()
                        .getVariablesDeclaradasPorUnidad().entrySet()) {
            String unidad = entrada.getKey();
            for (Map.Entry<String, Tipo> var
                    : entrada.getValue().entrySet()) {
                String nombre = var.getKey();
                if (!(var.getValue() instanceof TipoArreglo)) {
                    continue;
                }
                if (ctx.getArreglos().containsKey(nombre)) {
                    continue;
                }
                if (ctx.esPunteroArreglo(nombre)) {
                    continue;
                }
                TipoArreglo tipoArr = (TipoArreglo) var.getValue();
                boolean todasDimensionesDesconocidas = true;
                for (Integer dim : tipoArr.getDimensiones()) {
                    if (dim != null && dim > 0) {
                        todasDimensionesDesconocidas = false;
                        break;
                    }
                }
                if (todasDimensionesDesconocidas) {
                    if (ctx.getCuartetas().getDimensionArreglo(nombre) != null) {
                        continue;
                    }
                    ctx.registrarPunteroArreglo(nombre,
                            tipoArr.getTipoBase());
                    continue;
                }
                int total = tipoArr.getTotalElementos();
                if (total <= 0) {
                    continue;
                }
                ctx.getDimsLocales().put(nombre, tipoArr);
                boolean asignadoDesdeLlamada = false;
                for (Cuarteta c : ctx.getCuartetas().getCuartetas()) {
                    if (c.getOperador() == OperadorCuarteta.ASIGNACION
                            && nombre.equals(c.getResultado())
                            && c.getArg1() != null) {
                        for (Cuarteta c2 : ctx.getCuartetas().getCuartetas()) {
                            if (c2.getOperador() == OperadorCuarteta.LLAMADA
                                    && c.getArg1().equals(c2.getResultado())) {
                                asignadoDesdeLlamada = true;
                                break;
                            }
                        }
                        if (asignadoDesdeLlamada) {
                            break;
                        }
                    }
                }
                if (asignadoDesdeLlamada) {
                    ctx.registrarPunteroArreglo(nombre,
                            tipoArr.getTipoBase());
                    continue;
                }
                boolean esGlobal = ctx.nombreMain(unidad);
                if (!esGlobal) {
                    Set<String> locales = ctx.getLocalesPorUnidad()
                            .get(unidad);
                    if (locales != null && locales.contains(nombre)) {
                        ctx.getArreglos().put(nombre, total);
                    }
                    continue;
                }
                ctx.getArreglos().put(nombre, total);
            }
        }
    }

    private void corregirDimensionesArreglos(List<Unidad> unidades,
            ContextoTraduccion ctx) {
        java.util.Map<String, String> origenTemp = new java.util.LinkedHashMap<>();
        java.util.Map<String, String> tempToUnit = new java.util.LinkedHashMap<>();
        for (Unidad unidad : unidades) {
            if (unidad.getNombre() == null) {
                continue;
            }
            for (Cuarteta c : unidad.getCuartetas()) {
                if (c.getOperador() == OperadorCuarteta.ASIGNACION
                        && c.getArg1() != null
                        && ctx.contieneArregloNuevo(c.getArg1())
                        && c.getResultado() != null) {
                    origenTemp.put(c.getResultado(), c.getArg1());
                    tempToUnit.put(c.getResultado(), unidad.getNombre());
                }
            }
        }
        for (Unidad unidad : unidades) {
            if (unidad.getNombre() == null) {
                continue;
            }
            for (Cuarteta c : unidad.getCuartetas()) {
                if (c.getOperador() == OperadorCuarteta.PUNTERO_INICIO
                        && c.getResultado() != null
                        && ctx.contieneArregloNuevo(c.getResultado())) {
                    Tipo tipo = ctx.tipoDeTemporal(c.getResultado());
                    if (!(tipo instanceof TipoArreglo)) {
                        continue;
                    }
                    TipoArreglo tipoAlloc = (TipoArreglo) tipo;
                    for (java.util.Map.Entry<String, String> entrada
                            : origenTemp.entrySet()) {
                        if (c.getResultado().equals(entrada.getValue())) {
                            String nombreUnidad = tempToUnit.get(
                                    entrada.getKey());
                            String clase = ctx.claseDeUnidad(nombreUnidad);
                            if (clase != null) {
                                ctx.getDimsArreglosResueltas().put(
                                        clase + "." + entrada.getKey(),
                                        tipoAlloc);
                            }
                        }
                    }
                }
            }
        }
    }
}
