package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.User.UserRegister1Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserRegisterActivityThirdPage extends AppCompatActivity {

    private static final String TAG = "UserRegisterActivityThi";

    EditText editTextHeight, editTextWeight, editTextRatioProtein, editTextRatioFat, editTextRatioCarb;
    ImageView imageViewSomatotype;
    SeekBar seekBarSomatotype;
    TextView textViewEndo, textViewMezo, textViewEcto;
    Button buttonFinish;
    String userName, userFirstName, userBirthday, userPassword, userEmail, userMale, userID, somatotype;

    /* Gettings date */
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = simpleDateFormat.format(c.getTime());

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_third_page);
        Log.e(TAG, "onCreate: started");
        // toolbar in xml file
        Toolbar toolbar10 = (Toolbar) findViewById(R.id.toolbar10);
        setSupportActionBar(toolbar10);

        // statusbarcolor
        getWindow().setStatusBarColor(ContextCompat.getColor(UserRegisterActivityThirdPage.this, R.color.color_main_activity_statusbar));

        Intent intent1 = getIntent();
        userFirstName = intent1.getStringExtra("userFirstName");
        userName = intent1.getStringExtra("userName");
        userBirthday = intent1.getStringExtra("userBirthday");
        userPassword = intent1.getStringExtra("userPassword");
        userEmail = intent1.getStringExtra("userEmail");
        userMale = intent1.getStringExtra("userMale");
        Log.e(TAG,"informacje"+userFirstName+" "+userName+" "+userBirthday+" "+userPassword+" "+userEmail+" "+userMale);

        textViewEndo = findViewById(R.id.textViewEndo);
        textViewMezo = findViewById(R.id.textViewMezo);
        textViewEcto = findViewById(R.id.textViewEcto);
        editTextRatioProtein = findViewById(R.id.editTextRatioProtein);
        editTextRatioFat = findViewById(R.id.editTextRatioFat);
        editTextRatioCarb = findViewById(R.id.editTextRatioCarb);


        imageViewSomatotype = findViewById(R.id.imageViewSomatotype);
        if (userMale.equals("m")){
            imageViewSomatotype.setImageResource(R.drawable.somatotype_man_endomorph_);
            somatotype = "200";
            textViewEndo.setTypeface(textViewEndo.getTypeface(), Typeface.BOLD);
            editTextRatioProtein.setText("35");
            editTextRatioFat.setText("40");
            editTextRatioCarb.setText("25");

        } else if (userMale.equals("w")){
            imageViewSomatotype.setImageResource(R.drawable.somatotype_woman_endomorph_);
            somatotype = "200";
            textViewEndo.setTypeface(textViewEndo.getTypeface(), Typeface.BOLD);
            editTextRatioProtein.setText("35");
            editTextRatioFat.setText("40");
            editTextRatioCarb.setText("25");
        }

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);

        seekBarSomatotype = findViewById(R.id.seekBarSomatotype);
        seekBarSomatotype.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (userMale.equals("m")) {
                    if  (progress == 0){
                        imageViewSomatotype.setImageResource(R.drawable.somatotype_man_endomorph_);
                        somatotype = "200";
                        textViewEndo.setTypeface(textViewEndo.getTypeface(), Typeface.BOLD);
                        textViewMezo.setTypeface(Typeface.DEFAULT);
                        textViewEcto.setTypeface(Typeface.DEFAULT);
                        editTextRatioProtein.setText("35");
                        editTextRatioFat.setText("40");
                        editTextRatioCarb.setText("25");
                    } else if (progress == 1){
                        imageViewSomatotype.setImageResource(R.drawable.somatotype_man_mezomorph_);
                        somatotype = "500";
                        textViewMezo.setTypeface(textViewMezo.getTypeface(), Typeface.BOLD);
                        textViewEndo.setTypeface(Typeface.DEFAULT);
                        textViewEcto.setTypeface(Typeface.DEFAULT);
                        editTextRatioProtein.setText("40");
                        editTextRatioFat.setText("30");
                        editTextRatioCarb.setText("30");
                    } else if (progress == 2){
                        imageViewSomatotype.setImageResource(R.drawable.somatotype_man_ectomorph_);
                        somatotype = "900";
                        textViewEcto.setTypeface(textViewEcto.getTypeface(), Typeface.BOLD);
                        textViewEndo.setTypeface(Typeface.DEFAULT);
                        textViewMezo.setTypeface(Typeface.DEFAULT);
                        editTextRatioProtein.setText("25");
                        editTextRatioFat.setText("20");
                        editTextRatioCarb.setText("55");
                    }
                } else if (userMale.equals("w")) {
                    if (progress == 0) {
                        imageViewSomatotype.setImageResource(R.drawable.somatotype_woman_endomorph_);
                        somatotype = "200";
                        textViewEndo.setTypeface(textViewEndo.getTypeface(), Typeface.BOLD);
                        textViewMezo.setTypeface(Typeface.DEFAULT);
                        textViewEcto.setTypeface(Typeface.DEFAULT);
                        editTextRatioProtein.setText("35");
                        editTextRatioFat.setText("40");
                        editTextRatioCarb.setText("25");
                    } else if (progress == 1) {
                        imageViewSomatotype.setImageResource(R.drawable.somatotype_woman_mezomorph_);
                        somatotype = "500";
                        textViewMezo.setTypeface(textViewMezo.getTypeface(), Typeface.BOLD);
                        textViewEndo.setTypeface(Typeface.DEFAULT);
                        textViewEcto.setTypeface(Typeface.DEFAULT);
                        editTextRatioProtein.setText("40");
                        editTextRatioFat.setText("30");
                        editTextRatioCarb.setText("30");
                    } else if (progress == 2) {
                        imageViewSomatotype.setImageResource(R.drawable.somatotype_woman_ectomorph_);
                        somatotype = "900";
                        textViewEcto.setTypeface(textViewEcto.getTypeface(), Typeface.BOLD);
                        textViewEndo.setTypeface(Typeface.DEFAULT);
                        textViewMezo.setTypeface(Typeface.DEFAULT);
                        editTextRatioProtein.setText("25");
                        editTextRatioFat.setText("20");
                        editTextRatioCarb.setText("55");
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




        buttonFinish = findViewById(R.id.buttonFinish);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Double height = Double.valueOf(editTextHeight.getText().toString());
                final Double weight = Double.valueOf(editTextWeight.getText().toString());
                final Double dSomatotype = Double.valueOf(somatotype);
                final Double proteinsratio = Double.valueOf(editTextRatioProtein.getText().toString());
                final Double fatsratio = Double.valueOf(editTextRatioFat.getText().toString());
                final Double carbsratio = Double.valueOf(editTextRatioCarb.getText().toString());

                double ratioresult = Double.valueOf(editTextRatioProtein.getText().toString()) + Double.valueOf(editTextRatioFat.getText().toString()) + Double.valueOf(editTextRatioCarb.getText().toString());

                if (ratioresult > 100d || ratioresult < 100d){
                    Log.d(TAG, "onResponse: ratio result false "+ratioresult);

                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivityThirdPage.this);
                    builder.setMessage("Total value of nutrients ratio should be 100, not more not less.")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    final Intent intent = new Intent(UserRegisterActivityThirdPage.this, UserLoginActivity.class);
                                    UserRegisterActivityThirdPage.this.startActivity(intent);
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Log.e(TAG, "response " + response);
                        }

// FIXME: 19.04.2018 im working here... need to send those data to mysql, and need to fix php file also which is called UserRegister1Request

                };
                UserRegister1Request userRegister1Request = new UserRegister1Request(userName, userFirstName, userPassword, userBirthday, userEmail, userMale, editTextWeight.getText().toString(),editTextHeight.getText().toString(), somatotype, Double.valueOf(editTextRatioProtein.getText().toString()),Double.valueOf(editTextRatioFat.getText().toString()), Double.valueOf(editTextRatioCarb.getText().toString()), date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserRegisterActivityThirdPage.this);
                queue.add(userRegister1Request);
            }
        });




    }
}
