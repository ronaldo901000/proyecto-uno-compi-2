package com.ronaldo.cd3.compiler.api.services.analisis;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.dtos.entrada.EntradaDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.exceptions.EntradaException;
import com.ronaldo.cd3.compiler.api.services.analisis.pig.AnalizadorLenguajePig;
import com.ronaldo.cd3.compiler.api.services.analisis.y.AnalizadorLenguajeY;
import com.ronaldo.cd3.compiler.api.services.analisis.z.AnalizadorLenguajeZ;
import com.ronaldo.cd3.compiler.api.services.separador.archivos.SeparadorArchivos;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Analizador {

    public RespuestaDTO iniciar(EntradaDTO entrada) throws EntradaException {
        RespuestaDTO respuestaDTO = new RespuestaDTO();

        //separar los archivos por Extension
        SeparadorArchivos separador = new SeparadorArchivos();
        separador.separar(entrada.getArchivos());

        //archivos ya separados por extension
        List<ArchivoDTO> archivosY = separador.getArchivosY();
        List<ArchivoDTO> archivosZ = separador.getArchivosZ();
        List<ArchivoDTO> archivosPig = separador.getArchivosPig();

        //Analisis de los archivos .y
        AnalizadorLenguajeY analizadorY = new AnalizadorLenguajeY();
        analizadorY.analizar(archivosY, respuestaDTO);

        //Analisis de archivos .z
        AnalizadorLenguajeZ analizadorZ = new AnalizadorLenguajeZ();
        analizadorZ.analizar(archivosZ, respuestaDTO);

        //Analisis de archivo .pig
        if (archivosPig.isEmpty()) {
            throw new EntradaException(""
                    + "Se necesita un archivo .pig (Lenguaje Principal) "
                    + "para continuar con el analisis"
            );
        }
        AnalizadorLenguajePig analizadorPig = new AnalizadorLenguajePig();
        analizadorPig.analizar(archivosPig, respuestaDTO);
        
        return respuestaDTO;

    }

}
