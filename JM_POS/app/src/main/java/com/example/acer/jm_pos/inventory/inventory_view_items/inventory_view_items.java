package com.example.acer.jm_pos.inventory.inventory_view_items;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.home_sales;
import com.example.acer.jm_pos.inventory.inventory_add_items.inventory_add_items;
import com.example.acer.jm_pos.inventory.inventory_fragment;
import com.example.acer.jm_pos.inventory.inventory_update_items.inventory_update_items;

import java.util.ArrayList;

public class inventory_view_items extends AppCompatActivity implements inventory_viewContract.inventory_View{

    //Object Declarations
    inventory_viewPresenter presenter;

    //ImagView declaration
    ImageView back_button;
    ImageView resfresh_button;

    //Button declaration
    Button add_product;

    //RecyclerView declaration
    RecyclerView inventory_viewRecycler;

    //ProgressDialog
    ProgressDialog pd;

    //instance
    static inventory_view_items instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_view_items);
        presenter = new inventory_viewPresenter(this);

        //instance
        instance = inventory_view_items.this;

        //Object Declarations
        back_button             = findViewById(R.id.back_button);
        inventory_viewRecycler  = findViewById(R.id.inventory_list);
        add_product             = findViewById(R.id.add_product);
        resfresh_button         = findViewById(R.id.refresh);
        pd                      = new ProgressDialog(inventory_view_items.this);

        //System Process at Startup
        Click();
        onSystemStart();
    }

    public void Click(){
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackButton(getApplicationContext());
            }
        });

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inventory_view_items.this, inventory_add_items.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        resfresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRefreshTabke();
            }
        });
    }

    public void onSystemStart(){
        presenter.onGettingAllItems();

    }


    @Override
    public void populateRecyclerView(ArrayList<String> item_id,
                                     ArrayList<String> category_id,
                                     ArrayList<String> item_price,
                                     ArrayList<String> inventory_id,
                                     ArrayList<String> item_name,
                                     ArrayList<String> item_desc,
                                     ArrayList<String> item_stock,
                                     ArrayList<String> item_image) {


        inventory_view_adapter adapter = new inventory_view_adapter(getApplicationContext());
        adapter.SetData(item_id,category_id,item_price,inventory_id,item_name,item_desc,item_stock,item_image);
        inventory_viewRecycler.setAdapter(adapter);
        inventory_viewRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }


    @Override
    public void goToDelete(String item_name) {
        //Trigger delete request
        presenter.onDeleteItem(item_name);
    }

    @Override
    public void refreshTable() {
        pd.setMessage("Loading");
        pd.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(inventory_view_items.this,inventory_view_items.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        },1000);
    }

    public void deleteItem(String item_name){
        //Ask for confirmation in deletion of item
        presenter.onDeleteAlert(item_name);

    }

    public void updateItem(String item_name){
        //update item
        presenter.onUpdateItem(item_name);
    }

}
