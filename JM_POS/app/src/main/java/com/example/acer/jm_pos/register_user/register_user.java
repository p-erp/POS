package com.example.acer.jm_pos.register_user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.login.login;

public class register_user extends AppCompatActivity implements register_userContract.register_userContractView{

    register_userPresenter presenter;

    TextInputLayout first_name;
    TextInputLayout last_name;
    TextInputLayout age;
    TextInputLayout contact_number;
    TextInputLayout address;
    TextInputLayout username;
    TextInputLayout password;

    Button submit;

    ImageView back_button;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        presenter = new register_userPresenter(this);

        //Object declaration TextView
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        age = findViewById(R.id.age);
        contact_number = findViewById(R.id.contact_number);
        address = findViewById(R.id.address);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


        //Object declaration for image
        back_button = findViewById(R.id.back_button);

        //Object declaration Buttons
        submit = findViewById(R.id.submit);

        //progressDialog
        pd = new ProgressDialog(register_user.this);


        clicks();

    }

    public void clicks(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegister(first_name.getEditText().getText().toString(),
                                    last_name.getEditText().getText().toString(),
                                    age.getEditText().getText().toString(),
                                    contact_number.getEditText().getText().toString(),
                                    address.getEditText().getText().toString(),
                                    username.getEditText().getText().toString(),
                                    password.getEditText().getText().toString());
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onAccountCreate() {
        pd.setMessage("Creating Account...");
        pd.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.hide();
                Intent intent = new Intent(register_user.this,login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        },1000);

    }
}
