package com.example.acer.jm_pos.home_sales.POS_main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.jm_pos.localhost;
import com.example.acer.jm_pos.login.login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class POS_mainPresenter implements POS_mainContract.POS_mainPresenter {

    //MVP Declaration class
    private POS_mainContract.POS_mainView mView;
    private Context context;

    //network address Declaration
    String localhost = "";
    String main_subCat = localhost + "/MEATSHOP_POS_SALE/android_php/POS_main/POS_subCategory/pos_subCategory.php";
    String main_pos_cart = localhost + "/MEATSHOP_POS_SALE/android_php/POS_main/pos_cartItem/pos_cartItem.php";
    String main_pos_subSearch = localhost + "/MEATSHOP_POS_SALE/android_php/POS_main/POS_subSearch/pos_subsearch.php";
    com.example.acer.jm_pos.localhost lc = new localhost();


    POS_mainPresenter(POS_mainContract.POS_mainView view){
        mView = view;
        context = (Context) mView;
    }

    @Override
    public void getItem(final String category) {
        localhost = lc.getLocalhost();

        //Object declaration
        final ArrayList<String> item_id = new ArrayList<>();
        final ArrayList<String> category_name = new ArrayList<>();
        final ArrayList<String> item_name = new ArrayList<>();
        final ArrayList<String> item_price = new ArrayList<>();
        final ArrayList<String> item_desc = new ArrayList<>();
        final ArrayList<String> item_image = new ArrayList<>();
        final ArrayList<String> item_stock = new ArrayList<>();


        //request
        StringRequest getItem = new StringRequest(Request.Method.POST, localhost + main_subCat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getItemResponse",response);
                if(!response.contains("failed")){
                    if(!response.contains("no_item")){
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray jsonArray = jsonObject.getJSONArray("item_result");
                            for(int i = 0;i < jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                //ArrayList item desc
                                item_id.add(jsonObject1.getString("item_id"));
                                category_name.add(jsonObject1.getString("category_name"));
                                item_name.add(jsonObject1.getString("item_name"));
                                item_price.add(jsonObject1.getString("item_price"));
                                item_desc.add(jsonObject1.getString("item_desc"));
                                item_image.add(jsonObject1.getString("item_image"));
                                item_stock.add(jsonObject1.getString("item_stock"));

                            }

                            mView.populateItemList(item_id,category_name,item_name,item_price,item_desc,item_image,item_stock);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(context,"No Item",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context,"Failed to retrieved data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getItemError",error.toString());
                Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","getItem");
                hashMap.put("cat_getItem",category);

                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(getItem);
    }

    @Override
    public void onInsertToCart(final String customer_id, final String item_name, final String item_quantity, final String item_price) {
        localhost = lc.getLocalhost();

        //Link
        Log.d("onCartLink",localhost+main_pos_cart);
        StringRequest insertToCart = new StringRequest(Request.Method.POST, localhost + main_pos_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("in_cartResponse",response.toString());
                if(!response.contains("failed")){
                    if(response.contains("success")){
                        Log.d("cartData","Item added to Cart!");

                        getCartList(customer_id);

                    }
                }else{
                    Toast.makeText(context,"failed to retrieve data",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("in_cartError",error.toString());
                Toast.makeText(context,"Connection Problem!",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","insertToCart");
                hashMap.put("ins_customerId",customer_id);
                hashMap.put("ins_itemName",item_name);
                hashMap.put("ins_itemQuantity",item_quantity);
                hashMap.put("ins_itemPrice",item_price);


                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(insertToCart);
    }

    @Override
    public void calculateTotal(ArrayList<String> cart_id, ArrayList<String> customer_id, ArrayList<String> item_name, ArrayList<String> item_quantity, ArrayList<String> item_price,ArrayList<String> item_image) {

        double total_value = 0;
        //Compute Total
        for(int i = 0; i < cart_id.size(); i ++){
            double compute_total = (Double.parseDouble(item_quantity.get(i)))*(Double.parseDouble(item_price.get(i)));
            total_value = total_value + compute_total;
        }

        //Compute for VAT Exclusive
        double vat_exlusive = total_value * 0.12;
        double tota_value_with_vat = total_value + vat_exlusive;



        //Store the latest value
        SharedPreferences store_home_sale_state = context.getSharedPreferences("store_payment_value", Context.MODE_PRIVATE);
        SharedPreferences.Editor store_home_sale_stateEdit = store_home_sale_state.edit();
        store_home_sale_stateEdit.putString("store_payment_value",String.valueOf(tota_value_with_vat));
        store_home_sale_stateEdit.commit();


        //back to Default
        SharedPreferences store_user_id = context.getSharedPreferences("discounted_payment", Context.MODE_PRIVATE);
        SharedPreferences.Editor store_username_editor = store_user_id.edit();

        store_username_editor.putString("discounted_payment",String.valueOf(tota_value_with_vat));
        store_username_editor.putString("split_count","1");
        store_username_editor.putString("total_discount","0");
        store_username_editor.commit();

        mView.updatTotalData(cart_id,customer_id,item_name,item_quantity,item_price,total_value,vat_exlusive,tota_value_with_vat,item_image);

    }

    @Override
    public void cartId(final String cart_id, final Context context_1, final String customer_id) {
        localhost = lc.getLocalhost();

        String item_name;

        //query for getting the cart item
        StringRequest getCartItem = new StringRequest(Request.Method.POST, localhost + main_pos_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getCartItemRes",response);
                if(!response.contains("failed")){
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        JSONArray jsonArray = jsonObject.getJSONArray("cart_data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String  item_quantity = jsonObject1.getString("item_quantity");
                            String item_name      = jsonObject1.getString("item_name");

                            mView.openEditCartDialog(Double.parseDouble(item_quantity),item_name,context_1,customer_id);
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
                Log.d("getCartItemError",error.toString());
                Toast.makeText(context,"Connection Problem!",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","getCartItem");
                hashMap.put("get_cartItem",cart_id);

                return hashMap;

            }
        };
        Volley.newRequestQueue(context).add(getCartItem);
    }

    @Override
    public void searchItem(final String search_string) {
        localhost = lc.getLocalhost();

        //Object declaration
        final ArrayList<String> item_id = new ArrayList<>();
        final ArrayList<String> category_name = new ArrayList<>();
        final ArrayList<String> item_name = new ArrayList<>();
        final ArrayList<String> item_price = new ArrayList<>();
        final ArrayList<String> item_desc = new ArrayList<>();
        final ArrayList<String> item_image = new ArrayList<>();
        final ArrayList<String> item_stock = new ArrayList<>();

        StringRequest searchItem = new StringRequest(Request.Method.POST, localhost + main_pos_subSearch, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("searchItemResponse",response);
                if(!response.contains("failed")){
                    if(!response.contains("not_exists")){
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray jsonArray = jsonObject.getJSONArray("search_data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                //ArrayList item desc
                                item_id.add(jsonObject1.getString("item_id"));
                                category_name.add(jsonObject1.getString("category_name"));
                                item_name.add(jsonObject1.getString("item_name"));
                                item_price.add(jsonObject1.getString("item_price"));
                                item_desc.add(jsonObject1.getString("item_desc"));
                                item_image.add(jsonObject1.getString("item_image"));
                                item_stock.add(jsonObject1.getString("item_stock"));

                            }

                            mView.populateListFromSearch(item_id,category_name,item_name,item_price,item_desc,item_image,item_stock);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }else{
                        Toast.makeText(context,"No Item Found",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(context,"Failed to retrieve data",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("searchItemError",error.toString());
                Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","getSearchItem");
                hashMap.put("search_item",search_string);

                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(searchItem);


    }


    //Ask user if he want to delete an item
    @Override
    public void showDeleteConfirmation(final String item_name, final String customer_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Prompt");
        builder.setMessage("Do you want to delete this item?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mView.onValidatedDelete(item_name,customer_id);
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


    //When user confirmed. Proceed in deleting the item
    @Override
    public void deleteItem(final String item_name, final String customer_id) {
        localhost = lc.getLocalhost();

        //Object declaration
        final ProgressDialog pd = new ProgressDialog(context);
        pd.show();
        pd.setMessage("Deleting...");

        StringRequest deleteItem = new StringRequest(Request.Method.POST, localhost + main_pos_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("deleteItemResponse",response.toString());
                if(!response.contains("failed")){
                    if(response.contains("success")){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.hide();
                                Toast.makeText(context,"Item has been deleted!",Toast.LENGTH_LONG).show();
                                getCartList(customer_id);
                            }
                        },1000);
                    }else{
                        pd.hide();
                        Toast.makeText(context,"Failed to delete item",Toast.LENGTH_LONG).show();
                    }
                }else{
                    pd.hide();
                    Toast.makeText(context,"Failed to retrieve data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Log.d("deleteItemError",error.toString());
                Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","deleteItem");
                hashMap.put("onDeleteItem_name",item_name);
                hashMap.put("onDeleteItem_customerID",customer_id);

                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(deleteItem);
    }


    //Update Item
    @Override
    public void updateItem(final double item_quantity, final String item_name, final String customer_id) {
        localhost = lc.getLocalhost();

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Saving...");
        pd.show();

        StringRequest updateItem = new StringRequest(Request.Method.POST, localhost + main_pos_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("updateItemRes",response);
                if(!response.contains("failed")){
                    if(response.contains("success")){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.hide();
                                getCartList(customer_id);
                                Toast.makeText(context,"Your data has been updated!",Toast.LENGTH_LONG).show();
                            }
                        },1000);

                    }
                }else{
                    pd.hide();
                    Toast.makeText(context,"failed to retrieve data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Log.d("updateItemError",error.toString());
                Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","updateItem");
                hashMap.put("onUpdate_itemName",item_name);
                hashMap.put("onUpdate_itemQuantity",String.valueOf(item_quantity));
                hashMap.put("onUpdate_customerId",customer_id);



                return hashMap;

            }
        };
        Volley.newRequestQueue(context).add(updateItem);
    }



    //Calculate the discount with logic
    @Override
    public void calculateDiscount(int split_count, int stud_count, int sen_count) {
        localhost = lc.getLocalhost();

        //object declaration
        double total_payment_value = 0;
        double total_sen_stud_discount = 0;

        //get the stored payment value
        SharedPreferences get_payment_value = context.getSharedPreferences("store_payment_value", Context.MODE_PRIVATE);
        total_payment_value = Double.parseDouble(get_payment_value.getString("store_payment_value",""));


        //START OF LOGIC
        double new_total = 0;
        if(split_count>0){
            new_total = (total_payment_value/split_count);
        }else{
            new_total = total_payment_value;
        }

        ArrayList<String> data_discount = new ArrayList<>();
        ArrayList<String> cart_total_filtered = new ArrayList<>();
        int total_val = (((sen_count+stud_count) - split_count))*-1;

        data_discount.add(String.valueOf(total_val));
        data_discount.add(String.valueOf(stud_count));
        data_discount.add(String.valueOf(sen_count));

        Log.d("dat_discount_filed",data_discount.toString());

        //regular
        if(!data_discount.get(0).equals("0")){
            for(int reg = 0; reg < Integer.parseInt(data_discount.get(0)); reg ++){
                cart_total_filtered.add(String.format("%.2f",new_total));
            }
        }

        //student
        if(!data_discount.get(1).equals("0")){
            for(int stud = 0; stud < Integer.parseInt(data_discount.get(1));stud++){
                double stud_disc_total = Double.parseDouble(data_discount.get(1))*0.20;
                double stud_disc_final = (new_total*stud_disc_total);
                double final_stud_value = ((stud_disc_final - new_total)*-1);

                cart_total_filtered.add(String.format("%.2f",final_stud_value));

                //Store to total discount
                total_sen_stud_discount = total_sen_stud_discount + stud_disc_final;
            }
        }
        //senior
        if(!data_discount.get(2).equals("0")){
            for(int sen = 0; sen < Integer.parseInt(data_discount.get(1)); sen ++){
                double stud_disc_total = Double.parseDouble(data_discount.get(1))*0.20;
                double stud_disc_final = (new_total*stud_disc_total);
                double final_stud_value = ((stud_disc_final - new_total)*-1);

                cart_total_filtered.add(String.format("%.2f",final_stud_value));

                //Store to total discount
                total_sen_stud_discount = total_sen_stud_discount + stud_disc_final;
            }
        }

        double final_total = 0;
        for(int c = 0; c < cart_total_filtered.size(); c ++){
            final_total = final_total + (Double.parseDouble(cart_total_filtered.get(c)));
        }


        Log.d("final_value_discounted",cart_total_filtered.toString() + " Total: " + final_total+ "\n TOTAL DISCOUNT"+total_sen_stud_discount);


        mView.updateTotalWithDiscount(final_total,split_count,total_sen_stud_discount);


        int regular_number = split_count - (stud_count+sen_count);

        SharedPreferences store_user_id = context.getSharedPreferences("receipt_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor store_username_editor = store_user_id.edit();
        store_username_editor.putString("regular_number",String.valueOf(regular_number));
        store_username_editor.putString("student_number",String.valueOf(stud_count));
        store_username_editor.putString("senior_number",String.valueOf(sen_count));
        store_username_editor.commit();

    }

    //store to local the new discounted payment
    @Override
    public void storeDiscountedPayment(double total_with_discount,int split_count, double total_discount) {

        //Store the data to local
        SharedPreferences store_user_id = context.getSharedPreferences("discounted_payment", Context.MODE_PRIVATE);
        SharedPreferences.Editor store_username_editor = store_user_id.edit();
        store_username_editor.putString("discounted_payment",String.valueOf(total_with_discount));
        store_username_editor.putString("split_count",String.valueOf(split_count));
        store_username_editor.putString("total_discount",String.valueOf(total_discount));
        store_username_editor.commit();

    }

    //getCartList
    public void getCartList(final String customer_id){
        localhost = lc.getLocalhost();

        final ArrayList<String> cart_id_1 = new ArrayList<>();
        final ArrayList<String> customer_id_1 = new ArrayList<>();
        final ArrayList<String> item_name_1 = new ArrayList<>();
        final ArrayList<String> item_quantity_1 = new ArrayList<>();
        final ArrayList<String> item_price_1 = new ArrayList<>();
        final ArrayList<String> item_image_1 = new ArrayList<>();


        StringRequest getCartList = new StringRequest(Request.Method.POST, localhost + main_pos_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getCartListRes",response.toString());
                if(!response.contains("failed")){
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        JSONArray jsonArray = jsonObject.getJSONArray("cart_data");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            //Getting the value and storing to arraylist
                            cart_id_1.add(jsonObject1.getString("cart_id"));
                            customer_id_1.add(jsonObject1.getString("customer_id"));
                            item_name_1.add(jsonObject1.getString("item_name"));
                            item_quantity_1.add(jsonObject1.getString("item_quantity"));
                            item_price_1.add(jsonObject1.getString("item_price"));
                            item_image_1.add(jsonObject1.getString("item_image"));

                            //Populate cart list of the view
                            mView.populateCartList(cart_id_1,customer_id_1,item_name_1,item_quantity_1,item_price_1,item_image_1);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("getCartPrintStact",e.toString());
                        Toast.makeText(context,"Exception Error!",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(context,"Failed to retrieve data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getCartListError",error.toString());
                Toast.makeText(context,"Connection Problem!",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","getCartList");
                hashMap.put("get_cartCustomerId",customer_id);

                return hashMap;

            }
        };
        Volley.newRequestQueue(context).add(getCartList);

    }
}
