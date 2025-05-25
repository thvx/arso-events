package com.um.reservas.infrastructure.output.persistence.repository;

import com.um.reservas.infrastructure.output.persistence.entity.EventoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoMongoRepository extends MongoRepository<EventoDocument, String> {
}