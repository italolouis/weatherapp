package br.com.weather.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.weather.R;
import br.com.weather.model.Usuario;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView email;
    private EditText senha;
    private Button mEmailSignInButton;
    FirebaseAuth firebaseAuth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        email = (EditText) findViewById(R.id.edtCadastroEmail);
        senha = (EditText) findViewById(R.id.edtCadastroSenha);

        mEmailSignInButton = (Button) findViewById(R.id.btn_cadastro);
        mEmailSignInButton.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        if(v == mEmailSignInButton){
            usuario = new Usuario(email.getText().toString(), senha.getText().toString());
            firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegistrarActivity.this, "Cadastrado com sucesso",
                                        Toast.LENGTH_LONG).show();
                                Intent loginIntent = new Intent(RegistrarActivity.this, LoginActivity.class);
                                RegistrarActivity.this.startActivity(loginIntent);
                            }else{
                                Toast.makeText(RegistrarActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }
}
