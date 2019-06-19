package com.example.acer.jm_pos.home_sales.POS_main.POS_subSearch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;

public class POS_subSearchDialog extends AppCompatDialogFragment {

    //Object declaration

    EditText searchItem;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.pos_subsearchdialog, null);

        //object declaration
        searchItem = view.findViewById(R.id.search_item);



        builder.setView(view)
                .setTitle("Search")
                .setNegativeButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        POS_main pom = POS_main.instance;
                        if(pom!=null){
                            pom.search(searchItem.getText().toString());
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





}
