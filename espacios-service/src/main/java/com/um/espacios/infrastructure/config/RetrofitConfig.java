package com.um.espacios.infrastructure.config;

import com.um.espacios.infrastructure.adapters.output.retrofit.RetrofitEventoApi;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {

    private String eventosServiceUrl;

    @Bean
    public RetrofitEventoApi retrofitEventoApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(eventosServiceUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(RetrofitEventoApi.class);
    }
}