package br.com.weather.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.weather.R;

public class ContaFragment extends Fragment implements View.OnClickListener {
    private Button btnSair;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conta, container, false);
        btnSair = view.findViewById(R.id.btn_Sair);
        btnSair.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        getActivity().moveTaskToBack(true);
        getActivity().finishAffinity();
    }
}
