package com.um.reservas.infrastructure.output.persistence.repository;

import com.um.reservas.infrastructure.output.persistence.entity.ReservaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataEventoRepository extends MongoRepository<ReservaDocument, String> {
}
