package com.example.acer.jm_pos.home_sales.POS_main.POS_Option;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;
import com.example.acer.jm_pos.localhost;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class POS_OptionPresenter implements POS_OptionContract.pos_optionPresenter {
    //MVP declaration
    POS_OptionContract.pos_optionView mView;
    Context context;

    //network address Declaration
    String localhost = "";
    String main_posOption = localhost + "/MEATSHOP_POS_SALE/android_php/POS_main/POS_Option/pos_option.php";
    com.example.acer.jm_pos.localhost lc = new localhost();


    POS_OptionPresenter(POS_OptionContract.pos_optionView view){
        this.mView = view;
        this.context = (Context) mView;

    }

    @Override
    public void saveCapitalToDatabase(final double capital) {
        localhost = lc.getLocalhost();

        //This generate date
        SimpleDateFormat monthlyFormat = new SimpleDateFormat("MM");
        final String month = monthlyFormat.format(new Date());

        StringRequest saveCapital_Db = new StringRequest(Request.Method.POST, localhost + main_posOption, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("saveCptl_db",response.toString());
                if(!response.contains("failed")){
                    if(response.contains("success")){

                        if(response.contains("exists")){
                            Toast.makeText(context,"You already entered the capital for this month!",Toast.LENGTH_LONG).show();
                        }else if(response.contains("success")){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mView.goToPOSMain();
                                }
                            },1000);
                        }

                    }
                }else{
                    Toast.makeText(context,"Failed to execute Process!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("saveCptl_dbError", error.toString());
                Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","saveCapital");
                hashMap.put("save_capitalValue",String.format("%.2f",capital));
                hashMap.put("save_capitalMonth",month);



                return  hashMap;
            }

        };
        Volley.newRequestQueue(context).add(saveCapital_Db);
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(context,POS_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }
}
