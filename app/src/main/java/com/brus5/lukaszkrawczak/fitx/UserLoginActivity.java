package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.User.UserLoginRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class UserLoginActivity extends AppCompatActivity {

//    EditText etLogin;
//    EditText etPassword;
//    Button btLogin;
//    TextView tvRegister;
//    ProgressBar progressBar;

    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);


        final int statusChecker = 0;

        final EditText etLogin = findViewById(R.id.etLogin);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button btLogin = findViewById(R.id.btLogin);
        final TextView tvRegister = findViewById(R.id.tvRegister);
        final ProgressBar progressBar = findViewById(R.id.progressBar);


        loginButton = findViewById(R.id.btFbLogin);
        textView = findViewById(R.id.tvLoginStatus);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                textView.setText("Login success \n" + loginResult.getAccessToken().getUserId() + "\n" + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                textView.setText("Login canceled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, com.brus5.lukaszkrawczak.fitx.UserRegisterActivity.class);
                UserLoginActivity.this.startActivity(intent);
            }
        });



        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);




//                Handler handler1 = new Handler();
//                handler1.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                },5000);
//
//                etLogin.setVisibility(View.INVISIBLE);
//                etPassword.setVisibility(View.INVISIBLE);
//                btLogin.setVisibility(View.INVISIBLE);
//                tvRegister.setVisibility(View.INVISIBLE);
//
//                progressBar.setVisibility(View.VISIBLE);


                final String username = etLogin.getText().toString();
                final String password = etPassword.getText().toString();

                final Handler handler = new Handler();

//                handler.postDelayed(new Runnable() {
//                    private long time = 10;
//                    @Override
//                    public void run() {
//                        time += 2000;
//
//                        handler.postDelayed(this,2000);
//                    }
//                },1000);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                                    intent.putExtra("username", username);
                                    UserLoginActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserLoginActivity.this);
                                    builder.setMessage("Login failed")
                                            .setNegativeButton("Try again", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        Log.e("UserLoginActivity","Response"+response);
                    }
                };


                final UserLoginRequest userLoginRequest = new UserLoginRequest(username, password, responseListener);
                final RequestQueue queue = Volley.newRequestQueue(UserLoginActivity.this);

                handler.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        queue.add(userLoginRequest);
                    }
                }, 2000);
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
