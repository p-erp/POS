package com.example.acer.jm_pos.inventory.inventory_add_category;

import android.app.ProgressDialog;
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

import java.util.HashMap;
import java.util.Map;

public class inventory_addCatPresenter implements inventory_addCatContract.inventory_addPresenter {
    //declaration for contract MVP and context
    inventory_addCatContract.inventory_addView mView;
    Context context_1;

    //network address Declaration
    String localhost = "";
    String main_inventory = localhost + "/MEATSHOP_POS_SALE/android_php/inventory/main_inventory.php";
    com.example.acer.jm_pos.localhost lc = new localhost();


    //object declaration

    //progressdialog declaration
    ProgressDialog pd;


    inventory_addCatPresenter(inventory_addCatContract.inventory_addView view){
        this.mView = view;
        this.context_1 = (Context) mView;
    }

    @Override
    public void saveCatToDatabase(final String category_name) {
        localhost = lc.getLocalhost();

        //object declaration
        pd = new ProgressDialog(context_1);

        StringRequest saveCatRequest = new StringRequest(Request.Method.POST, localhost + main_inventory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.hide();
                Log.d("saveCatResponse",response.toString());

                //response condition
                if(!response.contains("failed")){
                    if(response.contains("success")){
                        mView.onSaveSuccess();
                    }else if(response.contains("category_exists")){
                        Toast.makeText(context_1,"Category Name exists, Please choose another one!",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context_1,"Failed to retrieve data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Log.d("saveCatError",error.toString());
                Toast.makeText(context_1,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put("function","addCategory");
                hashMap.put("addCat_name",category_name);

                return hashMap;
            }
        };
        Volley.newRequestQueue(context_1).add(saveCatRequest);
    }

}
