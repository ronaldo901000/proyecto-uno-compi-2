package com.ronaldo.cd3.compiler.api.modelos.semantica;

import com.ronaldo.cd3.compiler.api.enums.ModificadoresAcceso;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import java.util.Objects;

/**
 *
 * @author ronaldo
 */
public class VerificadorAcceso {

    /*VERIFICA EL ACCESO A ATRIBUTOS Y METODOS DEPENDIENDO EL MODIFICADOR DE ACCESO**/
    public boolean verificar(Contexto contexto, int fila, int columna,
            String nombreMiembro, ModificadoresAcceso modificador, String claseDueña) {

        if (puedeAcceder(contexto, modificador, claseDueña)) {
            return true;
        }

        contexto.agregarError(fila, columna, nombreMiembro,
                "'" + nombreMiembro + "' tiene acceso " + descripcion(modificador)
                + " en la clase '" + claseDueña);

        return false;
    }

    public boolean puedeAcceder(Contexto contexto, ModificadoresAcceso modificador,
            String claseDueña) {
        if (claseDueña == null) {
            return true;
        }
        if (modificador == null || modificador == ModificadoresAcceso.PUBLIC) {
            return true;
        }

        SimboloClase claseActual = contexto.getClaseActual();

        // Caso cuando .pig quiera acceder 
        if (claseActual == null) {
            if (modificador == ModificadoresAcceso.PRIVATE) {
                return false;
            }
            return esMismoPaqueteQueArchivo(contexto, claseDueña);
        }

        if (modificador == ModificadoresAcceso.PRIVATE) {
            return claseActual.getId().equals(claseDueña);
        }
        if (modificador == ModificadoresAcceso.PROTECTED) {
            return esMismoPaquete(contexto, claseActual, claseDueña)
                    || esSubclase(claseActual, claseDueña);
        }
        return esMismoPaquete(contexto, claseActual, claseDueña);
    }

    private boolean esMismoPaqueteQueArchivo(Contexto contexto, String claseDueña) {
        SimboloClase simboloDueña = contexto.getTablaSimbolos().buscarClase(claseDueña);
        if (simboloDueña == null) {
            return false;
        }
        return Objects.equals(contexto.getRutaPaquete(), simboloDueña.getRutaPaquete());
    }

    /**
     * Sube por la cadena de herencia desde la clase actual hasta la clase dueña
     * del simbolo
     */
    private boolean esSubclase(SimboloClase claseActual, String claseDueña) {
        for (SimboloClase actual = claseActual; actual != null; actual = actual.getClasePadre()) {
            if (actual.getId().equals(claseDueña)) {
                return true;
            }
        }
        return false;
    }

    private boolean esMismoPaquete(Contexto contexto, SimboloClase claseActual,
            String claseDueña) {
       
        if (claseActual.getId().equals(claseDueña)) {
            return true;
        }

        SimboloClase simboloDueña = contexto.getTablaSimbolos().buscarClase(claseDueña);
        if (simboloDueña == null) {
            return false;
        }

        return Objects.equals(claseActual.getRutaPaquete(), simboloDueña.getRutaPaquete());
    }

    private String descripcion(ModificadoresAcceso modificador) {
        if (modificador == ModificadoresAcceso.PRIVATE) {
            return "privado";
        }
        if (modificador == ModificadoresAcceso.PROTECTED) {
            return "protegido";
        }
        return "de paquete";
    }
}
