package com.brus5.lukaszkrawczak.fitx;

import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Diet.Diet;
import com.brus5.lukaszkrawczak.fitx.Diet.DietInsertMeal;
import com.brus5.lukaszkrawczak.fitx.Diet.DietInsertProduct;
import com.brus5.lukaszkrawczak.fitx.Diet.DietListAdapter;
import com.brus5.lukaszkrawczak.fitx.Diet.DietProductsSearchByName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DietSearchActivity2 extends AppCompatActivity {

    private static final String TAG = "DietSearchActivity2";

    String userFirstName, userName, userUserName, userBirthday, userPassword, userEmail, userMale, dateInsde;
    int userIDint,userAgeint;
    ListView mTaskListView;

    DietListAdapter adapter;
    EditText inputSearch;
    ArrayList<Diet> dietArrayList = new ArrayList<>();

    // Getting date
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = simpleDateFormat.format(c.getTime());


    double proteins = 0d;
    double  fats = 0d;
    double  carbs = 0d;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_search2);

        Toolbar toolbar3 = findViewById(R.id.toolbarDietSearch);
        setSupportActionBar(toolbar3);
        mTaskListView = findViewById(R.id.viewProducts1);
        getWindow().setStatusBarColor(ContextCompat.getColor(DietSearchActivity2.this,R.color.color_main_activity_statusbar));

        Intent intent1 = getIntent();
        userIDint = intent1.getIntExtra("userIDint",0);
        userFirstName = intent1.getStringExtra("userFirstName");
        userName = intent1.getStringExtra("userName");
        userBirthday = intent1.getStringExtra("userBirthday");
        userAgeint = intent1.getIntExtra("userAgeint",0);
        userPassword = intent1.getStringExtra("userPassword");
        userEmail = intent1.getStringExtra("userEmail");
        userMale = intent1.getStringExtra("userMale");
        dateInsde = intent1.getStringExtra("dateInsde");
        Log.e(TAG,"informacje"+" "+userIDint+" "+userFirstName+" "+userName+" "+userBirthday+" "+userAgeint+" "+userPassword+" "+userEmail+" "+userMale+" "+dateInsde);

        inputSearch = findViewById(R.id.inputSearch1);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dietArrayList.clear();
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: response "+response);
                        double kcalResult = 0;
                        double proteinsResult = 0;
                        double fatsResult = 0;
                        double carbsResult = 0;

                        String proteinsratio = "";
                        String fatsratio = "";
                        String carbsratio="";
                        String result="";

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray server_response = jsonObject.getJSONArray("server_response");

                            String name;
                            int product_id;
                            double proteins;
                            double fats;
                            double carbs;
                            String date;
                            String username;
                            double kcal;

                            if (server_response.length() > 0){
                                for (int i = 0; i < server_response.length(); i++){
                                    JSONObject c = server_response.getJSONObject(i);
                                    product_id = Integer.valueOf(c.getString("product_id"));
                                    name = c.getString("name");
                                    proteins = Double.valueOf(c.getString("proteins"));
                                    fats = Double.valueOf(c.getString("fats"));
                                    carbs = Double.valueOf(c.getString("carbs"));
                                    kcal = Double.valueOf(c.getString("kcal"));

/*
                                    weight = c.getString("weight");
                                    proteins = c.getString("proteins");
                                    fats = c.getString("fats");
                                    carbs = c.getString("carbs");
                                    date = c.getString("date");
*/

                                    Log.e(TAG, "onResponse: name "+name);

                                    String upName = name.substring(0,1).toUpperCase() + name.substring(1);

                                    Diet diet = new Diet(String.valueOf(product_id), upName, "100", String.format("%.1f",proteins), String.format("%.1f",fats), String.format("%.1f",carbs),String.format("%.0f",kcal));
                                    dietArrayList.add(diet);

                                    adapter = new DietListAdapter(DietSearchActivity2.this,R.layout.meal_row,dietArrayList);
                                    mTaskListView.setAdapter(adapter);
                                    mTaskListView.invalidate();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                DietProductsSearchByName dietProductsSearchByName = new DietProductsSearchByName(s.toString(),responseListener);
                RequestQueue queue = Volley.newRequestQueue(DietSearchActivity2.this);
                queue.add(dietProductsSearchByName);
            }
        });





mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: "+position+" "+id);

        TextView taskTextView = view.findViewById(R.id.meal_title);
        TextView tvId = view.findViewById(R.id.meal_id);
        TextView tvWeight = view.findViewById(R.id.meal_weight);
        final TextView tvProteins = view.findViewById(R.id.meal_proteins);
        TextView tvFats =   view.findViewById(R.id.meal_fats);
        TextView tvCarbs = view.findViewById(R.id.meal_carbs);

        final String description = taskTextView.getText().toString();
        final int idd = Integer.valueOf(tvId.getText().toString());
        final String weight = String.valueOf(tvWeight.getText());
        final String proteins = String.valueOf(tvProteins.getText());
        final String fats = String.valueOf(tvFats.getText());
        final String carbs = String.valueOf(tvCarbs.getText());

        Log.e(TAG, "onItemClick: desc"+description );
        Log.e(TAG, "onItemClick: idd"+idd );
        Log.e(TAG, "onItemClick: weight"+weight );

        LayoutInflater inflater = LayoutInflater.from(DietSearchActivity2.this);
        View textEntryView = inflater.inflate(R.layout.activity_diet_options,null);

        final TextView mProteins = textEntryView.findViewById(R.id.meal_proteins4);
        final TextView mFats = textEntryView.findViewById(R.id.meal_fats4);
        final TextView mCarbs = textEntryView.findViewById(R.id.meal_carbs4);

        mProteins.setText(proteins);
        mFats.setText(fats);
        mCarbs.setText(carbs);

        Log.e(TAG, "onItemLongClick: getProteins "+Double.valueOf(proteins)*100/Double.valueOf(weight));

        final EditText editTextUserWeight = textEntryView.findViewById(R.id.editTextRestTime);

        tvProteins.setText(proteins);
        tvFats.setText(fats);
        tvCarbs.setText(carbs);

//        editTextUserWeight.setText(weight);


        final double dProteins = Double.valueOf(proteins) * 100 / Double.valueOf(weight);
        final double dFats = Double.valueOf(fats) * 100 / Double.valueOf(weight);
        final double dCarbs = Double.valueOf(carbs) * 100 / Double.valueOf(weight);
        Log.e(TAG, "onItemLongClick: proteins100 "+dProteins );

        editTextUserWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                double finalProteins = 0d;
                double finalFats = 0d;
                double finalCarbs = 0d;

                if (!s.toString().isEmpty()) {
                    finalProteins = Double.valueOf(s.toString())/100 * dProteins;
                    finalFats = Double.valueOf(s.toString())/100 * dFats;
                    finalCarbs = Double.valueOf(s.toString())/100 * dCarbs;

                    Log.e(TAG, "beforeTextChanged: proteins final "+finalProteins);
                }
                mProteins.setText(String.format("%.1f",finalProteins));
                mFats.setText(String.format("%.1f",finalFats));
                mCarbs.setText(String.format("%.1f",finalCarbs));
