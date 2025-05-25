package com.um.espacios.infrastructure.adapters.output.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitEventoApi {
    @GET("/api/eventos/espacios/{espacioId}/ocupaciones-activas")
    Call<Boolean> tieneOcupacionesActivas(@Path("espacioId") String espacioId);
}