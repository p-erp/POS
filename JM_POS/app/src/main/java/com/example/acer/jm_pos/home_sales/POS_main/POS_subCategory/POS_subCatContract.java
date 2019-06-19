package com.example.acer.jm_pos.home_sales.POS_main.POS_subCategory;

import android.content.Context;

import java.util.ArrayList;

public interface POS_subCatContract {
    interface pos_subView{
        void populateCategoryList(ArrayList<String> category_name);

    }
    interface pos_subPresenter{
        void getCategoriesList(Context context);

    }
}
