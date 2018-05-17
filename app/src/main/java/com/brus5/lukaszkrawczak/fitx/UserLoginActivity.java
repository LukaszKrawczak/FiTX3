package com.brus5.lukaszkrawczak.fitx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Facebook.FacebookRegisterRequest;
import com.brus5.lukaszkrawczak.fitx.User.UserLoginRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserLoginActivity extends AppCompatActivity {

    private static final String TAG = "UserLoginActivity";

    LoginButton loginButton;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    TextView textView;
    CallbackManager callbackManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initializing FacebookSdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserLoginActivity.this, R.color.color_main_activity_statusbar));
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar2);

        // generate keyhash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.brus5.lukaszkrawczak.fitx",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }  } catch (PackageManager.NameNotFoundException e) { } catch (NoSuchAlgorithmException e) { }

        // releasing method updateWithToken
        // if connected via Facebook the first handler is gonna run up after one second
        // if not connected via Facebook the second handler is gonna run up after one second
        updateWithToken(AccessToken.getCurrentAccessToken());

        final int statusChecker = 0;

        final EditText etLogin = findViewById(R.id.etLogin);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button btLogin = findViewById(R.id.btLogin);
        final TextView tvRegister = findViewById(R.id.tvRegister);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        loginButton = findViewById(R.id.btFbLogin);
        textView = findViewById(R.id.tvLoginStatus);
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
            }
        };

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                ProgressDialog dialog = ProgressDialog.show(UserLoginActivity.this,"Loading...",
                        "Loading application View, please wait...", false, false);
                dialog.show();
                textView.setText("Login success \n" + loginResult.getAccessToken().getUserId() + "\n" + loginResult.getAccessToken().getToken());
                Log.e(TAG,"TESTUJE");

                GraphRequest request =  GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {

                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    String user_lastname = me.optString("last_name");
                                    String user_firstname = me.optString("first_name");
                                    String user_email = response.getJSONObject().optString("email");
                                    String birthday = me.optString("birthday");
                                    String gender = me.optString("gender");
                                    String location = me.optString("location");

                                    Log.e(TAG,"user_email: "+user_email);
                                    Log.e(TAG,"user_lastname: "+user_lastname);
                                    Log.e(TAG,"user_firstname: "+user_firstname);
                                    Log.e(TAG,"birthday: "+birthday);
                                    Log.e(TAG,"gender: "+gender);
                                    Log.e(TAG,"location: "+location);

                                    if (gender.equals("male")){
                                        gender = "m";
                                    }
                                    if (gender.equals("female")){
                                        gender = "w";
                                    }
                                    Log.e(TAG,"gender: "+gender);

                                    // constricting my new data type from 09/04/1989 to converted_birthday: 04.09.1989
                                    String day = birthday.substring(3,5);
                                    String month = birthday.substring(0,2);
                                    String year = birthday.substring(6,10);

                                    String converted_birthday = day+"."+month+"."+year;
                                    Log.e(TAG,"converted_birthday: "+converted_birthday);
                                    Log.e(TAG,"TESTUJE");

                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                boolean success = jsonObject.getBoolean("success");
                                                if (success) {
                                                    Toast.makeText(UserLoginActivity.this,"Created new account",Toast.LENGTH_LONG).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();

                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    boolean userused = jsonObject.getBoolean("userused");
                                                    if (userused){
                                                        Toast.makeText(UserLoginActivity.this,"Logged in with existing account",Toast.LENGTH_LONG).show();
                                                    }
                                                } catch (JSONException e1) {
                                                    e1.printStackTrace();
                                                }

                                            }
                                            Log.e("RESPONSE",""+response);
                                        }
                                    };

                                    FacebookRegisterRequest facebookRegisterRequest = new FacebookRegisterRequest(user_firstname,String.valueOf(loginResult.getAccessToken().getUserId()),16,"123",gender,user_email,responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(UserLoginActivity.this);

                                    queue.add(facebookRegisterRequest);
                                }
                            }
                        });
                Log.e(TAG,"TESTUJE");
                final Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location"); // pushing parameters from facebook
                request.setParameters(parameters);
                request.executeAsync();
                Log.e(TAG,"TESTUJE");
                Log.e(TAG,"request: "+request);

                loginButton.setVisibility(View.INVISIBLE);

                // starting activity after 2 seconds delay, because need to batch all data
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);
                        intent.putExtra("loginResult",loginResult.toString());
                        intent.putExtras(parameters);
                        startActivity(intent);
                        finish();
                    }
                },2000);

                Log.e(TAG,"loginResult: "+loginResult);
            }

            @Override
            public void onCancel() {
                Toast.makeText(UserLoginActivity.this,"Facebook login cancelled by user",Toast.LENGTH_LONG).show();
                Log.d("LOGIN_CANCEL", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(UserLoginActivity.this,"Cannot connect with Facebook account",Toast.LENGTH_LONG).show();
                Log.d("LOGIN_ERROR", "Error");
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        // getting accesstoken
        accessToken = AccessToken.getCurrentAccessToken();

        // if accesstoken isn't null then start new Activity which is MainActivity.class
        // after that, closing UserLoginActivity with finish();
        if (accessToken != null){
            Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        if (SaveSharedPreference.getUserName(UserLoginActivity.this).length() == 0){
            tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivityFirstPage.class);
                UserLoginActivity.this.startActivity(intent);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                final String userName = etLogin.getText().toString();
                final String password = etPassword.getText().toString();
                final Handler handler = new Handler();

                // Login... Trying with responseListener if theres a defined account name...
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                                    intent.putExtra("userName", userName);
                                    intent.putExtra("defaultLogin",true);
                                    SaveSharedPreference.setUserName(UserLoginActivity.this,userName);
                                    SaveSharedPreference.setDefLogin(UserLoginActivity.this,true);
                                    UserLoginActivity.this.startActivity(intent);
                                    finish();
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
                final UserLoginRequest userLoginRequest = new UserLoginRequest(userName, password, responseListener);
                final RequestQueue queue = Volley.newRequestQueue(UserLoginActivity.this);
                queue.add(userLoginRequest);
            }

        });
            // call Login Activity
        }
        else {
            Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
            intent.putExtra("defaultLogin",true);
            UserLoginActivity.this.startActivity(intent);
            finish();
        }
    }

    // running up facebookLogin
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    // checking currentAccessToken
    // if connected via Facebook the first handler is gonna run up after one second
    // if not connected via Facebook the second handler is gonna run up after one second
    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            Log.e(TAG,"Connected via Facebook");
            Log.e(TAG,"com.facebook.Profile.getCurrentProfile().getId()         "+com.facebook.Profile.getCurrentProfile().getId());
            Log.e(TAG,"com.facebook.Profile.getCurrentProfile().getFirstName()  "+com.facebook.Profile.getCurrentProfile().getFirstName());
            Log.e(TAG,"com.facebook.Profile.getCurrentProfile().getMiddleName() "+com.facebook.Profile.getCurrentProfile().getMiddleName());
            Log.e(TAG,"com.facebook.Profile.getCurrentProfile().getLastName()   "+com.facebook.Profile.getCurrentProfile().getLastName());
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }, 1000);
        } else {
            Log.e(TAG,"Not connected via Facebook");
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//
//                }
//            }, 1000);
        }
    }

}
