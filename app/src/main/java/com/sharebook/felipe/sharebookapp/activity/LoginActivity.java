package com.sharebook.felipe.sharebookapp.activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sharebook.felipe.sharebookapp.R;

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

        btn_loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = user.getText().toString();
                password = pass.getText().toString();
                startActivity( new Intent(getBaseContext(), MenuActivity.class ) );
            }
        });



    }

    public void registrar (View view){
        startActivity( new Intent(getBaseContext(), RegisterActivity.class ) );

    }



}
