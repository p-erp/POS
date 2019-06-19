package com.example.acer.jm_pos.inventory;

import android.content.Context;

public interface inventory_fragContract {
    interface inventory_fragView{

    }
    interface inventory_fragPresenter{
        void onViewItems(Context context);
        void onAddItems(Context context);
        void onAddCategory(Context context);
    }
}
