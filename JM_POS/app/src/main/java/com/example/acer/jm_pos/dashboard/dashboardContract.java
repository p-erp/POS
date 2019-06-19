package com.example.acer.jm_pos.dashboard;

import android.content.Context;

public interface dashboardContract {

    interface dashboardView{
        void displayTotalSales(String total_sales);
        void renderChart();

    }

    interface dashboardPresenter{
        void populateSummary(Context context);


    }
}
