package com.example.acer.jm_pos.dashboard;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChartView;
import com.example.acer.jm_pos.localhost;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class dashboardPresenter implements dashboardContract.dashboardPresenter {
    //MVP declaration
    dashboardContract.dashboardView mView;

    //Database Connection Declaration [Do Not Delete]
    String localhost = "";
    String main_dashboard = localhost + "/MEATSHOP_POS_SALE/android_php/dashboard/dashboard.php";
    com.example.acer.jm_pos.localhost lc = new localhost();

    dashboardPresenter(dashboardContract.dashboardView view){
        this.mView = view;

    }

    //get the sales data (sales total)
    @Override
    public void populateSummary(final Context context) {
        localhost = lc.getLocalhost();

        StringRequest getSales_data = new StringRequest(Request.Method.POST, localhost + main_dashboard, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getSales_resp",response);
                if(!response.contains("failed")){
                    mView.displayTotalSales(response);
                }else{
                    Toast.makeText(context,"Failed to retrieve data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getSales_error",error.toString());
                Toast.makeText(context,"Connection Problem!",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","getSales_data");


                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(getSales_data);
    }


}
