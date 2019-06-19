package com.example.acer.jm_pos.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.home_sales;

public class login extends AppCompatActivity implements  loginContract.loginView{

    private loginPresenter presenter;

    ProgressDialog pd;

    TextInputLayout username;
    TextInputLayout password;
    Button login;

    TextView createAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new loginPresenter(this);

        pd = new ProgressDialog(login.this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        createAccount = findViewById(R.id.createAccount);
        login = findViewById(R.id.login);

        click();
        presenter.getDeviceIP(getApplicationContext());

        username.getEditText().setText("haripazha");
        password.getEditText().setText("123");
    }

    public void click(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!username.getEditText().toString().equals("")&&!password.getEditText().getText().toString().equals("")){
                    presenter.login(username.getEditText().getText().toString(),password.getEditText().getText().toString(),getApplicationContext());
                }else{
                    Toast.makeText(getApplicationContext(),"Please fill out all the empty fields",Toast.LENGTH_LONG).show();
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createAccount();
            }
        });
    }


    @Override
    public void isLoginSuccessful() {
        pd.setMessage("Logging In...");
        pd.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.hide();
                Intent intent = new Intent(com.example.acer.jm_pos.login.login.this,home_sales.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        },1000);
    }

    @Override
    public void createNewAccount() {
        presenter.userHasNoAccount();
    }

    @Override
    public void getUserId(String username) {
        presenter.getUserData(username);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    public void systemStart(){

    }
}
