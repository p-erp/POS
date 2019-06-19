package com.example.acer.jm_pos.home_sales.POS_main.POS_subPayment.POS_subPayment_cash;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.acer.jm_pos.home_sales.POS_main.POS_subPayment.POS_subPayment_dialog;
import com.example.acer.jm_pos.localhost;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class POS_subPayment_cashPresenter implements POS_subPayment_cashContract.cash_paymentPresenter {
    //Class declaration
    POS_subPayment_cashContract.cash_paymentView mView;
    Context context;


    //Storage declaration
    SharedPreferences get_payment_value;
    SharedPreferences get_total_payment;

    //Variable declaration
    double vatabble_sales;
    double total_payment;
    int split_count;
    double total_discount;

    //Database Connection Declaration [Do Not Delete]
    String localhost = "";
    String main_subPayment = localhost + "/MEATSHOP_POS_SALE/android_php/POS_main/POS_subPayment/pos_subPayment.php";
    com.example.acer.jm_pos.localhost lc = new localhost();

    POS_subPayment_cashPresenter(POS_subPayment_cashContract.cash_paymentView view){
        this.mView = view;
        this.context = (Context) mView;

    }


    //Provide summary of payment
    @Override
    public void getSummaryOfPayment() {

        //get the stored payment value
        get_payment_value = context.getSharedPreferences("store_payment_value", Context.MODE_PRIVATE);
        vatabble_sales = Double.parseDouble(get_payment_value.getString("store_payment_value",""));

        //get final value payment, receipt count and total discount
        get_total_payment = context.getSharedPreferences("discounted_payment", Context.MODE_PRIVATE);
        total_payment = Double.parseDouble(get_total_payment.getString("discounted_payment",""));
        split_count = Integer.parseInt(get_total_payment.getString("split_count",""));
        total_discount = Double.parseDouble(get_total_payment.getString("total_discount",""));

        mView.populatePaymentSummary(vatabble_sales,total_payment,split_count,total_discount);
    }

    @Override
    public void submitPayment(Double payment) {
        //object declaration
        get_total_payment = context.getSharedPreferences("discounted_payment", Context.MODE_PRIVATE);
        total_payment = Double.parseDouble(get_total_payment.getString("discounted_payment",""));

        if(payment>total_payment){
            double change = payment - total_payment;
            submitPaymentToSales(change);
        }else{
            Toast.makeText(context,"Amount is not enough, We don't accept credit!",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void goToReceipt() {

    }

    //Submit Payment to sales
    public void submitPaymentToSales(final double change){
        localhost = lc.getLocalhost();

        //Object declaration
        final String sales_day;
        final String sales_month;
        final String sales_year;
        final String cart_id;

        final ProgressDialog pd = new ProgressDialog(context);


        //This generate the day
        SimpleDateFormat sales_getDay = new SimpleDateFormat("dd");
        sales_day = sales_getDay.format(new Date());

        //This generate the month
        SimpleDateFormat sales_getMonth= new SimpleDateFormat("MMMM");
        sales_month = sales_getMonth.format(new Date());

        //This generate the year
        SimpleDateFormat sales_getYear = new SimpleDateFormat("yyyy");
        sales_year = sales_getYear.format(new Date());

        //get the stored cart_id
        SharedPreferences get_stored_cartID = context.getSharedPreferences("cart_id", Context.MODE_PRIVATE);
        cart_id = get_stored_cartID.getString("cart_id","");

        //Show dialog
        pd.setMessage("Completing transaction...");
        pd.show();
        StringRequest submitPayment_toSales = new StringRequest(Request.Method.POST, localhost + main_subPayment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("subPay_toSalesRes",response);
                if(!response.contains("failed")){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.hide();


                            mView.displayPrintOption(change);
                        }
                    },1000);

                }else{
                    pd.hide();
                    Toast.makeText(context,"Transaction Failed!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Log.d("subPay_toSalesError",error.toString());
                Toast.makeText(context,"Connection Problem!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("function","submitPay_toSales");
                hashMap.put("sales_day",sales_day);
                hashMap.put("sales_month",sales_month);
                hashMap.put("sales_year",sales_year);
                hashMap.put("cart_id",cart_id);

                return hashMap;

            }
        };
        Volley.newRequestQueue(context).add(submitPayment_toSales);
    }

}
