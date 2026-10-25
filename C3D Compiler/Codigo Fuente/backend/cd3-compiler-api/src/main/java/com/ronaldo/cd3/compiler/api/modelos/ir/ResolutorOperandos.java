package com.ronaldo.cd3.compiler.api.modelos.ir;

import com.ronaldo.cd3.compiler.api.enums.RolOperando;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoAtributo;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoEtiqueta;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoFuncion;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoIndice;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoLiteral;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoParametro;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoTemporal;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoThis;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoVariable;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoPrimitivo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.List;
import java.util.Map;

public class ResolutorOperandos {

    private static final String PREFIJO_THIS = "this->";

    private final ListaCuartetas cuartetas;
    private String unidadActual;

    public ResolutorOperandos(ListaCuartetas cuartetas) {
        this.cuartetas = cuartetas;
    }

    public void setUnidadActual(String unidadActual) {
        this.unidadActual = unidadActual;
    }

    public Operando resolver(String texto, RolOperando rol) {
        if (texto == null) {
            return null;
        }
        if ("this".equals(texto)) {
            return new OperandoThis(texto, tipoDeThis(), rol);
        }
        TipoOperando categoria = cuartetas.categoriaDe(texto);
        if (categoria == TipoOperando.TEMPORAL) {
            return new OperandoTemporal(texto, cuartetas.tipoDeTemporal(texto), rol);
        }
        if (categoria == TipoOperando.ETIQUETA_INTERNA) {
            return new OperandoEtiqueta(texto, rol);
        }
        if (categoria == TipoOperando.FUNCION) {
            return new OperandoFuncion(texto, cuartetas.tipoDeFuncion(texto), rol);
        }
        if (esCategoriaConstante(categoria)) {
            return new OperandoLiteral(texto, categoria, tipoDeConstante(categoria), rol);
        }
        if (texto.indexOf('[') >= 0) {
            return resolverIndice(texto, rol);
        }
        int corte = posicionCorte(texto);
        if (corte >= 0) {
            return resolverAtributo(texto, rol, corte);
        }
        if (esParametroDeUnidad(texto)) {
            return new OperandoParametro(texto, tipoDeVariable(texto), rol);
        }
        if (esAtributoImplicito(texto)) {
            return resolverAtributoImplicito(texto, rol);
        }
        return new OperandoVariable(texto, tipoDeVariable(texto), rol);
    }

    private Operando resolverAtributoImplicito(String texto, RolOperando rol) {
        Tipo tipoClase = tipoDeThis();
        Tipo tipo = tipoAtributo(tipoClase, texto);
        int offset = offsetAtributo(tipoClase, texto);
        Operando base = new OperandoThis("this", tipoClase, RolOperando.VALOR);
        return new OperandoAtributo(texto, tipo, rol, base, texto, offset);
    }

    private boolean esAtributoImplicito(String texto) {
        if (esLocalDeUnidad(texto)) {
            return false;
        }
        Tipo tipoClase = tipoDeThis();
        return tipoClase != null && tipoAtributo(tipoClase, texto) != null;
    }

    private boolean esLocalDeUnidad(String texto) {
        if (unidadActual == null) {
            return false;
        }
        Map<String, Tipo> declaradas = cuartetas
                .getVariablesDeclaradasPorUnidad().get(unidadActual);
        return declaradas != null
                && declaradas.containsKey(sinPrefijoThis(texto));
    }

    public Operando resolverEtiqueta(String texto) {
        if (texto == null) {
            return null;
        }
        return new OperandoEtiqueta(texto, RolOperando.VALOR);
    }

    public Operando resolverFuncion(String texto) {
        if (texto == null) {
            return null;
        }
        return new OperandoFuncion(texto, cuartetas.tipoDeFuncion(texto),
                RolOperando.VALOR);
    }

