package com.ronaldo.cd3.compiler.api.modelos.tipos;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ronaldo
 */
public class TablaTipos {

    private final List<Tipo> tipos;
    private final Map<String, TipoStructura> clasesZ;

    public TablaTipos() {
        this.tipos = new ArrayList<>();
        this.clasesZ = new HashMap<>();
        cargarTiposPrimitivos();
    }

    private void cargarTiposPrimitivos() {
        this.tipos.add(new TipoPrimitivo(TipoDato.ENTERO));
        this.tipos.add(new TipoPrimitivo(TipoDato.DECIMAL));
        this.tipos.add(new TipoPrimitivo(TipoDato.CADENA));
        this.tipos.add(new TipoPrimitivo(TipoDato.CHAR));
        this.tipos.add(new TipoPrimitivo(TipoDato.BOOLEAN));
        this.tipos.add(new TipoPrimitivo(TipoDato.VOID));
        this.tipos.add(new TipoPrimitivo(TipoDato.NULO));
        this.tipos.add(new TipoError());
    }

    public boolean agregarTipo(Tipo nuevoTipo) {
        if (existeTipo(nuevoTipo)) {
            return false;
        }
        this.tipos.add(nuevoTipo);
        return true;
    }

    public boolean existeTipo(Tipo tipoBuscado) {
        for (Tipo t : tipos) {
            if (t.esIgual(tipoBuscado)) {
                return true;
            }
        }
        return false;
    }

    public TipoPrimitivo getPrimitivo(TipoDato primitivo) {
        for (Tipo t : tipos) {
            if (t instanceof TipoPrimitivo) {
                TipoPrimitivo p = (TipoPrimitivo) t;
                if (p.getTipoDato() == primitivo) {
                    return p;
                }
            }
        }
        return null;
    }

    public TipoPrimitivo getEntero() {
        return getPrimitivo(TipoDato.ENTERO);
    }

    public TipoPrimitivo getDecimal() {
        return getPrimitivo(TipoDato.DECIMAL);
    }

    public TipoPrimitivo getCadena() {
        return getPrimitivo(TipoDato.CADENA);
    }

    public TipoPrimitivo getCaracter() {
        return getPrimitivo(TipoDato.CHAR);
    }

    public TipoPrimitivo getBooleano() {
        return getPrimitivo(TipoDato.BOOLEAN);
    }

    public TipoPrimitivo getVoid() {
        return getPrimitivo(TipoDato.VOID);
    }

    public TipoPrimitivo getNulo() {
        return getPrimitivo(TipoDato.NULO);
    }

    public TipoError getError() {
        for (Tipo t : tipos) {
            if (t instanceof TipoError) {
                return (TipoError) t;
            }
        }
        return null;
    }

    public TipoArreglo getArreglo(Tipo tipoBase, List<Integer> dimensiones) {
        TipoArreglo candidato = new TipoArreglo(tipoBase, dimensiones);
        for (Tipo t : tipos) {
            if (t.esIgual(candidato)) {
                return (TipoArreglo) t;
            }
        }
        this.tipos.add(candidato);
        return candidato;
    }

    public TipoStructura registrarEstructura(String nombreStruct, String ambito) {
        TipoStructura existente = buscarEstructura(nombreStruct);
        if (existente != null) {
            return existente;
        }
        TipoStructura nueva = new TipoStructura(nombreStruct, ambito);
        this.tipos.add(nueva);
        return nueva;
    }

    public TipoStructura buscarEstructura(String nombreStruct) {
        for (Tipo t : tipos) {
            if (t instanceof TipoStructura) {
                TipoStructura struct = (TipoStructura) t;
                if (struct.getNombreStruct().equalsIgnoreCase(nombreStruct)) {
                    return struct;
                }
            }
        }
        return null;
    }

    public TipoStructura registrarClase(String nombreClase) {
        TipoStructura existente = buscarClaseTipo(nombreClase);
        if (existente != null) {
            return existente;
        }
        TipoStructura nueva = new TipoStructura(nombreClase, "clase_z");
        clasesZ.put(nombreClase.toLowerCase(), nueva);
        this.tipos.add(nueva);
        return nueva;
    }

    public TipoStructura buscarClaseTipo(String nombreClase) {
        if (nombreClase == null) {
            return null;
        }
        return clasesZ.get(nombreClase.toLowerCase());
    }

    public Tipo resolver(String nombreTipo) {
        return resolverConIdioma(nombreTipo, false);
    }

    public Tipo resolverZ(String nombreTipo) {
        return resolverConIdioma(nombreTipo, true);
    }

    private Tipo resolverConIdioma(String nombreTipo, boolean clasesPrimero) {
        if (nombreTipo == null) {
            return null;
        }
        switch (nombreTipo.toLowerCase()) {
            case "entero":
            case "int":
            case "numerus":
                return getEntero();
            case "flotante":
            case "decimal":
            case "double":
            case "decimalis":
                return getDecimal();
            case "cadena":
            case "string":
            case "textum":
                return getCadena();
            case "caracter":
            case "char":
            case "littera":
                return getCaracter();
            case "bool":
            case "boolean":
                return getBooleano();
            case "void":
                return getVoid();
            case "nulo":
            case "null":
                return getNulo();
            default:
                if (clasesPrimero) {
                    TipoStructura clase = buscarClaseTipo(nombreTipo);
                    if (clase != null) {
                        return clase;
                    }
                }
                return buscarEstructura(nombreTipo);
        }
    }

    public List<Tipo> getTipos() {
        return tipos;
    }
}