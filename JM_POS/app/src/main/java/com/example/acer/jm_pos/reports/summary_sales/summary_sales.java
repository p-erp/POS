package com.example.acer.jm_pos.reports.summary_sales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.reports.reports_fragment;

public class summary_sales extends AppCompatActivity implements summary_salesContract.summary_view{
    //mvp declaration
    summary_salesPresenter presenter;

    //Object declaration
    ImageView back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_sales);
        presenter = new summary_salesPresenter(this);

        //object declaration
        back_button = findViewById(R.id.back_button);

        //system start
        systemStart();
    }


    //System start
    public void systemStart(){

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
              finish();
            }
        });
    }
}
