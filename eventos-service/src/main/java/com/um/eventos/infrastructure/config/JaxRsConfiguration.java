package com.um.eventos.infrastructure.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/api")
public class JaxRsConfiguration extends ResourceConfig {

    public JaxRsConfiguration() {
        // Paquete donde están los controladores REST
        packages("com.um.eventos.infrastructure.adapters.input.rest.controller");

        // Configuración adicional de Jersey
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);

        // Habilitar CORS
        register(CorsFilter.class);

        // Registrar proveedores JSON
        register(JacksonFeature.class);
    }

    // Filtro CORS para desarrollo
    public static class CorsFilter implements ContainerResponseFilter {
        @Override
        public void filter(ContainerRequestContext requestContext,
                           ContainerResponseContext responseContext) {
            responseContext.getHeaders().add(
                    "Access-Control-Allow-Origin", "*");
            responseContext.getHeaders().add(
                    "Access-Control-Allow-Credentials", "true");
            responseContext.getHeaders().add(
                    "Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            responseContext.getHeaders().add(
                    "Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        }
    }
}