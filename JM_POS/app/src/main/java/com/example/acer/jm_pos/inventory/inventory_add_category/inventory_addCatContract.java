package com.example.acer.jm_pos.inventory.inventory_add_category;

public interface inventory_addCatContract {

    interface inventory_addView{
        void onSaveSuccess();
    }
    interface inventory_addPresenter{
        void saveCatToDatabase(String category_name);

    }
}
