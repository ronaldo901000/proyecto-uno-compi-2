package com.ronaldo.cd3.compiler.api.services.analisis;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.dtos.entrada.EntradaDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.exceptions.EntradaException;
import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ClaseZ;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.programaPig.ProgramaPig;
import com.ronaldo.cd3.compiler.api.modelos.programaY.ProgramaY;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.services.analisis.pig.AnalizadorLenguajePig;
import com.ronaldo.cd3.compiler.api.services.analisis.semantico.AnalizadorSemanticoPig;
import com.ronaldo.cd3.compiler.api.services.analisis.semantico.AnalizadorSemanticoY;
import com.ronaldo.cd3.compiler.api.services.analisis.semantico.AnalizadorSemanticoZ;
import com.ronaldo.cd3.compiler.api.services.analisis.semantico.VerificadorImportesPig;
import com.ronaldo.cd3.compiler.api.services.analisis.y.AnalizadorLenguajeY;
import com.ronaldo.cd3.compiler.api.services.analisis.z.AnalizadorLenguajeZ;
import com.ronaldo.cd3.compiler.api.services.separador.archivos.SeparadorArchivos;
import com.ronaldo.cd3.compiler.api.services.traduccion.TraductorC;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ronaldo
 */
public class Analizador {

    public RespuestaDTO iniciar(EntradaDTO entrada) throws EntradaException {
        RespuestaDTO respuestaDTO = new RespuestaDTO();

        SeparadorArchivos separador = new SeparadorArchivos();
        separador.separar(entrada.getArchivos());

        List<ArchivoDTO> archivosY = separador.getArchivosY();
        List<ArchivoDTO> archivosZ = separador.getArchivosZ();
        List<ArchivoDTO> archivosPig = separador.getArchivosPig();

        TablaTipos tablaTipos = new TablaTipos();
        TablaSimbolos tablaSimbolos = TablaSimbolos.nuevoGlobal();
        ListaCuartetas cuartetas = new ListaCuartetas();

        AnalizadorLenguajeY analizadorY = new AnalizadorLenguajeY();
        analizadorY.analizar(archivosY, respuestaDTO, tablaTipos, tablaSimbolos, cuartetas);

        AnalizadorLenguajeZ analizadorZ = new AnalizadorLenguajeZ();
        analizadorZ.parsear(archivosZ, respuestaDTO);

        AnalizadorLenguajePig analizadorPig = new AnalizadorLenguajePig();
        analizadorPig.analizar(archivosPig, respuestaDTO, tablaTipos, tablaSimbolos, cuartetas);

        if (archivosPig.isEmpty()) {
            throw new EntradaException(""
                    + "Se necesita un archivo .pig (Lenguaje Principal) "
                    + "para continuar con el analisis"
            );
        }

        List<ArchivoDTO> archivosImportables = new ArrayList<>();
        archivosImportables.addAll(archivosY);
        archivosImportables.addAll(archivosZ);

        AnalizadorSemanticoY analizadorSemY = new AnalizadorSemanticoY();
        analizadorSemY.analizar(analizadorY.getProgramas(), respuestaDTO,
                tablaTipos, tablaSimbolos, cuartetas, Collections.emptySet());

        AnalizadorSemanticoZ analizadorSemZ = new AnalizadorSemanticoZ();
        analizadorSemZ.analizar(analizadorZ.getClases(), respuestaDTO,
                tablaTipos, tablaSimbolos, cuartetas, Collections.emptySet());

        Set<String> rutasImportadas = new LinkedHashSet<>();
        for (ProgramaPig programa : analizadorPig.getProgramas()) {
            VerificadorImportesPig verificador = new VerificadorImportesPig();
            Contexto contextoTmp = new Contexto(tablaTipos, tablaSimbolos, tablaSimbolos);
            verificador.verificar(contextoTmp, programa, archivosImportables,
                    analizadorY.getProgramas());
            rutasImportadas.addAll(verificador.getRutasArchivosImportados());
        }

        Contexto contextoGen = new Contexto(tablaTipos, tablaSimbolos, tablaSimbolos);
        for (ProgramaY programa : analizadorY.getProgramas()) {
            if (rutasImportadas.contains(programa.getArchivo().getRuta())) {
                contextoGen.setRuta(programa.getArchivo().getRuta());
                programa.generarCuartetas(contextoGen, cuartetas);
            }
        }
        for (ClaseZ clase : analizadorZ.getClases()) {
            if (rutasImportadas.contains(clase.getArchivo().getRuta())) {
                contextoGen.setRuta(clase.getArchivo().getRuta());
                clase.generarCuartetas(contextoGen, cuartetas);
            }
        }

        AnalizadorSemanticoPig analizadorSemPig = new AnalizadorSemanticoPig();
        for (ProgramaPig programa : analizadorPig.getProgramas()) {
            analizadorSemPig.analizar(programa, respuestaDTO,
                    tablaTipos, tablaSimbolos, cuartetas,
                    archivosImportables, analizadorY.getProgramas());
        }

        if (!respuestaDTO.isHayErrores()
                && cuartetas != null
                && !cuartetas.getCuartetas().isEmpty()) {

            TraductorC traductorC = new TraductorC();
            String codigoC = traductorC.traducir(cuartetas);
            respuestaDTO.setCodigoC(codigoC);

        }

        return respuestaDTO;

    }

}
