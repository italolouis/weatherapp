package br.com.weather.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.weather.R;

public class InicioFragment extends Fragment {
    private EditText textHomeUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        Bundle bundle = new Bundle();
        String usuario = bundle.getString("USUARIO");
        if(usuario != null){
            textHomeUser.setText(usuario);
        }

        return view;
    }

}
