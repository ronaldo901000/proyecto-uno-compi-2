package com.ronaldo.cd3.compiler.api.resources;

import com.ronaldo.cd3.compiler.api.dtos.entrada.EntradaDTO;
import com.ronaldo.cd3.compiler.api.exceptions.EntradaException;
import com.ronaldo.cd3.compiler.api.services.analisis.Analizador;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author
 */
@Path("/analisis")
public class AnalisisResorce {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response analizar(EntradaDTO entrada) {

        Analizador analizador = new Analizador();
        try {
            return Response.ok(analizador.iniciar(entrada)).build();
        } catch (EntradaException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}
