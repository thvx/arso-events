package com.um.eventos.infrastructure.config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import com.um.eventos.infrastructure.adapters.output.retrofit.RetrofitEspacioApi;

@ApplicationScoped
public class RetrofitConfig {

    @Produces
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(System.getProperty("retrofit.espacios-service.url", "http://espacios-service:8081"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Produces
    public RetrofitEspacioApi espacioApi(Retrofit retrofit) {
        return retrofit.create(RetrofitEspacioApi.class);
    }
}