package com.example.acer.jm_pos.home_sales;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.acer.jm_pos.inventory.inventory_fragment;
import com.example.acer.jm_pos.localhost;
import com.example.acer.jm_pos.login.login;
import com.example.acer.jm_pos.utility.ProxyHurlStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class home_sales_presenter implements home_sales_contract.home_sales_presenter{
    Context context;
    home_sales_contract.home_sales_view Mview;


    //Database Connection Declaration [Do Not Delete]
    String localhost = "";
    String main_login_php = localhost + "/MEATSHOP_POS_SALE/android_php/home_sales/main_home_sale.php";
    com.example.acer.jm_pos.localhost lc = new localhost();


    //Object Declaration
    String user_id = "";

    home_sales_presenter(home_sales_contract.home_sales_view view){
        Mview = view;
        context = (Context) Mview;
    }


    @Override
    public void onLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Prompt");
        builder.setMessage("Do you want to sign out?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog pd = new ProgressDialog(context);
                        pd.setMessage("Please Wait");
                        pd.show();
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                pd.hide();
                                Intent to_login_page = new Intent(context,login.class);
                                to_login_page.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(to_login_page);
                                Mview.onExit();

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
    public void onUserData(final String username) {
        localhost = lc.getLocalhost();

        Log.d("username",username);

        StringRequest getUserData = new StringRequest(Request.Method.POST, localhost + main_login_php, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onUserDataResponse",response.toString());
                    if(!response.contains("failed")){
                        /*
                        if(!response.contains("not_exists")){
                            try {
                                JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                JSONArray jsonArray = jsonObject.getJSONArray("user_data");
                                for(int a=0;a<jsonArray.length();a++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(a);
                                    String first_name   = jsonObject1.getString("first_name");
                                    String last_name    = jsonObject1.getString("last_name");
                                    String age          = jsonObject1.getString("age");
                                    String contact_number = jsonObject1.getString("contact_number");
                                    String address        = jsonObject1.getString("address");
                                    Mview.onPopulateNavData(first_name,last_name,contact_number);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(context,"Can't Retrieve Data!",Toast.LENGTH_LONG).show();
                        }*/
                    }else{
                        Toast.makeText(context,"Failed Query",Toast.LENGTH_LONG).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onUserDataDBError",error.toString());
                    Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("function","getUserData");
                    hashMap.put("get_username",username);
                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(getUserData);
    }

    @Override
    public void onSalesFragment() {
        Mview.changeFragmentToSales();
    }

    @Override
    public void onInventoryFragment() {
        Mview.changeFragmentToInventory();
    }

}
