package com.example.acer.jm_pos.home_sales.POS_main.POS_subReceipt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subPayment.POS_subPayment_cash.POS_subPayment_cash;

public class POS_printing_dialog extends AppCompatDialogFragment {

    //Object declaration


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.pos_printing_dialog, null);


        //this is disable (We don't need this to be edited) as we will focus on the buttons
        builder.setView(view)
                .setTitle("Receipt Printing")
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });


        //System start
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
                POS_subReceipt posR = POS_subReceipt.instance;
                if(posR!=null){
                    posR.deleteFromReceipt();
                }
            }
        },2000);

        return builder.create();
    }

}
