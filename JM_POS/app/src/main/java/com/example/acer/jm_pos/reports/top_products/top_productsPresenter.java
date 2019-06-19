package com.example.acer.jm_pos.reports.top_products;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.jm_pos.localhost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class top_productsPresenter implements top_productsContract.top_productsPresenter {
    //MVP declaration
    top_productsContract.top_productsView mView;
    Context context;


    //network address Declaration
    String localhost = "";
    String main_inventory = localhost + "/MEATSHOP_POS_SALE/android_php/inventory/main_inventory.php";
    com.example.acer.jm_pos.localhost lc = new localhost();

    top_productsPresenter(top_productsContract.top_productsView view){
        this.mView = view;
        this.context = (Context) mView;
    }

    @Override
    public void getCategories() {
        //Object declaration
        localhost = lc.getLocalhost();

        //object declaration
        final ArrayList<String> category_name = new ArrayList<>();


        //Add dummy category_name
        category_name.add("-Select Category-");

        StringRequest onReadItemCat = new StringRequest(Request.Method.POST, localhost + main_inventory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onReadItemCatRes",response);
                if(!response.contains("failed")){

                    //convert the json data
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        JSONArray jsonArray = jsonObject.getJSONArray("category_data");
                        for(int i=0; i < jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            //get the string
                            category_name.add(jsonObject1.getString("category_name"));
                            mView.populateCategoryList(category_name);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(context,"Failed to retrieve data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onReadItemCatError",error.toString());
                Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","onReadItemCat");


                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(onReadItemCat);
    }

    //get the top item per category
    @Override
    public void getCategoriesPerCategory(final String category) {
        //Object declaration
        localhost = lc.getLocalhost();

        //object declaration
        final ArrayList<String> item_id = new ArrayList<>();
        final ArrayList<String> category_name = new ArrayList<>();
        final ArrayList<String> item_name = new ArrayList<>();
        final ArrayList<String> item_price = new ArrayList<>();
        final ArrayList<String> item_desc = new ArrayList<>();
        final ArrayList<String> item_image = new ArrayList<>();
        final ArrayList<String> sales_total = new ArrayList<>();


        StringRequest getTopItem_perCat = new StringRequest(Request.Method.POST, localhost + main_inventory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getTopPerCat_res",response.toString());
                if(!response.contains("failed")){

                    //convert the json to string object
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        JSONArray jsonArray = jsonObject.getJSONArray("item_data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            //get the string object
                            item_id.add(jsonObject1.getString("item_id"));
                            category_name.add(jsonObject1.getString("category_name"));
                            item_name.add(jsonObject1.getString("item_name"));
                            item_price.add(jsonObject1.getString("item_price"));
                            item_desc.add(jsonObject1.getString("item_desc"));
                            item_image.add(jsonObject1.getString("item_image"));
                            sales_total.add(jsonObject1.getString("sales_total"));



                            mView.populateTopItemList(item_id,category_name,item_name,item_price,item_desc,item_image,sales_total);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(context,"Failed to retrieve data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getTopItem_error",error.toString());
                Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","getTopItemPerCategory");
                hashMap.put("get_topItemCategory",category);


                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(getTopItem_perCat);
    }


}
