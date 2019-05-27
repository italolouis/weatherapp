package br.com.weather.retrofit;
import br.com.weather.service.ClimaService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializador {
    private final Retrofit retrofit;
    public static final String APPID = "0a58196515691b7b9bfdb5e30ab9dc05";
    public static final String UNIT = "metric";

    public RetrofitInicializador(){
        //Criação do interceptador
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //Criação do cliente
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public ClimaService getClima() {
        return retrofit.create(ClimaService.class);
    }
}
