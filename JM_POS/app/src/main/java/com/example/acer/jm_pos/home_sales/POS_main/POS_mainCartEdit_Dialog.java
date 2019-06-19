package com.example.acer.jm_pos.home_sales.POS_main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.localhost;

public class POS_mainCartEdit_Dialog extends AppCompatDialogFragment {

    public static POS_mainCartEdit_Dialog instance;

    //Declaration
    TextView item_name;
    EditText item_name_text;
    EditText item_quantity_text;
    Button delete_item;



    //Network connection
    String localhost = "";
    String main_pos_cart = localhost + "/MEATSHOP_POS_SALE/android_php/POS_main/pos_cartItem/pos_cartItem.php";
    com.example.acer.jm_pos.localhost lc = new localhost();

    //local stored declaration
    SharedPreferences get_item_data;

    //Object declaratin
    String getItem_name;
    String getItem_quantity;
    String getItem_customerId;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //intance declaration
        instance = this;

        //Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.pos_maincartedit_dialog, null);

        //Get stored item data
        get_item_data      = getContext().getApplicationContext().getSharedPreferences("edit_item_data", Context.MODE_PRIVATE);

        //Object declaration
        item_name = view.findViewById(R.id.item_name_title);
        item_name_text = view.findViewById(R.id.item_name);
        item_quantity_text = view.findViewById(R.id.item_quantity);
        delete_item = view.findViewById(R.id.delete_item);


        //getLocal Storage
        getItem_quantity = get_item_data.getString("quantity","");
        getItem_name = get_item_data.getString("item_name","");
        getItem_customerId = get_item_data.getString("item_customer_id","");

        //On System start
        systemStart();


        builder.setView(view)
                .setTitle("Edit Item")
                .setNegativeButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!item_quantity_text.getText().toString().equals("")){

                            //Update Item from POS_main
                            POS_main pos_main = POS_main.instance;
                            if(pos_main!=null){
                                pos_main.updateItem(Double.parseDouble(item_quantity_text.getText().toString()),getItem_name,getItem_customerId);
                            }

                        }else{
                            Toast.makeText(getContext(),"Please insert valid value!",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });
        return builder.create();
    }


    //Get the item list data and populate the textfield
    public void populateEditField(Double quantity,String item_name_1){
        localhost = lc.getLocalhost();

        item_quantity_text.setText(""+String.format("%.2f",quantity));
        item_name.setText(item_name_1.toUpperCase());
    }

    //System start
    public void systemStart(){

        //populate editText list
        populateEditField(Double.parseDouble(getItem_quantity),getItem_name);

        //Button
        delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                POS_main pos_m = POS_main.instance;
                if(pos_m!=null){
                    pos_m.confirmDeletionOfItem(getItem_name,getItem_customerId);
                }
            }
        });
    }


}
