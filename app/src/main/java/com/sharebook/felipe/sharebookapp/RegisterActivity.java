package com.sharebook.felipe.sharebookapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sharebook.felipe.sharebookapp.persistence.dao.model.RetrofiNetwork;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Usuario;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.txt_password);
        EditText nombre = (EditText) findViewById(R.id.nombre);
        EditText cel = (EditText) findViewById(R.id.celular);
        EditText confirm = (EditText) findViewById(R.id.txt_password_confirm);
        registro = (Button) findViewById(R.id.button3);
        this.email = email.getText().toString();
        this.password = pass.getText().toString();
        this.celular = cel.getText().toString();
        this.confirm_password = confirm.getText().toString();

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registro();
            }
        });



    }

    public void cancelar (View view){
        startActivity( new Intent(getBaseContext(), MainActivity.class ) );

    }

    public void registro(){

        Usuario usuario = new Usuario();
        usuario.setCelular(this.celular);
        usuario.setEmail(this.email);
        usuario.setPassword(this.password);
        usuario.setNombre(this.nombre);
        resources = new RetrofiNetwork();
        resources.registroUsuario(usuario);
    }

}
