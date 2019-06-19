package com.example.acer.jm_pos.home_sales.POS_main.POS_Option;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;

public class POS_Option extends AppCompatActivity implements POS_OptionContract.pos_optionView{
    //MVP declaration
    POS_OptionPresenter presenter;


    //Object declaration
    Button save;
    EditText capital;
    ImageView back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos__option);
        presenter = new POS_OptionPresenter(this);


        //object declaration
        save = findViewById(R.id.save);
        capital = findViewById(R.id.capital);
        back_button = findViewById(R.id.back_button);


        //systemStart
        systemStart();
    }

    //System start
    public void systemStart(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveCapitalToDatabase(Double.parseDouble(capital.getText().toString()));
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), POS_main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void goToPOSMain() {
        presenter.goToMain();
    }
}
