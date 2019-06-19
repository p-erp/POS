package com.example.acer.jm_pos.inventory.inventory_view_items;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public interface inventory_viewContract {
    interface inventory_View{
        void populateRecyclerView(ArrayList<String> item_id,
                                  ArrayList<String> category_id,
                                  ArrayList<String> item_price,
                                  ArrayList<String> inventory_id,
                                  ArrayList<String> item_name,
                                  ArrayList<String> item_desc,
                                  ArrayList<String> item_stock,
                                  ArrayList<String> item_image);


        void goToDelete(String item_name);
        void refreshTable();

    }
    interface inventory_presenter{
        void onBackButton(Context context);
        void onGettingAllItems();
        void onDeleteItem(String item_name);
        void onUpdateItem(String item_name);
        void onDeleteAlert(String item_name);
        void onRefreshTabke();

    }
}
