package com.example.acer.jm_pos.inventory.inventory_add_items;

import java.util.ArrayList;

public interface inventory_addContract {

    interface inventory_view{
        void populateCategoriesList(ArrayList<String> category_id,
                                    ArrayList<String> category_name,
                                    ArrayList<String> category_added);

    }
    interface inventory_presenter{
        void onBackPressed();
        void onAddItem(String product_image_string,
                       String item_category,
                       String product_name,
                       String product_price,
                       String product_stock,
                       String product_desc);

        void onReadCategories();

    }
}
