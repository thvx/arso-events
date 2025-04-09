package application.usecases;

import domain.model.EspacioFisico;
import application.ports.output.EspacioRepository;

import java.time.LocalDateTime;
import java.util.List;

public class BuscarEspaciosDisponiblesUseCaseImpl {

    private final EspacioRepository repository;

    public BuscarEspaciosDisponiblesUseCaseImpl(EspacioRepository repository) {
        this.repository = repository;
    }

    public List<EspacioFisico> ejecutar(LocalDateTime fechaInicio, LocalDateTime fechaFin, int capacidadMinima) {
        return repository.buscarDisponibles(fechaInicio, fechaFin, capacidadMinima);
    }
}
