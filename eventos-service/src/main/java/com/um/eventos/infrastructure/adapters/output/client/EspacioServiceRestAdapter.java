package com.um.eventos.infrastructure.adapters.output.client;

import com.um.eventos.application.ports.output.EspacioServicePort;
import com.um.eventos.domain.model.EspacioFisico;
import com.um.eventos.infrastructure.adapters.output.client.dto.EspacioResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Optional;


@Produces
@ApplicationScoped
public class EspacioServiceRestAdapter implements EspacioServicePort {

    private final Client client;
    private final String API_BASE_URL = "https://http://127.0.0.1:8080/api"; // URL base de la API externa

    @Inject
    public EspacioServiceRestAdapter() {
        this.client = ClientBuilder.newClient();
    }

    @Override
    public Optional<EspacioFisico> obtenerEspacioPorId(String id) {
        try {
            Response response = client.target(API_BASE_URL)
                    .path("/espacios/{id}")
                    .resolveTemplate("id", id)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                EspacioResponseDTO dto = response.readEntity(EspacioResponseDTO.class);
                return Optional.of(mapToDomain(dto));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar API de espacios", e);
        }
    }

    // Mapeador de DTO a dominio
    private EspacioFisico mapToDomain(EspacioResponseDTO dto) {
        return EspacioFisico.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .capacidad(dto.getCapacidad())
                .estado(EspacioFisico.EstadoEspacio.valueOf(dto.getEstado()))
                .build();
    }
}