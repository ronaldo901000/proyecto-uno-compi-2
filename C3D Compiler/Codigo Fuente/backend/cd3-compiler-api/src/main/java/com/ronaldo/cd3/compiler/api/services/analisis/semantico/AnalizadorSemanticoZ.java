package com.ronaldo.cd3.compiler.api.services.analisis.semantico;

import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorSemantico;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ClaseZ;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class AnalizadorSemanticoZ {

    public void analizar(List<ClaseZ> clases, RespuestaDTO respuesta,
            TablaTipos tablaTipos, TablaSimbolos tablaSimbolos,
            ListaCuartetas cuartetas) {

        if (clases == null || clases.isEmpty()) {
            return;
        }

        Contexto contexto = new Contexto(tablaTipos, tablaSimbolos, tablaSimbolos);
        contexto.setEsLenguajeZ(true);

        //Primera pasada: registrar las clases (atributos, metodos y constructores)
        for (ClaseZ clase : clases) {
            contexto.setRuta(clase.getArchivo().getRuta());
            clase.registrarEstructuraYFirmas(contexto);
        }

        //Segunda pasada: verificar los cuerpos de los metodos y constructores
        for (ClaseZ clase : clases) {
            contexto.setRuta(clase.getArchivo().getRuta());
            clase.verificarCuerpos(contexto);
        }

        for (ErrorSemantico error : contexto.getErrores()) {
            respuesta.agregarUnError(error);
        }
        if (contexto.hayErrores()) {
            respuesta.setHayErrores(true);
        }

        //Generacion de cuartetas
        if (!contexto.hayErrores() && !respuesta.isHayErrores()) {
            Contexto contextoGeneracion = new Contexto(tablaTipos, tablaSimbolos, tablaSimbolos);
            contextoGeneracion.setEsLenguajeZ(true);
            for (ClaseZ clase : clases) {
                contextoGeneracion.setRuta(clase.getArchivo().getRuta());
                clase.generarCuartetas(contextoGeneracion, cuartetas);
            }
            respuesta.setCuartetas(cuartetas.getCuartetas());
        }

    }

}
