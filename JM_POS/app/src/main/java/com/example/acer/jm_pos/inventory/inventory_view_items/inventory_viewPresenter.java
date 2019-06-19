package com.example.acer.jm_pos.inventory.inventory_view_items;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.home_sales;
import com.example.acer.jm_pos.home_sales.home_sales_contract;
import com.example.acer.jm_pos.inventory.inventory_fragment;
import com.example.acer.jm_pos.inventory.inventory_update_items.inventory_update_items;
import com.example.acer.jm_pos.localhost;
import com.example.acer.jm_pos.login.login;
import com.example.acer.jm_pos.utility.ProxyHurlStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.logging.Handler;

public class inventory_viewPresenter implements inventory_viewContract.inventory_presenter {
    Context context_1;

    //network address Declaration
    String localhost = "";
    String main_inventory = localhost + "/MEATSHOP_POS_SALE/android_php/inventory/main_inventory.php";
    com.example.acer.jm_pos.localhost lc = new localhost();

    //Object Database Declaration for get Items
    ArrayList<String> item_id_list;
    ArrayList<String> category_id_list;
    ArrayList<String> item_price_list;
    ArrayList<String> inventory_id_list;
    ArrayList<String> item_name_list;
    ArrayList<String> item_desc_list;
    ArrayList<String> item_stock_list;
    ArrayList<String> item_image_list;


    inventory_viewContract.inventory_View mView;
    inventory_viewPresenter(inventory_viewContract.inventory_View inv_view){
        this.mView = inv_view;
        context_1 = (Context) mView;
    }


    @Override
    public void onBackButton(Context context) {
        Intent intent = new Intent(context_1, home_sales.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context_1.startActivity(intent);
    }

    @Override
    public void onGettingAllItems() {
        //Object declaration
        localhost = lc.getLocalhost();

        //Object Declaration
        item_id_list        = new ArrayList<>();
        category_id_list    = new ArrayList<>();
        item_price_list     = new ArrayList<>();
        inventory_id_list   = new ArrayList<>();
        item_name_list      = new ArrayList<>();
        item_desc_list      = new ArrayList<>();
        item_stock_list     = new ArrayList<>();
        item_image_list     = new ArrayList<>();

        //Http Request
        StringRequest getItems = new StringRequest(Request.Method.POST, localhost + main_inventory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("inv_getItemResponse",response);
                    if(!response.contains("failed")){
                        if(!response.contains("no_items")){
                            try {
                                JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                JSONArray jsonArray = jsonObject.getJSONArray("items_data");
                                for(int i = 0; i < jsonArray.length(); i ++){

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    item_id_list.add(jsonObject1.getString("item_id"));
                                    category_id_list.add(jsonObject1.getString("category_id"));
                                    item_price_list.add(jsonObject1.getString("item_price"));
                                    item_name_list.add(jsonObject1.getString("item_name"));
                                    item_desc_list.add(jsonObject1.getString("item_desc"));
                                    item_stock_list.add(jsonObject1.getString("item_stock"));
                                    item_image_list.add(jsonObject1.getString("item_image"));

                                    mView.populateRecyclerView(item_id_list,category_id_list,item_price_list,inventory_id_list,item_name_list,item_desc_list,item_stock_list,item_image_list);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context_1,"Problem In Converting Data!",Toast.LENGTH_LONG).show();
                                Log.d("getItems_JSONException",e.toString());
                            }
                        }else{
                            Toast.makeText(context_1,"No Items",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(context_1,"Failed Query",Toast.LENGTH_LONG).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("inv_getItemError",error.toString());
                    Toast.makeText(context_1,"Connection Problem!",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("function","getInvItems");

                return hashMap;
            }
        };
        Volley.newRequestQueue(context_1).add(getItems);
    }

    @Override
    public void onDeleteItem(final String item_name) {
        //Object declaration
        localhost = lc.getLocalhost();

        //object declaration
        final ProgressDialog pd = new ProgressDialog(context_1);

        pd.setMessage("Deleting...");
        pd.show();

        //Request for deletion of data
        StringRequest deleteItem = new StringRequest(Request.Method.POST, localhost + main_inventory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("deleteItemRes",response.toString());

                pd.hide();
                if(!response.contains("failed")){
                    if(response.contains("success")){
                        Toast.makeText(context_1,"Your data has been deleted successfully",Toast.LENGTH_LONG).show();
                        mView.refreshTable();
                    }
                }else{
                    Toast.makeText(context_1,"Failed to retrieve data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Log.d("deleteItemErro",error.toString());
                    Toast.makeText(context_1,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","deleteItem");
                hashMap.put("onDel_item_name",item_name);


                return hashMap;

            }
        };
        Volley.newRequestQueue(context_1).add(deleteItem);

    }

    @Override
    public void onUpdateItem(String item_name) {
        //Go To Update
        Intent intent = new Intent(context_1,inventory_update_items.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context_1.startActivity(intent);
    }

    @Override
    public void onDeleteAlert(final String item_name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context_1);
        builder.setCancelable(true);
        builder.setTitle("Prompt");
        builder.setMessage("Do you want to delete this item??");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog pd = new ProgressDialog(context_1);
                        pd.setMessage("Please Wait");
                        pd.show();
                        new android.os.Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                pd.hide();
                               mView.goToDelete(item_name);

                            }
                        }, 2000);

                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRefreshTabke() {
        mView.refreshTable();
    }
}