    private Operando resolverIndice(String texto, RolOperando rol) {
        int fin = texto.length() - 1;
        int prof = 0, ini = -1;
        for (int i = fin; i >= 0; i--) {
            char c = texto.charAt(i);
            if (c == ']') {
                prof++;
            } else if (c == '[') {
                prof--;
                if (prof == 0) {
                    ini = i;
                    break;
                }
            }
        }
        String textoBase = texto.substring(0, ini);
        String textoIndice = texto.substring(ini + 1, fin);
        Operando base = resolver(textoBase, RolOperando.VALOR);
        Operando indice = resolverIndiceTexto(textoIndice);

        Tipo tipoElemento;
        if (textoBase.indexOf('[') >= 0) {
            Tipo t = base.getTipo();
            tipoElemento = (t instanceof TipoArreglo) ? ((TipoArreglo) t).getTipoBase() : t;
        } else {
            tipoElemento = tipoElemento(textoBase);
        }
        int tamañoElemento = (tipoElemento != null) ? tipoElemento.tamañoBytes() : 0;
        return new OperandoIndice(texto, tipoElemento, rol, base, indice, tamañoElemento);
    }

    private Operando resolverIndiceTexto(String textoIndice) {
        if (textoIndice != null && textoIndice.trim().matches("-?\\d+")) {
            return new OperandoLiteral(textoIndice.trim(),
                    TipoOperando.CONSTANTE_ENTERA,
                    tipoDeConstante(TipoOperando.CONSTANTE_ENTERA),
                    RolOperando.VALOR);
        }
        return resolver(textoIndice, RolOperando.VALOR);
    }

    private Operando resolverAtributo(String texto, RolOperando rol, int corte) {
        String textoBase = texto.substring(0, corte);
        boolean flecha = texto.startsWith("->", corte);
        String nombreAtributo = texto.substring(corte + (flecha ? 2 : 1));
        Operando base = resolver(textoBase, RolOperando.VALOR);
        Tipo tipoBase = (base != null) ? base.getTipo() : null;
        String nombre = nombreSinIndices(nombreAtributo);
        Tipo tipoAtributo = tipoAtributo(tipoBase, nombre);
        int offset = offsetAtributo(tipoBase, nombre);
        return new OperandoAtributo(texto, tipoAtributo, rol, base,
                nombreAtributo, offset);
    }

    private boolean esCategoriaConstante(TipoOperando categoria) {
        return categoria == TipoOperando.CONSTANTE_ENTERA
                || categoria == TipoOperando.CONSTANTE_DECIMAL
                || categoria == TipoOperando.CONSTANTE_CADENA
                || categoria == TipoOperando.CONSTANTE_CARACTER
                || categoria == TipoOperando.BOOLEANO_VERDADERO
                || categoria == TipoOperando.BOOLEANO_FALSO
                || categoria == TipoOperando.NULO;
    }

    private Tipo tipoDeConstante(TipoOperando categoria) {
        if (categoria == null) {
            return null;
        }
        switch (categoria) {
            case CONSTANTE_ENTERA:
                return new TipoPrimitivo(TipoDato.ENTERO);
            case CONSTANTE_DECIMAL:
                return new TipoPrimitivo(TipoDato.DECIMAL);
            case CONSTANTE_CADENA:
                return new TipoPrimitivo(TipoDato.CADENA);
            case CONSTANTE_CARACTER:
                return new TipoPrimitivo(TipoDato.CHAR);
            case BOOLEANO_VERDADERO:
            case BOOLEANO_FALSO:
                return new TipoPrimitivo(TipoDato.BOOLEAN);
            case NULO:
                return new TipoPrimitivo(TipoDato.NULO);
            default:
                return null;
        }
    }

