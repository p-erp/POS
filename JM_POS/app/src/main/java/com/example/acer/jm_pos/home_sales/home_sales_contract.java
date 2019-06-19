package com.example.acer.jm_pos.home_sales;

public interface home_sales_contract {

    interface home_sales_view{
        void onExit();
        void onPopulateNavData(String first_name,String last_name,String contact_number);
        void changeFragmentToSales();
        void changeFragmentToInventory();


    }
    interface home_sales_presenter{
        void onLogout();
        void onUserData(String username);
        void onSalesFragment();
        void onInventoryFragment();

    }
}
