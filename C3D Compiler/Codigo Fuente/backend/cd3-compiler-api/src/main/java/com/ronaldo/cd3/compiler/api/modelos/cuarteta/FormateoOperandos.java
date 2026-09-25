package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Formateo de operandos y tipos para la emision de codigo C: normalizacion
 * de constantes, conversion de accesos aplanados ("base.campo") a los
 * operadores "." / "->" de C, el prefijo this-> de los atributos de la
 * unidad actual, y resolucion de tipos de operandos/variables/arreglos.
 *
 * @author ronaldo
 */
public class FormateoOperandos {

    private final ContextoTraduccion contexto;

    public FormateoOperandos(ContextoTraduccion contexto) {
        this.contexto = contexto;
    }

    public String formatearOperando(String operando) {
        String resultado = dirThisUnidad(normalizar(operando));
        return aplanarSiPunteroArreglo(resultado);
    }

    public String normalizar(String operando) {
        if (operando == null) {
            return null;
        }
        TipoOperando cat = contexto.categoriaDe(operando);
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
        return formatearAccesoCampos(operando);
    }

    private String formatearAccesoCampos(String operando) {
        if (operando == null || operando.indexOf('.') < 0) {
            return operando;
        }
        List<String> segmentos = separarSegmentosPunto(operando);

        StringBuilder resultado = new StringBuilder(segmentos.get(0));
        Tipo tipoActual = tipoTrasIndices(
                tipoBase(segmentos.get(0)), segmentos.get(0));

        for (int i = 1; i < segmentos.size(); i++) {
            String segmento = segmentos.get(i);
            boolean esPuntero;
            if (i == 1) {
                esPuntero = esPrimerEnlacePuntero(segmentos.get(0));
            } else {
                esPuntero = contexto.esObjetoPorReferencia(tipoActual);
            }
            resultado.append(esPuntero ? "->" : ".").append(segmento);

            Tipo tipoCampo = (tipoActual instanceof TipoStructura)
                    ? ((TipoStructura) tipoActual)
                            .getTipoAtributo(nombreSinIndices(segmento))
                    : null;
            tipoActual = tipoTrasIndices(tipoCampo, segmento);
        }
        return resultado.toString();
    }

    private List<String> separarSegmentosPunto(String operando) {
        List<String> segmentos = new ArrayList<>();
        int profundidad = 0;
        int inicio = 0;
        for (int i = 0; i < operando.length(); i++) {
            char c = operando.charAt(i);
            if (c == '[') {
                profundidad++;
            } else if (c == ']') {
                profundidad--;
            } else if (c == '.' && profundidad == 0) {
                segmentos.add(operando.substring(inicio, i));
                inicio = i + 1;
            }
        }
        segmentos.add(operando.substring(inicio));
        return segmentos;
    }

    private String nombreSinIndices(String segmento) {
        int corchete = segmento.indexOf('[');
        return (corchete >= 0) ? segmento.substring(0, corchete) : segmento;
    }

    private boolean esPrimerEnlacePuntero(String raiz) {
        String nombre = nombreSinIndices(raiz);
        if ("this".equals(nombre) || nombre.startsWith("this->")) {
            return true;
        }
        if (contexto.getUnidadActual() != null) {
            List<SimboloParametro> params = contexto.getCuartetas()
                    .parametrosDeFuncion(contexto.getUnidadActual());
            if (params != null) {
                for (SimboloParametro p : params) {
                    if (nombre.equals(p.getId())
                            || nombre.equals(normalizar(p.getId()))) {
                        if (p.getTipo() instanceof TipoStructura) {
                            return true;
                        }
                        return contexto.esObjetoPorReferencia(p.getTipo());
                    }
                }
            }
        }
        return contexto.esPunteroEstructura(nombre);
    }

    private Tipo tipoBase(String segmento) {
        String nombre = nombreSinIndices(segmento);
        if (contexto.esCategoria(nombre, TipoOperando.TEMPORAL)) {
            return contexto.getCuartetas().tipoDeTemporal(nombre);
        }
        return contexto.tipoDeVariableDe(nombre);
    }

    private Tipo tipoTrasIndices(Tipo tipo, String segmento) {
        Tipo actual = tipo;
        int i = 0;
        while (i < segmento.length() && actual != null) {
            if (segmento.charAt(i) == '[' && actual instanceof TipoArreglo) {
                actual = ((TipoArreglo) actual).getTipoBase();
            }
            i++;
        }
        return actual;
    }

