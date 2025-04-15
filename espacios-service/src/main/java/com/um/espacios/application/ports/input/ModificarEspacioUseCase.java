package com.um.espacios.application.ports.input;

public interface ModificarEspacioUseCase {
    void modificar(String id, String nombre, int capacidad, String descripcion);
}