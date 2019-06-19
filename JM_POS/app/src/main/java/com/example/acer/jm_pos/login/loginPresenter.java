package com.example.acer.jm_pos.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.jm_pos.localhost;
import com.example.acer.jm_pos.register_user.register_user;
import com.example.acer.jm_pos.utility.ProxyHurlStack;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.WIFI_SERVICE;

public class loginPresenter implements loginContract.loginPresenter {
    private loginContract.loginView loginView;
    private Context context;

    //network address Declaration
    String localhost = "";
    String main_login_php = localhost + "/MEATSHOP_POS_SALE/android_php/login/main_login.php";
    localhost lc = new localhost();

    //Object Declaration
    ProgressDialog pd;
    loginPresenter(loginContract.loginView view){
        loginView = view;
        context = (Context) loginView;
    }

    @Override
    public void login(final String username, final String password, final Context context1) {
        //Object declaration
        localhost = lc.getLocalhost();
        pd = new ProgressDialog(context);

        pd.setMessage("Logging In...");
        pd.show();
        Log.d("StringUserPass",""+username +" "+password);
        Log.d("link_login_string",localhost + main_login_php);
        StringRequest getDatabaseReq = new StringRequest(Request.Method.POST, localhost + main_login_php, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("login_response",response);
                    if(response.contains("user_not_exists")){
                        pd.hide();
                        loginView.createNewAccount();
                    }else if(response.contains("failed")){
                        pd.hide();
                        Toast.makeText(context,"Failed Query",Toast.LENGTH_LONG).show();
                    }else{

                     //new version for getting the data
                     loginView.getUserId(username);

                    /*
                    //Filtering string
                    String filtered_string = response.replaceAll("user_exists","");
                    Log.d("filtered_string_login",filtered_string);

                    //Storing user_id to local
                    SharedPreferences store_user_id = context.getSharedPreferences("username", Context.MODE_PRIVATE);
                    SharedPreferences.Editor store_username_editor = store_user_id.edit();
                    store_username_editor.putString("username",response);
                    store_username_editor.commit();

                    //Storing home_sales_state to local
                    SharedPreferences store_home_sale_state = context.getSharedPreferences("home_sales_state", Context.MODE_PRIVATE);
                    SharedPreferences.Editor store_home_sale_stateEdit = store_home_sale_state.edit();
                    store_home_sale_stateEdit.putString("home_sales_state","home_sales");
                    store_home_sale_stateEdit.commit();

                    //condition
                    loginView.isLoginSuccessful();
                    */
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Log.d("loginError",error.toString());
                Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("function","loginUser");
                    hashMap.put("username",username);
                    hashMap.put("password",password);

                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(getDatabaseReq);
    }

    @Override
    public void getDeviceIP(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        DhcpInfo dhcp = wm.getDhcpInfo();
        int dhc = dhcp.serverAddress;
        String dhcS = ( dhc & 0xFF)+ "."+((dhc >> 8 ) & 0xFF)+"."+((dhc >> 16 ) & 0xFF)+"."+((dhc >> 24 ) & 0xFF);

        Log.d("deviceIP",ip+"\n "+dhcS);
    }

    @Override
    public void userHasNoAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Prompt");
        builder.setMessage("User has no account, Do you want to create new?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pd = new ProgressDialog(context);
                        pd.setMessage("Please Wait");
                        pd.show();
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                pd.hide();
                                Intent intent = new Intent(context,register_user.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(intent);
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
    public void createAccount() {
        Intent intent = new Intent(context,register_user.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }



    @Override
    public void getUserData(final String username) {
        localhost = lc.getLocalhost();

        StringRequest getUserData = new StringRequest(Request.Method.POST, localhost + main_login_php, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getUserData_rest",response);
                if(!response.contains("failed")){

                    //Storing user_id to local
                    SharedPreferences store_user_id = context.getSharedPreferences("username", Context.MODE_PRIVATE);
                    SharedPreferences.Editor store_username_editor = store_user_id.edit();
                    store_username_editor.putString("username",response);
                    store_username_editor.commit();


                    //Storing home_sales_state to local
                    SharedPreferences store_home_sale_state = context.getSharedPreferences("home_sales_state", Context.MODE_PRIVATE);
                    SharedPreferences.Editor store_home_sale_stateEdit = store_home_sale_state.edit();
                    store_home_sale_stateEdit.putString("home_sales_state","home_sales");
                    store_home_sale_stateEdit.commit();

                    //condition
                    loginView.isLoginSuccessful();

                }else{
                    Toast.makeText(context,"Failed to retrieve data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getUserDataError",error.toString());
                    Toast.makeText(context,"Connection Problem!",Toast.LENGTH_LONG).show();
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
}
