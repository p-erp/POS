package com.example.acer.jm_pos.home_sales.POS_main.POS_subPayment.POS_subPayment_cash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subCategory.POS_subCatContract;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subCategory.POS_subCatPresenter;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subCategory.POS_subCategoryDialog;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subCategory.POS_subCategoryDialog_Adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class POS_subReceiptInformation_dialog extends AppCompatDialogFragment{

    //presenter declaration
    POS_subCatPresenter presenter;

    //Object declaration
    RecyclerView categoryRecycler_View;
    TextView change;
    TextView time_and_date;

    //instance
    public static POS_subCategoryDialog instance;

    //variable declaration
    double payment_change = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.pos_subreceiptinformation_dialog, null);

        //get the local storage for payment change
        SharedPreferences stored = getContext().getSharedPreferences("payment_change", Context.MODE_PRIVATE);
        payment_change = Double.parseDouble(stored.getString("payment_change",""));

        //Object declaration
        categoryRecycler_View = view.findViewById(R.id.category_list);
        change = view.findViewById(R.id.change);
        time_and_date = view.findViewById(R.id.time_and_date);


        Log.d("payment",""+payment_change);
        builder.setView(view)
                .setTitle("Transaction Complete")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        POS_subPayment_cash pc = POS_subPayment_cash.instance;
                        if(pc!=null){
                            pc.goToPOS_main();
                            dismiss();
                        }
                    }
                })
                .setPositiveButton("Print Receipt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        POS_subPayment_cash pc = POS_subPayment_cash.instance;
                        if(pc!=null){
                            pc.goToReceipt();
                            dismiss();
                        }

                    }
                });

        //System start
        systemStart();

        return builder.create();
    }


    public void systemStart(){
        //Generate date
        //This generate the day
        SimpleDateFormat sales_getDay = new SimpleDateFormat("MM/dd/yyyy");
        String date = sales_getDay.format(new Date());

        change.setText("Php "+payment_change);
        time_and_date.setText("Date: "+date);
    }
}
