package com.ronaldo.cd3.compiler.api.services.traduccion;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Cuarteta;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoPrimitivo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Traductor de cuartetas (codigo de tres direcciones) a codigo C.
 *
 * Modelo de memoria simplificado:
 * - Los tipos se toman en cuenta (int, double, char, char*, int para bool).
 * - El tipo de cada temporal y variable se registra durante la generacion de
 *   cuartetas (ListaCuartetas), por lo que aca se respeta la precedencia de
 *   tipos (por ejemplo double + int -> double).
 * - Los temporales tN son variables (globales o locales segun su unidad).
 * - Los arreglos se declaran como T nombre[tamaño].
 * - Los objetos (PUNTERO_INICIO) se asignan como bloques de memoria.
 * - Los atributos objeto.atributo se aplanan a una variable global objeto_atributo.
 * - Las funciones se generan sin parametros (los valores se comparten via globales).
 *
 * @author ronaldo
 */
public class TraductorC {

    private final Set<String> funciones = new LinkedHashSet<>();
    private final Set<String> temporales = new LinkedHashSet<>();
    private final Set<String> temporalesPuntero = new LinkedHashSet<>();
    private final Set<String> escalares = new LinkedHashSet<>();
    private final Map<String, Integer> arreglos = new LinkedHashMap<>();
    private final Map<String, Tipo> punterosArreglo = new LinkedHashMap<>();
    private final Map<String, TipoStructura> estructuras = new LinkedHashMap<>();
    private final Map<String, String> arreglosNuevos = new LinkedHashMap<>();
    private boolean usaConcatenacion = false;
    private boolean usaComparacionCadenas = false;

    private ListaCuartetas cuartetas;

    public String traducir(ListaCuartetas cuartetas) {
        if (cuartetas == null || cuartetas.getCuartetas() == null
                || cuartetas.getCuartetas().isEmpty()) {
            return "";
        }
        this.cuartetas = cuartetas;
        List<Cuarteta> lista = cuartetas.getCuartetas();
        analizar(lista);
        List<Unidad> unidades = separarUnidades(lista);

        StringBuilder sb = new StringBuilder();
        encabezado(sb);

        boolean hayMain = false;
        for (Unidad unidad : unidades) {
            if (nombreMain(unidad.nombre)) {
                hayMain = true;
                continue;
            }
            if (unidad.nombre != null) {
                sb.append(tipoCDeFuncion(unidad.nombre)).append(' ')
                        .append(unidad.nombre).append("();\n");
            }
        }
        if (!hayMain) {
            sb.append("int main(void);\n");
        }

        emitirEstructuras(sb, listaEstructuras());
        sb.append('\n');

        for (String nombre : escalares) {
            if (punterosArreglo.containsKey(nombre)) {
                continue;
            }
            sb.append(tipoCDeVariable(nombre)).append(' ').append(nombre).append(";\n");
        }
        for (Map.Entry<String, Integer> arreglo : arreglos.entrySet()) {
            sb.append(tipoCDeElementoArreglo(arreglo.getKey()))
                    .append(' ').append(arreglo.getKey())
                    .append('[').append(arreglo.getValue()).append("];\n");
        }
        for (Map.Entry<String, Tipo> puntero : punterosArreglo.entrySet()) {
            sb.append(tipoCDe(puntero.getValue())).append("* ")
                    .append(puntero.getKey()).append(";\n");
        }
        for (String puntero : temporalesPuntero) {
            sb.append("double* ").append(puntero).append(";\n");
        }
        if (usaConcatenacion || usaComparacionCadenas) {
            emitirAyudasCadenas(sb);
        }

        sb.append('\n');
        for (Unidad unidad : unidades) {
            emitirUnidad(sb, unidad);
        }
        return sb.toString();
    }

