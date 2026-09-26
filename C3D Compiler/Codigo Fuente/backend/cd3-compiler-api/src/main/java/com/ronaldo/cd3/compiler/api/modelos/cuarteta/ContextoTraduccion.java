package com.ronaldo.cd3.compiler.api.modelos.cuarteta;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
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
 * Estado compartido durante la traduccion de las cuartetas a codigo C. Fachada
 * que delega el analisis de ambitos a {@link AnalisisAmbitos} y el formateo de
 * operandos a {@link FormateoOperandos}.
 *
 * @author ronaldo
 */
public class ContextoTraduccion {

    private final Set<String> funciones = new LinkedHashSet<>();
    private final Set<String> temporales = new LinkedHashSet<>();
    private final Set<String> temporalesPuntero = new LinkedHashSet<>();
    private final Set<String> escalares = new LinkedHashSet<>();
    private final Map<String, Integer> arreglos = new LinkedHashMap<>();
    private final Map<String, Tipo> punterosArreglo = new LinkedHashMap<>();
    private final Map<String, TipoStructura> estructuras = new LinkedHashMap<>();
    private final Map<String, String> arreglosNuevos = new LinkedHashMap<>();
    private final Map<String, TipoStructura> punterosEstructura
            = new LinkedHashMap<>();
    private final Map<String, TipoArreglo> dimsArreglosResueltas
            = new LinkedHashMap<>();
    private final Map<String, TipoArreglo> dimsLocales = new LinkedHashMap<>();
    private final ListaCuartetas cuartetas;
    private boolean usaConcatenacion = false;
    private boolean usaComparacionCadenas = false;
    private final Set<String> variablesGlobales = new LinkedHashSet<>();
    private final Map<String, Set<String>> localesPorUnidad = new LinkedHashMap<>();
    private String unidadActual;
    private boolean unidadEsVoid = false;
    private boolean terminoConRetorno = false;
    private final List<String> argumentosPendientes = new ArrayList<>();

    private final AnalisisAmbitos analisis;
    private final FormateoOperandos formateo;

    public ContextoTraduccion(ListaCuartetas cuartetas) {
        this.cuartetas = cuartetas;
        this.analisis = new AnalisisAmbitos(this);
        this.formateo = new FormateoOperandos(this);
    }

    public AnalisisAmbitos getAnalisis() {
        return analisis;
    }

    public FormateoOperandos getFormateo() {
        return formateo;
    }

    public void analizar(List<Unidad> unidades) {
        analisis.analizar(unidades);
    }

    public void clasificarAmbitosVariables(List<Unidad> unidades) {
        analisis.clasificarAmbitosVariables(unidades);
    }

    public List<TipoStructura> listaEstructuras() {
        return analisis.listaEstructuras();
    }

    public String formatearOperando(String operando) {
        return formateo.formatearOperando(operando);
    }

    public String aplanarSiPunteroArreglo(String operando) {
        return formateo.aplanarSiPunteroArreglo(operando);
    }

    public String normalizar(String operando) {
        return formateo.normalizar(operando);
    }

    public String raizIdentificador(String operando) {
        return analisis.raizIdentificador(operando);
    }

    public boolean tamañoConocido(TipoArreglo arreglo) {
        return formateo.tamañoConocido(arreglo);
    }

    public Set<String> getFunciones() {
        return funciones;
    }

    public Set<String> getTemporales() {
        return temporales;
    }

    public Map<String, String> getArreglosNuevos() {
        return arreglosNuevos;
    }

    public ListaCuartetas getCuartetas() {
        return cuartetas;
    }

    public Map<String, Set<String>> getLocalesPorUnidad() {
        return localesPorUnidad;
    }

    public boolean isUsaConcatenacion() {
        return usaConcatenacion;
    }

    public void setUsaConcatenacion(boolean usaConcatenacion) {
        this.usaConcatenacion = usaConcatenacion;
    }

    public boolean isUsaComparacionCadenas() {
        return usaComparacionCadenas;
    }

    public void setUsaComparacionCadenas(boolean usaComparacionCadenas) {
        this.usaComparacionCadenas = usaComparacionCadenas;
    }

    public void setUnidadActual(String unidadActual) {
        this.unidadActual = unidadActual;
    }

    public String getUnidadActual() {
        return unidadActual;
    }

    public void setUnidadEsVoid(boolean unidadEsVoid) {
        this.unidadEsVoid = unidadEsVoid;
    }

    public boolean esVoidUnidad() {
        return unidadEsVoid;
    }

    public Set<String> getVariablesGlobales() {
        return variablesGlobales;
    }

    public Set<String> getEscalares() {
        return escalares;
    }

    public Map<String, Integer> getArreglos() {
        return arreglos;
    }

    public Map<String, Tipo> getPunterosArreglo() {
        return punterosArreglo;
    }

