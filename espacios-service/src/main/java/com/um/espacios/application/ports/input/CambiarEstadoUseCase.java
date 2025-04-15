package com.um.espacios.application.ports.input;

public interface CambiarEstadoUseCase {
    void activar(String espacioId);
    void desactivar(String espacioId) throws Exception;
}