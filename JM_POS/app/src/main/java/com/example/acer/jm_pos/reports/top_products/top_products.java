package com.example.acer.jm_pos.reports.top_products;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.inventory.inventory_view_items.inventory_view_adapter;

import java.util.ArrayList;

public class top_products extends AppCompatActivity implements top_productsContract.top_productsView{
    //mvp declaration
    top_productsPresenter presenter;


    //Object declaration
    ImageView back_button;

    Spinner category_list;

    TextView total_items;

    LinearLayout noItemSelectedLayout;
    LinearLayout loadingLayout;
    RecyclerView item_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_products);
        presenter = new top_productsPresenter(this);

        //object declaration
        back_button = findViewById(R.id.back_button);
        category_list = findViewById(R.id.category_list);
        total_items = findViewById(R.id.total_items);
        noItemSelectedLayout = findViewById(R.id.no_item_selected_layout);
        loadingLayout = findViewById(R.id.loading);
        item_list = findViewById(R.id.item_list);

        //System start
        systemStart();
    }

    public void systemStart(){

        //Back button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        //Run the categories
        presenter.getCategories();

        //When Click listener
        category_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = category_list.getSelectedItem().toString();

                if(!selected_item.equals("-Select Category-")){
                    noItemSelectedLayout.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.VISIBLE);

                    //get the category
                    presenter.getCategoriesPerCategory(selected_item);
                }else{
                    loadingLayout.setVisibility(View.GONE);
                    noItemSelectedLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //populate the list
    @Override
    public void populateCategoryList(ArrayList<String> categories) {
        ArrayAdapter item_categories_spinnerAdapter = new ArrayAdapter(getApplicationContext(),R.layout.top_products_category_text,categories);
        item_categories_spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_list.setAdapter(item_categories_spinnerAdapter);
    }

    //populate the top item list
    @Override
    public void populateTopItemList(final ArrayList<String> item_id, ArrayList<String> category_name, final ArrayList<String> item_name, final ArrayList<String> item_price, ArrayList<String> item_desc, final ArrayList<String> item_image, final ArrayList<String> sales_total) {
        final String item_count = String.valueOf(item_id.size());



        //Time delay for data to retrieve
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //set total items
                total_items.setText("Total Items: "+item_count);

                //show item list
                item_list.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.GONE);

                //run the adapter for item list
                top_products_adapter adapter = new top_products_adapter(top_products.this);
                adapter.SetData(item_id,sales_total,item_image,item_name);
                item_list.setAdapter(adapter);
                item_list.setLayoutManager(new LinearLayoutManager(top_products.this, LinearLayoutManager.VERTICAL,false));

            }
        },1000);



    }
}
