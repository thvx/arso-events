package com.um.eventos.infrastructure.adapters.output.persistence.mapper;

import com.um.eventos.domain.model.Evento;
import com.um.eventos.domain.model.EspacioFisico;
import com.um.eventos.domain.model.Ocupacion;
import com.um.eventos.infrastructure.adapters.output.persistence.entities.EventoEntity;
import com.um.eventos.infrastructure.adapters.output.persistence.entities.OcupacionEntity;
import org.mapstruct.*;

@Mapper(componentModel = "cdi")
public interface EventoPersistenceMapper {

    @Mapping(target = "ocupacion", source = "entity.ocupacion")
    Evento toDomain(EventoEntity entity);

    @Mapping(target = "ocupacion", source = "domain.ocupacion")
    EventoEntity toEntity(Evento domain);

    @AfterMapping
    default void afterToDomain(EventoEntity entity, @MappingTarget Evento.EventoBuilder evento) {
        if (entity.getOcupacion() != null) {
            evento.ocupacion(Ocupacion.builder()
                    .fechaInicio(entity.getOcupacion().getFechaInicio())
                    .fechaFin(entity.getOcupacion().getFechaFin())
                    .espacio(EspacioFisico.builder()
                            .id(entity.getOcupacion().getEspacioId())
                            .build())
                    .estado(entity.getOcupacion().isEstado())
                    .build());
        }
    }

    @AfterMapping
    default void afterToEntity(Evento domain, @MappingTarget EventoEntity.EventoEntityBuilder entity) {
        if (domain.getOcupacion() != null) {
            entity.ocupacion(OcupacionEntity.builder()
                    .fechaInicio(domain.getOcupacion().getFechaInicio())
                    .fechaFin(domain.getOcupacion().getFechaFin())
                    .espacioId(domain.getOcupacion().getEspacio() != null ?
                            domain.getOcupacion().getEspacio().getId() : null)
                    .estado(domain.getOcupacion().isEstado())
                    .build());
        }
    }
}