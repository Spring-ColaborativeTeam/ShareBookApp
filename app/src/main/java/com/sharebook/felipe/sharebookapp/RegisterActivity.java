package com.sharebook.felipe.sharebookapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by 2087052 on 5/3/17.
 */

public class RegisterActivity extends AppCompatActivity {

    private String email;
    private String password;
    private String confirm_password;
    private String celular;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.txt_password);
        EditText nombre = (EditText) findViewById(R.id.nombre);
        EditText cel = (EditText) findViewById(R.id.celular);
        EditText confirm = (EditText) findViewById(R.id.txt_password_confirm);
        this.email = email.getText().toString();
        this.password = pass.getText().toString();
        this.celular = cel.getText().toString();
        this.confirm_password = confirm.getText().toString();



    }

    public void cancelar (View view){
        startActivity( new Intent(getBaseContext(), MainActivity.class ) );

    }

}