    private String dirThisUnidad(String operando) {
        if (operando == null || contexto.getUnidadActual() == null
                || operando.startsWith("this->")) {
            return operando;
        }
        Set<String> campos =
                contexto.camposDeUnidad(contexto.getUnidadActual());
        if (campos == null) {
            return operando;
        }
        String base = contexto.getAnalisis().raizIdentificador(operando);
        if (base != null && campos.contains(base)) {
            return "this->" + prefijarCamposEnIndices(operando, campos);
        }
        return operando;
    }

    private String prefijarCamposEnIndices(String operando, Set<String> campos) {
        StringBuilder sb = new StringBuilder();
        int inicio = 0;
        boolean dentroCorchetes = false;
        for (int i = 0; i < operando.length(); i++) {
            char c = operando.charAt(i);
            if (c == '[') {
                sb.append(operando, inicio, i + 1);
                inicio = i + 1;
                dentroCorchetes = true;
            } else if (c == ']' && dentroCorchetes) {
                String contenido = operando.substring(inicio, i);
                if (campos.contains(contenido)) {
                    sb.append("this->");
                }
                sb.append(operando, inicio, i + 1);
                inicio = i + 1;
                dentroCorchetes = false;
            }
        }
        sb.append(operando, inicio, operando.length());
        return sb.toString();
    }

    public boolean tamanioConocido(TipoArreglo arreglo) {
        List<Integer> dimensiones = arreglo.getDimensiones();
        if (dimensiones == null || dimensiones.isEmpty()) {
            return false;
        }
        for (Integer d : dimensiones) {
            if (d == null || d <= 0) {
                return false;
            }
        }
        return true;
    }

    public String aplanarSiPunteroArreglo(String operando) {
        if (operando == null || operando.indexOf('[') < 0) {
            return operando;
        }
        int primerCorchete = operando.indexOf('[');
        String base = operando.substring(0, primerCorchete);
        String baseLimpio = base.startsWith("this->")
                ? base.substring(6) : base;
        Tipo tipoCampo = tipoCampoArreglo(baseLimpio);
        Tipo tipoPuntero = contexto.getPunterosArreglo().get(baseLimpio);
        Tipo tipoElemento = tipoCampo != null ? tipoCampo : tipoPuntero;
        if (!(tipoElemento instanceof TipoArreglo)) {
            return operando;
        }
        List<String> indices = extraerIndices(operando.substring(primerCorchete));
        List<Integer> dims = ((TipoArreglo) tipoElemento).getDimensiones();
        if (dims.isEmpty() || indices.size() > dims.size()) {
            return operando;
        }
        StringBuilder plano = new StringBuilder();
        for (int i = 0; i < indices.size(); i++) {
            if (i > 0) {
                plano.append(" + ");
            }
            if (i < dims.size()) {
                int stride = 1;
                for (int j = i + 1; j < dims.size(); j++) {
                    stride *= dims.get(j);
                }
                if (indices.get(i).contains("*")) {
                    plano.append(indices.get(i));
                } else if (stride > 1) {
                    plano.append("(").append(indices.get(i))
                            .append(" * ").append(stride).append(")");
                } else {
                    plano.append(indices.get(i));
                }
            } else {
                plano.append(indices.get(i));
            }
        }
        return base + "[(int)(" + plano + ")]";
    }

    private Tipo tipoCampoArreglo(String nombreCampo) {
        String unidad = contexto.getUnidadActual();
        if (unidad == null) {
            return null;
        }
        String clase = contexto.claseDeUnidad(unidad);
        if (clase == null) {
            return null;
        }
        String clave = clase + "." + nombreCampo;
        TipoArreglo resuelto = contexto.getDimsArreglosResueltas().get(clave);
        if (resuelto != null) {
            return resuelto;
        }
        TipoStructura estructura = contexto.getEstructuras().get(clase);
        if (estructura == null) {
            return null;
        }
        Tipo tipoCampo = estructura.getTipoAtributo(nombreCampo);
        return (tipoCampo instanceof TipoArreglo) ? tipoCampo : null;
    }

    private List<String> extraerIndices(String subcadena) {
        List<String> indices = new ArrayList<>();
        int profundidad = 0;
        int inicio = -1;
        for (int i = 0; i < subcadena.length(); i++) {
            char c = subcadena.charAt(i);
            if (c == '[' && profundidad == 0) {
                inicio = i + 1;
                profundidad = 1;
            } else if (c == '[') {
                profundidad++;
            } else if (c == ']') {
                profundidad--;
                if (profundidad == 0 && inicio >= 0) {
                    indices.add(subcadena.substring(inicio, i));
                    inicio = -1;
                }
            }
        }
        return indices;
    }

