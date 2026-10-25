package com.ronaldo.cd3.compiler.api.resources;

import com.ronaldo.cd3.compiler.api.dtos.entrada.EntradaDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.EjecutarSesionEnviarDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.EjecutarSesionRespuestaDTO;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.exceptions.EntradaException;
import com.ronaldo.cd3.compiler.api.exceptions.SesionException;
import com.ronaldo.cd3.compiler.api.services.analisis.Analizador;
import com.ronaldo.cd3.compiler.api.services.ejecutor.riscv.EjecutorRiscV;
import com.ronaldo.cd3.compiler.api.services.generador.riscv.GeneradorRiscV;
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
@Path("/ejecutar-sesion")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EjecutarSesionResource {

    private final EjecutorRiscV ejecutor = new EjecutorRiscV();

    @POST
    @Path("/iniciar")
    public Response iniciar(EntradaDTO entrada) {
        try {
            Analizador analizador = new Analizador();
            RespuestaDTO resDto = analizador.iniciar(entrada);

            if (resDto.isHayErrores()) {
                EjecutarSesionRespuestaDTO conErrores = new EjecutarSesionRespuestaDTO(
                        false, null, null, resDto.getErrores(), null, null);
                return Response.ok(conErrores).build();
            }

            String codigo = new GeneradorRiscV().generar(analizador.getCuartetas());
            if (codigo == null || codigo.isBlank()) {
                return error(Response.Status.INTERNAL_SERVER_ERROR, "No se genero codigo RISC-V");
            }

            EjecutarSesionRespuestaDTO resp
                    = ejecutor.iniciar(codigo, analizador.getCuartetas().getCuartetas());
            return Response.ok(resp).build();

        } catch (Exception ex) {
            return manejarError(ex);
        }
    }

    @POST
    @Path("/enviar")
    public Response enviar(EjecutarSesionEnviarDTO dto) {
        try {
            EjecutarSesionRespuestaDTO resp
                    = ejecutor.enviar(dto.getSessionId(), dto.getTexto());
            return Response.ok(resp).build();
        } catch (Exception ex) {
            return manejarError(ex);
        }
    }

    @POST
    @Path("/salida")
    public Response salida(EjecutarSesionEnviarDTO dto) {
        try {
            EjecutarSesionRespuestaDTO resp = ejecutor.leerSalida(dto.getSessionId());
            return Response.ok(resp).build();
        } catch (Exception ex) {
            return manejarError(ex);
        }
    }

    @POST
    @Path("/detener")
    public Response detener(EjecutarSesionEnviarDTO dto) {
        try {
            EjecutarSesionRespuestaDTO resp = ejecutor.detener(dto.getSessionId());
            return Response.ok(resp).build();
        } catch (Exception ex) {
            return manejarError(ex);
        }
    }

    private Response manejarError(Exception ex) {
        if (ex instanceof SesionException) {
            return error(estado((SesionException) ex), ex.getMessage());
        }
        if (ex instanceof EntradaException) {
            return error(Response.Status.BAD_REQUEST, ex.getMessage());
        }
        ex.printStackTrace();
        return error(Response.Status.INTERNAL_SERVER_ERROR, "Error interno: " + ex.getMessage());
    }

    private Response.Status estado(SesionException ex) {
        switch (ex.getTipo()) {
            case INVALIDA:
                return Response.Status.BAD_REQUEST;
            case NO_ENCONTRADA:
                return Response.Status.NOT_FOUND;
            default:
                return Response.Status.GONE;
        }
    }

    private Response error(Response.Status status, String mensaje) {
        return Response.status(status).entity(mensaje).build();
    }
}
