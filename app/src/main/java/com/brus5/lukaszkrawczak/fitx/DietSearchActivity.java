package com.brus5.lukaszkrawczak.fitx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Diet.DietInsertMeal;
import com.brus5.lukaszkrawczak.fitx.Diet.DietInsertProduct;
import com.brus5.lukaszkrawczak.fitx.Diet.DietProductsSearch;
import com.brus5.lukaszkrawczak.fitx.Diet.DietProductsSearchByName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class DietSearchActivity extends AppCompatActivity{

    private static final String TAG = "SearchForMeal.java";

    // Generating some useful variables
//    String name, username, age, password, email, male, somatotypeS;
//    int id;

    String userFirstName, userName, userUserName, userBirthday, userPassword, userEmail, userMale, dateInsde;
    int userIDint,userAgeint;

    int mealID;
    String mealName;
    String productName;
    boolean executed = false;
    String[] products = {"You don't see any meal? Add your product in the top right corner."};
    String[] products100;
    // Getting date
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String date = simpleDateFormat.format(c.getTime());

    int proteins = 0;
    int fats = 0;
    int carbs = 0;

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<Object> adapter;

    // Search EditText
    EditText inputSearch;

    // ArrayList for updating
//    ArrayList<String> products1 = new ArrayList<>();
    ArrayList<String> products2 = new ArrayList<>();
    ArrayList<String> productNameList = new ArrayList<>();
    ArrayList<Integer> productIDList = new ArrayList<>();

    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;

    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_meal);

        Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar3);
        getWindow().setStatusBarColor(ContextCompat.getColor(DietSearchActivity.this,R.color.color_main_activity_statusbar));
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
        getData();

        // Listview Data
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                products2 = new ArrayList<>(Arrays.asList(products100));
                Log.e(TAG,"run() 500mils delay      "+products2);

            }
        },500);


        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);


        inputSearch.addTextChangedListener(new TextWatcher() {
            // Doing while we are not typing
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                String isEmpty = s;
                products100 = new String[0];
                productNameList.clear();
                productIDList.clear();
                products2.clear();
                if (s.toString().isEmpty())
                {
                  products2.toArray(products100);
                }
                else {
                    products2.clear();
                }
                Log.e(TAG,"productNameList.size()"+productNameList.size());
            }

            // Doing things while typing
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DietSearchActivity.this.adapter.getFilter().filter(s);

                Log.d(TAG, "onTextChanged: productNameList.size "+productNameList.size());

                lv.setVisibility(View.VISIBLE);

                Handler handler1 = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },1000);

            }

            // Showing records after typed text
            @Override
            public void afterTextChanged(final Editable s) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(String.valueOf(s));

                Log.d(TAG, "afterTextChanged: stringBuffer "+stringBuffer);

                productName = String.valueOf(s);
                Log.d(TAG, "afterTextChanged: productName "+productName);
                getProductName(String.valueOf(s));

                Log.d(TAG, "afterTextChanged: productNameList "+productNameList);
                Log.d(TAG, "afterTextChanged: productNameList.size() "+productNameList.size());
                Log.d(TAG, "afterTextChanged: products100 "+products100);
                Log.d(TAG, "afterTextChanged: Arrays.toString(products100) "+ Arrays.toString(products100));
                Log.d(TAG, "afterTextChanged: s "+s);
            }
        });

            // Doing stuff after clicked item ...
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Log.e(TAG,"productNameList onItemClick method:        "+productNameList.get(position));
                    Log.e(TAG,"productIDList onItemClick method:    "+productIDList.get(position));
                    LayoutInflater inflater = LayoutInflater.from(DietSearchActivity.this);
                    final View textEntry = inflater.inflate(R.layout.activity_add_meal,null);
                    final EditText etProductWeight = textEntry.findViewById(R.id.etProductWeight);
                    final AlertDialog.Builder alert = new AlertDialog.Builder(DietSearchActivity.this);
                    alert.setTitle("Add "+productNameList.get(position)+" to plan")
                            .setView(textEntry)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e(TAG,"etProductWeight:                 "+etProductWeight.getText().toString());
                                    Log.e(TAG,"productNameList.get(position):   "+productNameList.get(position));
                                    Log.e(TAG,"productIDList.get(position):     "+productIDList.get(position));

                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                        }
                                    };
                                    DietInsertMeal dietInsertMeal = new DietInsertMeal(Integer.valueOf(productIDList.get(position)),Integer.valueOf(etProductWeight.getText().toString()),userName, dateInsde, responseListener);
                                    RequestQueue requestQueue = Volley.newRequestQueue(DietSearchActivity.this);
                                    requestQueue.add(dietInsertMeal);
                                }
                            });

                    alert.show().getWindow().setLayout(800,600);
                }
            });


