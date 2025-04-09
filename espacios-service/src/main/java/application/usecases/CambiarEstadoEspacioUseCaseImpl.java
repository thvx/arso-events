package application.usecases;

import domain.exceptions.EspacioConOcupacionesActivasException;
import domain.model.EspacioFisico;
import application.ports.output.EspacioRepository;

public class CambiarEstadoEspacioUseCaseImpl {

    private final EspacioRepository repository;

    public CambiarEstadoEspacioUseCaseImpl(EspacioRepository repository) {
        this.repository = repository;
    }

    public void cerrarTemporalmente(String idEspacio) throws EspacioConOcupacionesActivasException {
        if (repository.tieneOcupacionesActivas(idEspacio)) {
            throw new IllegalStateException("El espacio tiene ocupaciones activas.");
        }

        EspacioFisico espacio = repository.buscarPorId(idEspacio)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        espacio.desactivarEspacio(repository.ocupacionesActivas(idEspacio));
        repository.actualizar(espacio);
    }

    public void activar(String idEspacio) {
        EspacioFisico espacio = repository.buscarPorId(idEspacio)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        espacio.activar();
        repository.actualizar(espacio);
    }
}
