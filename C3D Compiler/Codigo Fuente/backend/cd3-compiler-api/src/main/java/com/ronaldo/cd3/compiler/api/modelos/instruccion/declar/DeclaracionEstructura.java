package com.ronaldo.cd3.compiler.api.modelos.instruccion.declar;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.LiteralStructura;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloVariable;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class DeclaracionEstructura extends Declaracion {

    private final Reglas reglas = new Reglas();
    private List<Expresion> valoresIniciales;
    private Expresion valorExpresion;

    public DeclaracionEstructura(List<Expresion> valoresIniciales, Expresion valorExpresion, String tipoDato, String id, int fila, int columna) {
        super(tipoDato, id, fila, columna);
        this.valoresIniciales = valoresIniciales;
        this.valorExpresion = valorExpresion;
    }

    public List<Expresion> getValoresIniciales() {
        return valoresIniciales;
    }

    public Expresion getValorExpresion() {
        return valorExpresion;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        Tipo tipo = reglas.resolverTipo(contexto, tipoDato, fila, columna);
        if (reglas.esError(tipo)) {
            return;
        }
        if (!(tipo instanceof TipoStructura)) {
            contexto.agregarError(fila, columna, tipoDato,
                    "El tipo '" + tipoDato + "' de la declaración '" + id + "' no es una estructura");
            return;
        }
        TipoStructura estructura = (TipoStructura) tipo;
        SimboloVariable variable = reglas.registrarVariable(contexto, id, tipo, fila, columna);
        if (variable == null) {
            return;
        }
        if (valorExpresion != null) {
            valorExpresion.verificarSemantica(contexto);
            if (!reglas.esAsignable(tipo, valorExpresion.getTipo())) {
                contexto.agregarError(fila, columna, id,
                        "La asignación a '" + id + "' es incompatible con el tipo " + tipoDato);
            }
        }
        if (valoresIniciales != null) {
            int contador = 0;
            for (Expresion valor : valoresIniciales) {
                valor.verificarSemantica(contexto);
                contador++;
            }
            if (contador > estructura.getAtributos().size()) {
                contexto.agregarError(fila, columna, id,
                        "Demasiados valores iniciales para la estructura '" + id + "'");
            }
        }
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        Tipo tipo = reglas.resolverTipo(contexto, tipoDato, fila, columna);
        if (reglas.esError(tipo) || !(tipo instanceof TipoStructura)) {
            return null;
        }
        TipoStructura estructura = (TipoStructura) tipo;
        cuartetas.registrarTipoVariable(id, tipo);
        for (String nombreAtributo : estructura.getAtributos().keySet()) {
            cuartetas.registrarTipoVariable(id + "." + nombreAtributo,
                    estructura.getTipoAtributo(nombreAtributo));
            cuartetas.registrarTipoVariable(id + "_" + nombreAtributo,
                    estructura.getTipoAtributo(nombreAtributo));
        }
        if (valorExpresion != null) {
            String dirValor = valorExpresion.generarCuartetas(contexto, cuartetas);
            cuartetas.agregar(OperadorCuarteta.ASIGNACION, dirValor,
                    null, id, fila, columna);
            return null;
        }
        if (valoresIniciales != null) {
            int indice = 0;
            for (String nombreAtributo : estructura.getAtributos().keySet()) {
                if (indice >= valoresIniciales.size()) {
                    break;
                }
                Expresion valor = valoresIniciales.get(indice++);
                emitirInicializacionCampo(contexto, cuartetas,
                        id + "." + nombreAtributo,
                        estructura.getTipoAtributo(nombreAtributo),
                        valor, fila, columna);
            }
        }
        return null;
    }

    private void emitirInicializacionCampo(Contexto contexto, ListaCuartetas cuartetas,
            String destino, Tipo tipoCampo, Expresion valor, int fila, int columna) {
        cuartetas.registrarTipoVariable(destino, tipoCampo);
        if (tipoCampo instanceof TipoArreglo) {
            cuartetas.registrarTipoArreglo(destino,
                    ((TipoArreglo) tipoCampo).getTipoBase());
        }
        if (valor instanceof LiteralStructura) {
            if (tipoCampo instanceof TipoArreglo) {
                List<Expresion> elementos = listaValores(valor);
                int i = 0;
                for (Expresion elemento : elementos) {
                    emitirInicializacionCampo(contexto, cuartetas,
                            destino + "[" + i + "]",
                            ((TipoArreglo) tipoCampo).getTipoBase(),
                            elemento, fila, columna);
                    i++;
                }
                return;
            }
            if (tipoCampo instanceof TipoStructura) {
                TipoStructura sub = (TipoStructura) tipoCampo;
                List<Expresion> subValores = listaValores(valor);
                int indice = 0;
                for (String subAtributo : sub.getAtributos().keySet()) {
                    if (indice >= subValores.size()) {
                        break;
                    }
                    emitirInicializacionCampo(contexto, cuartetas,
                            destino + "." + subAtributo,
                            sub.getTipoAtributo(subAtributo),
                            subValores.get(indice++), fila, columna);
                }
                return;
            }
        }
        String dirValor = valor.generarCuartetas(contexto, cuartetas);
        if (dirValor != null) {
            cuartetas.agregar(OperadorCuarteta.ASIGNACION, dirValor,
                    null, destino, fila, columna);
        }
    }

    private List<Expresion> listaValores(Expresion valor) {
        if (valor instanceof LiteralStructura) {
            List<Expresion> valores = ((LiteralStructura) valor).getValores();
            return (valores != null) ? valores : List.of();
        }
        return List.of(valor);
    }

}