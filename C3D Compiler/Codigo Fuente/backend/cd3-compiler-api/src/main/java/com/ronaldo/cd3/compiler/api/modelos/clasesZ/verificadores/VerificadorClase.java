package com.ronaldo.cd3.compiler.api.modelos.clasesZ.verificadores;

import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ClaseZ;
import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ResolutorTipoRetorno;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloClase;

/*
 * CONTROLADOR DE ANALISIS SEMANTICO DE CADA MIEMBRO DE UNA CLASE
 * @author ronaldo
 */
public class VerificadorClase {

    private final ClaseZ clase;
    private final VerificadorAtributos verificadorAtributos;
    private final VerificadorMetodos verificadorMetodos;
    private final VerificadorConstructores verificadorConstructores;

    public VerificadorClase(ClaseZ clase, Reglas reglas, ResolutorTipoRetorno resolutorRetorno) {
        this.clase = clase;

        VerificadorContenidoMiembro verificadorCuerpo = new VerificadorContenidoMiembro(clase, reglas);

        this.verificadorAtributos = new VerificadorAtributos(clase, reglas);
        this.verificadorMetodos = new VerificadorMetodos(clase, reglas,
                resolutorRetorno, verificadorCuerpo);
        this.verificadorConstructores = new VerificadorConstructores(clase, verificadorCuerpo);
    }

    public void verificar(Contexto contexto) {
        SimboloClase simboloClase = clase.getSimboloClase();
        if (simboloClase == null) {
            return;
        }

        TablaSimbolos ambitoPrevio = contexto.getAmbito();
        contexto.setAmbito(simboloClase.getAmbito());

        verificadorAtributos.verificar(contexto);
        verificadorMetodos.verificar(contexto);
        verificadorConstructores.verificar(contexto);

        contexto.setAmbito(ambitoPrevio);
    }

}
