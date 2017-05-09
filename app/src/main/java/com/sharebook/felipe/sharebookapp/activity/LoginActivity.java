package com.sharebook.felipe.sharebookapp.activity;

//import com.facebook.HttpMethod;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RetrofiNetwork;

public class LoginActivity extends AppCompatActivity {

    private RetrofiNetwork retrofiNetwork;
    private String email;
    private String password;
    private Button btn_loggin;
    private EditText user;
    private EditText pass;
    private TextView mTitleAction;
    private TextView mPromptAction;
    private EditText emailET;
    private EditText passwordET;
    private final static String TAG = "LoginActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofiNetwork = new RetrofiNetwork();

        mTitleAction = (TextView) findViewById(R.id.textView2);
        mPromptAction = (TextView) findViewById(R.id.textView3);
        emailET = (EditText) findViewById(R.id.txt_user);
        passwordET = (EditText) findViewById(R.id.txt_password);
        btn_loggin = (Button) findViewById(R.id.btn_login);

        btn_loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                startActivity( new Intent(getBaseContext(), MenuActivity.class ) );
                //login(email, password);
            }
        });
    }

    public void registrar (View view){
        startActivity( new Intent(getBaseContext(), RegisterActivity.class ) );
    }

}
