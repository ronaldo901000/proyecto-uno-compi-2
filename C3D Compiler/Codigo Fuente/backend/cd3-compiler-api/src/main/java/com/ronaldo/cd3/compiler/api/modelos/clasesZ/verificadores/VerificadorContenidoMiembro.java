package com.ronaldo.cd3.compiler.api.modelos.clasesZ.verificadores;

import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ClaseZ;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.funciones.Parametro;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.List;

/**
 * Verifica el contenido de un miembro con instrucciones Lo comparten
 * VerificadorMetodos y VerificadorConstructores
 *
 * @author ronaldo
 */
public class VerificadorContenidoMiembro {

    private final ClaseZ clase;
    private final Reglas reglas;

    public VerificadorContenidoMiembro(ClaseZ clase, Reglas reglas) {
        this.clase = clase;
        this.reglas = reglas;
    }

    public void verificar(Contexto contexto, String nombreMiembro,
            List<Parametro> parametros, Tipo tipoRetorno, List<Instruccion> cuerpo) {

        SimboloClase simboloClase = clase.getSimboloClase();

        TablaSimbolos anterior = contexto.nuevoAmbito(nombreMiembro);

        registrarParametros(contexto, parametros);

        Tipo retornoAnterior = contexto.getTipoRetornoActual();

        SimboloClase claseAnterior = contexto.getClaseActual();

        contexto.setTipoRetornoActual(tipoRetorno);
        contexto.setClaseActual(simboloClase);

        reglas.verificarInstrucciones(contexto, cuerpo);

        contexto.setClaseActual(claseAnterior);
        contexto.setTipoRetornoActual(retornoAnterior);
        contexto.restaurarAmbito(anterior);
    }

    private void registrarParametros(Contexto contexto, List<Parametro> parametros) {
        int posicion = 0;
        if (parametros != null) {
            for (Parametro parametro : parametros) {
                if (parametro.getTipo() == null) {
                    continue;
                }
                SimboloParametro simboloParametro = new SimboloParametro(
                        parametro.getNombre(), parametro.getTipo(), posicion);
                contexto.getAmbito().agregar(simboloParametro);
                posicion += parametro.getTipo().tamañoBytes();
            }
        }
        contexto.getAmbito().setSiguientePosicion(posicion);
    }
}
