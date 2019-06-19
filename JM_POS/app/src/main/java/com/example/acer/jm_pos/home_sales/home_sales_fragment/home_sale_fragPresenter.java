package com.example.acer.jm_pos.home_sales.home_sales_fragment;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.example.acer.jm_pos.home_sales.POS_main.POS_Option.POS_Option;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;

public class home_sale_fragPresenter implements home_sale_fragContract.home_salePresenter{

    //Class declaration
    home_sale_fragContract.home_saleView mView;

    //object declaration



    home_sale_fragPresenter(home_sale_fragContract.home_saleView view){
        this.mView = view;


    }

    @Override
    public void toPOSMain(Context context) {
        Intent intent = new Intent(context, POS_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    @Override
    public void toPOSOption(Context context) {
        Intent intent = new Intent(context,POS_Option.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);

    }


}
