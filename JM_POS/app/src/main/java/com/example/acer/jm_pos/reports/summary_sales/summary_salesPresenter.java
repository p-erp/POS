package com.example.acer.jm_pos.reports.summary_sales;

import android.content.Context;
import android.content.Intent;

import com.example.acer.jm_pos.reports.reports_fragment;

public class summary_salesPresenter implements summary_salesContract.summary_presenter {
    //Mvp declaration
    summary_salesContract.summary_view mView;
    Context context;


    summary_salesPresenter(summary_salesContract.summary_view view){
        this.mView = view;
        this.context = (Context) mView;


    }


}
