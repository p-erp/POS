package com.example.acer.jm_pos.home_sales.POS_main;

import android.content.Context;

import java.util.ArrayList;

public interface POS_mainContract{

    interface POS_mainView{
        void populateItemList(ArrayList<String> item_id,
                              ArrayList<String> category_name,
                              ArrayList<String> item_name,
                              ArrayList<String> item_price,
                              ArrayList<String> item_desc,
                              ArrayList<String> item_image,
                              ArrayList<String> item_stock);

        void populateCartList(ArrayList<String> cart_id,
                              ArrayList<String> customer_id,
                              ArrayList<String> item_name,
                              ArrayList<String> item_quantity,
                              ArrayList<String> item_price,
                              ArrayList<String> item_image);

        void updatTotalData(ArrayList<String> cart_id,
                            ArrayList<String> customer_id,
                            ArrayList<String> item_name,
                            ArrayList<String> item_quantity,
                            ArrayList<String> item_price,
                            double total_value,
                            double vat_exlusive,
                            double tota_value_with_vat,
                            ArrayList<String> image_item);

        void openEditCartDialog(double quantity,String item_name,Context context,String customer_id);

        void populateListFromSearch(ArrayList<String> item_id,
                                    ArrayList<String> category_name,
                                    ArrayList<String> item_name,
                                    ArrayList<String> item_price,
                                    ArrayList<String> item_desc,
                                    ArrayList<String> item_image,
                                    ArrayList<String> item_stock);

        void onValidatedDelete(String item_name,String customer_id);
        void updateTotalWithDiscount(double total_with_discount, int split_count,double total_discount);
    }
    interface POS_mainPresenter{
        void getItem(String category);
        void onInsertToCart(String customer_id,String item_name,String item_quantity,String item_price);
        void calculateTotal(ArrayList<String> cart_id,
                            ArrayList<String> customer_id,
                            ArrayList<String> item_name,
                            ArrayList<String> item_quantity,
                            ArrayList<String> item_price,
                            ArrayList<String> item_image);

        void cartId(String cart_id,Context context,String customer_id);
        void searchItem(String search_string);
        void showDeleteConfirmation(String item_name,String customer_id);
        void deleteItem(String item_name,String customer_id);
        void updateItem(double item_quantity,String item_name,String customer_id);
        void calculateDiscount(int split_count, int stud_count, int sen_count);
        void storeDiscountedPayment(double total_with_discount,int split_count, double total_discount);
    }

}
