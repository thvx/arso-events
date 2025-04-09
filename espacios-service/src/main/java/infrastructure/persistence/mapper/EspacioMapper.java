package infrastructure.persistence.mapper;

import domain.exceptions.EspacioConOcupacionesActivasException;
import domain.model.*;
import infrastructure.persistence.entities.*;

import java.util.List;
import java.util.stream.Collectors;

public class EspacioMapper {

    public static EspacioFisicoEntity toEntity(EspacioFisico model) {
        EspacioFisicoEntity entity = new EspacioFisicoEntity();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setPropietario(model.getPropietario());
        entity.setCapacidad(model.getCapacidad());
        entity.setDireccion(model.getUbicacion().getDireccion());
        entity.setLongitud(model.getUbicacion().getLongitud());
        entity.setLatitud(model.getUbicacion().getLatitud());
        entity.setDescripcion(model.getDescripcion());
        entity.setEstado(model.getEstado().name());

        entity.setPuntosDeInteres(
                model.getPuntosDeInteres().stream()
                        .map(EspacioMapper::toEntity)
                        .collect(Collectors.toList())
        );

        return entity;
    }

    public static EspacioFisico toModel(EspacioFisicoEntity entity) throws EspacioConOcupacionesActivasException {
        EspacioFisico espacio = EspacioFisico.crearEspacio(
                entity.getNombre(),
                entity.getPropietario(),
                entity.getCapacidad(),
                entity.getLongitud(),
                entity.getLatitud(),
                entity.getDireccion(),
                entity.getDescripcion()
        );
        espacio.setId(entity.getId());
        espacio.asignarPuntosDeInteres(
                entity.getPuntosDeInteres().stream()
                        .map(EspacioMapper::toModel)
                        .collect(Collectors.toList())
        );

        if (!espacio.getEstado().equals(EstadoEspacio.valueOf(entity.getEstado()))) {
            if (EstadoEspacio.valueOf(entity.getEstado()) == EstadoEspacio.CERRADO_TEMPORALMENTE) {
                espacio.activar();
                espacio.desactivarEspacio(List.of()); // sin ocupaciones activas
            }
        }

        return espacio;
    }

    public static PuntoDeInteresEntity toEntity(PuntoDeInteres model) {
        PuntoDeInteresEntity entity = new PuntoDeInteresEntity();
        entity.setNombre(model.getNombre());
        entity.setDescripcion(model.getDescripcion());
        entity.setDistancia(model.getDistancia());
        entity.setUrlWikipedia(model.getUrlWikipedia());
        return entity;
    }

    public static PuntoDeInteres toModel(PuntoDeInteresEntity entity) {
        return new PuntoDeInteres(
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getDistancia(),
                entity.getUrlWikipedia()
        );
    }
}