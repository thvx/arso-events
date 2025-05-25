package com.um.eventos.infrastructure.adapters.output.retrofit;

import com.um.eventos.application.ports.output.EspacioServicePort;
import com.um.eventos.domain.exceptions.ExternalServiceException;
import com.um.eventos.domain.model.EspacioFisico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

@ApplicationScoped
public class EspacioServiceClientImpl implements EspacioServicePort {

    @Inject
    RetrofitEspacioApi espacioApi;

    @Override
    public Optional<EspacioFisico> obtenerEspacio(String espacioId) {
        try {
            Response<EspacioFisico> response = espacioApi.getEspacio(espacioId).execute();
            if (response.isSuccessful() && response.body() != null) {
                return Optional.of(response.body());
            }
            throw new ExternalServiceException("Error obteniendo espacio: " + response.code());
        } catch (IOException e) {
            throw new ExternalServiceException("Error de comunicación con espacios-service");
        }
    }

    @Override
    public boolean verificarDisponibilidadEspacio(String espacioId) {
        try {
            Response<Boolean> response = espacioApi.verificarDisponibilidad(espacioId).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
            throw new ExternalServiceException("Error verificando disponibilidad: " + response.code());
        } catch (IOException e) {
            throw new ExternalServiceException("Error de comunicación con espacios-service");
        }
    }
}