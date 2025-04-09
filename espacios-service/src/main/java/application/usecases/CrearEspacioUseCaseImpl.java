package application.usecases;

import domain.model.EspacioFisico;
import application.ports.output.EspacioRepository;

import java.util.UUID;

public class CrearEspacioUseCaseImpl {

    private final EspacioRepository repository;

    public CrearEspacioUseCaseImpl(EspacioRepository repository) {
        this.repository = repository;
    }

    public String ejecutar(String nombre, String propietario, int capacidad, String direccion,
                           double longitud, double latitud, String descripcion) {
        EspacioFisico espacio = EspacioFisico.crearEspacio(
                nombre,
                propietario,
                capacidad,
                longitud,
                latitud,
                direccion,
                descripcion
        );
        espacio.setId(UUID.randomUUID().toString());
        repository.crear(espacio);
        return espacio.getId();
    }
}
