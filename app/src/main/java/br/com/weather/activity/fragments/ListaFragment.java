package br.com.weather.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.weather.R;
import br.com.weather.RecyclerDadosAdapter;
import br.com.weather.model.Dados;


public class ListaFragment extends Fragment implements RecyclerDadosAdapter.ItemListener, View.OnClickListener{
    private RecyclerView recyclerView;
    private List<Dados> dadosArray;
    RecyclerDadosAdapter recyclerDadosAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        recyclerView = view.findViewById(R.id.recycler_dados);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        dadosArray = new ArrayList<Dados>();
        getDadosDatabase(container);

        return view;
    }

    public void getDadosDatabase(final ViewGroup container){
        databaseReference.child("Clima").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dadosArray.clear();
                for (DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Dados dado = objSnapshot.getValue(Dados.class);
                    dadosArray.add(dado);
                    mLayoutManager = new LinearLayoutManager(container.getContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerDadosAdapter = new RecyclerDadosAdapter(container.getContext(), dadosArray, null);
                    recyclerView.setAdapter(recyclerDadosAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onItemClick(Dados item) {

    }

    @Override
    public void onClick(View v) {

    }
}
