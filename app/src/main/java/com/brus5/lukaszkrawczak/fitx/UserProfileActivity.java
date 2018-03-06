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
import com.brus5.lukaszkrawczak.fitx.User.UserInfoShowRequest;
import com.brus5.lukaszkrawczak.fitx.User.UserProfileUpdateRequest;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class UserProfileActivity extends AppCompatActivity {

    private final static String TAG = "UserProfileActivity";

    String UserInfo = "", name, username, age, password, email, male, id1;
    boolean defaultLogin = false;
    int id;
    String wynik;

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

        Button btLogout = findViewById(R.id.btFbLogout);
//        LoginButton loginButton = findViewById(R.id.btFbLogin);
        // TODO need to add logout to UserProfileActivity

        btLogout.setOnClickListener(new View.OnClickListener() {
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

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                Intent intent = new Intent(UserProfileActivity.this,UserLoginActivity.class);
//                startActivity(intent);
//            }
//        });

        final Intent intent = getIntent();
        username = intent.getStringExtra("username");
        defaultLogin = intent.getBooleanExtra("defaultLogin",false);
        final String username1 = username;
        etUsername.setText(username1);
        etUsername.setEnabled(false);
        if (defaultLogin){
            etUsername.setEnabled(true);
        }

        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                Log.e("UserProfileActivity",    "ResponseListener "  +response);
                Log.e("UserProfileActivity",    "wynik "             +wynik);
                response = username;
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

                Log.e(TAG,   "Arrays.toString(words): " + Arrays.toString(words));
                Log.e(TAG,   "id:                     " + id);
                Log.e(TAG,   "name:                   " + name);
                Log.e(TAG,   "username:               " + username);
                Log.e(TAG,   "age:                    " + age);
                Log.e(TAG,   "password:               " + password);
                Log.e(TAG,   "email:                  " + email);
                Log.e(TAG,   "male:                   " + male);


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

        UserInfoShowRequest userInfoShowRequest = new UserInfoShowRequest(username, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(UserProfileActivity.this);
        queue1.add(userInfoShowRequest);

//        new BackgroundTask().execute();

        btSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String updatename = etName.getText().toString();
                final String updateusername = etUsername.getText().toString();
                final int updateage = Integer.parseInt(etAge.getText().toString());
                final String updatepassword = etPassword.getText().toString();
                final String updateemail = etEmail.getText().toString();

                Log.e("UserProfileActivity",    "updatename "        + updatename);
                Log.e("UserProfileActivity",    "username "          + username);
                Log.e("UserProfileActivity",    "updateusername "    + updateusername);
                Log.e("UserProfileActivity",    "int updateage "     + String.valueOf(updateage));
                Log.e("UserProfileActivity",    "updatepassword "    + updatepassword);
                Log.e("UserProfileActivity",    "updateemail "       + updateemail);

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
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                                builder.setMessage("Data up to date")
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
                        System.out.println("TUTAJ JEST WYNIK RESPONSE");
                        System.out.println(response);
                    }
                };*/
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (etName.length() <= 1 /*|| etPassword.length() <= 2*/ || etUsername.length() <= 2){
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
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
                                    final Intent intent = new Intent(UserProfileActivity.this, UserLoginActivity.class);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                                    builder.setMessage("Information changed, you'll be logged off")
                                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    UserProfileActivity.this.startActivity(intent);
                                                }
                                            })
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Log.e("UserProfileActivity", "response " + response);
                        }
                    }



                };

                UserProfileUpdateRequest userProfileUpdateRequest = new UserProfileUpdateRequest(updatename, username, updateusername, updateage, updatepassword, updateemail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserProfileActivity.this);
                queue.add(userProfileUpdateRequest);



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
@Override
public void onDestroy() {
    super.onDestroy();
//    LoginManager.getInstance().logOut();
}
//    private void logout(){
//        // clear any user information
//        mApp.clearUserPrefs();
//        // find the active session which can only be facebook in my app
//        PackageInstaller.Session session = PackageInstaller.Session.getActiveSession();
//        // run the closeAndClearTokenInformation which does the following
//        // DOCS : Closes the local in-memory Session object and clears any persistent
//        // cache related to the Session.
//        session.closeAndClearTokenInformation();
//        // return the user to the login screen
//        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//        // make sure the user can not access the page after he/she is logged out
//        // clear the activity stack
//        finish();
//    }

}

