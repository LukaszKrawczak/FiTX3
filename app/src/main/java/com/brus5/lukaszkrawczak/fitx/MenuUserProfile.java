package com.brus5.lukaszkrawczak.fitx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MenuUserProfile extends AppCompatActivity {
    String username = "";
    String JSON_STRING;
    String response = "";
    String wynik = "";
    EditText etName, etUsername, etAge, etPassword, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final EditText etName = findViewById(R.id.etName);
        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etAge = findViewById(R.id.etAge);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etEmail = findViewById(R.id.etEmail);

        final Button btSaveSettings = findViewById(R.id.btSaveSettings);
        final Button btRestoreData = findViewById(R.id.btRestoreData);



        final Intent intent = getIntent();
        username = intent.getStringExtra("username");
        final String username1 = username;
        etUsername.setText(username1);

        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

//                    JSONArray aJsonString = jsonObject.getJSONArray("array()");
//                    Log.e("CHECK AJSONSTRING",""+aJsonString);
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuUserProfile.this);
                        builder.setMessage("username up to date")
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                        Intent intent1 = new Intent(MenuUserProfile.this,LoginActivity.class);
                        MenuUserProfile.this.startActivity(intent1);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                wynik = response;
                Log.e("MenuUserProfile",    "ResponseListener "  +response);
                Log.e("MenuUserProfile",    "wynik "             +wynik);
                response = username;
                String result;
                result = wynik.replaceAll("[\\p{Punct}&&[^@.]]"," ");

                String[] words = new String[]{};

                words = result.split("\\s+");

                String name = words[4];
                String username = words[6];
                String age = words[8];
                String password = words[10];
                String email = words[12];

                Log.e("MenuUserProfile",   "Arrays.toString(words) "    + Arrays.toString(words));
                Log.e("MenuUserProfile",   "name "                      + name);
                Log.e("MenuUserProfile",   "username "                  + username);
                Log.e("MenuUserProfile",   "age "                       + age);
                Log.e("MenuUserProfile",   "password "                  + password);
                Log.e("MenuUserProfile",   "email "                     + email);

/*                etName = (EditText) findViewById(R.id.etName);
                etAge = (EditText) findViewById(R.id.etAge);
                etPassword = (EditText) findViewById(R.id.etPassword);
                etEmail = (EditText) findViewById(R.id.etEmail);*/

                etName.setText(name);
                etAge.setText(age);
                etPassword.setText(password);
                etEmail.setText(email);
            }
        };

        UpdateRequest1 updateRequest1 = new UpdateRequest1(username, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(MenuUserProfile.this);
        queue1.add(updateRequest1);

//        new BackgroundTask().execute();

        btSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String updatename = etName.getText().toString();
                final String updateusername = etUsername.getText().toString();
                final int updateage = Integer.parseInt(etAge.getText().toString());
                final String updatepassword = etPassword.getText().toString();
                final String updateemail = etEmail.getText().toString();

                Log.e("MenuUserProfile",    "updatename "        + updatename);
                Log.e("MenuUserProfile",    "username "          + username);
                Log.e("MenuUserProfile",    "updateusername "    + updateusername);
                Log.e("MenuUserProfile",    "int updateage "     + String.valueOf(updateage));
                Log.e("MenuUserProfile",    "updatepassword "    + updatepassword);
                Log.e("MenuUserProfile",    "updateemail "       + updateemail);

/*                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            String aJsonString = jsonObject.getString("username");
//                            Log.e("TEST_USERNAME",""+aJsonString);
                            Log.e("TEST_RESPONSE",""+response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MenuUserProfile.this);
                                builder.setMessage("Data up to date")
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();
                                Intent intent1 = new Intent(MenuUserProfile.this,LoginActivity.class);
                                MenuUserProfile.this.startActivity(intent1);
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("TUTAJ JEST WYNIK RESPONSE");
                        System.out.println(response);
                    }
                };*/
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (etName.length() <= 1 /*|| etPassword.length() <= 2*/ || etUsername.length() <= 2){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MenuUserProfile.this);
                            builder.setMessage("Please enter proper data")
                                    .setNegativeButton("OK",null)
                                    .create()
                                    .show();
                        }

                        else {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    final Intent intent = new Intent(MenuUserProfile.this, LoginActivity.class);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MenuUserProfile.this);
                                    builder.setMessage("Information changed, you'll be logged off")
                                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    MenuUserProfile.this.startActivity(intent);
                                                }
                                            })
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Log.e("MenuUserProfile", "response " + response);
                        }
                    }



                };

                UpdateRequest updateRequest = new UpdateRequest(updatename, username, updateusername, updateage, updatepassword, updateemail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MenuUserProfile.this);
                queue.add(updateRequest);



            }

        });


        btRestoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("message","CZEŚć");
        setResult(RESULT_OK,i);
        finish();
    }
/*    class BackgroundTask extends AsyncTask<Void, Void, String>{
            String jsonURL;
        @Override
        protected void onPreExecute() {
            jsonURL = "http://justfitx.xyz/ShowResult.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(jsonURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {



            exec();

            Log.e("WYNIK",""+wynik);


            Log.e("RESULT",""+result);

*//*            result = wynik.replaceAll("[\\p{Punct}&&[^@.]]"," ");

            String[] words = new String[]{};

            words = result.split("\\s+");

            String name = words[4];
            String username = words[6];
            String age = words[8];
            String password = words[10];
            String email = words[12];

            Log.e("WYNIK",""+ Arrays.toString(words));
            Log.e("name",""+name);
            Log.e("username",""+username);
            Log.e("age",""+age);
            Log.e("password",""+password);
            Log.e("email",""+email);

            etName = (EditText) findViewById(R.id.etName);
            etAge = (EditText) findViewById(R.id.etAge);
            etPassword = (EditText) findViewById(R.id.etPassword);
            etEmail = (EditText) findViewById(R.id.etEmail);

            etName.setText(name);
            etAge.setText(age);
            etPassword.setText(password);
            etEmail.setText(email);*//*
        }

        private void exec() {





        }


    }*/

}

