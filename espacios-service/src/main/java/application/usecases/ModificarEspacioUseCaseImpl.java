package application.usecases;

import domain.model.EspacioFisico;
import application.ports.output.EspacioRepository;

public class ModificarEspacioUseCaseImpl {

    private final EspacioRepository repository;

    public ModificarEspacioUseCaseImpl(EspacioRepository repository) {
        this.repository = repository;
    }

    public void ejecutar(String idEspacio, String nombre, int capacidad, String descripcion) {
        EspacioFisico espacio = repository.buscarPorId(idEspacio)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        espacio.modificarDatos(nombre, capacidad, descripcion);
        repository.actualizar(espacio);
    }
}
