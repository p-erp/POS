package com.example.acer.jm_pos.inventory.inventory_add_items;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.jm_pos.home_sales.home_sales;
import com.example.acer.jm_pos.localhost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class inventory_addPresenter implements inventory_addContract.inventory_presenter {
    inventory_addContract.inventory_view mView;
    Context context;

    //network address Declaration
    String localhost = "";
    String main_inventory = localhost + "/MEATSHOP_POS_SALE/android_php/inventory/main_inventory.php";
    com.example.acer.jm_pos.localhost lc = new localhost();

    //Object Declaration
    ProgressDialog pd;

    inventory_addPresenter(inventory_addContract.inventory_view view){
        this.mView = view;
        this.context = (Context) this.mView;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, home_sales.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    @Override
    public void onAddItem(final String product_image_string, final String item_category, final String product_name, final String product_price, final String product_stock, final String product_desc) {
        localhost = lc.getLocalhost();

        //Object Declaration
        pd = new ProgressDialog(context);
        pd.setMessage("Saving...");
        pd.show();

        Log.d("data_link",""+product_image_string+" "+item_category+" "+product_name+" "+product_price+" "+product_stock+" "+product_desc);
        StringRequest onAddItemRequest = new StringRequest(Request.Method.POST, localhost + main_inventory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onAddItemRes",response.toString());
                pd.hide();
                    if(!response.contains("failed")){
                        if(response.contains("success")){
                            Toast.makeText(context,"Item has been saved!",Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                    }else{
                        Toast.makeText(context,"Data failed to retrieve",Toast.LENGTH_LONG).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Log.d("onAddItemError",error.toString());
                    Toast.makeText(context,"Connection Problem!",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","onAddItems");
                hashMap.put("onAdd_itemCategory",item_category);
                hashMap.put("onAdd_productName",product_name);
                hashMap.put("onAdd_productPrice",product_price);
                hashMap.put("onAdd_productStock",product_stock);
                hashMap.put("onAdd_productDesc",product_desc);


                if(product_image_string.equals("")){
                    hashMap.put("onAdd_imageString","noImage");
                    Log.d("image_string","No Data: "+product_image_string.toString());
                }else{
                    hashMap.put("onAdd_imageString",""+product_image_string);
                    Log.d("image_string","with Data: "+product_image_string.toString());
                }


                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(onAddItemRequest);
    }

    @Override
    public void onReadCategories() {
        localhost = lc.getLocalhost();


        //Object declaration
        final ArrayList<String> category_id       = new ArrayList<>();
        final ArrayList<String> category_name     = new ArrayList<>();
        final ArrayList<String> category_added    = new ArrayList<>();

        //Add temporary Categories
        category_name.add("-Select Categories-");
        StringRequest onReadItemCategories = new StringRequest(Request.Method.POST, localhost + main_inventory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onReadCatRes",response);
                if (!response.contains("failed")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        JSONArray jsonArray = jsonObject.getJSONArray("category_data");
                        for(int i=0; i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                category_id.add(jsonObject1.getString("category_id"));
                                category_name.add(jsonObject1.getString("category_name"));
                                category_added.add(jsonObject1.getString("category_added"));
                        }

                        mView.populateCategoriesList(category_id,category_name,category_added);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("onReadItemException",e.toString());
                    }
                }else{
                    Toast.makeText(context,"Can't Retrieve Data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onReadCatErr",error.toString());
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
        Volley.newRequestQueue(context).add(onReadItemCategories);
    }
}
