package com.example.acer.jm_pos.inventory.inventory_update_items;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.acer.jm_pos.R;

public class inventory_update_items extends AppCompatActivity implements inventory_updateContract.inventory_updateView{

    //presenter declaration for MVP
    inventory_updatePresenter presenter;

    //declaration for object
    ImageView back_button;
    ImageView delete_item;

    EditText item_name;
    EditText item_price;
    EditText item_desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_update_items);
        presenter = new inventory_updatePresenter(this);

        //declaration for object
        back_button = findViewById(R.id.back_button);
        delete_item = findViewById(R.id.delete_item);
        item_name   = findViewById(R.id.item_name);
        item_price  = findViewById(R.id.item_price);
        item_desc   = findViewById(R.id.item_desc);


        //Start Process for this module
        systemStart();
    }

    public void systemStart(){
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
               finish();
            }
        });

        delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
