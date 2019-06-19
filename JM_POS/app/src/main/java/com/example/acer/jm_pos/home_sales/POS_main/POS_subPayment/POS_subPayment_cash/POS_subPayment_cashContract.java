package com.example.acer.jm_pos.home_sales.POS_main.POS_subPayment.POS_subPayment_cash;

public interface POS_subPayment_cashContract {

    interface cash_paymentView{
        void populatePaymentSummary(double vatable_sales,double total_payment,
                                    int receipt_count,double total_discounts);
        void backToMainPost();
        void displayPrintOption(Double change);

    }
    interface cash_paymentPresenter{
        void getSummaryOfPayment();
        void submitPayment(Double payment);
        void goToReceipt();
    }
}
