package application.ports.input;

import domain.exceptions.EntidadNoEncontradaException;
import domain.exceptions.RepositorioException;
import domain.exceptions.EspacioConOcupacionesActivasException;

public interface CambiarEstadoEspacioUseCase {
    void cerrarTemporalmente(String id)
        throws RepositorioException, EntidadNoEncontradaException;

    void activar(String id)
        throws RepositorioException, EntidadNoEncontradaException, EspacioConOcupacionesActivasException;
}