    public Map<String, TipoStructura> getPunterosEstructura() {
        return punterosEstructura;
    }

    public Set<String> getTemporalesPuntero() {
        return temporalesPuntero;
    }

    public Map<String, TipoStructura> getEstructuras() {
        return estructuras;
    }

    public Map<String, TipoArreglo> getDimsArreglosResueltas() {
        return dimsArreglosResueltas;
    }

    public Map<String, TipoArreglo> getDimsLocales() {
        return dimsLocales;
    }

    public Set<String> getLocalesDeUnidad(String unidad) {
        return localesPorUnidad.get(unidad);
    }

    public boolean esVariableGlobal(String nombre) {
        return variablesGlobales.contains(nombre);
    }

    public boolean esTemporalPuntero(String nombre) {
        return temporalesPuntero.contains(nombre);
    }

    public boolean esPunteroArreglo(String nombre) {
        return punterosArreglo.containsKey(nombre);
    }

    public void registrarPunteroArreglo(String nombre, Tipo tipoBase) {
        punterosArreglo.put(nombre, tipoBase);
        arreglos.remove(nombre);
        escalares.remove(nombre);
    }

    public Tipo tipoPunteroArreglo(String nombre) {
        return punterosArreglo.get(nombre);
    }

    public boolean esPunteroEstructura(String nombre) {
        return punterosEstructura.containsKey(nombre);
    }

    public TipoStructura tipoPunteroEstructura(String nombre) {
        return punterosEstructura.get(nombre);
    }

    public Integer tamañoArreglo(String nombre) {
        return arreglos.get(nombre);
    }

    public boolean esArreglo(String nombre) {
        return arreglos.containsKey(nombre)
                || punterosArreglo.containsKey(nombre);
    }

    public boolean esArregloEnUnidad(String nombre, String unidad) {
        Map<String, Tipo> declaradas = cuartetas
                .getArreglosDeclaradosPorUnidad().get(unidad);
        return declaradas != null && declaradas.containsKey(nombre);
    }

    public boolean usaAyudasCadenas() {
        return usaConcatenacion || usaComparacionCadenas;
    }

    public void agregarArgumentoPendiente(String operando) {
        String argumento = formatearOperando(operando);
        if (argumento != null) {
            this.argumentosPendientes.add(argumento);
        }
    }

    public List<String> getArgumentosPendientes() {
        return this.argumentosPendientes;
    }

    public void limpiarArgumentosPendientes() {
        this.argumentosPendientes.clear();
    }

    public boolean terminoConRetorno() {
        return terminoConRetorno;
    }

    public void setTerminoConRetorno(boolean terminoConRetorno) {
        this.terminoConRetorno = terminoConRetorno;
    }

    public boolean contieneArregloNuevo(String nombre) {
        return arreglosNuevos.containsKey(nombre);
    }

    public String arregloNuevoCantidad(String nombre) {
        return arreglosNuevos.get(nombre);
    }

    public String tipoCampoEstructura(Tipo tipo, String nombre) {
        return formateo.tipoCampoEstructura(tipo, nombre);
    }

    public String tipoCDeTemporal(String nombre) {
        Tipo tipo = cuartetas.tipoDeTemporal(nombre);
        return tipoCDe(tipo);
    }

    public String tipoCDeVariable(String nombre) {
        return tipoCDe(tipoDeVariableDe(nombre));
    }

    public String tipoCDeElementoArreglo(String nombre) {
        return tipoCDe(tipoDeArregloDe(nombre));
    }

    public String tipoCDeFuncion(String nombre) {
        return tipoCDe(cuartetas.tipoDeFuncion(nombre));
    }

    public String firmaParametros(String nombre) {
        return formateo.firmaParametros(nombre);
    }

    public String tipoCDe(Tipo tipo) {
        if (tipo instanceof TipoArreglo) {
            TipoArreglo arreglo = (TipoArreglo) tipo;
            String texto = tipo.tipoC();

            if (esObjetoPorReferencia(arreglo.getTipoBase())) {
                texto = texto + "*";
            }
            return texto;
        }
        if (esObjetoPorReferencia(tipo)) {
            return tipo.tipoC() + "*";
        }
        if (tipo instanceof TipoStructura) {
            return tipo.tipoC();
        }
        return tipoCValido(tipo) ? tipo.tipoC() : "double";
    }

    public boolean tipoCValido(Tipo tipo) {
        return tipo != null && tipo.tipoC() != null
                && !"error".equals(tipo.tipoC());
    }

    public Tipo tipoDeOperando(String operando) {
        return formateo.tipoDeOperando(operando);
    }

    public Tipo tipoDeVariableDe(String nombre) {
        return formateo.tipoDeVariableDe(nombre);
    }

