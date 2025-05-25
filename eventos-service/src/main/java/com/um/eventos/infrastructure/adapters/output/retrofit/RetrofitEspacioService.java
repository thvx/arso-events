package com.um.eventos.infrastructure.adapters.output.retrofit;

import com.um.eventos.application.ports.output.EspacioServicePort;
import com.um.eventos.domain.model.EspacioFisico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import retrofit2.Response;

import java.util.Optional;

@ApplicationScoped
public class RetrofitEspacioService implements EspacioServicePort {

    @Inject
    RetrofitEspacioApi espacioApi;

    @Override
    public Optional<EspacioFisico> obtenerEspacio(String espacioId) {
        try {
            Response<EspacioFisico> response = espacioApi.getEspacio(espacioId).execute();
            if (response.isSuccessful()) {
                return Optional.ofNullable(response.body());
            }
            throw new RuntimeException("Error obteniendo espacio: " + response.code());
        } catch (Exception e) {
            throw new RuntimeException("Error en comunicación con espacios-service", e);
        }
    }

    @Override
    public boolean verificarDisponibilidadEspacio(String espacioId) {
        try {
            Response<Boolean> response = espacioApi.verificarDisponibilidad(espacioId).execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            throw new RuntimeException("Error verificando disponibilidad: " + response.code());
        } catch (Exception e) {
            throw new RuntimeException("Error en comunicación con espacios-service", e);
        }
    }
}