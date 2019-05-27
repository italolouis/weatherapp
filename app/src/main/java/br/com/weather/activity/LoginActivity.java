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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.weather.R;
import br.com.weather.model.Usuario;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mNovoUsuario;
    private TextView mEmailView;
    private TextView mPasswordView;
    private Button mEmailSignInButton;
    FirebaseAuth firebaseAuth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        mEmailView = (TextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mNovoUsuario = (TextView) findViewById(R.id.textNovoCadastro);
        mNovoUsuario.setOnClickListener(this);

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mNovoUsuario){
            Intent cadastroIntent = new Intent(LoginActivity.this, RegistrarActivity.class);
            LoginActivity.this.startActivity(cadastroIntent);
        }else if(v == mEmailSignInButton){
            usuario = new Usuario(mEmailView.getText().toString(), mPasswordView.getText().toString());
            if(usuario != null){
                firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    mainIntent.putExtra("USUARIO", usuario.getEmail());
                                    LoginActivity.this.startActivity(mainIntent);
                                }else{
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

        }

    }
}
