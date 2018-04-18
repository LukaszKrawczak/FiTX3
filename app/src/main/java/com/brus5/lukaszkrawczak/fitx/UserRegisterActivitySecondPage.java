package com.brus5.lukaszkrawczak.fitx;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.User.UserRegisterSearchExistingUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Pattern;

public class UserRegisterActivitySecondPage extends AppCompatActivity {

    private static final String TAG = "UserRegisterActivitySec";

    public static final int STATUS_OK = 1;
    public static final int STATUS_NOT_OK = 0;

    private int cUserName = 0;
    private int cUserFirstName = 0;
    private int cUserPassword = 0;
    private int cUserRepassword = 0;
    private int cUserEmail = 0;
    private int cUserBirthday = 0;
    private int cUserMale = 0;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private String userName, userFirstName, userPassword, userEmail, userBirthday, userMale;
// FIXME: 18.04.2018 need to make username without white char's and special char's.

    ImageView imageViewUsername, imageViewName, imageViewPassword, imageViewRetypePass, imageViewEmail, imageViewBirthday, imageViewSex;
    RadioButton radioButtonWoman, radioButtonMan;
    DatePickerDialog datePickerDialog;
    Button buttonDateAdd, buttonDateEdit, buttonNextStep;

    TextView textViewBirthday;
    private String password;
    private String passwordRe;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_second_page);

        // toolbar in xml file
        Toolbar toolbar10 = (Toolbar) findViewById(R.id.toolbar10);
        setSupportActionBar(toolbar10);

        // statusbarcolor
        getWindow().setStatusBarColor(ContextCompat.getColor(UserRegisterActivitySecondPage.this, R.color.color_main_activity_statusbar));

        imageViewUsername = findViewById(R.id.imageViewUsername);
        imageViewName = findViewById(R.id.imageViewName);
        imageViewPassword = findViewById(R.id.imageViewPassword);
        imageViewRetypePass = findViewById(R.id.imageViewRetypePass);
        imageViewEmail = findViewById(R.id.imageViewEmail);
        imageViewBirthday = findViewById(R.id.imageViewBirthday);
        imageViewSex = findViewById(R.id.imageViewSex);

        // edittext username
        EditText editTextRegisterUsername = findViewById(R.id.editTextRegisterUsername);
        editTextRegisterUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                imageViewUsername.setImageResource(R.drawable.icon_decline);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.toString().length() > 4) {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
//                            boolean userused = jsonObject.getBoolean("userused");
                                if (success) {
//                                    Intent intent = new Intent(UserRegisterActivitySecondPage.this, UserLoginActivity.class);
//                                    UserRegisterActivitySecondPage.this.startActivity(intent);

                                    imageViewUsername.setImageResource(R.drawable.icon_confirm);
                                    cUserName = STATUS_OK;
                                    userName = s.toString();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean userused = jsonObject.getBoolean("userused");
                                    if (userused) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivitySecondPage.this);
                                        builder.setMessage("Woops! Someone has exacly the same nickname. Please change it.")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                        imageViewUsername.setImageResource(R.drawable.icon_decline);
                                        cUserName = STATUS_NOT_OK;
                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                            Log.e("RESPONSE", "" + response);
                        }
                    };
                    UserRegisterSearchExistingUser userRegisterSearchExistingUser = new UserRegisterSearchExistingUser(s.toString(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(UserRegisterActivitySecondPage.this);
                    queue.add(userRegisterSearchExistingUser);



                }
            }
        });

        // edittext first name
        EditText editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 1) {
                    imageViewName.setImageResource(R.drawable.icon_confirm);
                    cUserFirstName = STATUS_OK;
                    userFirstName = s.toString();
                }
                else {
                    imageViewName.setImageResource(R.drawable.icon_decline);
                    cUserFirstName = STATUS_NOT_OK;
                }
            }
        });

        // edittext password
        final EditText editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        editTextRegisterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 4) {
                    imageViewPassword.setImageResource(R.drawable.icon_confirm);
                    cUserPassword = STATUS_OK;
                    setPassword(s.toString());
                }
                else {
                    imageViewPassword.setImageResource(R.drawable.icon_decline);
                    cUserPassword = STATUS_NOT_OK;
                }
            }
        });

        // edittext retype password
        final EditText editTextRegisterRetypepassword = findViewById(R.id.editTextRegisterRetypepassword);
        editTextRegisterRetypepassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 4 && editTextRegisterPassword.getText().toString().equals(editTextRegisterRetypepassword.getText().toString())) {
                    imageViewRetypePass.setImageResource(R.drawable.icon_confirm);
                    cUserRepassword = STATUS_OK;
                    setPasswordRe(s.toString());
                }
                else {
                    imageViewRetypePass.setImageResource(R.drawable.icon_decline);
                    cUserRepassword = STATUS_NOT_OK;
                }
            }
        });

        // edittext email
        EditText editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        editTextRegisterEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (VALID_EMAIL_ADDRESS_REGEX.matcher(s.toString()).matches()) {
                    imageViewEmail.setImageResource(R.drawable.icon_confirm);
                    cUserEmail = STATUS_OK;
                    userEmail = s.toString();
                }
                else {
                    imageViewEmail.setImageResource(R.drawable.icon_decline);
                    cUserEmail = STATUS_NOT_OK;
                }
            }
        });

        String[] emails = { "\"Fred Bloggs\"@example.com", "user@.invalid.com", "Chuck Norris <gmail@chucknorris.com>", "webmaster@müller.de", "matteo@78.47.122.114" };
        for (String email : emails) {
            System.out.println(email + " is " + (VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches() ? "valid" : "invalid"));
        }

        Log.e(TAG, "onCreate: checking1");
        textViewBirthday = findViewById(R.id.textViewBirthday);

        buttonDateAdd = findViewById(R.id.buttonDateAdd);

        buttonDateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int dYear = c.get(Calendar.YEAR); // current year
                int mYear = c.get(Calendar.YEAR)-18;
                final int mMonth = c.get(Calendar.MONTH); // current month
                final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(UserRegisterActivitySecondPage.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text

                                if (dYear < year && mMonth < monthOfYear && mDay < dayOfMonth){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivitySecondPage.this);
                                    builder.setMessage("Are you from future? :)")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                } else {
                                    textViewBirthday.setText(dayOfMonth + "."
                                            + (monthOfYear + 1) + "." + year);
                                    textViewBirthday.setVisibility(View.VISIBLE);
                                    buttonDateAdd.setVisibility(View.INVISIBLE);
                                    buttonDateEdit.setVisibility(View.VISIBLE);
                                    imageViewBirthday.setImageResource(R.drawable.icon_confirm);
                                    cUserBirthday = STATUS_OK;
                                    userBirthday = textViewBirthday.getText().toString();
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        Log.e(TAG, "onCreate: checking2");

        buttonDateEdit = findViewById(R.id.buttonDateEdit);
        buttonDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int dYear = c.get(Calendar.YEAR); // current year
                int mYear = c.get(Calendar.YEAR)-18;
                final int mMonth = c.get(Calendar.MONTH); // current month
                final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(UserRegisterActivitySecondPage.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
// FIXME: 17.04.2018 if i will pick future date program will crash :)

                                if (dYear < year && mMonth < monthOfYear && mDay < dayOfMonth){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivitySecondPage.this);
                                    builder.setMessage("Are you from future? :)")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                } else {

                                    textViewBirthday.setText(dayOfMonth + "."
                                            + (monthOfYear + 1) + "." + year);
                                    textViewBirthday.setVisibility(View.VISIBLE);
                                    buttonDateAdd.setVisibility(View.INVISIBLE);
                                    buttonDateEdit.setVisibility(View.VISIBLE);
                                    imageViewBirthday.setImageResource(R.drawable.icon_confirm);
                                    cUserBirthday = STATUS_OK;
                                    userBirthday = textViewBirthday.getText().toString();
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        Log.e(TAG, "onCreate: checking3");

        radioButtonMan = findViewById(R.id.radioButtonMan);
        radioButtonWoman = findViewById(R.id.radioButtonWoman);

        radioButtonWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonWoman.setChecked(true);
                radioButtonMan.setChecked(false);
                imageViewSex.setImageResource(R.drawable.icon_confirm);
                cUserMale = STATUS_OK;
                userMale = "w";
            }
        });

        radioButtonMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonWoman.setChecked(false);
                radioButtonMan.setChecked(true);
                imageViewSex.setImageResource(R.drawable.icon_confirm);
                cUserMale = STATUS_OK;
                userMale = "m";
            }
        });


        /*
        imageViewUsername = findViewById(R.id.imageViewUsername);
        imageViewName = findViewById(R.id.imageViewName);
        imageViewPassword = findViewById(R.id.imageViewPassword);
        imageViewRetypePass = findViewById(R.id.imageViewRetypePass);
        imageViewEmail = findViewById(R.id.imageViewEmail);
        imageViewBirthday = findViewById(R.id.imageViewBirthday);
        imageViewSex = findViewById(R.id.imageViewSex);*/

        Log.e(TAG, "onCreate: checking4");

        buttonNextStep = findViewById(R.id.buttonNextStep1);





            buttonNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (cUserName == STATUS_OK && cUserFirstName == STATUS_OK && cUserPassword == STATUS_OK && cUserRepassword == STATUS_OK && cUserEmail == STATUS_OK && cUserBirthday == STATUS_OK && cUserMale == STATUS_OK) {

                        if (getPassword().equals(getPasswordRe())) {
                            userPassword = getPassword();
                        }

                        Intent intent = new Intent(UserRegisterActivitySecondPage.this, UserRegisterActivityThirdPage.class);
                        intent.putExtra("userName", userName);
                        intent.putExtra("userFirstName", userFirstName);
                        intent.putExtra("userPassword", userPassword);
                        intent.putExtra("userEmail", userEmail);
                        intent.putExtra("userBirthday", userBirthday);
                        intent.putExtra("userMale", userMale);
                        UserRegisterActivitySecondPage.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivitySecondPage.this);
                        builder.setMessage("Please add proper data")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                    }
            });



    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRe() {
        return passwordRe;
    }

    public void setPasswordRe(String passwordRe) {
        this.passwordRe = passwordRe;
    }
}
