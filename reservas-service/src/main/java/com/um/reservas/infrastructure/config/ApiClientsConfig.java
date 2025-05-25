package com.um.reservas.infrastructure.config;

import com.um.reservas.application.ports.output.EventoRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ApiClientsConfig {

    @Value("${eventos.service.url}")
    private String eventosServiceUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(eventosServiceUrl)
                .build();
    }

    @Bean
    public EventoRepositoryPort eventosServicePort(WebClient webClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return factory.createClient(EventoRepositoryPort.class);
    }
}