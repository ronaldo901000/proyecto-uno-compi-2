package com.ronaldo.cd3.compiler.api.resources;

import com.ronaldo.cd3.compiler.api.dtos.entrada.TextoDTO;
import com.ronaldo.cd3.compiler.api.services.coloreado.ColoreadorLenguajes;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author ronaldo
 */
@Path("coloreado")
public class ColoreadoResource {

    @POST
    @Path("/lenguaje-y")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response colorearLenguajeY(TextoDTO texto) {
        ColoreadorLenguajes coloreador = new ColoreadorLenguajes();

        return Response
                .ok(coloreador.generarColoreado(texto.getTexto(), texto.getOpcion()))
                .build();
    }
}