    public Tipo tipoDeOperando(String operando) {
        if (operando == null) {
            return null;
        }
        TipoOperando cat = contexto.categoriaDe(operando);
        if (cat == TipoOperando.TEMPORAL) {
            return contexto.getCuartetas().tipoDeTemporal(operando);
        }
        if (cat == TipoOperando.CONSTANTE_CADENA) {
            return contexto.primitivo(TipoDato.CADENA);
        }
        if (cat == TipoOperando.CONSTANTE_CARACTER) {
            return contexto.primitivo(TipoDato.CHAR);
        }
        if (cat == TipoOperando.CONSTANTE_ENTERA) {
            return contexto.primitivo(TipoDato.ENTERO);
        }
        if (cat == TipoOperando.CONSTANTE_DECIMAL) {
            return contexto.primitivo(TipoDato.DECIMAL);
        }
        if (cat == TipoOperando.BOOLEANO_VERDADERO
                || cat == TipoOperando.BOOLEANO_FALSO) {
            return contexto.primitivo(TipoDato.BOOLEAN);
        }
        if (cat == TipoOperando.NULO) {
            return contexto.primitivo(TipoDato.NULO);
        }
        String base = contexto.baseIndice(operando);
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

    public Tipo tipoDeVariableDe(String nombre) {
        String limpio = contexto.sinPrefijoThis(nombre);
        if (contexto.getUnidadActual() != null) {
            Map<String, Tipo> declaradas = contexto.getCuartetas()
                    .getVariablesDeclaradasPorUnidad()
                    .get(contexto.getUnidadActual());
            if (declaradas != null && declaradas.containsKey(limpio)) {
                return declaradas.get(limpio);
            }
        }
        Tipo tipo = contexto.getCuartetas().tipoDeVariable(limpio);
        if (tipo == null) {
            tipo = contexto.getCuartetas().tipoDeVariable(
                    limpio.replace('_', '.'));
        }
        return tipo;
    }

    public Tipo tipoDeArregloDe(String nombre) {
        String limpio = contexto.sinPrefijoThis(nombre);
        if (contexto.getUnidadActual() != null) {
            Map<String, Tipo> declaradas = contexto.getCuartetas()
                    .getArreglosDeclaradosPorUnidad()
                    .get(contexto.getUnidadActual());
            if (declaradas != null && declaradas.containsKey(limpio)) {
                return declaradas.get(limpio);
            }
        }
        Tipo tipo = contexto.getCuartetas().tipoDeArreglo(limpio);
        if (tipo == null) {
            tipo = contexto.getCuartetas().tipoDeArreglo(
                    limpio.replace('_', '.'));
        }
        return tipo;
    }

    public String firmaParametros(String nombre) {
        String clase = contexto.claseDeUnidad(nombre);
        List<SimboloParametro> parametros =
                contexto.getCuartetas().parametrosDeFuncion(nombre);
        StringBuilder firma = new StringBuilder("(");
        boolean primero = true;
        if (clase != null) {
            firma.append("struct ").append(clase).append("* this");
            primero = false;
        }
        if (parametros != null && !parametros.isEmpty()) {
            for (SimboloParametro parametro : parametros) {
                if (!primero) {
                    firma.append(", ");
                }
                primero = false;
                String tipoC = contexto.tipoCDe(parametro.getTipo());
                if (parametro.getTipo() instanceof TipoStructura
                        && !contexto.esObjetoPorReferencia(
                                parametro.getTipo())) {
                    tipoC = tipoC + "*";
                }
                firma.append(tipoC)
                        .append(' ')
                        .append(normalizar(parametro.getId()));
            }
        }
        return firma.append(')').toString();
    }

    public String tipoCampoEstructura(Tipo tipo, String nombre) {
        if (tipo instanceof TipoArreglo) {
            TipoArreglo arreglo = (TipoArreglo) tipo;
            Tipo base = arreglo.getTipoBase();
            String tipoElemento = contexto.esObjetoPorReferencia(base)
                    ? base.tipoC() + "*"
                    : (contexto.tipoCValido(base) ? base.tipoC() : "int");
            if (tamanioConocido(arreglo)) {
                return tipoElemento + " " + nombre
                        + "[" + arreglo.getTotalElementos() + "]";
            }
            return tipoElemento + "* " + nombre;
        }
        if (contexto.esObjetoPorReferencia(tipo)) {
            return tipo.tipoC() + "* " + nombre;
        }
        if (!contexto.tipoCValido(tipo)) {
            return null;
        }
        return tipo.tipoC() + " " + nombre;
    }
}
