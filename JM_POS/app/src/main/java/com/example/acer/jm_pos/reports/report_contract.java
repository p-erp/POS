package com.example.acer.jm_pos.reports;

import android.content.Context;

public interface report_contract {

    interface report_view{

    }
    interface report_presenter{
        void top_products(Context context);
        void to_salesAndProfitsReport(Context context);
    }
}