    private int posicionCorte(String texto) {
        int profundidad = 0;
        int ultimo = -1;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '[') {
                profundidad++;
            } else if (c == ']') {
                profundidad--;
            } else if (profundidad == 0) {
                if (c == '.') {
                    ultimo = i;
                } else if (c == '-' && i + 1 < texto.length()
                        && texto.charAt(i + 1) == '>') {
                    ultimo = i;
                }
            }
        }
        return ultimo;
    }

    private String nombreSinIndices(String segmento) {
        int corchete = segmento.indexOf('[');
        return (corchete >= 0) ? segmento.substring(0, corchete) : segmento;
    }

    private Tipo tipoAtributo(Tipo tipoBase, String nombre) {
        if (tipoBase instanceof TipoStructura) {
            return ((TipoStructura) tipoBase).getTipoAtributo(nombre);
        }
        return null;
    }

    private int offsetAtributo(Tipo tipoBase, String nombre) {
        if (tipoBase instanceof TipoStructura) {
            return ((TipoStructura) tipoBase).getOffsetAtributo(nombre);
        }
        return -1;
    }

    private Tipo tipoElemento(String textoBase) {
        Tipo elemento = tipoDeArreglo(textoBase);
        if (elemento != null) {
            return elemento;
        }
        Tipo tipoBase = tipoDeVariable(textoBase);
        if (tipoBase instanceof TipoArreglo) {
            return ((TipoArreglo) tipoBase).getTipoBase();
        }
        return tipoBase;
    }

    private Tipo tipoDeVariable(String nombre) {
        String limpio = sinPrefijoThis(nombre);
        if (unidadActual != null) {
            Map<String, Tipo> declaradas = cuartetas
                    .getVariablesDeclaradasPorUnidad().get(unidadActual);
            if (declaradas != null && declaradas.containsKey(limpio)) {
                return declaradas.get(limpio);
            }
        }
        Tipo tipo = cuartetas.tipoDeVariable(limpio);
        if (tipo == null) {
            tipo = cuartetas.tipoDeVariable(limpio.replace('_', '.'));
        }
        if (tipo == null && limpio.contains("->")) {
            tipo = cuartetas.tipoDeVariable(limpio.replace("->", "."));
        }
        return tipo;
    }

    private Tipo tipoDeArreglo(String nombre) {
        String limpio = sinPrefijoThis(nombre);
        Tipo tipo = cuartetas.tipoDeArreglo(limpio);
        if (tipo == null) {
            tipo = cuartetas.tipoDeArreglo(limpio.replace('_', '.'));
        }
        return tipo;
    }

    private boolean esParametroDeUnidad(String texto) {
        if (unidadActual == null) {
            return false;
        }
        List<SimboloParametro> parametros
                = cuartetas.parametrosDeFuncion(unidadActual);
        if (parametros == null) {
            return false;
        }
        for (SimboloParametro parametro : parametros) {
            if (texto.equals(parametro.getId())) {
                return true;
            }
        }
        return false;
    }

    private Tipo tipoDeThis() {
        String clase = claseDeUnidad(unidadActual);
        if (clase == null) {
            return null;
        }
        return buscarEstructura(clase);
    }

    private TipoStructura buscarEstructura(String nombre) {
        for (Tipo tipo : cuartetas.tiposRegistrados()) {
            if (tipo instanceof TipoStructura
                    && ((TipoStructura) tipo).getNombreStruct().equals(nombre)) {
                return (TipoStructura) tipo;
            }
        }
        return null;
    }

    private String claseDeUnidad(String nombre) {
        if (nombre == null) {
            return null;
        }
        if (nombre.startsWith("metodo_")
                || nombre.startsWith("constructor_")) {
            int primero = nombre.indexOf('_');
            int segundo = (primero < 0)
                    ? -1 : nombre.indexOf('_', primero + 1);
            if (segundo > primero + 1) {
                return nombre.substring(primero + 1, segundo);
            }
        }
        return null;
    }

    private String sinPrefijoThis(String nombre) {
        if (nombre == null) {
            return null;
        }
        return nombre.startsWith(PREFIJO_THIS)
                ? nombre.substring(PREFIJO_THIS.length()) : nombre;
    }
}
