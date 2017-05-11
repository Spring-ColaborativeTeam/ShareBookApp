package com.sharebook.felipe.sharebookapp.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.auth0.android.jwt.JWT;
import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.LibroService;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RetrofiNetwork;
import com.sharebook.felipe.sharebookapp.security.model.ServerRequest;
import com.sharebook.felipe.sharebookapp.security.model.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btn_loggin;
    private EditText user;
    private EditText pass;
    private TextView mTitleAction;
    private TextView mPromptAction;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleAction = (TextView) findViewById(R.id.textView2);
        mPromptAction = (TextView) findViewById(R.id.textView3);
        mEditEmail = (EditText) findViewById(R.id.txt_user);
        mEditPassword = (EditText) findViewById(R.id.txt_password);
        btn_loggin = (Button) findViewById(R.id.btn_login);
        user = (EditText) findViewById(R.id.txt_user);
        pass = (EditText) findViewById(R.id.txt_password);

        btn_loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = user.getText().toString();
                password = pass.getText().toString();
                login();
                //startActivity( new Intent(getBaseContext(), MenuActivity.class ) );
            }
        });
    }

    public void registrar (View view){
        startActivity( new Intent(getBaseContext(), RegisterActivity.class ) );

    }

    private void login(){
        LibroService loginService = RetrofiNetwork.createService(LibroService.class, email, password);
        ServerRequest request = new ServerRequest();
        request.setUsername(email);
        request.setPassword(password);
        Call<ServerResponse> call = loginService.login(request);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.d("LoginActivity", response.raw().toString());
                if(response.code() == 200){
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userDetails", 0);
                    SharedPreferences.Editor editor = pref.edit();

                    JWT jwt = new JWT(response.body().getToken());
                    editor.putString("username", jwt.getSubject().toString());
                    editor.commit();
                    Log.d("LoginActivity -- jwt", jwt.getSubject().toString());
                    startActivity( new Intent(getBaseContext(), MenuActivity.class ) );
                }
                else{

                }



                //Log.d("LoginActivity", call.request().headers().toString());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d("Error", call.getClass().toString());
            }
        });
    }
}
