package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.User.UserRegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Entering the readable text fields

        final EditText etName = findViewById(R.id.etName);
        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etAge = findViewById(R.id.etAge);
        final EditText etEmail = findViewById(R.id.etEmail);

        final Button btRegister = findViewById(R.id.btRegister);

        final RadioButton rbWoman = findViewById(R.id.rbWoman);
        final RadioButton rbMan = findViewById(R.id.rbMan);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Save EditText to Strings variables


                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final int age = Integer.parseInt(etAge.getText().toString());
                final String email = etEmail.getText().toString();

                String male = "";

                if (rbMan.isChecked()){
                    male = "m";
                }
                else if (rbWoman.isChecked()){
                    male = "w";
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
//                            boolean userused = jsonObject.getBoolean("userused");
                            if (success) {
                                Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                                UserRegisterActivity.this.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean userused = jsonObject.getBoolean("userused");
                                if (userused){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
                                    builder.setMessage("Woops! Someone has exacly the same nickname. Please change it.")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                        Log.e("RESPONSE",""+response);
                    }
                };
                System.out.println(male);

                UserRegisterRequest userRegisterRequest = new UserRegisterRequest(name, username, age, password, male, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserRegisterActivity.this);
                queue.add(userRegisterRequest);
            }
        });
    }




}