    private void analizar(List<Cuarteta> lista) {
        for (Cuarteta cuarteta : lista) {
            if (cuarteta.getOperador() == OperadorCuarteta.LLAMADA) {
                funciones.add(cuarteta.getArg1());
            }
            if (cuarteta.getOperador() == OperadorCuarteta.ETIQUETA
                    && esEntradaDeFuncion(cuarteta.getArg1())) {
                funciones.add(cuarteta.getArg1());
            }
            if (cuarteta.getOperador() == OperadorCuarteta.SUMA
                    && cuarteta.getResultado() != null
                    && esDeTipo(tipoDeOperando(cuarteta.getResultado()), TipoDato.CADENA)) {
                usaConcatenacion = true;
            }
            if (cuarteta.getOperador() == OperadorCuarteta.IGUAL
                    || cuarteta.getOperador() == OperadorCuarteta.DISTINTO) {
                Tipo izquierdo = tipoDeOperando(cuarteta.getArg1());
                Tipo derecho = tipoDeOperando(cuarteta.getArg2());
                if (esDeTipo(izquierdo, TipoDato.CADENA)
                        || esDeTipo(derecho, TipoDato.CADENA)) {
                    usaComparacionCadenas = true;
                }
            }
            if (cuarteta.getOperador() == OperadorCuarteta.PUNTERO_INICIO
                    && cuarteta.getArg1() != null
                    && cuarteta.getResultado() != null) {
                arreglosNuevos.put(cuarteta.getResultado(), cuarteta.getArg2());
                analizarOperando(cuarteta.getArg2());
                continue;
            }
            analizarOperando(cuarteta.getArg1());
            analizarOperando(cuarteta.getArg2());
            analizarOperando(cuarteta.getResultado());
        }
        for (Tipo tipo : cuartetas.tiposRegistrados()) {
            recolectarEstructuras(tipo);
        }
        for (Tipo puntero : punterosArreglo.values()) {
            recolectarEstructuras(puntero);
        }
    }

    private Set<String> temporalesDeUnidad(List<Cuarteta> cuartetasUnidad) {
        Set<String> locales = new LinkedHashSet<>();
        for (Cuarteta cuarteta : cuartetasUnidad) {
            agregarTemporalSiAplica(locales, cuarteta.getArg1());
            agregarTemporalSiAplica(locales, cuarteta.getArg2());
            agregarTemporalSiAplica(locales, cuarteta.getResultado());
        }
        return locales;
    }

    private void agregarTemporalSiAplica(Set<String> locales, String operando) {
        if (operando != null && esCategoria(operando, TipoOperando.TEMPORAL)) {
            locales.add(operando);
        }
    }

    private void analizarOperando(String operando) {
        if (operando == null) {
            return;
        }
        if (esCategoria(operando, TipoOperando.TEMPORAL)) {
            temporales.add(operando);
            return;
        }
        if (esCategoria(operando, TipoOperando.ETIQUETA_INTERNA)
                || esConstante(operando)) {
            return;
        }
        if (funciones.contains(operando)) {
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
            if (esCategoria(base, TipoOperando.TEMPORAL)) {
                temporalesPuntero.add(base);
            } else if (esNombreDeVariable(base)
                    && !funciones.contains(base)) {
                Integer maximo = maximoIndiceNumerico(operando);
                int tamaño = (maximo != null) ? (maximo + 1) : 100;
                arreglos.put(base, Math.max(arreglos.getOrDefault(base, 0), tamaño));
                escalares.remove(base);
            }
            return;
        }
        if (esNombreDeVariable(operando)) {
            if (esArregloPasadoPorReferencia(operando)) {
                registrarPunteroArreglo(operando);
                return;
            }
            if (!(tipoDeVariableDe(operando) instanceof TipoArreglo)
                    && !arreglos.containsKey(operando)) {
                escalares.add(operando);
            }
        }
    }

    private boolean esArregloPasadoPorReferencia(String nombre) {
        if (nombre == null) {
            return false;
        }
        return cuartetas.tipoDeVariable(nombre) instanceof TipoArreglo
                && cuartetas.tipoDeArreglo(nombre) == null;
    }

    private boolean esNuevoArregloHaciaVariable(String origen, String destino) {
        if (origen == null || destino == null) {
            return false;
        }
        Tipo tipo = cuartetas.tipoDeVariable(destino);
        return arreglosNuevos.containsKey(origen)
                && tipo instanceof TipoArreglo
                && cuartetas.tipoDeArreglo(destino) != null;
    }

