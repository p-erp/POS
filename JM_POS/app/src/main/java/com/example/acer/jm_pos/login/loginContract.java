package com.example.acer.jm_pos.login;

import android.content.Context;

public interface loginContract {
    interface loginView {
        void isLoginSuccessful();
        void createNewAccount();
        void getUserId(String username);

    }

    interface loginPresenter{
        void login(String username,String password,Context context);
        void getDeviceIP(Context context);
        void userHasNoAccount();
        void createAccount();
        void onBackPressed();
        void getUserData(String username);
    }
}
