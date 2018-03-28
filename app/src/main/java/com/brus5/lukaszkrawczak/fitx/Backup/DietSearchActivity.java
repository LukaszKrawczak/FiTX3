package com.brus5.lukaszkrawczak.fitx.Backup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Diet.DietInsertMeal;
import com.brus5.lukaszkrawczak.fitx.Diet.DietProductsSearch;
import com.brus5.lukaszkrawczak.fitx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DietSearchActivity extends AppCompatActivity{

    private static final String TAG = "SearchForMeal.java";
    // I was here from Windows10
    // Checking something
    // Generating some useful variables
    String name, username, age, password, email, male, somatotypeS;
    int id;

    int mealID;
    String mealName;

    String[] products = {"You don't see any meal? Add your product in the top right corner."};
    String[] products100;
    // Getting date
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String date = simpleDateFormat.format(c.getTime());

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<Object> adapter;

    // Search EditText
    EditText inputSearch;

    // ArrayList for updating
    ArrayList<String> products1 = new ArrayList<>();
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

        Intent intent1 = getIntent();
        id = intent1.getIntExtra("id",0);
        name = intent1.getStringExtra("name");
        username = intent1.getStringExtra("username");
        age = intent1.getStringExtra("age");
        male = intent1.getStringExtra("male");
        getData();

        // Listview Data
//        final String products[] = new String[productNameList.size()];
//        final ArrayList<String> products = new ArrayList<>();
/*

         final String[] products = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                "iPhone 4S", "Samsung Galaxy Note 800",
                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};

*/

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                products1 = new ArrayList<>(Arrays.asList(products100));
                products2 = new ArrayList<>(Arrays.asList(products100));
                Log.e(TAG,"run() 500mils delay      "+products1);
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
                Log.e(TAG,"productNameList.size()"+productNameList.size());
            }

            // Showing records after typed text
            @Override
            public void afterTextChanged(Editable s) {
                String word = String.valueOf(s);
                Log.e(TAG,"word afterTextChanged                        " + word);
                Log.e(TAG,"wordIsEmpty afterTextChanged                 " + word.isEmpty());
                Log.e(TAG,"productNameList afterTextChanged             " + productNameList);
                Log.e(TAG,"productNameList.size() afterTextChanged      " + productNameList.size());
                Log.e(TAG,"products100 afterTextChanged                 " + products100);
                Log.e(TAG,"products100 afterTextChanged                 " + Arrays.toString(products100));

                if (word.isEmpty()){
                    products2.addAll(products1);
                    Log.e(TAG,"products2 afterTextChanged if word.isEmpty()     " +products2);
                    Log.e(TAG,"products1 afterTextChanged if word.isEmpty()     " +products1);
                }
                else {
                    getPossibleStrings(products1,changeCharSize(String.valueOf(s)));
                    products2.addAll(getPossibleStrings(products1,changeCharSize(String.valueOf(s))));
                    Log.e(TAG,"products2 if word is not Empty                   " +products2);
                    Log.e(TAG,"products1 if word is not Empty                   " +products1);
                }

                Log.e(TAG, "Editable s      " + s);
            }
        });

            // Doing stuff after clicked item ...
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Log.e(TAG,"products2 onItemClick method:        "+products2.get(position));
                    Log.e(TAG,"productIDList onItemClick method:    "+productIDList.get(position));

                    LayoutInflater inflater = LayoutInflater.from(DietSearchActivity.this);

                    final View textEntry = inflater.inflate(R.layout.activity_add_meal,null);

                    final EditText etProductWeight = textEntry.findViewById(R.id.etProductWeight);

                    final AlertDialog.Builder alert = new AlertDialog.Builder(DietSearchActivity.this);
                    alert.setTitle("Add "+products2.get(position)+" to plan")
                            .setView(textEntry)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e(TAG,"etProductWeight:                 "+etProductWeight.getText().toString());
                                    Log.e(TAG,"products2.get(position):         "+products2.get(position));
                                    Log.e(TAG,"productIDList.get(position):     "+productIDList.get(position));

                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                        }
                                    };

                                    DietInsertMeal dietInsertMeal = new DietInsertMeal(Integer.valueOf(productIDList.get(position)),Integer.valueOf(etProductWeight.getText().toString()),username, date, responseListener);
                                    RequestQueue requestQueue = Volley.newRequestQueue(DietSearchActivity.this);
                                    requestQueue.add(dietInsertMeal);
                                }
                            });

                    alert.show().getWindow().setLayout(800,600);
//alert.show();
                }
            });


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e(TAG, "View v "+view);
                Log.e(TAG, "scrollstate "+scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e(TAG, "View v "+view);
                Log.e(TAG, "firstVisibleItem "+firstVisibleItem);
                Log.e(TAG, "visibleItemCount "+visibleItemCount);
                Log.e(TAG, "totalItemCount "+totalItemCount);
            }
        });

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
                            id = c.getString("id");
                            name = c.getString("name");
                            weight = c.getString("weight");
                            proteins = c.getString("proteins");
                            fats = c.getString("fats");
                            carbs = c.getString("carbs");
                            date = c.getString("date");

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
        adapter = new ArrayAdapter<Object>(this, R.layout.meal_row_search, R.id.product_name,products100);

        lv.setAdapter(adapter);
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


    // Dynamically updating array when typing text in TextView
    public List<String> getPossibleStrings(List<String> strings, String query){
        List<String> result = new ArrayList<>();
        for (String s: strings){
            if (s.startsWith(query))
                result.add(s);
        }
        return result;
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

                                Log.e(TAG,"etProductName: "+etProductName.getText().toString());
                                Log.e(TAG,"etProteins: "+etProteins.getText().toString());
                                Log.e(TAG,"etFats: "+etFats.getText().toString());
                                Log.e(TAG,"etCarbs: "+etCarbs.getText().toString());

//                                DietInsertProduct dietInsertProduct = new DietInsertProduct(etProductName.getText().toString(), Float.valueOf(etProteins.getText().toString()), Float.valueOf(etFats.getText().toString()), Float.valueOf(etCarbs.getText().toString()), date, username, listener);
//                                RequestQueue queue = Volley.newRequestQueue(DietSearchActivity.this);
//                                queue.add(dietInsertProduct);
                            }
                        });
                alert.show().getWindow().setLayout(730,1000);


        }
        return super.onOptionsItemSelected(item);

    }
}
