package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.ObtenerPuntosDeInteresUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.domain.model.PuntoDeInteres;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObtenerPuntosDeInteresUseCaseImpl implements ObtenerPuntosDeInteresUseCase {

    private final EspacioRepository espacioRepository;

    @Override
    public List<PuntoDeInteres> obtenerPuntos(double latitud, double longitud) {
        // Validación de coordenadas
        if (!sonCoordenadasValidas(latitud, longitud)) {
            throw new IllegalArgumentException("Coordenadas geográficas inválidas");
        }

        return espacioRepository.buscarPuntosDeInteresCercanos(latitud, longitud);
    }

    private boolean sonCoordenadasValidas(double latitud, double longitud) {
        return latitud >= -90 && latitud <= 90 &&
                longitud >= -180 && longitud <= 180;
    }
}