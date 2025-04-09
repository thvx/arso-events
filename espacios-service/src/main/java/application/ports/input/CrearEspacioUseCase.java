package application.ports.input;

import domain.model.EspacioFisico;
import domain.exceptions.EntidadNoEncontradaException;
import domain.exceptions.RepositorioException;

public interface CrearEspacioUseCase {
    String crearEspacio(String nombre, String propietario, int capacidad, String direccion,
                        double longitud, double latitud, String descripcion)
            throws RepositorioException, EntidadNoEncontradaException;
}