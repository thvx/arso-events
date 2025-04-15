package com.um.espacios.domain.model;

public enum EstadoEspacio {
    ACTIVO,
    CERRADO_TEMPORALMENTE;

    public boolean isActivo() {
        return this.equals(ACTIVO);
    }
}
