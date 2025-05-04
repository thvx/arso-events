package com.um.eventos.infrastructure.adapters.input.rest;

import com.um.eventos.application.ports.input.*;
import com.um.eventos.domain.exceptions.*;
import com.um.eventos.infrastructure.adapters.input.rest.dto.request.*;
import com.um.eventos.infrastructure.adapters.input.rest.dto.response.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/eventos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventoResource {

    @Inject
    private CrearEventoUseCase crearEventoUseCase;

    @Inject
    private ObtenerEventoUseCase obtenerEventoUseCase;

    @Inject
    private ObtenerEventosPorEspacioUseCase obtenerEventosPorEspacioUseCase;

    @Inject
    private ResumenEventosUseCase resumenEventosUseCase;

    @Inject
    private ModificarEventoUseCase modificarEventoUseCase;

    @POST
    public Response crearEvento(CrearEventoRequest request) {
        try {
            var evento = crearEventoUseCase.crearEvento(
                    request.getNombre(),
                    request.getDescripcion(),
                    request.getOrganizador(),
                    request.getPlazas(),
                    request.getCategoria(),
                    request.getFechaInicio(),
                    request.getFechaFin(),
                    request.getEspacioId()
            );
            return Response.ok(new EventoCreadoResponse(evento.getId())).build();
        } catch (EspacioNoDisponibleException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El espacio no está disponible en las fechas solicitadas")
                    .build();
        } catch (EntidadNoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response modificarEvento(@PathParam("id") String id, ModificarEventoRequest request) {
        try {
            modificarEventoUseCase.modificarEvento(
                    id,
                    request.getDescripcion(),
                    request.getFechaInicio(),
                    request.getFechaFin(),
                    request.getPlazas(),
                    request.getEspacioId()
            );
            return Response.ok().build();
        } catch (EspacioNoDisponibleException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El espacio no está disponible en las fechas solicitadas")
                    .build();
        } catch (EntidadNoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}/cancelar")
    public Response cancelarEvento(@PathParam("id") String id) {
        try {
            //modificarEventoUseCase.cancelarEvento(id);
            return Response.ok().build();
        } catch (EntidadNoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtenerEvento(@PathParam("id") String id) {
        try {
            var evento = obtenerEventoUseCase.obtenerEvento(id);
            return Response.ok(new EventoResponse(evento)).build();
        } catch (EntidadNoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/espacio/{espacioId}")
    public Response obtenerEventosPorEspacio(@PathParam("espacioId") String espacioId) {
        var eventos = obtenerEventosPorEspacioUseCase.obtenerEventosPorEspacio(espacioId);
        return Response.ok(new EventosPorEspacioResponse(eventos)).build();
    }

    @GET
    @Path("/resumen")
    public Response obtenerResumenEventos(@QueryParam("mes") String mes, @QueryParam("año") String anio) {
        var eventos = resumenEventosUseCase.obtenerEventosPorMes(mes, anio);
        var resumenResponse = new ResumenEventosResponse(eventos);
        return Response.ok(resumenResponse).build();
    }
}