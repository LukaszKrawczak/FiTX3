package com.brus5.lukaszkrawczak.fitx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.User.UserInfoShowRequest;
import com.brus5.lukaszkrawczak.fitx.User.UserProfileUpdateRequest;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class UserProfileActivity extends AppCompatActivity {

    private final static String TAG = "UserProfileActivity";

    String UserInfo = "", name, username, age, password, email, male, height, weight, somatotype, proteinsratio, fatsratio, carbsratio, id1;
    boolean defaultLogin = false;
    int id;
    String wynik;

    /* Gettings date */
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String dateToday = simpleDateFormat.format(c.getTime());


    EditText editTextUserFirstName, editTextUserName, editTextUserbirthday,editTextUserPassword,editTextUserEmail,editTextUserHeight,editTextUserWeight, editTextProteinsRatio, editTextFatsRatio, editTextCarbsRatio;
    SeekBar seekBarUserSomatotype;
    ImageView imageViewUserSomatotype;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user_profile);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserProfileActivity.this,R.color.color_profile_activity_statusbar));

        init();


        Intent intent = getIntent();
        username = intent.getStringExtra("userName");
        Log.e(TAG,"username "+username);
        defaultLogin = intent.getBooleanExtra("defaultLogin",false);
//        final String username1 = username;
        editTextUserName.setText(username);
        editTextUserName.setEnabled(false);
        editTextUserPassword.setEnabled(false);
        if (defaultLogin){
            editTextUserName.setEnabled(true);
            editTextUserPassword.setEnabled(true);
        }

        getData();
        seekbar();

    }

    private void init() {
        editTextUserFirstName = findViewById(R.id.editTextUserFirstName);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextUserbirthday = findViewById(R.id.editTextUserbirthday);
        editTextUserPassword = findViewById(R.id.editTextUserPassword);
        editTextUserEmail = findViewById(R.id.editTextUserEmail);
        editTextUserHeight = findViewById(R.id.editTextUserHeight);
        editTextUserWeight = findViewById(R.id.editTextUserWeight);
        seekBarUserSomatotype = findViewById(R.id.seekBarUserSomatotype);
        editTextProteinsRatio = findViewById(R.id.editTextProteinsRatio);
        editTextFatsRatio = findViewById(R.id.editTextFatsRatio);
        editTextCarbsRatio = findViewById(R.id.editTextCarbsRatio);
        imageViewUserSomatotype = findViewById(R.id.imageViewUserSomatotype);

        Button buttonProfileAccept = findViewById(R.id.buttonProfileAccept);
        Button buttonProfileLogout = findViewById(R.id.buttonProfileLogout);


            buttonProfileAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String updatename = editTextUserFirstName.getText().toString();
                    final String updateusername = editTextUserName.getText().toString();
                    final String updatebirthday = editTextUserbirthday.getText().toString();
                    final String updatepassword = editTextUserPassword.getText().toString();
                    final String updateemail = editTextUserEmail.getText().toString();
                    final Double updateheight = Double.valueOf(editTextUserHeight.getText().toString());
                    final Double updateweight = Double.valueOf(editTextUserWeight.getText().toString());
                    final Double updatesomatotype = Double.valueOf(somatotype);
                    final Double updateproteinsratio = Double.valueOf(editTextProteinsRatio.getText().toString());
                    final Double updatefatsratio = Double.valueOf(editTextFatsRatio.getText().toString());
                    final Double updatecarbsratio = Double.valueOf(editTextCarbsRatio.getText().toString());

                    double ratioresult = Double.valueOf(editTextProteinsRatio.getText().toString()) + Double.valueOf(editTextFatsRatio.getText().toString()) + Double.valueOf(editTextCarbsRatio.getText().toString());

                    if (ratioresult > 100d || ratioresult < 100d){
                        Log.d(TAG, "onResponse: ratio result false "+ratioresult);
                        Toast.makeText(UserProfileActivity.this, "Total value of nutrients ratio should be 100, not more not less", Toast.LENGTH_SHORT).show();
                    }
                    else if (ratioresult == 100d) {
                        Log.d(TAG, "onResponse: ratio result is good " + ratioresult);


                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (editTextUserFirstName.length() <= 1 /*|| etPassword.length() <= 2*/ || editTextUserName.length() <= 2) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                                    builder.setMessage("Please enter proper data")
                                            .setNegativeButton("OK", null)
                                            .create()
                                            .show();
                                } else {
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) {
                                            final Intent intent = new Intent(UserProfileActivity.this, UserLoginActivity.class);
                                            AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                                            builder.setMessage("Information changed, you'll be logged off")
                                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            UserProfileActivity.this.startActivity(intent);
                                                            onDestroy();
                                                        }
                                                    })
                                                    .show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    Log.e(TAG, "response " + response);
                                }
                            }


                        };

                        UserProfileUpdateRequest userProfileUpdateRequest = new UserProfileUpdateRequest(updatename, username, updateusername, updatebirthday, updatepassword, updateemail, updateheight, updateweight, updatesomatotype, updateproteinsratio, updatefatsratio, updatecarbsratio, dateToday, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(UserProfileActivity.this);
                        queue.add(userProfileUpdateRequest);
                    }
                }
            });

        buttonProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(UserProfileActivity.this);
                alert.setTitle("Logout")
                        .setMessage("Do you want to Logout?")
                        .setNegativeButton("Cancel",null)
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                                LoginManager.getInstance().logOut();
                                Intent intent = new Intent(UserProfileActivity.this,UserLoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();
            }
        });
    }

    private void getData() {
        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"response"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

//                    JSONArray aJsonString = jsonObject.getJSONArray("array()");
//                    Log.e("CHECK AJSONSTRING",""+aJsonString);
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                        builder.setMessage("username up to date")
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                        Intent intent1 = new Intent(UserProfileActivity.this,UserLoginActivity.class);
                        UserProfileActivity.this.startActivity(intent1);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                wynik = response;
                Log.e(TAG,    "ResponseListener "  +response);
                Log.e(TAG,    "wynik "             +wynik);

                String result;
                result = wynik.replaceAll("[\\p{Punct}&&[^@.]]"," ");

                String[] words;

                words = result.split("\\s+");

                id1 = words[5];
                id = Integer.parseInt(id1);
                name = words[7];
                username = words[9];
                age = words[11];
                password = words[13];
                email = words[15];
                male = words[17];
                height = words[19];
                weight = words[21];
                somatotype = words[23];
                proteinsratio = words[25];
                fatsratio = words[27];
                carbsratio = words[29];

                Log.e(TAG,   "Arrays.toString(words): " + Arrays.toString(words));
                Log.e(TAG,   "id:                     " + id);
                Log.e(TAG,   "name:                   " + name);
                Log.e(TAG,   "username:               " + username);
                Log.e(TAG,   "birthday:               " + age);
                Log.e(TAG,   "password:               " + password);
                Log.e(TAG,   "email:                  " + email);
                Log.e(TAG,   "male:                   " + male);
                Log.e(TAG,   "height:                 " + height);
                Log.e(TAG,   "weight:                 " + weight);
                Log.e(TAG,   "somatotype:             " + somatotype);
                Log.e(TAG,   "proteinsratio:          " + proteinsratio);
                Log.e(TAG,   "fatsratio:              " + fatsratio);
                Log.e(TAG,   "carbsratio:             " + carbsratio);
                Log.e(TAG,   "words:                  " + Arrays.toString(words));


                editTextUserFirstName.setText(name);
//                editTextUserName.setText(username);
                editTextUserbirthday.setText(age);
                editTextUserPassword.setText(password);
                editTextUserEmail.setText(email);
                editTextUserHeight.setText(height);
                editTextUserWeight.setText(weight);
                editTextProteinsRatio.setText(proteinsratio);
                editTextFatsRatio.setText(fatsratio);
                editTextCarbsRatio.setText(carbsratio);

                if (male.equals("m")) {
                    if (Double.valueOf(somatotype) == 200d) {
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_man_endomorph_);
                        seekBarUserSomatotype.setProgress(0);
                    } else if (Double.valueOf(somatotype) == 500d) {
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_man_mezomorph_);
                        seekBarUserSomatotype.setProgress(1);
                    } else if (Double.valueOf(somatotype) == 900d) {
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_man_ectomorph_);
                        seekBarUserSomatotype.setProgress(2);
                    }
                } else if (male.equals("w")){
                    if (Double.valueOf(somatotype) == 200d) {
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_woman_endomorph_);
                        seekBarUserSomatotype.setProgress(0);
                    } else if (Double.valueOf(somatotype) == 500d) {
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_woman_mezomorph_);
                        seekBarUserSomatotype.setProgress(1);
                    } else if (Double.valueOf(somatotype) == 900d) {
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_woman_ectomorph_);
                        seekBarUserSomatotype.setProgress(2);
                    }
                }

            }
        };

        UserInfoShowRequest userInfoShowRequest = new UserInfoShowRequest(username, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(UserProfileActivity.this);
        queue1.add(userInfoShowRequest);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        };


    }

    private void seekbar() {
        seekBarUserSomatotype.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (male.equals("m")) {
                    if  (progress == 0){
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_man_endomorph_);
                        somatotype = "200";
                    } else if (progress == 1){
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_man_mezomorph_);
                        somatotype = "500";
                    } else if (progress == 2){
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_man_ectomorph_);
                        somatotype = "900";
                    }
                } else if (male.equals("w")){
                    if  (progress == 0){
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_woman_endomorph_);
                        somatotype = "200";
                    } else if (progress == 1){
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_woman_mezomorph_);
                        somatotype = "500";
                    } else if (progress == 2){
                        imageViewUserSomatotype.setImageResource(R.drawable.somatotype_woman_ectomorph_);
                        somatotype = "900";
                    }
                }

                Log.e(TAG,"somatotype "+somatotype);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }



}

