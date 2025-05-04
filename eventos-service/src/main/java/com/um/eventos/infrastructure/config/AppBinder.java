package com.um.eventos.infrastructure.config;

import com.um.eventos.application.ports.input.*;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.application.services.*;
import com.um.eventos.infrastructure.adapters.output.persistence.repositories.EventoRepositoryImpl;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AppBinder extends AbstractBinder {

    @Override
    protected void configure() {
        // Configuración JPA con implementación directa
        bindFactory(new EntityManagerFactoryProvider())
                .to(EntityManagerFactory.class)
                .in(Singleton.class);

        bindFactory(new EntityManagerProvider())
                .to(EntityManager.class);

        // Bindings de casos de uso (sin cambios)
        bind(CrearEventoUseCaseImpl.class).to(CrearEventoUseCase.class);
        bind(ObtenerEventoUseCaseImpl.class).to(ObtenerEventoUseCase.class);
        bind(ObtenerEventosPorEspacioUseCaseImpl.class).to(ObtenerEventosPorEspacioUseCase.class);
        bind(ResumenEventosUseCaseImpl.class).to(ResumenEventosUseCase.class);
        bind(ModificarEventoUseCaseImpl.class).to(ModificarEventoUseCase.class);

        // Binding del repositorio
        bind(EventoRepositoryImpl.class).to(EventoRepository.class);
    }

    // Proveedor como inner class
    public static class EntityManagerFactoryProvider implements Factory<EntityManagerFactory> {
        @Override
        public EntityManagerFactory provide() {
            return Persistence.createEntityManagerFactory("eventosPU");
        }

        @Override
        public void dispose(EntityManagerFactory instance) {
            if (instance != null && instance.isOpen()) {
                instance.close();
            }
        }
    }

    public static class EntityManagerProvider implements Factory<EntityManager> {
        @jakarta.inject.Inject  // Inyección Jakarta
        private EntityManagerFactory emf;

        @Override
        public EntityManager provide() {
            return emf.createEntityManager();
        }

        @Override
        public void dispose(EntityManager instance) {
            if (instance != null && instance.isOpen()) {
                instance.close();
            }
        }
    }
}