package br.com.weather.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Random;

import br.com.weather.R;
import br.com.weather.model.Dados;
import br.com.weather.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuscarFragment extends Fragment implements View.OnClickListener {
    private ImageView imageViewBuscar;
    private EditText edtExercicioBuscar;
    private EditText edtLatBuscar;
    private EditText edtLonBuscar;
    private TextView textTemperatura;
    private TextView textPressao;
    private TextView textUmidade;
    private TextView textTempMax;
    private TextView textTempMin;
    private TextView labelTemperatura;
    private TextView labelPressao;
    private TextView labelUmidade;
    private TextView labelTempMax;
    private TextView labelTempMin;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);
        edtExercicioBuscar = (EditText) view.findViewById(R.id.edtCidadeBuscar);
        edtLatBuscar = (EditText) view.findViewById(R.id.edtLatBuscar);
        edtLonBuscar = (EditText) view.findViewById(R.id.edtLonBuscar);

        imageViewBuscar = (ImageView) view.findViewById(R.id.imageViewBuscar);
        imageViewBuscar.setOnClickListener(this);

        textTemperatura = (TextView) view.findViewById(R.id.textTemperatura);
        textPressao = (TextView)view.findViewById(R.id.textPressao);
        textUmidade = (TextView) view.findViewById(R.id.textUmidade);
        textTempMax = (TextView) view.findViewById(R.id.textTempMax);
        textTempMin = (TextView) view.findViewById(R.id.textTempMin);

        labelTemperatura = (TextView) view.findViewById(R.id.labelTemperatura);
        labelPressao = (TextView) view.findViewById(R.id.labelPressao);
        labelUmidade = (TextView) view.findViewById(R.id.labelUmidade);
        labelTempMax = (TextView) view.findViewById(R.id.labelTempMax);
        labelTempMin= (TextView) view.findViewById(R.id.labelTempMin);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == imageViewBuscar){
            String cidade = edtExercicioBuscar.getText().toString();
            String latitude = edtLatBuscar.getText().toString();
            String longitude = edtLonBuscar.getText().toString();

            if(!cidade.isEmpty()){
                final Call<Dados> call = new RetrofitInicializador().getClima().getClimaCidade(cidade, RetrofitInicializador.APPID.toString(), RetrofitInicializador.UNIT);
                call.enqueue(new Callback<Dados>() {
                    @Override
                    public void onResponse(Call<Dados>  call, Response<Dados> response) {
                        Dados dados = response.body();
                        setLabel();
                        populaViewClima(dados);
                        insereDatabase(dados);
                    }

                    //Nao deu certo a execução
                    @Override
                    public void onFailure(Call<Dados> call, Throwable t) {
                        Log.e("onFailure", "Requisicao falhou");
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else if(!latitude.isEmpty() && !longitude.isEmpty()){
                final Call<Dados> call = new RetrofitInicializador().getClima().getClimaCoordenadas(latitude, longitude, RetrofitInicializador.APPID.toString(), RetrofitInicializador.UNIT);
                call.enqueue(new Callback<Dados>() {
                    @Override
                    //Conseguiu conectar com o servidor
                    public void onResponse(Call<Dados>  call, Response<Dados> response) {
                        Dados dados = response.body();
                        setLabel();
                        populaViewClima(dados);
                    }

                    @Override
                    public void onFailure(Call<Dados> call, Throwable t) {
                        Log.e("onFailure", "Requisicao falhou");
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void populaViewClima(Dados dados){
        textTemperatura.setText(getTextCelius(Double.toString(dados.getMain().getTemp())));
        textPressao.setText(Double.toString(dados.getMain().getPressure()));
        textUmidade.setText(Double.toString(dados.getMain().getHumidity()));
        textTempMin.setText(getTextCelius(Double.toString(dados.getMain().getTemp_min())));
        textTempMax.setText(getTextCelius(Double.toString(dados.getMain().getTemp_max())));
    }

    private String getTextCelius(String valor){
        return valor + "°C";
    }

    private void setLabel(){
        labelTemperatura.setText("Temperatura: ");
        labelPressao.setText("Pressão: ");
        labelUmidade.setText("Umidade: ");
        labelTempMax.setText("Temperatura max: ");
        labelTempMin.setText("Temperatura min: ");
    }

    private void insereDatabase(Dados dados){
        Random gerador = new Random();
        dados.setId(dados.getId()+gerador.nextLong());
        dados.setDia(new Date());
        databaseReference.child("Clima").child(dados.getId().toString()).setValue(dados);
    }

}
