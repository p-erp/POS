package com.example.acer.jm_pos.home_sales.POS_main.POS_subReceipt;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.jm_pos.inventory.inventory_view_items.inventory_view_adapter;
import com.example.acer.jm_pos.localhost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class POS_subReceiptPresenter implements POS_subReceiptContract.pos_subPresenter {

    //MVP object declaration
    POS_subReceiptContract.pos_subView mView;
    Context context;

    //Object declaration
    RecyclerView receipt_list;

    //Network declaration
    String localhost = "";
    String main_pos_subReceipt = localhost + "/MEATSHOP_POS_SALE/android_php/POS_main/POS_subReceipt/pos_subReceipt.php";
    com.example.acer.jm_pos.localhost lc = new localhost();


    //Object declaration for getReceipt Item
    String date = "";
    String inCharge = "";
    String itemName = "";
    String itemPrice = "";
    String itemQuantity = "";
    String itemTotal = "";
    String item_SumTotal = "";


    POS_subReceiptPresenter(POS_subReceiptContract.pos_subView view){
        this.mView = view;
        this.context = (Context) mView;
    }

    @Override
    public void getReceiptData() {

        //variable declaration
        ArrayList<String> split_countReceipt = new ArrayList<>();
        ArrayList<String> receipt_type  = new ArrayList<>();

        //get the split count value
        SharedPreferences get_splitCount = context.getSharedPreferences("discount_data", Context.MODE_PRIVATE);
        String split_count = get_splitCount.getString("split_receipt","");

        //get the value for deviceID
        SharedPreferences get_deviceID = context.getSharedPreferences("cart_id", Context.MODE_PRIVATE);
        String customer_id = get_deviceID.getString("cart_id","");

        //get the stored payment value
        SharedPreferences get_payment_value = context.getSharedPreferences("receipt_data", Context.MODE_PRIVATE);
        String regular_number = get_payment_value.getString("regular_number","");
        String student_number = get_payment_value.getString("student_number","");
        String senior_number = get_payment_value.getString("senior_number","");

        //Make a log for local storage data
        Log.d("receipt_dataGet","reg: "+ regular_number+" std: "+student_number+" sen: "+senior_number+" Split: "+split_count);

        //Populate the receipt_count
        for(int receipt_count = 0; receipt_count < Integer.parseInt(split_count);receipt_count ++){
            split_countReceipt.add(String.valueOf(receipt_count));
        }

        //Logic for dividing the receipt into regular, student and senior receipt
        for(int reg = 0 ; reg < Integer.parseInt(regular_number); reg ++){
            receipt_type.add("regular");
        }
        for(int stud = 0 ; stud < Integer.parseInt(student_number); stud ++){
            receipt_type.add("student");
        }
        for(int sen = 0 ; sen < Integer.parseInt(senior_number) ; sen ++){
            receipt_type.add("senior");
        }


        //Get the receipt_item
        getReceipt_data(split_countReceipt,receipt_type,customer_id);

    }

    @Override
    public void printReceipt() {
        localhost = lc.getLocalhost();

        //get the value for deviceID
        SharedPreferences get_deviceID = context.getSharedPreferences("cart_id", Context.MODE_PRIVATE);
        final String customer_id = get_deviceID.getString("cart_id","");

        //HTTP request
        final StringRequest deleteFrom_receipt = new StringRequest(Request.Method.POST, localhost + main_pos_subReceipt, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("deleteFrm_receiptRes",response.toString());
                if(!response.contains("failed")){
                    if(response.contains("success")){
                        mView.goToPOS_Main();
                    }
                }else {
                    Toast.makeText(context,"Failed to execute process",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("deleteFrom_receiptErro",error.toString());
                Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","deleteFrom_rcpt");
                hashMap.put("del_deviceID",customer_id);


                return hashMap;

            }
        };
        Volley.newRequestQueue(context).add(deleteFrom_receipt);
    }

    //Get the receipt_items
    public void getReceipt_data(final ArrayList<String> split_countReceipt, final ArrayList<String> receipt_type, final String customer_id){
        localhost = lc.getLocalhost();


        //request HTTP
        StringRequest getReceipt_items = new StringRequest(Request.Method.POST, localhost + main_pos_subReceipt, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getReceipt_res",response.toString());
                if(!response.contains("failed")){
                    //Convert the json object to arraylist
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        JSONArray jsonArray = jsonObject.getJSONArray("receipt_data");
                        for(int i = 0; i < jsonArray.length();i ++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            //Get the string of json data
                            date = jsonObject1.getString("receipt_date");
                            inCharge = jsonObject1.getString("receipt_inCharge");
                            itemName = itemName + jsonObject1.getString("receipt_itemName")+"\n";
                            itemPrice = itemPrice + jsonObject1.getString("receipt_itemPrice")+"\n";
                            itemQuantity = itemQuantity + jsonObject1.getString("receipt_itemQuantity")+"\n";
                            itemTotal = itemTotal + jsonObject1.getString("receipt_itemTotal")+"\n";
                            item_SumTotal = jsonObject1.getString("receipt_sumTotal");

                            //Populate the receipt_list recycler view
                            mView.populateReceipt_list(split_countReceipt,receipt_type,date,inCharge,itemName,itemPrice,itemQuantity,itemTotal,item_SumTotal);
                        }




                    } catch (JSONException e) {
                        Log.d("JSONEXceptionReceipt",e.toString());
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(context,"Failed to retrieve Data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getReceipt_error",error.toString());
                Toast.makeText(context,"Connection Problem!",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","get_receiptItem");
                hashMap.put("getReceipt_ID",customer_id);


                return hashMap;

            }
        };
        Volley.newRequestQueue(context).add(getReceipt_items);



    }
}
