package com.example.acer.jm_pos.home_sales.POS_main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subCategory.POS_subCategoryDialog;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subDiscount.POS_subDiscount_dialog;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subPayment.POS_subPayment_dialog;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subSearch.POS_subSearchDialog;
import com.example.acer.jm_pos.home_sales.home_sales;

import java.util.ArrayList;

public class POS_main extends AppCompatActivity implements POS_mainContract.POS_mainView {
    //presenter declaration
    POS_mainPresenter presenter;

    //Object declaration
    Button home;
    Button category;
    Button search;
    Button discount;
    Button payment;

    TextView total;
    TextView vatable_sales;
    TextView VAT;
    TextView discounts;


    public static POS_main instance;

    RecyclerView item_recyclerView;
    RecyclerView cart_recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_main);
        presenter = new POS_mainPresenter(this);
        instance = this;

        //Object declaration
        home = findViewById(R.id.home);
        category = findViewById(R.id.category);
        search = findViewById(R.id.search_item);
        discount = findViewById(R.id.discount_setting);
        total = findViewById(R.id.total);
        vatable_sales = findViewById(R.id.vatable_sales);
        VAT = findViewById(R.id.vat_included);
        payment = findViewById(R.id.payment);
        discounts = findViewById(R.id.discount);

        //Recycler
        item_recyclerView = findViewById(R.id.item_list);
        cart_recyclerView = findViewById(R.id.cart_list);

        //Start process on create
        systemStart();
    }


    public void systemStart(){

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(POS_main.this,home_sales.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS_subCategoryDialog pos_sub = new POS_subCategoryDialog();
                pos_sub.show(getSupportFragmentManager(), "example dialog");
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS_subSearchDialog pos_search = new POS_subSearchDialog();
                pos_search.show(getSupportFragmentManager(), "example dialog");
            }
        });


        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS_subDiscount_dialog pos_discount = new POS_subDiscount_dialog();
                pos_discount.show(getSupportFragmentManager(), "example dialog");
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS_subPayment_dialog payments = new POS_subPayment_dialog();
                payments.show(getSupportFragmentManager(), "example dialog");
            }
        });
    }

    //Run POS_mainCategoryItem Adapter
    public void POS_categoryItem(String category){
        presenter.getItem(category);
    }


    //populate the recyclerview
    @Override
    public void populateItemList(ArrayList<String> item_id, ArrayList<String> category_name, ArrayList<String> item_name, ArrayList<String> item_price, ArrayList<String> item_desc, ArrayList<String> item_image,ArrayList<String> item_stock) {

        //Populate item list data
        POS_mainCategoryItem_Adapter adapter = new POS_mainCategoryItem_Adapter(getApplicationContext());
        adapter.SetData(item_id,item_name,item_price,item_desc,item_image,item_stock);
        item_recyclerView.setAdapter(adapter);
        item_recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));

    }

    @Override
    public void populateCartList(ArrayList<String> cart_id, ArrayList<String> customer_id, ArrayList<String> item_name, ArrayList<String> item_quantity, ArrayList<String> item_price,ArrayList<String> item_image) {

        //Calculate Total
        presenter.calculateTotal(cart_id,customer_id,item_name,item_quantity,item_price,item_image);

    }

    @Override
    public void updatTotalData(ArrayList<String> cart_id, ArrayList<String> customer_id, ArrayList<String> item_name, ArrayList<String> item_quantity, ArrayList<String> item_price,
                               double total_value,
                               double vat_exlusive,
                               double tota_value_with_vat,
                               ArrayList<String> image_item) {


        //Set text for vatable sales
        vatable_sales.setText(String.format("%.2f",+total_value));

        //SetText for 12% vat
        VAT.setText(String.format("%.2f",vat_exlusive));

        //SetText for
        total.setText("Total: Php "+String.format("%.2f",tota_value_with_vat));

        //Populate cart item list data
        POS_mainCartItem_Adapter adapter = new POS_mainCartItem_Adapter(getApplicationContext());
        adapter.SetData(cart_id,customer_id,item_name,item_quantity,item_price,image_item);
        cart_recyclerView.setAdapter(adapter);
        cart_recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
    }


    @Override
    public void openEditCartDialog(double quantity,String item_name,Context context,String customer_id) {

        //Save the id of item and value of item,
        SharedPreferences store_item_data = context.getSharedPreferences("edit_item_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor store_item_dataEditor = store_item_data.edit();
        store_item_dataEditor.putString("quantity",String.valueOf(quantity));
        store_item_dataEditor.putString("item_name",item_name);
        store_item_dataEditor.putString("item_customer_id",customer_id);
        store_item_dataEditor.commit();

        //Open Dialog
        POS_mainCartEdit_Dialog pos_mainCart = new POS_mainCartEdit_Dialog();
        pos_mainCart.show(getSupportFragmentManager(), "example dialog");


    }

    //Populate Item search
    @Override
    public void populateListFromSearch(ArrayList<String> item_id, ArrayList<String> category_name, ArrayList<String> item_name, ArrayList<String> item_price, ArrayList<String> item_desc, ArrayList<String> item_image, ArrayList<String> item_stock) {

        //Populate item list data
        POS_mainCategoryItem_Adapter adapter = new POS_mainCategoryItem_Adapter(getApplicationContext());
        adapter.SetData(item_id,item_name,item_price,item_desc,item_image,item_stock);
        item_recyclerView.setAdapter(adapter);
        item_recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));

    }

    @Override
    public void onValidatedDelete(String item_name,String customer_id) {
        presenter.deleteItem(item_name,customer_id);
    }

    @Override
    public void updateTotalWithDiscount(double total_with_discount,int split_count,double total_discount) {

        //Update Total
        if(split_count>1){
            total.setText("Total: Php "+String.valueOf(String.format("%.2f",total_with_discount))+" ("+split_count+")");
            if(total_discount>0){
                discounts.setText(String.format("%.2f",total_discount));
            }
        }else{
            total.setText("Total: Php "+String.valueOf(String.format("%.2f",total_with_discount)));
            if(total_discount>0){
                discounts.setText(String.format("%.2f",total_discount));
            }
        }

        //Store new
        presenter.storeDiscountedPayment(total_with_discount,split_count,total_discount);
    }


    //insert to cart
    public void insertToCart(String customer_id,String item_name,String item_quantity,String item_price){
        presenter.onInsertToCart(customer_id,item_name,item_quantity,item_price);
    }


    //Open EditCartDialog
    public void openCartDialog(String cart_id,String customer_id){
        presenter.cartId(cart_id,getApplicationContext(),customer_id);
    }

    //searchItem
    public void search(String search_string){
        presenter.searchItem(search_string);

    }

    //Confirmation of deleting an Item
    public void confirmDeletionOfItem(String item_name,String customer_id){
        presenter.showDeleteConfirmation(item_name,customer_id);
    }

    //updateItem
    public void updateItem(Double item_quantity,String item_name,String customer_id){

        presenter.updateItem(item_quantity,item_name,customer_id);
    }


    //read The discount data
    public void applyDiscountData(int receipt_split, int stud_count, int sen_count){
        presenter.calculateDiscount(receipt_split,stud_count,sen_count);
    }
}
