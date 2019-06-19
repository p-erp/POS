package com.example.acer.jm_pos.inventory.inventory_add_category;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acer.jm_pos.R;

public class inventory_add_category extends AppCompatActivity implements inventory_addCatContract.inventory_addView{

    //declaration of presenter
    inventory_addCatPresenter presenter;


    //declaration for editText
    EditText category_name;

    //declaration for buttons
    Button save_category;

    //declaration for imageView
    ImageView back_button;


    //declaration for progress dialog
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_add_category);
        presenter = new inventory_addCatPresenter(this);

        //object declaration
        category_name = findViewById(R.id.category_name);
        save_category = findViewById(R.id.save_add_category);
        pd            = new ProgressDialog(inventory_add_category.this);
        back_button   = findViewById(R.id.back_button);


        //Run System Start Processes
        systemStart();
    }


    public void systemStart(){
        save_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!category_name.getText().toString().equals("")){
                    pd.setMessage("Adding...");
                    pd.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.saveCatToDatabase(category_name.getText().toString());
                        }
                    },1000);
                }else{
                    Toast.makeText(getApplicationContext(),"Please fill up the field!",Toast.LENGTH_LONG).show();
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }

    @Override
    public void onSaveSuccess() {
        onBackPressed();
        finish();
    }
}
