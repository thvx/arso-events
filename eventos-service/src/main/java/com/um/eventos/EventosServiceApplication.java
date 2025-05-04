package com.um.eventos;

import com.um.eventos.infrastructure.config.AppBinder;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class EventosServiceApplication extends ResourceConfig {

    public EventosServiceApplication() {
        // Registrar el binder para inyección de dependencias
        register(new AppBinder());

        // Configurar paquetes para escaneo de recursos
        packages("com.um.eventos.infrastructure.adapters.input.rest");

        // Habilitar Swagger
        register(OpenApiResource.class);

        // Configuración adicional
        property(ServerProperties.PROVIDER_PACKAGES,
                "com.um.eventos.infrastructure.adapters.input.rest");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }

    public static void main(String[] args) {
        final int PORT = 8080;
        final String CONTEXT_PATH = "/";

        // Configurar la aplicación Jersey
        ResourceConfig config = new EventosServiceApplication();

        // Configurar Jetty
        Server server = new Server(PORT);
        ServletContextHandler context = new ServletContextHandler(server, CONTEXT_PATH);

        // Configurar el servlet de Jersey
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));
        jerseyServlet.setInitOrder(0);
        context.addServlet(jerseyServlet, "/api/*");

        try {
            server.start();
            System.out.println("Servidor Jetty iniciado en http://localhost:" + PORT);
            System.out.println("API disponible en http://localhost:" + PORT + "/api");
            System.out.println("Swagger disponible en http://localhost:" + PORT + "/api/openapi.json");
            server.join();
        } catch (Exception e) {
            System.err.println("Error al iniciar Jetty: " + e.getMessage());
            System.exit(1);
        }
    }
}