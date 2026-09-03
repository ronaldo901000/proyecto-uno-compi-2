package com.ronaldo.cd3.compiler.api.interfaces;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public interface Analizable {

    public void analizar(List<ArchivoDTO> archivos, RespuestaDTO respuesta);
}
