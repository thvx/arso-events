package com.um.espacios.infrastructure.adapters.output.retrofit;

import com.um.espacios.application.ports.output.EventoServicePort;
import com.um.espacios.domain.exceptions.ServicioNoDisponibleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

@Service
@Component
@RequiredArgsConstructor
public class RetrofitEventoServiceAdapter implements EventoServicePort {

    private final RetrofitEventoApi retrofitEventoApi;

    @Override
    public boolean tieneOcupacionesActivas(String espacioId) throws ServicioNoDisponibleException {
        try {
            Response<Boolean> response = retrofitEventoApi.tieneOcupacionesActivas(espacioId).execute();
            if (!response.isSuccessful()) {
                throw new ServicioNoDisponibleException("Error al consultar ocupaciones", null);
            }
            return Boolean.TRUE.equals(response.body());
        } catch (IOException e) {
            throw new ServicioNoDisponibleException("Error de conexión con servicio de eventos", e);
        }
    }
}