    public Tipo tipoDeArregloDe(String nombre) {
        return formateo.tipoDeArregloDe(nombre);
    }

    public String sinPrefijoThis(String nombre) {
        return (nombre != null && nombre.startsWith("this->"))
                ? nombre.substring("this->".length()) : nombre;
    }

    public boolean esDeTipo(Tipo tipo, TipoDato dato) {
        return tipo != null && tipo.getTipoDato() == dato;
    }

    public boolean esTipoVoid(Tipo tipo) {
        return tipo != null && tipo.getTipoDato() == TipoDato.VOID;
    }

    public Tipo primitivo(TipoDato dato) {
        return new TipoPrimitivo(dato);
    }

    public String operandoParaCadena(String operando) {
        if (operando == null) {
            return "\"\"";
        }
        Tipo tipo = tipoDeOperando(operando);
        if (tipo != null && tipo.esNumerico()) {
            return "cad_numero(" + operando + ")";
        }
        return operando;
    }

    public boolean usaComparacionCadenasPara(String izquierdo, String derecho) {
        return usaComparacionCadenas
                && (esDeTipo(tipoDeOperando(izquierdo), TipoDato.CADENA)
                || esDeTipo(tipoDeOperando(derecho), TipoDato.CADENA));
    }

    public boolean esEntero(String operando) {
        if (operando == null) {
            return false;
        }
        TipoOperando cat = categoriaDe(operando);
        if (cat == TipoOperando.CONSTANTE_ENTERA) {
            return true;
        }
        return esDeTipo(tipoDeOperando(operando), TipoDato.ENTERO);
    }

    public boolean esNuevoArregloHaciaVariable(String origen, String destino) {
        if (origen == null || destino == null) {
            return false;
        }
        Tipo tipo = cuartetas.tipoDeVariable(destino);
        return arreglosNuevos.containsKey(origen)
                && tipo instanceof TipoArreglo
                && cuartetas.tipoDeArreglo(destino) != null;
    }

    public String tipoElementoDeArreglo(String nombre) {
        Tipo elemento = cuartetas.tipoDeArreglo(nombre);
        return tipoCValido(elemento) ? elemento.tipoC() : "int";
    }

    public Tipo tipoDeFuncionDe(String nombre) {
        return cuartetas.tipoDeFuncion(nombre);
    }

    public Tipo tipoDeTemporal(String nombre) {
        return cuartetas.tipoDeTemporal(nombre);
    }

    public boolean nombreMain(String nombre) {
        return nombre != null && nombre.equals("main");
    }

    public String claseDeUnidad(String nombre) {
        if (nombre == null) {
            return null;
        }
        if (nombre.startsWith("metodo_")
                || nombre.startsWith("constructor_")) {
            int primero = nombre.indexOf('_');
            int segundo = (primero < 0)
                    ? -1 : nombre.indexOf('_', primero + 1);
            if (segundo > primero + 1) {
                String clase = nombre.substring(primero + 1, segundo);
                if (estructuras.containsKey(clase)) {
                    return clase;
                }
            }
        }
        return null;
    }

    public Set<String> camposDeUnidad(String nombre) {
        String clase = claseDeUnidad(nombre);
        if (clase == null) {
            return null;
        }
        TipoStructura estructura = estructuras.get(clase);
        if (estructura == null) {
            return null;
        }
        return estructura.getAtributos().keySet();
    }

    public TipoOperando categoriaDe(String operando) {
        return cuartetas.categoriaDe(operando);
    }

    public boolean esCategoria(String operando, TipoOperando esperado) {
        return categoriaDe(operando) == esperado;
    }

    public boolean esConstante(String operando) {
        TipoOperando cat = categoriaDe(operando);
        return cat == TipoOperando.CONSTANTE_ENTERA
                || cat == TipoOperando.CONSTANTE_DECIMAL
                || cat == TipoOperando.CONSTANTE_CADENA
                || cat == TipoOperando.CONSTANTE_CARACTER
                || cat == TipoOperando.BOOLEANO_VERDADERO
                || cat == TipoOperando.BOOLEANO_FALSO
                || cat == TipoOperando.NULO;
    }

    public boolean esNombreDeVariable(String operando) {
        if (operando == null) {
            return false;
        }
        TipoOperando cat = categoriaDe(operando);
        return cat == null || cat == TipoOperando.VARIABLE;
    }

    public String baseIndice(String operando) {
        int corchete = operando.indexOf('[');
        if (corchete < 0) {
            return null;
        }
        return operando.substring(0, corchete);
    }

    public boolean esObjetoPorReferencia(Tipo tipo) {
        return tipo instanceof TipoStructura
                && "clase_z".equals(
                        ((TipoStructura) tipo).getAmbito());
    }

    public void linea(StringBuilder sb, String texto) {
        sb.append("\t").append(texto).append('\n');
    }
}