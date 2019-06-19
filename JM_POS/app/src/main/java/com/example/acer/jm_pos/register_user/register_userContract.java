package com.example.acer.jm_pos.register_user;

public interface register_userContract {

    interface register_userContractView{
        void onAccountCreate();

    }

    interface register_userContractPresenter{
        void onRegister(String first_name,String last_name,String age,String contact_number,String address,String username,String password);
    }

}
