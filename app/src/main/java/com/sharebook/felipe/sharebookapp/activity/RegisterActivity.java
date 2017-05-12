package com.sharebook.felipe.sharebookapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.LibroService;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RetrofiNetwork;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2087052 on 5/3/17.
 */

public class RegisterActivity extends AppCompatActivity {

    private String email;
    private String password;
    private String confirm_password;
    private String celular;
    private String nombre;
    private RetrofiNetwork resources;
    private Button registro;
    private EditText email_t;
    private EditText pass_t;
    private EditText nombre_t;
    private EditText cel_t;
    private EditText confirm_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        email_t = (EditText) findViewById(R.id.email);
        pass_t = (EditText) findViewById(R.id.txt_password);
        nombre_t = (EditText) findViewById(R.id.nombre);
        cel_t = (EditText) findViewById(R.id.celular);
        confirm_t = (EditText) findViewById(R.id.txt_password_confirm);
    }

    public void cancelar (View view){
        startActivity( new Intent(getBaseContext(), LoginActivity.class ) );

    }

    public void registro(View view){
        LibroService libroService = RetrofiNetwork.createService(LibroService.class, "sad", "sa");
        Log.d("sadsdasdasdadadas", "entro registro");
        this.email = email_t.getText().toString();
        this.password = pass_t.getText().toString();
        this.celular = cel_t.getText().toString();
        this.confirm_password = confirm_t.getText().toString();
        this.nombre = nombre_t.getText().toString();
        Usuario usuario = new Usuario();
        usuario.setCelular(this.celular);
        usuario.setEmail(this.email);
        usuario.setPassword(this.password);
        usuario.setNombre(this.nombre);
        usuario.setImagen("x");
        //resources = new RetrofiNetwork();
        //resources.registroUsuario(usuario);

        Call<Usuario> call = libroService.createUser(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                startActivity( new Intent(getBaseContext(), LoginActivity.class ) );
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                startActivity( new Intent(getBaseContext(), LoginActivity.class ) );
            }
        });


    }

}
