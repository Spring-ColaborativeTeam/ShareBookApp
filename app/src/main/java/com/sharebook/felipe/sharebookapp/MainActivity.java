package com.sharebook.felipe.sharebookapp;

//import com.facebook.HttpMethod;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sharebook.felipe.sharebookapp.jwt.NetworkRequest;
import com.sharebook.felipe.sharebookapp.jwt.Token;

public class MainActivity extends AppCompatActivity {




    private Button btn_loggin;
    private EditText user;
    private EditText pass;
    private TextView mTitleAction;
    private TextView mPromptAction;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private ProgressDialog mProgressDialog;
    private AuthHelper mAuthHelper;
    private boolean mIsSignUpShowing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthHelper = AuthHelper.getInstance(this);
        mProgressDialog = new ProgressDialog(this);

        mTitleAction = (TextView) findViewById(R.id.textView2);
        mPromptAction = (TextView) findViewById(R.id.textView3);
        mEditEmail = (EditText) findViewById(R.id.txt_user);
        mEditPassword = (EditText) findViewById(R.id.txt_password);
        btn_loggin = (Button) findViewById(R.id.btn_login);

        setupView(mIsSignUpShowing);

        if (mAuthHelper.isLoggedIn()) {
            startActivity(PublicarFragment.getCallingIntent(this));
        }

    }

    public void registrar (View view){
        startActivity( new Intent(getBaseContext(), RegisterActivity.class ) );

    }

    /**
     * Sets up the view based on whether or not the sign up screen is showing
     *
     * @param isSignUpShowing - flag indicating whether the sign up form is showing
     */
    private void setupView(boolean isSignUpShowing) {
        mIsSignUpShowing = isSignUpShowing;
        btn_loggin.setOnClickListener(isSignUpShowing ? doSignUpClickListener : doLoginClickListener);
        mPromptAction.setOnClickListener(isSignUpShowing ? showLoginFormClickListener :
                showSignUpFormClickListener);
    }

    /**
     * Sign up the user and navigate to profile screen
     */
    private void doSignUp() {
        String username = user.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.toast_no_empty_field, Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressDialog.setMessage(getString(R.string.progress_signup));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        NetworkRequest request = new NetworkRequest();
        request.doSignUp(username, password, mSignUpCallback);
    }

    private void doLogin() {
        //Logger.getAnonymousLogger("entra login");
        String username = mEditEmail.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.toast_no_empty_field, Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressDialog.setMessage(getString(R.string.progress_login));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        NetworkRequest request = new NetworkRequest();
        request.doLogin(username, password, mLoginCallback);
    }

    /**
     * Save session details and navigates to the quotes activity
     * @param token - {@link Token} received on login or signup
     */
    private void saveSessionDetails(@NonNull Token token) {
        mAuthHelper.setIdToken(token);

        // start profile activity
        //startActivity(PublicarFragment.getCallingIntent(this));
        startActivity( new Intent(getBaseContext(), MenuActivity.class ) );
    }

    /**
     * Callback for login
     */
    private NetworkRequest.Callback<Token> mLoginCallback = new NetworkRequest.Callback<Token>() {
        @Override
        public void onResponse(@NonNull Token response) {
            dismissDialog();
            // save token and go to profile page
            saveSessionDetails(response);
        }

        @Override
        public void onError(String error) {
            dismissDialog();
            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public Class<Token> type() {
            return Token.class;
        }

    };

    /**
     * Callback for sign up
     */
    private NetworkRequest.Callback<Token> mSignUpCallback = new NetworkRequest.Callback<Token>() {
        @Override
        public void onResponse(@NonNull Token response) {
            dismissDialog();
            // save token and go to profile page
            saveSessionDetails(response);
        }

        @Override
        public void onError(String error) {
            dismissDialog();
            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public Class<Token> type() {
            return Token.class;
        }
    };

    /**
     * Dismiss the dialog if it's showing
     */
    private void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Click listener to show sign up form
     */
    private final View.OnClickListener showSignUpFormClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setupView(true);
        }
    };

    /**
     * Click listener to show login form
     */
    private final View.OnClickListener showLoginFormClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setupView(false);
        }
    };

    /**
     * Click listener to invoke login
     */
    private final View.OnClickListener doLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Logger.getAnonymousLogger("entro login");
            doLogin();
        }
    };

    /**
     * Click listener to invoke sign up
     */
    private final View.OnClickListener doSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            doSignUp();
        }
    };

    /*
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btn_loggin;
    protected static final String TAG = MainActivity.class.getSimpleName();
    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity( new Intent(getBaseContext(), MenuActivity.class ) );
                    finish();
                    Log.d("", "onAuthStateChanged:signed_in:" + user.getUid());
                    System.out.println("Login ok"+ user.getDisplayName());
                } else {
                    // User is signed out
                    Log.d("", "onAuthStateChanged:signed_out");
                }
            }
        };

        mCallbackManager  = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        btn_loggin = (Button)findViewById(R.id.btn_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d("", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                System.out.println("Login success" + loginResult);
                startActivity( new Intent(getBaseContext(), MenuActivity.class ) );
                finish();
            }

            @Override
            public void onCancel() {
                Log.d("", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("", "facebook:onError", error);
                System.out.println("Login error" + error);
            }


        });

    }

    public void registrar (View view){
        startActivity( new Intent(getBaseContext(), RegisterActivity.class ) );

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        mAuth.signOut();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);

        }
    }

    public void onLoginClicked( View view )
    {
        btn_loggin.setEnabled( false );
        mAuth.signInAnonymously();
        //new FetchSecuredResourceTask().execute();
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("", "signInWithCredential:onComplete:" + task.isSuccessful());
                        System.out.println("Entre a completaqr la autenticacion");
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
        }

    // ***************************************
    // Private methods
    // ***************************************
    private void displayResponse(Message response) {
        Toast.makeText(this, response.getText(), Toast.LENGTH_LONG).show();
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class FetchSecuredResourceTask extends AsyncTask<Void, Void, Message> {

        private String username;

        private String password;

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

            // build the message object
            EditText editText = (EditText) findViewById(R.id.txt_user);
            this.username = editText.getText().toString();

            editText = (EditText) findViewById(R.id.txt_password);
            this.password = editText.getText().toString();
        }

        @Override
        protected Message doInBackground(Void... params) {
            final String url = getString(R.string.base_uri);

            // Populate the HTTP Basic Authentitcation header with the username and password
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {
                // Make the network request
                Log.d(TAG, url);
                ResponseEntity<Message> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), Message.class);
                return response.getBody();
            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Message(0, e.getStatusText(), e.getLocalizedMessage());
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Message(0, e.getClass().getSimpleName(), e.getLocalizedMessage());
            }
        }

        @Override
        protected void onPostExecute(Message result) {
            dismissProgressDialog();
            displayResponse(result);
        }

    }


    // ...*/
    }
