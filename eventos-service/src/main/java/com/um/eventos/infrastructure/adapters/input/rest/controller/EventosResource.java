package com.um.eventos.infrastructure.adapters.input.rest.controller;

import com.um.eventos.application.ports.input.*;
import com.um.eventos.domain.exceptions.EntidadNoEncontradaException;
import com.um.eventos.domain.exceptions.EspacioNoDisponibleException;
import com.um.eventos.domain.model.Evento;
import com.um.eventos.infrastructure.adapters.input.rest.dto.request.EventoRequestDTO;
import com.um.eventos.infrastructure.adapters.input.rest.dto.response.EventoCreadoDTO;
import com.um.eventos.infrastructure.adapters.input.rest.dto.response.EventoResponseDTO;
import com.um.eventos.infrastructure.adapters.input.rest.dto.response.ResumenEventoDTO;
import com.um.eventos.infrastructure.adapters.input.rest.mapper.EventoRestMapper;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.Getter;
import org.mapstruct.Context;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Path("/eventos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventosResource {

    private UriInfo uriInfo;

    @Inject
    CrearEventoUseCase crearEventoUseCase;

    @Inject
    ModificarEventoUseCase modificarEventoUseCase;

    @Inject
    EliminarEventoUseCase eliminarEventoUseCase;

    @Inject
    ListarEventosUseCase listarEventosUseCase;

    @Inject
    ObtenerEventoUseCase obtenerEventoUseCase;

    @Inject
    ResumenEventosUseCase resumenEventosUseCase;

    @Inject
    ObtenerEventosPorEspacioUseCase obtenerEventosPorEspacioUseCase;

    @Inject
    VerificarDisponibilidadUseCase verificarDisponibilidadUseCase;

    @Inject
    EventoRestMapper eventoRestMapper;

    @POST
    public Response crearEvento(@Valid EventoRequestDTO request) {
        try {
            Evento evento = crearEventoUseCase.crearEvento(
                    request.getNombre(),
                    request.getDescripcion(),
                    request.getOrganizador(),
                    request.getPlazas(),
                    request.getCategoria(),
                    request.getFechaInicio(),
                    request.getFechaFin(),
                    request.getEspacioId()
            );

            EventoCreadoDTO response = new EventoCreadoDTO(
                    evento.getId(),
                    LocalDateTime.now()
            );

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(evento.getId());

            return Response.created(builder.build())
                    .entity(response)
                    .build();

        } catch (EspacioNoDisponibleException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (EntidadNoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtenerEvento(@PathParam("id") String id) {
        try {
            Evento evento = obtenerEventoUseCase.obtenerEvento(id);
            EventoResponseDTO response = eventoRestMapper.toResponseDTO(evento);
            return Response.ok(response).build();
        } catch (EntidadNoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    public Response listarEventos(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("estado") String estado,
            @QueryParam("categoria") String categoria,
            @QueryParam("fechaInicio") String fechaInicio) {

        List<Evento> eventos = listarEventosUseCase.listarEventos();
        List<EventoResponseDTO> response = eventos.stream()
                .map(eventoRestMapper::toResponseDTO)
                .collect(Collectors.toList());

        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}")
    public Response modificarEvento(
            @PathParam("id") String id,
            @Valid EventoRequestDTO request) {
        try {
            Evento evento = modificarEventoUseCase.modificarEvento(
                    id,
                    request.getDescripcion(),
                    request.getFechaInicio(),
                    request.getFechaFin(),
                    request.getPlazas(),
                    request.getEspacioId()
            );

            EventoResponseDTO response = eventoRestMapper.toResponseDTO(evento);
            return Response.ok(response).build();

        } catch (EntidadNoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (EspacioNoDisponibleException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/cancelar")
    public Response cancelarEvento(@PathParam("id") String id) {
        try {
            modificarEventoUseCase.cancelarEvento(id);
            return Response.ok().build();
        } catch (EntidadNoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/resumen")
    public Response obtenerResumenEventos(
            @QueryParam("mes") String mes,
            @QueryParam("anio") String anio) {

        List<Evento> eventos = resumenEventosUseCase.obtenerEventosPorMes(mes, anio);
        List<ResumenEventoDTO> response = eventos.stream()
                .map(eventoRestMapper::toResumenDTO)
                .collect(Collectors.toList());

        return Response.ok(response).build();
    }

    @GET
    @Path("/espacio/{espacioId}")
    public Response obtenerEventosPorEspacio(@PathParam("espacioId") String espacioId) {
        List<Evento> eventos = obtenerEventosPorEspacioUseCase.obtenerEventosPorEspacio(espacioId);
        List<EventoResponseDTO> response = eventos.stream()
                .map(eventoRestMapper::toResponseDTO)
                .collect(Collectors.toList());

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarEvento(@PathParam("id") String id) {
        try {
            eliminarEventoUseCase.eliminarEvento(id);
            return Response.noContent().build();
        } catch (EntidadNoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/disponibilidad")
    public Response verificarDisponibilidad(
            @QueryParam("espacioId") String espacioId,
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin,
            @QueryParam("plazas") int plazas) {

        boolean disponible = verificarDisponibilidadUseCase.verificarDisponibilidad(
                espacioId,
                LocalDateTime.parse(fechaInicio),
                LocalDateTime.parse(fechaFin),
                plazas
        );

        return Response.ok().entity(new DisponibilidadResponse(disponible)).build();
    }

    // Clases auxiliares para respuestas de error
    @Getter
    private static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

    }

    @Getter
    private static class DisponibilidadResponse {
        private final boolean disponible;

        public DisponibilidadResponse(boolean disponible) {
            this.disponible = disponible;
        }

    }
}