package com.ronaldo.cd3.compiler.api.modelos.vtable;

import com.ronaldo.cd3.compiler.api.enums.ModificadoresAcceso;
import com.ronaldo.cd3.compiler.api.enums.RolSimbolo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.Simbolo;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloFuncion;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ronaldo
 */
public class TablaVTables {

    private final Map<String, List<EntradaVTable>> vTables = new HashMap<>();
    private final Map<String, Map<String, Integer>> slots = new HashMap<>();

    public static TablaVTables construir(TablaSimbolos tablaSimbolos) {
        TablaVTables tabla = new TablaVTables();
        if (tablaSimbolos == null) {
            return tabla;
        }
        for (Simbolo simbolo : tablaSimbolos.getSimbolosPorRol(RolSimbolo.CLASE)) {
            if (simbolo instanceof SimboloClase) {
                tabla.construirDe((SimboloClase) simbolo, new HashMap<String, Boolean>());
            }
        }
        return tabla;
    }

    private void construirDe(SimboloClase clase, Map<String, Boolean> listas) {
        if (clase == null || listas.containsKey(clase.getId())) {
            return;
        }
        listas.put(clase.getId(), Boolean.TRUE);

        // Construir primero al padre
        SimboloClase padre = clase.getClasePadre();
        if (padre != null) {
            construirDe(padre, listas);
        }

        List<EntradaVTable> tabla = new ArrayList<>();
        Map<String, Integer> indice = new HashMap<>();

        // hereda la vtable del padre conservando el orden de los slots
        if (padre != null) {
            List<EntradaVTable> vTablePadre = vTables.get(padre.getId());
            if (vTablePadre != null) {
                for (EntradaVTable entrada : vTablePadre) {
                    indice.put(entrada.getFirma(), tabla.size());
                    tabla.add(entrada);
                }
            }
        }

        for (SimboloFuncion metodo : clase.getMetodosLista()) {
            if (!esVirtual(metodo)) {
                continue;
            }
            String firma = firmaDe(metodo);
            Integer slot = indice.get(firma);
            if (slot != null) {
                // sobreescribe: misma firma, nueva implementacion
                tabla.set(slot, new EntradaVTable(firma, metodo.getEtiquetaInicio()));
            } else {
                indice.put(firma, tabla.size());
                tabla.add(new EntradaVTable(firma, metodo.getEtiquetaInicio()));
            }
        }

        vTables.put(clase.getId(), tabla);
        slots.put(clase.getId(), indice);
    }

    private boolean esVirtual(SimboloFuncion metodo) {
        return metodo.esMetodo()
                && metodo.getModAcceso() != ModificadoresAcceso.PRIVATE;
    }

    private String firmaDe(SimboloFuncion metodo) {
        List<Tipo> tipos = new ArrayList<>();
        if (metodo.getParametros() != null) {
            for (SimboloParametro parametro : metodo.getParametros()) {
                tipos.add(parametro.getTipo());
            }
        }
        return TablaSimbolos.claveFuncion(metodo.getId(), tipos);
    }

    public List<EntradaVTable> vTableDe(String nombreClase) {
        return vTables.get(nombreClase);
    }

    public int slotDe(String nombreClase, String firma) {
        Map<String, Integer> indice = slots.get(nombreClase);
        if (indice == null) {
            return -1;
        }
        Integer slot = indice.get(firma);
        return (slot != null) ? slot : -1;
    }

    public boolean tieneVTable(String nombreClase) {
        List<EntradaVTable> tabla = vTables.get(nombreClase);
        return tabla != null && !tabla.isEmpty();
    }

    public Map<String, List<EntradaVTable>> getVTables() {
        return vTables;
    }
}
