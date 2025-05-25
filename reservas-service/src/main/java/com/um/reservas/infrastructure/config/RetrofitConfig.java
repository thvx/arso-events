package com.um.reservas.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Bean
    public Retrofit retrofit(ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(new OkHttpClient())
                .build();
    }
}