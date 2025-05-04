package com.um.eventos.infrastructure.config;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Eventos",
                version = "1.0.0",
                description = "API para gestión de eventos",
                contact = @Contact(
                        name = "Soporte",
                        email = "soporte@eventos.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
@ApplicationPath("/api")
public class SwaggerConfig extends ResourceConfig {

    public SwaggerConfig() {
        // Registrar el paquete donde están tus recursos JAX-RS
        packages("package com.um.eventos.infrastructure.adapters.input.rest");

        // Registrar el recurso de Swagger/OpenAPI
        register(OpenApiResource.class);

        // Registrar el binder para inyección de dependencias
        register(new AppBinder());

        // Configuración adicional de Jersey
        property("jersey.config.server.wadl.disableWadl", "true");
    }
}