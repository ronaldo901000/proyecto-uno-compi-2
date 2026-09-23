package com.ronaldo.cd3.compiler.api.services.analisis.semantico;

import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorSemantico;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.programaY.ProgramaY;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class AnalizadorSemanticoY {

    public void analizar(List<ProgramaY> programas, RespuestaDTO respuesta,
            TablaTipos tablaTipos, TablaSimbolos tablaSimbolos,
            ListaCuartetas cuartetas) {

        if (programas == null || programas.isEmpty()) {
            return;
        }

        Contexto contexto = new Contexto(tablaTipos, tablaSimbolos, tablaSimbolos);

        //Primera pasada: registrar estructuras y firmas de todas las funciones
        for (ProgramaY programa : programas) {
            contexto.setRuta(programa.getArchivo().getRuta());
            programa.registrarEstructurasYFunciones(contexto);
        }

        //Segunda pasada: verificar los cuerpos de las funciones
        for (ProgramaY programa : programas) {
            contexto.setRuta(programa.getArchivo().getRuta());
            programa.verificarCuerpos(contexto);
        }

        for (ErrorSemantico error : contexto.getErrores()) {
            respuesta.agregarUnError(error);
        }
        if (contexto.hayErrores()) {
            respuesta.setHayErrores(true);
        }

        //INICIO DE LA GENERACION DE LA CUARTETA
        if (!contexto.hayErrores() && !respuesta.isHayErrores()) {
            Contexto contextoGeneracion = new Contexto(tablaTipos, tablaSimbolos, tablaSimbolos);
            for (ProgramaY programa : programas) {
                contextoGeneracion.setRuta(programa.getArchivo().getRuta());
                programa.generarCuartetas(contextoGeneracion, cuartetas);
            }
            respuesta.setCuartetas(cuartetas.getCuartetas());
        }
    }

}
