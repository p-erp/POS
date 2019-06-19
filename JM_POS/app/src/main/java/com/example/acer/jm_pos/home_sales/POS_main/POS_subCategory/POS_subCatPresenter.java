package com.example.acer.jm_pos.home_sales.POS_main.POS_subCategory;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class POS_subCatPresenter implements POS_subCatContract.pos_subPresenter{
    //MVP declaration
    POS_subCatContract.pos_subView mView;


    //network address Declaration
    String localhost = "";
    String main_subCat = localhost + "/MEATSHOP_POS_SALE/android_php/POS_main/POS_subCategory/pos_subCategory.php";
    com.example.acer.jm_pos.localhost lc = new localhost();


    POS_subCatPresenter(POS_subCatContract.pos_subView view){
        this.mView = view;

    }

    @Override
    public void getCategoriesList(final Context context) {
        localhost = lc.getLocalhost();

        //Object declaration
        final ArrayList<String> category_name = new ArrayList<>();


        StringRequest getCategoryList = new StringRequest(Request.Method.POST, localhost + main_subCat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getCatListResponse",response);
                if(!response.contains("failed")){
                    if(response.contains("No_categories")){
                        Toast.makeText(context,"No Categories! Please add categories first",Toast.LENGTH_LONG).show();
                    }else{
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray jsonArray = jsonObject.getJSONArray("category_data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                category_name.add(jsonObject1.getString("category_name"));
                            }
                            Log.d("getCatList",category_name.toString());
                            mView.populateCategoryList(category_name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }else{
                    Toast.makeText(context,"Failed to retrieve data!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getCatListError",error.toString());
                Toast.makeText(context,"Connection Problem",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","getCategoryList");


                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(getCategoryList);
    }
}
