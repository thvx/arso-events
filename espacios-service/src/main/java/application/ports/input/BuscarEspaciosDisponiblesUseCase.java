package application.ports.input;

import domain.exceptions.EspacioConOcupacionesActivasException;
import domain.model.EspacioFisico;
import domain.exceptions.RepositorioException;
import domain.exceptions.EntidadNoEncontradaException;

import java.time.LocalDateTime;
import java.util.List;

public interface BuscarEspaciosDisponiblesUseCase {
    List<EspacioFisico> buscar(LocalDateTime fechaInicio, LocalDateTime fechaFin, int capacidadMinima)
            throws RepositorioException, EntidadNoEncontradaException, EspacioConOcupacionesActivasException;
}