//                        double value = getPweight()*100*getProteins()/getPweight();
//                        mProteins.setText(String.format("%.1f",value));
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(DietSearchActivity2.this);
        alert.setTitle("Add product")
                .setView(textEntryView)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener<String> listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e(TAG,"response"+response);
                            }
                        };
                        DietInsertMeal dietInsertMeal = new DietInsertMeal(idd,Integer.valueOf(editTextUserWeight.getText().toString()),userName,dateInsde,listener);
                        RequestQueue requestQueue = Volley.newRequestQueue(DietSearchActivity2.this);
                        requestQueue.add(dietInsertMeal);

        inputSearch.setText("");

                    }
                })
                .setIcon(R.drawable.icon_plan)
        ;

        alert.show().getWindow().setLayout(800,800);





        Log.e(TAG, "onItemLongClick: desc "+description);

    }
});



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Instantiate menu XML files into Menu object
        getMenuInflater().inflate(R.menu.add_meal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_meal:

                LayoutInflater inflater = LayoutInflater.from(this);

                Log.d(TAG,"Add a new product");

                final View textEntryView = inflater.inflate(R.layout.activity_add_product,null);

                final EditText etProductName = textEntryView.findViewById(R.id.etProductName);
                final EditText etProteins = textEntryView.findViewById(R.id.etProteins);
                final EditText etCarbs = textEntryView.findViewById(R.id.etCarbs);
                final EditText etFats = textEntryView.findViewById(R.id.etFats);
                final EditText etKcal = textEntryView.findViewById(R.id.etKcal);

                etProteins.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (String.valueOf(s).isEmpty()){
                            setProteins(0d);
                        }
                        else if (String.valueOf(s).startsWith(".")){
                            Toast.makeText(DietSearchActivity2.this, "Start with number", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            setProteins(Double.valueOf(s+""));
                        }

                        if (String.valueOf(s).isEmpty()){
                            setProteins(0d);
//                            etKcal.setText("0");
                        }else {
                            etKcal.setText(String.valueOf(getCountKcal()));
                        }
                        if (getCountKcal() == 0d){
                            etKcal.setText("0");
                        }
                        Log.d(TAG, "afterTextChanged: getCountKcal() "+getCountKcal());
                    }
                });
                etCarbs.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (String.valueOf(s).isEmpty()){
                            setCarbs(0d);
                        }
                        else if (String.valueOf(s).startsWith(".")){
                            Toast.makeText(DietSearchActivity2.this, "Start with number", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            setCarbs(Double.valueOf(s+""));
                        }
                        if (String.valueOf(s).isEmpty()){
                            setCarbs(0d);

                        }else {
                            etKcal.setText(String.valueOf(getCountKcal()));
                        }
                        if (getCountKcal() == 0d){
                            etKcal.setText("0");
                        }
                        Log.d(TAG, "afterTextChanged: getCountKcal() "+getCountKcal());
                    }
                });
                etFats.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (String.valueOf(s).isEmpty()){
                            setFats(0d);
                        }
                        else if (String.valueOf(s).startsWith(".")){
                            Toast.makeText(DietSearchActivity2.this, "Start with number", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            setFats(Double.valueOf(s+""));
                        }

                        if (String.valueOf(s).isEmpty()){
                            setFats(0d);
                        }else {
                            etKcal.setText(String.valueOf(getCountKcal()));
                        }
                        if (getCountKcal() == 0d){
                            etKcal.setText("0");
                        }
                        Log.d(TAG, "afterTextChanged: getCountKcal() "+getCountKcal());
                    }
                });

                etKcal.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });





                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Add product")
                        .setView(textEntryView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Response.Listener<String> listener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                };


                                DietInsertProduct dietInsertProduct = new DietInsertProduct(etProductName.getText().toString(), Float.valueOf(etProteins.getText().toString()), Float.valueOf(etFats.getText().toString()), Float.valueOf(etCarbs.getText().toString()), Float.valueOf(etKcal.getText().toString()),dateInsde, userName, listener);
                                RequestQueue queue = Volley.newRequestQueue(DietSearchActivity2.this);
                                queue.add(dietInsertProduct);
                            }
                        });
                alert.show().getWindow().setLayout(730,1200);

        }
        return super.onOptionsItemSelected(item);
    }
    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    //    public int setCountKcal(int proteins, int fats, int carbs){
//        return proteins*4+fats*9+carbs*4;
//    }
    public double getCountKcal(){
        return proteins*4.0+fats*9.0+carbs*4.0;
    }


}
