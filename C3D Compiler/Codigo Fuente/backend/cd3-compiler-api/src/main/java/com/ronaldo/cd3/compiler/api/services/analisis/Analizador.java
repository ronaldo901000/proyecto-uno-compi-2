package com.ronaldo.cd3.compiler.api.services.analisis;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.dtos.entrada.EntradaDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.services.analisis.y.AnalizadorLenguajeY;
import com.ronaldo.cd3.compiler.api.services.separador.archivos.SeparadorArchivos;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class Analizador {

    public RespuestaDTO iniciar(EntradaDTO entrada) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();

        //separar los archivos por Extension
        SeparadorArchivos separador = new SeparadorArchivos();
        separador.separar(entrada.getArchivos());

        //archivos ya separados por extension
        List<ArchivoDTO> archivosY = separador.getArchivosY();

        //Analisis de los archivos .y
        AnalizadorLenguajeY analizadorY = new AnalizadorLenguajeY();
        analizadorY.analizar(archivosY, respuestaDTO);

        
        return respuestaDTO;

    }

}
