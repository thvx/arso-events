package com.um.espacios.infrastructure.adapters.output.persistence.mapper;

import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.domain.model.PuntoDeInteres;
import com.um.espacios.domain.model.Ubicacion;
import com.um.espacios.infrastructure.adapters.output.persistence.entitiy.EspacioFisicoDocument;
import com.um.espacios.infrastructure.adapters.output.persistence.entitiy.PuntoDeInteresDocument;

import java.util.stream.Collectors;

public class EspacioEntityMapper {

    public static EspacioFisicoDocument toDocument(EspacioFisico espacio) {
        EspacioFisicoDocument doc = new EspacioFisicoDocument();
        doc.setId(espacio.getId());
        doc.setNombre(espacio.getNombre());
        doc.setPropietario(espacio.getPropietario());
        doc.setCapacidad(espacio.getCapacidad());
        doc.setDescripcion(espacio.getDescripcion());
        doc.setEstado(espacio.getEstado());
        doc.setLongitud(espacio.getUbicacion().getLongitud());
        doc.setLatitud(espacio.getUbicacion().getLatitud());
        doc.setDireccion(espacio.getUbicacion().getDireccion());

        if (espacio.getPuntosDeInteres() != null) {
            doc.setPuntosDeInteres(espacio.getPuntosDeInteres().stream()
                    .map(p -> new PuntoDeInteresDocument(
                            p.getNombre(),
                            p.getDescripcion(),
                            p.getDistancia(),
                            p.getUrlWikipedia()))
                    .collect(Collectors.toList()));
        }

        return doc;
    }

    public static EspacioFisico toModel(EspacioFisicoDocument doc) {
        Ubicacion ubicacion = new Ubicacion(
                doc.getLongitud(),
                doc.getLatitud(),
                doc.getDireccion()
        );

        EspacioFisico espacio = new EspacioFisico(
                doc.getId(),
                doc.getNombre(),
                doc.getPropietario(),
                doc.getCapacidad(),
                ubicacion,
                null,
                doc.getDescripcion(),
                doc.getEstado()
        );

        if (doc.getPuntosDeInteres() != null) {
            espacio.asignarPuntosDeInteres(doc.getPuntosDeInteres().stream()
                    .map(p -> new PuntoDeInteres(
                            p.getNombre(),
                            p.getDescripcion(),
                            p.getDistancia(),
                            p.getUrlWikipedia()))
                    .collect(Collectors.toList()));
        }

        return espacio;
    }
}