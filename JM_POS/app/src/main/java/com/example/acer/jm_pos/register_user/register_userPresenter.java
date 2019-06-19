package com.example.acer.jm_pos.register_user;

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
import com.example.acer.jm_pos.utility.ProxyHurlStack;

import java.util.HashMap;
import java.util.Map;

public class register_userPresenter implements register_userContract.register_userContractPresenter{

    private register_userContract.register_userContractView mView;
    private Context context;

    String localhost = "";
    String main_login_php = localhost + "/MEATSHOP_POS_SALE/android_php/login/main_register.php";
    com.example.acer.jm_pos.localhost lc = new localhost();

    register_userPresenter(register_userContract.register_userContractView view){
        mView = view;
        context = (Context) mView;
    }


    @Override
    public void onRegister(final String first_name, final String last_name, final String age, final String contact_number, final String address, final String username, final String password) {
        localhost = lc.getLocalhost();
        StringRequest onRegisterRequest = new StringRequest(Request.Method.POST, localhost+main_login_php, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onRegisterResponse",response);
                    if(!response.contains("failed")){
                        if(response.contains("userExists")){
                            Toast.makeText(context,"Username already exists!",Toast.LENGTH_LONG).show();
                        }if(response.contains("success")){
                            mView.onAccountCreate();
                        }
                    }else{
                        Toast.makeText(context,"Failed Query",Toast.LENGTH_LONG).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onRegisterError",error.toString());
                Toast.makeText(context,"Connection Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("function","insertNewUser");
                    hashMap.put("first_name",first_name);
                    hashMap.put("last_name",last_name);
                    hashMap.put("age",age);
                    hashMap.put("contact_number",contact_number);
                    hashMap.put("address",address);
                    hashMap.put("username",username);
                    hashMap.put("password",password);

                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(onRegisterRequest);
    }
}
