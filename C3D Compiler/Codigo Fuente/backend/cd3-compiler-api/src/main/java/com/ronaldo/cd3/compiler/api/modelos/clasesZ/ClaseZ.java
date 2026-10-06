package com.ronaldo.cd3.compiler.api.modelos.clasesZ;

import com.ronaldo.cd3.compiler.api.modelos.clasesZ.registradores.RegistradorClase;
import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.interfaces.Generable;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.funciones.Funcion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.Declaracion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ClaseZ extends Nodo implements Verificable, Generable {

    private final Reglas reglas = new Reglas();
    private final ResolutorTipoRetorno resolutorRetorno = new ResolutorTipoRetorno(reglas);

    private final String nombre;
    private final String nombrePadre;
    private final List<Declaracion> atributos;
    private final List<ConstructorZ> constructores;
    private final List<Funcion> metodos;
    private SimboloClase simboloClase;
    private ArchivoDTO archivo;

    public ClaseZ(String nombre, String nombrePadre, List<Declaracion> atributos,
            List<ConstructorZ> constructores, List<Funcion> metodos,
            int fila, int columna) {
        super(fila, columna);
        this.nombre = nombre;
        this.nombrePadre = nombrePadre;
        this.atributos = atributos;
        this.constructores = constructores;
        this.metodos = metodos;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        registrarEstructuraYDeclaraciones(contexto);
        verificarCuerpos(contexto);
    }

    public void verificarHerencia(Contexto contexto) {
        
        if (nombrePadre == null || simboloClase == null) {
            return;
        }
        
        if (nombrePadre.equals(nombre)) {
            contexto.agregarError(fila, columna, nombrePadre,
                    "La clase '" + nombre + "' no puede heredar de sí misma.");
            return;
        }
        SimboloClase simboloPadre = contexto.getTablaSimbolos().buscarClase(nombrePadre);
        if (simboloPadre == null) {
            contexto.agregarError(fila, columna, nombrePadre,
                    "No existe la clase '" + nombrePadre + "'. No se puede usar extends.");
            return;
        }

        for (SimboloClase actual = simboloPadre; actual != null; actual = actual.getClasePadre()) {
            if (actual == simboloClase) {
                contexto.agregarError(fila, columna, nombrePadre,
                        "Herencia circular entre '" + nombre + "' y '" + nombrePadre + "'.");
                return;
            }
        }

        simboloClase.setClasePadre(simboloPadre);
        simboloClase.getAmbito().setPadre(simboloPadre.getAmbito());
    }

    public void registrarEstructuraYDeclaraciones(Contexto contexto) {
        RegistradorClase registrador = new RegistradorClase(this, reglas, resolutorRetorno);
        this.simboloClase = registrador.registrar(contexto);
    }

    public void verificarCuerpos(Contexto contexto) {
        VerificadorCuerposClase verificador
                = new VerificadorCuerposClase(this, reglas, resolutorRetorno);
        verificador.verificar(contexto);
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        if (simboloClase == null) {
            return null;
        }

        SimboloClase claseAnterior = contexto.getClaseActual();
        contexto.setClaseActual(simboloClase);

        if (simboloClase.getTipo() != null) {
            cuartetas.registrarTipoEstructura(nombre, simboloClase.getTipo());
        }

        if (simboloClase.getAtributos() != null) {
            for (SimboloVariable atributo : simboloClase.getAtributos().values()) {
                cuartetas.registrarTipoVariable(atributo.getId(), atributo.getTipo());
                if (atributo.getTipo() instanceof TipoArreglo) {
                    cuartetas.registrarTipoArreglo(atributo.getId(),
                            ((TipoArreglo) atributo.getTipo()).getTipoBase());
                }
            }
        }

        if (metodos != null) {
            for (Funcion metodo : metodos) {
                metodo.generarCuartetas(contexto, cuartetas);
            }
        }

        if (constructores != null) {
            for (ConstructorZ constructor : constructores) {
                if (constructor.getNombre().equals(nombre)) {
                    constructor.generarCuartetas(contexto, cuartetas);
                }
            }
        }

        contexto.setClaseActual(claseAnterior);
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public List<Declaracion> getAtributos() {
        return atributos;
    }

    public List<ConstructorZ> getConstructores() {
        return constructores;
    }

    public List<Funcion> getMetodos() {
        return metodos;
    }

    public SimboloClase getSimboloClase() {
        return simboloClase;
    }

    public ArchivoDTO getArchivo() {
        return archivo;
    }

    public void setArchivo(ArchivoDTO archivo) {
        this.archivo = archivo;
    }

}
