package com.example.acer.jm_pos.inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.example.acer.jm_pos.inventory.inventory_add_category.inventory_add_category;
import com.example.acer.jm_pos.inventory.inventory_add_items.inventory_add_items;
import com.example.acer.jm_pos.inventory.inventory_update_items.inventory_update_items;
import com.example.acer.jm_pos.inventory.inventory_view_items.inventory_view_items;

public class inventory_fragPresenter implements inventory_fragContract.inventory_fragPresenter {
    inventory_fragContract.inventory_fragView fragView;

    public inventory_fragPresenter(inventory_fragContract.inventory_fragView fragView){
        this.fragView = fragView;
    }

    @Override
    public void onViewItems(Context fragContext) {
        Intent intent = new Intent(fragContext, inventory_view_items.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        fragContext.startActivity(intent);
    }

    @Override
    public void onAddItems(Context context) {
        Intent intent = new Intent(context, inventory_add_items.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);

    }

    @Override
    public void onAddCategory(Context context) {
        Intent intent = new Intent(context,inventory_add_category.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }
}
