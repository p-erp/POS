package com.example.acer.jm_pos.home_sales.POS_main.POS_subReceipt;

import java.util.ArrayList;

public interface POS_subReceiptContract {

    interface pos_subView{

        void populateReceipt_list(ArrayList<String> receipt_count,
                                  ArrayList<String> receipt_type,
                                  String date,
                                  String inCharge,
                                  String itemName,
                                  String itemPrice,
                                  String itemQuantity,
                                  String itemTotal,
                                  String sumTotal);

        void goToPOS_Main();
    }
    interface pos_subPresenter{
        void getReceiptData();
        void printReceipt();
    }
}
