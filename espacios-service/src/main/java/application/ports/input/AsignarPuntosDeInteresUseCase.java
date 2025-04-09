package application.ports.input;

import domain.model.PuntoDeInteres;
import domain.exceptions.EntidadNoEncontradaException;
import domain.exceptions.RepositorioException;

import java.util.List;

public interface AsignarPuntosDeInteresUseCase {
    void asignarPuntos(String id, List<PuntoDeInteres> puntos)
        throws RepositorioException, EntidadNoEncontradaException;
}