package com.example.acer.jm_pos.home_sales.POS_main.POS_subPayment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subPayment.POS_subPayment_cash.POS_subPayment_cash;

public class POS_subPayment_dialog  extends AppCompatDialogFragment {

    //Object declaration

    ImageView payment_cash;
    ImageView payment_bank;
    ImageView payment_wallet;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.pos_subpayment_dialog, null);

        //object declaration
        payment_cash = view.findViewById(R.id.payment_cash);
        payment_bank = view.findViewById(R.id.payment_bank);
        payment_wallet = view.findViewById(R.id.payment_wallet);


        //this is disable (We don't need this to be edited) as we will focus on the buttons
        builder.setView(view)
                .setTitle("Payment Method")
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
        systemStart();

        return builder.create();
    }


    //Run when activty launch
    public void systemStart(){

        //when cash payment Click
        payment_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to payment cash
                Intent intent = new Intent(getContext(),POS_subPayment_cash.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                dismiss();
            }
        });

        //when bank payment click
        payment_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //when wallet payment click
        payment_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
