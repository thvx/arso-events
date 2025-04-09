package application.usecases;

import domain.exceptions.EspacioConOcupacionesActivasException;
import domain.model.EspacioFisico;
import domain.model.PuntoDeInteres;
import application.ports.output.EspacioRepository;

import java.util.List;

public class AsignarPuntosDeInteresUseCaseImpl {

    private final EspacioRepository repository;

    public AsignarPuntosDeInteresUseCaseImpl(EspacioRepository repository) {
        this.repository = repository;
    }

    public void ejecutar(String idEspacio, List<PuntoDeInteres> puntos) throws EspacioConOcupacionesActivasException {
        EspacioFisico espacio = repository.buscarPorId(idEspacio)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        espacio.asignarPuntosDeInteres(puntos);
        repository.actualizar(espacio);
    }
}
