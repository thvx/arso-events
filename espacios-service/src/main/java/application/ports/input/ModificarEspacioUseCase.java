package application.ports.input;

import domain.exceptions.EntidadNoEncontradaException;
import domain.exceptions.RepositorioException;

public interface ModificarEspacioUseCase {
    void modificarEspacio(String id, String nombre, int capacidad, String descripcion)
            throws RepositorioException, EntidadNoEncontradaException;
}