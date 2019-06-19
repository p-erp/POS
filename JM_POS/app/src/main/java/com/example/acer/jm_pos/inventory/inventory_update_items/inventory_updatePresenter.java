package com.example.acer.jm_pos.inventory.inventory_update_items;

import android.content.Context;

public class inventory_updatePresenter implements inventory_updateContract.inventory_updatePresenter {

    inventory_updateContract.inventory_updateView mView;
    Context context;

    inventory_updatePresenter(inventory_updateContract.inventory_updateView view){
        this.mView = view;
        this.context = (Context) mView;

    }

}
