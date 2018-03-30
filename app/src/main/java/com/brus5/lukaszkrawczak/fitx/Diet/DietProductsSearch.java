package com.brus5.lukaszkrawczak.fitx.Diet;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by lukaszkrawczak on 01.12.2017.
 */

public class DietProductsSearch extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/Diet/ProductsSearch.php";

    public DietProductsSearch(Response.Listener<String> listener){

        super(Method.POST,UPDATE_REQUEST_URL,listener,null);
}

}