    private String tipoElementoDeArreglo(String nombre) {
        Tipo elemento = cuartetas.tipoDeArreglo(nombre);
        return tipoCValido(elemento) ? elemento.tipoC() : "int";
    }

    private String tipoCampoEstructura(Tipo tipo, String nombre) {
        if (tipo instanceof TipoArreglo) {
            TipoArreglo arreglo = (TipoArreglo) tipo;
            String tipoElemento = tipoCValido(arreglo.getTipoBase())
                    ? arreglo.getTipoBase().tipoC() : "int";
            return tipoElemento + " " + nombre + "[" + primerTamaño(arreglo) + "]";
        }
        if (!tipoCValido(tipo)) {
            return null;
        }
        return tipo.tipoC() + " " + nombre;
    }

    private int primerTamaño(TipoArreglo arreglo) {
        List<Integer> dimensiones = arreglo.getDimensiones();
        if (dimensiones != null && !dimensiones.isEmpty()) {
            Integer ext = dimensiones.get(0);
            if (ext != null && ext > 0) {
                return ext;
            }
        }
        return 100;
    }

    private void registrarPunteroArreglo(String nombre) {
        Tipo tipo = cuartetas.tipoDeVariable(nombre);
        if (tipo instanceof TipoArreglo) {
            punterosArreglo.put(nombre, ((TipoArreglo) tipo).getTipoBase());
        }
        escalares.remove(nombre);
        arreglos.remove(nombre);
    }

    private void registrarEscalarRaiz(String nombre) {
        if (esCategoria(nombre, TipoOperando.TEMPORAL)
                || funciones.contains(nombre)) {
            return;
        }
        if (esArregloPasadoPorReferencia(nombre)) {
            registrarPunteroArreglo(nombre);
            return;
        }
        if (esNombreDeVariable(nombre)
                && !(tipoDeVariableDe(nombre) instanceof TipoArreglo)) {
            escalares.add(nombre);
        }
    }

