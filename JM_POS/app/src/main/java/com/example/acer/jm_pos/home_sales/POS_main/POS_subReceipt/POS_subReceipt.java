package com.example.acer.jm_pos.home_sales.POS_main.POS_subReceipt;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;
import com.example.acer.jm_pos.home_sales.POS_main.POS_mainCartEdit_Dialog;

import java.util.ArrayList;

public class POS_subReceipt extends AppCompatActivity implements POS_subReceiptContract.pos_subView{
    //mvp presenter
    POS_subReceiptPresenter presenter;


    //object declaration
    ImageView back_button;
    Button print_receipt;
    RecyclerView receipt_list;


    //intance
    static POS_subReceipt instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_sub_receipt);
        presenter = new POS_subReceiptPresenter(this);
        instance = this;

        //Object declaration
        back_button = findViewById(R.id.back_button);
        receipt_list = findViewById(R.id.receipt_list);
        print_receipt = findViewById(R.id.print);

        //system start
        systemStart();
    }

    //system start
    public void systemStart(){
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(POS_subReceipt.this, POS_main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        print_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Dialog
                POS_printing_dialog pos_printD = new POS_printing_dialog();
                pos_printD.show(getSupportFragmentManager(), "example dialog");


            }
        });


        presenter.getReceiptData();

    }

    @Override
    public void populateReceipt_list(ArrayList<String> receipt_count, ArrayList<String> receipt_type,
                                     String date,String inCharge,String itemName, String itemPrice,String itemQuantity,
                                     String itemTotal,String sumTotal) {

        //Log
        Log.d("populReceiptList","THIS IS IT"+receipt_type.toString());


        //Display the recycler view of receipt list
        POS_subReceipt_adapter adapter = new POS_subReceipt_adapter(getApplicationContext());
        adapter.SetData(receipt_count,receipt_type,date,inCharge,itemName,itemPrice,itemQuantity,itemTotal,sumTotal);
        receipt_list.setAdapter(adapter);
        receipt_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void goToPOS_Main() {
        Intent intent = new Intent(POS_subReceipt.this,POS_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    //process delete from receipt
    public void deleteFromReceipt(){
        presenter.printReceipt();
    }
}
