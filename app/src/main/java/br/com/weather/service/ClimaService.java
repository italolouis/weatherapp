package br.com.weather.service;

import br.com.weather.model.Dados;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClimaService {

    @GET("weather")
    Call<Dados> getClimaCidade(
            @Query("q") String cidade,
            @Query("APPID") String id,
            @Query("units") String metric
    );

    @GET("weather")
    Call<Dados> getClimaCoordenadas(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("APPID") String id,
            @Query("units") String metric
    );
}
