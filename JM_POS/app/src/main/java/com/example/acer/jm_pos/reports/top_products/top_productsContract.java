package com.example.acer.jm_pos.reports.top_products;

import java.util.ArrayList;

public interface top_productsContract {
    interface top_productsView{
        void populateCategoryList(ArrayList<String> categories);
        void populateTopItemList(ArrayList<String> item_id,
                                 ArrayList<String> category_name,
                                 ArrayList<String> item_name,
                                 ArrayList<String> item_price,
                                 ArrayList<String> item_desc,
                                 ArrayList<String> item_image,
                                 ArrayList<String> sales_total);

    }
    interface top_productsPresenter{
        void getCategories();
        void getCategoriesPerCategory(String category);

    }

}
