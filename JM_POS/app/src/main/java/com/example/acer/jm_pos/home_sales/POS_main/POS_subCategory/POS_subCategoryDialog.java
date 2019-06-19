package com.example.acer.jm_pos.home_sales.POS_main.POS_subCategory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class POS_subCategoryDialog extends AppCompatDialogFragment implements POS_subCatContract.pos_subView{

    //presenter declaration

    POS_subCatPresenter presenter;


    //Object declaration
    RecyclerView categoryRecycler_View;

    //instance
    public static POS_subCategoryDialog instance;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.pos_subcategorydialog, null);
        presenter = new POS_subCatPresenter(this);
        instance = this;

        //Object declaration
        categoryRecycler_View = view.findViewById(R.id.category_list);

        builder.setView(view)
                .setTitle("Categories")
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


    public void systemStart(){

        presenter.getCategoriesList(getContext());
    }

    @Override
    public void populateCategoryList(ArrayList<String> category_name) {
        Log.d("categoryList",category_name.toString());
        POS_subCategoryDialog_Adapter adapter = new POS_subCategoryDialog_Adapter(getActivity());
        adapter.SetData(category_name);
        categoryRecycler_View.setAdapter(adapter);
        categoryRecycler_View.setLayoutManager(new GridLayoutManager(getContext(),4));
    }

    public void dismiss(String category_name){
        dismiss();
        POS_main pm = POS_main.instance;

        if(pm!=null){
            pm.POS_categoryItem(category_name);

        }
    }
}
