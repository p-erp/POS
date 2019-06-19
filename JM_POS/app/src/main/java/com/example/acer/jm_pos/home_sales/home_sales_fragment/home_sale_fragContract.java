package com.example.acer.jm_pos.home_sales.home_sales_fragment;

import android.content.Context;

public interface home_sale_fragContract {
    interface home_saleView{

    }
    interface home_salePresenter{
        void toPOSMain(Context context);
        void toPOSOption(Context context);
    }
}
