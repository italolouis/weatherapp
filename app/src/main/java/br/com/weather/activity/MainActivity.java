package br.com.weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.com.weather.R;
import br.com.weather.activity.fragments.ContaFragment;
import br.com.weather.activity.fragments.ListaFragment;
import br.com.weather.activity.fragments.InicioFragment;
import br.com.weather.activity.fragments.BuscarFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigation;
    private String usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        getUsuario();
        loadFragment(new InicioFragment());
    }

    private void getUsuario() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            usuarioLogado = extras.getString("USUARIO");
        }
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
       Fragment fragment = null;
       switch (menuItem.getItemId()){
           case R.id.navigation_inicio:
               fragment = new InicioFragment();
               Bundle bundle = new Bundle();
               bundle.putString("USUARIO", usuarioLogado);
               fragment.setArguments(bundle);
               break;
           case R.id.navigation_buscar:
               fragment = new BuscarFragment();
               break;
           case R.id.navigation_listar:
               fragment = new ListaFragment();
               break;

           case R.id.navigation_conta:
               fragment = new ContaFragment();
               break;
       }
       return loadFragment(fragment);
    }
}
