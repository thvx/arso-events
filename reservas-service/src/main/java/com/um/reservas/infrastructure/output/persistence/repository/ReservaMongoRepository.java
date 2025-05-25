package com.um.reservas.infrastructure.output.persistence.repository;

import com.um.reservas.infrastructure.output.persistence.entity.ReservaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaMongoRepository extends MongoRepository<ReservaDocument, String> {
    List<ReservaDocument> findByEventoId(String eventoId);
}