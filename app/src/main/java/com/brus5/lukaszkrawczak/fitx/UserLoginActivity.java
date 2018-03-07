package com.brus5.lukaszkrawczak.fitx;

import android.app.ProgressDialog;
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
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
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

    private static final String TAG = "UserLoginActivity";

    public int SPLASH_TIME_OUT = 10;

    LoginButton loginButton;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    TextView textView;
    CallbackManager callbackManager;
    LoginResult loginResult;
    int isLogged = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

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
//                updateWithToken(newAccessToken);
            }
        };

//        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends","user_birthday","id"));
//        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                textView.setText("Login success \n" + loginResult.getAccessToken().getUserId() + "\n" + loginResult.getAccessToken().getToken());



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

                                    Log.e(TAG,"user_email: "+user_email);
                                    Log.e(TAG,"user_lastname: "+user_lastname);
                                    Log.e(TAG,"user_firstname: "+user_firstname);
                                    Log.e(TAG,"birthday: "+birthday);
                                    Log.e(TAG,"gender: "+gender);

                                    if (gender.equals("male")){
                                        gender = "m";
                                    }
                                    if (gender.equals("female")){
                                        gender = "w";
                                    }
                                    Log.e(TAG,"gender: "+gender);

                                    String day = birthday.substring(3,5);
                                    String month = birthday.substring(0,2);
                                    String year = birthday.substring(6,10);

                                    String converted_birthday = day+"."+month+"."+year;
                                    Log.e(TAG,"converted_birthday: "+converted_birthday);

                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                        }
                                    };

//                                    FacebookRegisterRequest facebookRegisterRequest = new FacebookRegisterRequest(user_firstname,String.valueOf(loginResult.getAccessToken().getUserId()),16,"123",gender,user_email,responseListener);
//                                    RequestQueue queue = Volley.newRequestQueue(UserLoginActivity.this);
//                                    queue.add(facebookRegisterRequest);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
//                parameters.putString("fields", "last_name,first_name,email,birthday");
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();

                Log.e(TAG,"request: "+request);

//                setFacebookData(loginResult);


                loginButton.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);
                intent.putExtra("loginResult",loginResult.toString());
//                intent.putExtra("username",loginResult.getAccessToken().getUserId());
                startActivity(intent);

                final ProgressDialog dialog=new ProgressDialog(UserLoginActivity.this);
                dialog.setMessage("Loading data");
                dialog.setCancelable(false);
                dialog.setInverseBackgroundForced(true);
                dialog.show();

//                finish();
                Log.e(TAG,"loginResult: "+loginResult);
                dialog.hide();
            }

            @Override
            public void onCancel() {
                Log.d("LOGIN_CANCEL", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("LOGIN_ERROR", "Error");
            }
        });



        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null){
            Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

//        isConnected();

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
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
                                    intent.putExtra("defaultLogin",true);
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
        Log.i("Response",String.valueOf(requestCode));
        Log.i("Response",String.valueOf(resultCode));
        Log.i("Response",String.valueOf(data));
    }

    private void setFacebookData(LoginResult loginResult){
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                // Application code
                try {
                    Log.i("Response",response.toString());

                    String email = response.getJSONObject().getString("email");
                    String firstName = response.getJSONObject().getString("first_name");
                    String lastName = response.getJSONObject().getString("last_name");
                    String gender = response.getJSONObject().getString("gender");

                    Profile profile = Profile.getCurrentProfile();
                    String id = profile.getId();
                    String link = profile.getLinkUri().toString();
                    Log.i("Link",link);
                    if (Profile.getCurrentProfile()!=null)
                    {
                        Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                    }

                    Log.i("Login" + "Email", email);
                    Log.i("Login"+ "FirstName", firstName);
                    Log.i("Login" + "LastName", lastName);
                    Log.i("Login" + "Gender", gender);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Log.e(TAG,"run1");
                    Log.e(TAG,"com.facebook.Profile.getCurrentProfile().getId() "+com.facebook.Profile.getCurrentProfile().getId());
                    Log.e(TAG,"com.facebook.Profile.getCurrentProfile().getId() "+com.facebook.Profile.getCurrentProfile().getFirstName());
                    Log.e(TAG,"com.facebook.Profile.getCurrentProfile().getId() "+com.facebook.Profile.getCurrentProfile().getMiddleName());
                    Log.e(TAG,"com.facebook.Profile.getCurrentProfile().getId() "+com.facebook.Profile.getCurrentProfile().getLastName());

//                    Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);
//                    intent.putExtra("username",com.facebook.Profile.getCurrentProfile().getId());
//
//                    Log.e(TAG,"intent "+intent);

//                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(UserLoginActivity.this);
//                    SharedPreferences.Editor editor = prefs.edit();
//                    editor.putString("string_id", com.facebook.Profile.getCurrentProfile().getId()); //InputString: from the EditText
//                    editor.commit();
                }
            }, 1000);
        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Log.e(TAG,"run2");

                }
            }, 1000);
        }
    }


}
