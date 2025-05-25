package com.um.eventos.infrastructure.adapters.output.retrofit;

import com.um.eventos.domain.model.EspacioFisico;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitEspacioApi {
    @GET("/api/espacios/{id}")
    Call<EspacioFisico> getEspacio(@Path("id") String espacioId);

    @GET("/api/espacios/{id}/disponibilidad")
    Call<Boolean> verificarDisponibilidad(@Path("id") String espacioId);
}