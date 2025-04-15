package com.um.espacios.infrastructure.adapters.output.persistence.repository;

import com.um.espacios.infrastructure.adapters.output.persistence.entitiy.EspacioFisicoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataEspacioRepository extends MongoRepository<EspacioFisicoDocument, String> {
}