    private String raizIdentificador(String operando) {
        if (operando == null || operando.isEmpty()) {
            return null;
        }
        String base = operando;
        int corchete = operando.indexOf('[');
        if (corchete >= 0) {
            base = operando.substring(0, corchete);
        }
        int punto = base.indexOf('.');
        if (punto >= 0) {
            base = base.substring(0, punto);
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

    private List<Unidad> separarUnidades(List<Cuarteta> lista) {
        List<Unidad> unidades = new ArrayList<>();
        Unidad actual = new Unidad(null);
        for (Cuarteta cuarteta : lista) {
            if (cuarteta.getOperador() == OperadorCuarteta.ETIQUETA
                    && esEntradaDeFuncion(cuarteta.getArg1())) {
                unidades.add(actual);
                actual = new Unidad(cuarteta.getArg1());
            } else {
                actual.cuartetas.add(cuarteta);
            }
        }
        unidades.add(actual);
        return unidades;
    }

    private boolean esEntradaDeFuncion(String etiqueta) {
        return etiqueta != null
                && !esCategoria(etiqueta, TipoOperando.ETIQUETA_INTERNA);
    }

    private boolean nombreMain(String nombre) {
        return nombre != null && nombre.equals("main");
    }

    private void encabezado(StringBuilder sb) {
        sb.append("/* Generado automaticamente a partir de cuartetas */\n");
        sb.append("#include <stdio.h>\n");
        sb.append("#include <stdlib.h>\n");
        sb.append("#include <string.h>\n");
        sb.append("#include <math.h>\n\n");
    }

    private void recolectarEstructuras(Tipo tipo) {
        if (tipo == null) {
            return;
        }
        if (tipo instanceof TipoStructura) {
            TipoStructura estructura = (TipoStructura) tipo;
            if (!estructuras.containsKey(estructura.getNombreStruct())) {
                estructuras.put(estructura.getNombreStruct(), estructura);
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

    private List<TipoStructura> listaEstructuras() {
        List<TipoStructura> pendientes = new ArrayList<>(estructuras.values());
        List<TipoStructura> ordenadas = new ArrayList<>();
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

    private boolean dependenciasListas(TipoStructura estructura, Set<String> emitidas) {
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

    private void emitirEstructuras(StringBuilder sb, List<TipoStructura> estructuras) {
        for (TipoStructura estructura : estructuras) {
            sb.append("struct ").append(estructura.getNombreStruct()).append(" {\n");
            for (Map.Entry<String, Tipo> atributo : estructura.getAtributos().entrySet()) {
                String campo = tipoCampoEstructura(atributo.getValue(), atributo.getKey());
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
        sb.append("\tchar* resultado = (char*)malloc(strlen(izquierdo) + strlen(derecho) + 1);\n");
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
        sb.append("\tif (izquierdo == NULL || derecho == NULL) return izquierdo == derecho;\n");
        sb.append("\treturn strcmp(izquierdo, derecho) == 0;\n");
        sb.append("}\n");
        sb.append("int cad_distinto(char* izquierdo, char* derecho) {\n");
        sb.append("\tif (izquierdo == NULL || derecho == NULL) return izquierdo != derecho;\n");
        sb.append("\treturn strcmp(izquierdo, derecho) != 0;\n");
        sb.append("}\n\n");
    }

    private void emitirUnidad(StringBuilder sb, Unidad unidad) {
        if (unidad.nombre == null) {
            return;
        }
        boolean esMain = nombreMain(unidad.nombre);
        boolean esVoid = !esMain && esTipoVoid(cuartetas.tipoDeFuncion(unidad.nombre));
        if (esMain) {
            sb.append("int main() {\n");
        } else {
            sb.append(tipoCDeFuncion(unidad.nombre)).append(' ')
                    .append(unidad.nombre).append("() {\n");
        }

        boolean terminoConRetorno = false;
        for (String temporal : temporalesDeUnidad(unidad.cuartetas)) {
            if (temporalesPuntero.contains(temporal)) {
                continue;
            }
            if (arreglosNuevos.containsKey(temporal)) {
                continue;
            }
            if (esTipoVoid(cuartetas.tipoDeTemporal(temporal))) {
                continue;
            }
            linea(sb, tipoCDeTemporal(temporal) + " " + temporal + ";");
        }
        if (!temporalesDeUnidad(unidad.cuartetas).isEmpty()) {
            linea(sb, "");
        }
        for (Cuarteta cuarteta : unidad.cuartetas) {
            if (cuarteta.getOperador() == OperadorCuarteta.PARAMETRO) {
                continue;
            }
            if (cuarteta.getOperador() == OperadorCuarteta.LLAMADA) {
                String resultado = normalizar(cuarteta.getResultado());
                String llamada = cuarteta.getArg1() + "()";
                Tipo tipoFuncion = cuartetas.tipoDeFuncion(cuarteta.getArg1());
                boolean esLlamadaVoid = esTipoVoid(tipoFuncion) || tipoFuncion == null;
                if (resultado != null && !esLlamadaVoid) {
                    linea(sb, resultado + " = " + llamada + ";");
                } else {
                    linea(sb, llamada + ";");
                }
                terminoConRetorno = false;
                continue;
            }
            if (cuarteta.getOperador() == OperadorCuarteta.RETORNO) {
                if (terminoConRetorno) {
                    continue;
                }
                String dir = normalizar(cuarteta.getArg1());
                if (dir != null) {
                    linea(sb, "return " + dir + ";");
                } else if (esVoid) {
                    linea(sb, "return;");
                } else {
                    linea(sb, "return 0;");
                }
                terminoConRetorno = true;
                continue;
            }
            terminoConRetorno = false;
            String linea = emitirCuarteta(cuarteta);
            if (linea != null) {
                linea(sb, linea);
            }
        }
        if (!terminoConRetorno && !esVoid) {
            linea(sb, "return 0;");
        }
        sb.append("}\n\n");
    }

    private String emitirCuarteta(Cuarteta cuarteta) {
        String arg1 = normalizar(cuarteta.getArg1());
        String arg2 = normalizar(cuarteta.getArg2());
        String resultado = normalizar(cuarteta.getResultado());

        switch (cuarteta.getOperador()) {
            case ASIGNACION:
                if (resultado != null) {
                    if (esNuevoArregloHaciaVariable(arg1, resultado)) {
                        return "memset(" + resultado + ", 0, "
                                + (arreglosNuevos.get(arg1) != null
                                ? arreglosNuevos.get(arg1) : "100")
                                + " * sizeof(" + tipoElementoDeArreglo(resultado) + "));";
                    }
                    return (arg1 != null) ? (resultado + " = " + arg1 + ";")
                            : (resultado + " = 0;");
                }
                return null;
            case SUMA:
                if (resultado != null
                        && tipoDeOperando(cuarteta.getResultado()) != null
                        && esDeTipo(tipoDeOperando(cuarteta.getResultado()), TipoDato.CADENA)) {
                    return resultado + " = cad_concat("
                            + operandoParaCadena(arg1) + ", "
                            + operandoParaCadena(arg2) + ");";
                }
                return resultado + " = " + arg1 + " + " + arg2 + ";";
            case RESTA:
                return resultado + " = " + arg1 + " - " + arg2 + ";";
            case MULTIPLICACION:
                return resultado + " = " + arg1 + " * " + arg2 + ";";
            case DIVISION:
                return resultado + " = " + arg1 + " / " + arg2 + ";";
            case MODULO:
                return (esEntero(arg1) && esEntero(arg2))
                        ? (resultado + " = " + arg1 + " % " + arg2 + ";")
                        : (resultado + " = fmod(" + arg1 + ", " + arg2 + ");");
            case MENOR_Q:
                return resultado + " = " + arg1 + " < " + arg2 + ";";
            case MENOR_EQ_Q:
                return resultado + " = " + arg1 + " <= " + arg2 + ";";
            case MAYOR_Q:
                return resultado + " = " + arg1 + " > " + arg2 + ";";
            case MAYOR_EQ_Q:
                return resultado + " = " + arg1 + " >= " + arg2 + ";";
            case IGUAL:
                if (usaComparacionCadenasPara(arg1, arg2)) {
                    return resultado + " = cad_igual(" + arg1 + ", " + arg2 + ");";
                }
                return resultado + " = " + arg1 + " == " + arg2 + ";";
            case DISTINTO:
                if (usaComparacionCadenasPara(arg1, arg2)) {
                    return resultado + " = cad_distinto(" + arg1 + ", " + arg2 + ");";
                }
                return resultado + " = " + arg1 + " != " + arg2 + ";";
            case AND:
                return resultado + " = " + arg1 + " && " + arg2 + ";";
            case OR:
                return resultado + " = " + arg1 + " || " + arg2 + ";";
            case NEGATIVO_UNARIO:
                return resultado + " = -" + arg1 + ";";
            case NOT:
                return resultado + " = !" + arg1 + ";";
            case INCREMENTO:
                return arg1 + "++;";
            case DECREMENTO:
                return arg1 + "--;";
            case ETIQUETA:
                return arg1 + ":;";
            case GOTO:
                return "goto " + arg1 + ";";
            case IF_FALSO:
                return "if (!(" + arg1 + ")) goto " + arg2 + ";";
            case IF_VERDADERO:
                return "if (" + arg1 + ") goto " + arg2 + ";";
            case IMPRIMIR:
                return emitirImpresion(arg1, cuarteta.getResultado());
            case LEER:
                return emitirLectura(resultado);
            case PUNTERO_INICIO:
                if (resultado != null
                        && arreglosNuevos.containsKey(cuarteta.getResultado())) {
                    return null;
                }
                return "memset(&" + resultado + ", 0, sizeof(" + resultado + "));";
            case PUNTERO_FINAL:
                return null;
            case COPIAR:
                return "memcpy(" + resultado + ", " + arg1
                        + ", sizeof(" + resultado + "));";
            case ACCESO_INDICE:
                return resultado + " = " + arg1 + "[(int)" + arg2 + "];";
            case ACCESO_ATRIBUTO:
                if (arg1 != null && arg2 != null && arg1.indexOf('[') < 0) {
                    return resultado + " = " + arg1 + "_" + arg2 + ";";
                }
                return (arg1 != null) ? (resultado + " = " + arg1 + ";")
                        : (resultado + " = 0;");
            default:
                return null;
        }
    }

    private String emitirImpresion(String arg1, String resultado) {
        boolean conSalto = "true".equalsIgnoreCase(resultado);
        String salto = conSalto ? "\\n" : "";
        if (arg1 == null) {
            return "printf(\"" + salto + "\");";
        }
        boolean esCadena = esCategoria(arg1, TipoOperando.CONSTANTE_CADENA);
        Tipo tipo = tipoDeOperando(arg1);
        String formato;
        if (esCadena || esDeTipo(tipo, TipoDato.CADENA)) {
            formato = "%s";
        } else if (esDeTipo(tipo, TipoDato.CHAR)) {
            formato = "%c";
        } else if (esDeTipo(tipo, TipoDato.ENTERO)
                || esDeTipo(tipo, TipoDato.BOOLEAN)) {
            formato = "%d";
        } else {
            formato = "%g";
        }
        return "printf(\"" + formato + salto + "\", " + arg1 + ");";
    }

    private String emitirLectura(String resultado) {
        if (resultado == null) {
            return ";";
        }
        Tipo tipo = tipoDeVariableDe(resultado);
        if (esDeTipo(tipo, TipoDato.DECIMAL)) {
            return "scanf(\"%lf\", &" + resultado + ");";
        }
        if (esDeTipo(tipo, TipoDato.CHAR)) {
            return "scanf(\" %c\", &" + resultado + ");";
        }
        if (esDeTipo(tipo, TipoDato.CADENA)) {
            return "scanf(\"%s\", " + resultado + ");";
        }
        return "scanf(\"%d\", &" + resultado + ");";
    }

    private String operandoParaCadena(String operando) {
        if (operando == null) {
            return "\"\"";
        }
        Tipo tipo = tipoDeOperando(operando);
        if (tipo != null && tipo.esNumerico()) {
            return "cad_numero(" + operando + ")";
        }
        return operando;
    }

    private boolean usaComparacionCadenasPara(String izquierdo, String derecho) {
        return usaComparacionCadenas
                && (esDeTipo(tipoDeOperando(izquierdo), TipoDato.CADENA)
                || esDeTipo(tipoDeOperando(derecho), TipoDato.CADENA));
    }

    private boolean esEntero(String operando) {
        if (operando == null) {
            return false;
        }
        TipoOperando cat = categoriaDe(operando);
        if (cat == TipoOperando.CONSTANTE_ENTERA) {
            return true;
        }
        return esDeTipo(tipoDeOperando(operando), TipoDato.ENTERO);
    }

    private Tipo tipoDeOperando(String operando) {
        if (operando == null) {
            return null;
        }
        TipoOperando cat = categoriaDe(operando);
        if (cat == TipoOperando.TEMPORAL) {
            return cuartetas.tipoDeTemporal(operando);
        }
        if (cat == TipoOperando.CONSTANTE_CADENA) {
            return primitivo(TipoDato.CADENA);
        }
        if (cat == TipoOperando.CONSTANTE_CARACTER) {
            return primitivo(TipoDato.CHAR);
        }
        if (cat == TipoOperando.CONSTANTE_ENTERA) {
            return primitivo(TipoDato.ENTERO);
        }
        if (cat == TipoOperando.CONSTANTE_DECIMAL) {
            return primitivo(TipoDato.DECIMAL);
        }
        if (cat == TipoOperando.BOOLEANO_VERDADERO
                || cat == TipoOperando.BOOLEANO_FALSO) {
            return primitivo(TipoDato.BOOLEAN);
        }
        if (cat == TipoOperando.NULO) {
            return primitivo(TipoDato.NULO);
        }
        String base = baseIndice(operando);
        if (base != null) {
            Tipo elemento = tipoDeArregloDe(base);
            if (elemento != null) {
                return elemento;
            }
            Tipo tipoBase = tipoDeVariableDe(base);
            if (tipoBase instanceof TipoArreglo) {
                return ((TipoArreglo) tipoBase).getTipoBase();
            }
            return tipoBase;
        }
        return tipoDeVariableDe(operando);
    }

    private Tipo primitivo(TipoDato dato) {
        return new TipoPrimitivo(dato);
    }

    private String tipoCDeTemporal(String nombre) {
        Tipo tipo = cuartetas.tipoDeTemporal(nombre);
        return tipoCDe(tipo);
    }

    private String tipoCDeVariable(String nombre) {
        return tipoCDe(tipoDeVariableDe(nombre));
    }

    private String tipoCDeElementoArreglo(String nombre) {
        return tipoCDe(tipoDeArregloDe(nombre));
    }

    private String tipoCDeFuncion(String nombre) {
        return tipoCDe(cuartetas.tipoDeFuncion(nombre));
    }

    private String tipoCDe(Tipo tipo) {
        if (tipo instanceof TipoArreglo) {
            return tipo.tipoC();
        }
        if (tipo instanceof TipoStructura) {
            return tipo.tipoC();
        }
        return tipoCValido(tipo) ? tipo.tipoC() : "double";
    }

    private boolean tipoCValido(Tipo tipo) {
        return tipo != null && tipo.tipoC() != null && !"error".equals(tipo.tipoC());
    }

    private Tipo tipoDeVariableDe(String nombre) {
        Tipo tipo = cuartetas.tipoDeVariable(nombre);
        if (tipo == null) {
            tipo = cuartetas.tipoDeVariable(nombre.replace('_', '.'));
        }
        return tipo;
    }

    private Tipo tipoDeArregloDe(String nombre) {
        Tipo tipo = cuartetas.tipoDeArreglo(nombre);
        if (tipo == null) {
            tipo = cuartetas.tipoDeArreglo(nombre.replace('_', '.'));
        }
        return tipo;
    }

    private boolean esDeTipo(Tipo tipo, TipoDato dato) {
        return tipo != null && tipo.getTipoDato() == dato;
    }

    private boolean esTipoVoid(Tipo tipo) {
        return tipo != null && tipo.getTipoDato() == TipoDato.VOID;
    }

    private String normalizar(String operando) {
        if (operando == null) {
            return null;
        }
        TipoOperando cat = categoriaDe(operando);
        if (cat != null && cat != TipoOperando.FUNCION
                && cat != TipoOperando.VARIABLE) {
            if (cat == TipoOperando.BOOLEANO_VERDADERO) {
                return "1";
            }
            if (cat == TipoOperando.BOOLEANO_FALSO
                    || cat == TipoOperando.NULO) {
                return "0";
            }
            return operando;
        }
        return operando;
    }

    private TipoOperando categoriaDe(String operando) {
        TipoOperando cat = cuartetas.categoriaDe(operando);
        if (cat != null) {
            return cat;
        }
        return null;
    }

    private boolean esCategoria(String operando, TipoOperando esperado) {
        return categoriaDe(operando) == esperado;
    }

    private boolean esConstante(String operando) {
        TipoOperando cat = categoriaDe(operando);
        return cat == TipoOperando.CONSTANTE_ENTERA
                || cat == TipoOperando.CONSTANTE_DECIMAL
                || cat == TipoOperando.CONSTANTE_CADENA
                || cat == TipoOperando.CONSTANTE_CARACTER
                || cat == TipoOperando.BOOLEANO_VERDADERO
                || cat == TipoOperando.BOOLEANO_FALSO
                || cat == TipoOperando.NULO;
    }

    private boolean esNombreDeVariable(String operando) {
        if (operando == null) {
            return false;
        }
        TipoOperando cat = categoriaDe(operando);
        return cat == null || cat == TipoOperando.VARIABLE;
    }

    private String baseIndice(String operando) {
        int corchete = operando.indexOf('[');
        if (corchete < 0) {
            return null;
        }
        return operando.substring(0, corchete);
    }

    private void linea(StringBuilder sb, String texto) {
        sb.append("\t").append(texto).append('\n');
    }
}