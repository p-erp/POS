package com.example.acer.jm_pos.home_sales.POS_main.POS_Option;

public interface POS_OptionContract {
    interface pos_optionView{
        void goToPOSMain();
    }
    interface pos_optionPresenter{
        void saveCapitalToDatabase(double capital);
        void goToMain();
    }
}
