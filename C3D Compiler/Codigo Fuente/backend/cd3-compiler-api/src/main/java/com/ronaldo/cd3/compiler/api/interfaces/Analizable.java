package com.ronaldo.cd3.compiler.api.interfaces;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public interface Analizable {

    public void analizar(List<ArchivoDTO> archivos, RespuestaDTO respuesta,
            TablaTipos tablaTipos, TablaSimbolos tablaSimbolos,
            ListaCuartetas cuartetas);
}
