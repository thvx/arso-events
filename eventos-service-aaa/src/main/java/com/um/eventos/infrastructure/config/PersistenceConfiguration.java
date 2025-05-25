package com.um.eventos.infrastructure.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class PersistenceConfiguration {

    @Produces
    @PersistenceContext(unitName = "eventosPU")
    private EntityManager entityManager;

    // Configuración adicional del EntityManager
    /*
    @Produces
    @ApplicationScoped
    public EntityManagerFactory createEntityManagerFactory() {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");

        return Persistence.createEntityManagerFactory("eventosPU", properties);
    }
    */
}