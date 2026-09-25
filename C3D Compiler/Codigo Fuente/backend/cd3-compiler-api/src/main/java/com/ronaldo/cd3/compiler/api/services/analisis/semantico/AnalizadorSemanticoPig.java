package com.ronaldo.cd3.compiler.api.services.analisis.semantico;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorSemantico;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.programaPig.ProgramaPig;
import com.ronaldo.cd3.compiler.api.modelos.programaY.ProgramaY;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class AnalizadorSemanticoPig {

    public void analizar(ProgramaPig programa, RespuestaDTO respuesta,
            TablaTipos tablaTipos, TablaSimbolos tablaSimbolos,
            ListaCuartetas cuartetas, List<ArchivoDTO> archivosImportables,
            List<ProgramaY> programasY) {

        if (programa == null) {
            return;
        }

        Contexto contexto = new Contexto(tablaTipos, tablaSimbolos, tablaSimbolos);

        contexto.setRuta(programa.getArchivo().getRuta());

        VerificadorImportesPig verificador = new VerificadorImportesPig();
        verificador.verificar(contexto, programa, archivosImportables, programasY);

        programa.iniciarVerificacionSemantica(contexto);

        for (ErrorSemantico error : contexto.getErrores()) {
            respuesta.agregarUnError(error);
        }
        if (contexto.hayErrores()) {
            respuesta.setHayErrores(true);
        }

        //Generacion de cuartetas
        if (!contexto.hayErrores() && !respuesta.isHayErrores()) {
            
            Contexto contextoGeneracion = new Contexto(tablaTipos, tablaSimbolos, tablaSimbolos);
            
            cuartetas.agregarEtiqueta("main", programa.getFila(), programa.getColumna());
            programa.generarCuartetas(contextoGeneracion, cuartetas);
            respuesta.setCuartetas(cuartetas.getCuartetas());
            
        }
    }
}
