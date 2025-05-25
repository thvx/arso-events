package com.um.eventos.infrastructure.adapters.output.client;

import com.um.eventos.application.ports.output.EspacioServicePort;
import com.um.eventos.domain.model.EspacioFisico;
import com.um.eventos.domain.model.EspacioFisico.EstadoEspacio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import java.util.Optional;

@ApplicationScoped
public class EspacioServiceClientImpl implements EspacioServicePort {

    private static final String API_BASE_URL = "http://localhost:8080";
    private static final String ESPACIOS_ENDPOINT = "/api/espacios/";

    private final Client client;

    public EspacioServiceClientImpl() {
        ClientConfig config = new ClientConfig()
                .property(ClientProperties.CONNECT_TIMEOUT, 5000)
                .property(ClientProperties.READ_TIMEOUT, 10000);

        this.client = ClientBuilder.newClient(config);
    }

    @Override
    public Optional<EspacioFisico> obtenerEspacioPorId(String id) {
        try {
            Response response = client.target(API_BASE_URL)
                    .path(ESPACIOS_ENDPOINT + id)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                EspacioResponseDTO dto = response.readEntity(EspacioResponseDTO.class);
                return Optional.of(mapToDomain(dto));
            } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                return Optional.empty();
            } else {
                throw new RuntimeException("Error al consultar espacio. Código: " + response.getStatus());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error en comunicación con servicio de espacios: " + e.getMessage(), e);
        }
    }

    private EspacioFisico mapToDomain(EspacioResponseDTO dto) {
        return EspacioFisico.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .capacidad(dto.getCapacidad())
                .estado(EstadoEspacio.valueOf(dto.getEstado().toUpperCase()))
                .build();
    }

    // Clase interna para mapear la respuesta JSON
    private static class EspacioResponseDTO {
        private String id;
        private String nombre;
        private int capacidad;
        private String estado;

        // Getters y Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public int getCapacidad() { return capacidad; }
        public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
    }
}