//        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.e(TAG, "View v "+view);
//                Log.e(TAG, "scrollstate "+scrollState);
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.e(TAG, "View v "+view);
//                Log.e(TAG, "firstVisibleItem "+firstVisibleItem);
//                Log.e(TAG, "visibleItemCount "+visibleItemCount);
//                Log.e(TAG, "totalItemCount "+totalItemCount);
//            }
//        });

    }

    public String getProductNameList (String s){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(s);
        return arrayList.get(0);
    }

    public String getProductNameList(){
        return this.productName;
    }

    public void getProductName(String s){

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(s);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },100);

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"response"+response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    String id;
                    String name;
                    String weight;
                    String proteins;
                    String fats;
                    String carbs;
                    String date;

                    final String[] products = new String[server_response.length()];

                    if (server_response.length() > 0){
                        for (int i = 0; i < server_response.length(); i++){
                            JSONObject c = server_response.getJSONObject(i);
                            id = c.getString("id");
                            name = c.getString("name");
/*
                                    weight = c.getString("weight");
                                    proteins = c.getString("proteins");
                                    fats = c.getString("fats");
                                    carbs = c.getString("carbs");
                                    date = c.getString("date");
*/
                            productNameList.add(name);
                            productIDList.add(Integer.valueOf(id));
                            products2.add(name);
                        }
                    }
                    Log.e(TAG,"productIDList:      "+productIDList);

                    products100 = productNameList.toArray(new String[productNameList.size()]);

                    Log.e(TAG,"productNameList"+productNameList);
                    Log.e(TAG,"productNameList.size()"+productNameList.size());
                    Log.e(TAG,"products100 in onResponse"+Arrays.toString(products100));
                    Log.e(TAG,"products2.toArray(products100);"+Arrays.toString(products2.toArray()));

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addItems();
                        }
                    },100);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        DietProductsSearchByName dietProductsSearchByName = new DietProductsSearchByName(String.valueOf(stringBuffer),responseListener);
        RequestQueue queue = Volley.newRequestQueue(DietSearchActivity.this);
        queue.add(dietProductsSearchByName);
    }


    // Get Data
    public void getData(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"response"+response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    String id;
                    String name;
                    String weight;
                    String proteins;
                    String fats;
                    String carbs;
                    String date;

                    final String[] products = new String[server_response.length()];

                    if (server_response.length() > 0){
                        for (int i = 0; i < server_response.length(); i++){
                            JSONObject c = server_response.getJSONObject(i);
                            id          = c.getString("id");
                            name        = c.getString("name");
                            weight      = c.getString("weight");
                            proteins    = c.getString("proteins");
                            fats        = c.getString("fats");
                            carbs       = c.getString("carbs");
                            date        = c.getString("date");

//                            productNameList.add(name+" "+weight+" "+proteins+" "+fats+" "+carbs+" "+date);
                            productNameList.add(name);
                            productIDList.add(Integer.valueOf(id));

//                            productNameList.add(id);
                        }
                    }
                    Log.e(TAG,"productIDList:      "+productIDList);

//                    getProducts(productNameList, server_response.length());

//                    products[productNameList.size()] = String.valueOf(productNameList.toArray(products));

//                    products100 = new String[productNameList.size()];
//                    productNameList.toArray(products100);



                        products100 = productNameList.toArray(new String[productNameList.size()]);


                    Log.e(TAG,"productNameList"+productNameList);
                    Log.e(TAG,"products"+Arrays.toString(products));
                    Log.e(TAG,"productNameList.size()"+productNameList.size());
                    Log.e(TAG,"products100"+products100);
                    Log.e(TAG,"products100"+Arrays.toString(products100));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        DietProductsSearch dietProductsSearch = new DietProductsSearch(responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(DietSearchActivity.this);
        requestQueue.add(dietProductsSearch);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addItems();
            }
        },1000);
    }

    // Adding items to listview
    public void addItems(){

        Animation animation = new AlphaAnimation(0.0f,1.0f);
//        Animation animation = new ScaleAnimation(1,1,0,1);

        lv.setAnimation(animation);
        adapter = new ArrayAdapter<Object>(this, R.layout.meal_row_search, R.id.product_name,products100);
        lv.setAdapter(adapter);

        lv.setVisibility(View.VISIBLE);
        animation.setDuration(500);
        animation.startNow();

    }

    public String[] getProducts(ArrayList<String> products1, int arraySize){

        String[] products2 = new String[arraySize];

        products2 = products1.toArray(products2);

        return products2;
    }

    public String changeCharSize(String word){
//        word = "java";
        String capWord = word.substring(0,1).toUpperCase() + word.substring(1);
        return capWord;
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

                final int kcal = 0;
                final String kcals = "";
                Log.e(TAG,"etProductName: "+etProductName.getText().toString());
                Log.e(TAG,"etProteins: "+etProteins.getText().toString());
                Log.e(TAG,"etFats: "+etFats.getText().toString());
                Log.e(TAG,"etCarbs: "+etCarbs.getText().toString());

                etProteins.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        Log.d(TAG, "beforeTextChanged: s "+s);
                        Log.d(TAG, "beforeTextChanged: start "+start);
                        Log.d(TAG, "beforeTextChanged: count "+count);
                        Log.d(TAG, "beforeTextChanged: after "+after);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.d(TAG, "onTextChanged: s "+s);
                        Log.d(TAG, "onTextChanged: start "+start);
                        Log.d(TAG, "onTextChanged: before "+before);
                        Log.d(TAG, "onTextChanged: count "+count);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (String.valueOf(s).isEmpty()){
                            setProteins(0);
                        }
                        else {
                            setProteins(Integer.valueOf(s+""));
                        }

                        if (String.valueOf(s).contains(".")){
                            Toast.makeText(DietSearchActivity.this, "Decimals only", Toast.LENGTH_SHORT).show();
                        }
                        else if (String.valueOf(s).isEmpty()){
                            setProteins(0);
//                            etKcal.setText("0");
                        }else {
                            etKcal.setText(String.valueOf(getCountKcal()));
                        }
                        if (getCountKcal() == 0){
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
                            setCarbs(0);
                        }
                        else {
                            setCarbs(Integer.valueOf(s+""));
                        }
                        if (String.valueOf(s).isEmpty()){
                            setCarbs(0);

                        }else {
                            etKcal.setText(String.valueOf(getCountKcal()));
                        }
                        if (getCountKcal() == 0){
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
                            setFats(0);
                        }
                        else {
                            setFats(Integer.valueOf(s+""));
                        }

                        if (String.valueOf(s).isEmpty()){
                            setFats(0);
                        }else {
                            etKcal.setText(String.valueOf(getCountKcal()));
                        }
                        if (getCountKcal() == 0){
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


                                DietInsertProduct dietInsertProduct = new DietInsertProduct(etProductName.getText().toString(), Float.valueOf(etProteins.getText().toString()), Float.valueOf(etFats.getText().toString()), Float.valueOf(etCarbs.getText().toString()), dateInsde, userName, listener);
                                RequestQueue queue = Volley.newRequestQueue(DietSearchActivity.this);
                                queue.add(dietInsertProduct);
                            }
                        });
                alert.show().getWindow().setLayout(730,1200);


        }
        return super.onOptionsItemSelected(item);
    }



    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

//    public int setCountKcal(int proteins, int fats, int carbs){
//        return proteins*4+fats*9+carbs*4;
//    }
    public int getCountKcal(){
        return proteins*4+fats*9+carbs*4;
    }


}
