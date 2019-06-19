package com.example.acer.jm_pos.home_sales.POS_main.POS_subPayment.POS_subPayment_cash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subPayment.POS_subPayment_dialog;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subReceipt.POS_subReceipt;

public class POS_subPayment_cash extends AppCompatActivity implements POS_subPayment_cashContract.cash_paymentView{

    //Instance
    public static POS_subPayment_cash instance;

    //presenter declaration
    POS_subPayment_cashPresenter presenter;

    //object declaration
    ImageView back_button;

    EditText payment;

    TextView vatable_sales;
    TextView split_type;
    TextView items;
    TextView discount;
    TextView total;

    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_sub_payment_cash);
        //presenter declaration
        presenter = new POS_subPayment_cashPresenter(this);
        instance = this;

        //object declaration
        back_button     = findViewById(R.id.back_button);
        vatable_sales   = findViewById(R.id.vatable_sales);
        split_type      = findViewById(R.id.split_type);
        payment         = findViewById(R.id.payment);
        items           = findViewById(R.id.items);
        discount        = findViewById(R.id.discount);
        total           = findViewById(R.id.total);
        pay             = findViewById(R.id.pay);


        //System start
        systemStart();
    }

    public void systemStart(){

        //get payment summary
        presenter.getSummaryOfPayment();


        //when user back pressed
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //payment
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!payment.getText().toString().equals("")){
                    presenter.submitPayment(Double.parseDouble(payment.getText().toString()));
                }else{
                    Toast.makeText(getApplicationContext(),"Please insert valid value",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //Populate the summary payment
    @Override
    public void populatePaymentSummary(double vatable_sales_1, double total_payment_1, int receipt_count_1, double total_discounts_1) {
        vatable_sales.setText("Vatable Sales: Php "+String.format("%.2f",vatable_sales_1));
        split_type.setText("Split Count: "+receipt_count_1+" (receipt)");
        items.setText("Items: n/a");
        discount.setText("Discounts: Php "+String.format("%.2f",total_discounts_1));
        total.setText("Total: Php "+String.format("%.2f",total_payment_1));
    }


    //back to main
    @Override
    public void backToMainPost() {

        Intent intent = new Intent(POS_subPayment_cash.this, POS_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();

    }

    @Override
    public void displayPrintOption(Double change) {
        //Store the data to local
        SharedPreferences store_user_id = getApplication().getSharedPreferences("payment_change", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor store_username_editor = store_user_id.edit();
        store_username_editor.putString("payment_change",String.format("%.2f",change));
        store_username_editor.commit();

        POS_subReceiptInformation_dialog print_info = new POS_subReceiptInformation_dialog();
        print_info.show(getSupportFragmentManager(), "example dialog");


    }


    public void goToReceipt(){
        Intent intent = new Intent(POS_subPayment_cash.this, POS_subReceipt.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void goToPOS_main(){
        Intent intent = new Intent(POS_subPayment_cash.this, POS_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
