package com.example.acer.jm_pos.home_sales.POS_main.POS_subReceipt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;
import com.example.acer.jm_pos.home_sales.POS_main.POS_mainCartItem_Adapter;

import java.util.ArrayList;

public class POS_subReceipt_adapter extends RecyclerView.Adapter<POS_subReceipt_adapter.viewHolder> {
    private Context context;

    //List Declaration
    ArrayList<String> split_count_1 = new ArrayList<>();
    ArrayList<String> receipt_type_1 = new ArrayList<>();

    //Variable declaration
    String date_1;
    String inCharge_1;
    String itemName_1;
    String itemPrice_1;
    String itemQuantity_1;
    String itemTotal_1;
    String item_sumTotal_1;


    public POS_subReceipt_adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<String> cart_id, ArrayList<String> receipt_type,
                        String date,String inCharge,String itemName, String itemPrice,
                        String itemQuantity, String itemTotal,String sum_total){
        this.split_count_1 = cart_id;
        this.receipt_type_1 = receipt_type;
        this.date_1 = date;
        this.inCharge_1 = inCharge;
        this.itemName_1 = itemName;
        this.itemPrice_1 = itemPrice;
        this.itemQuantity_1 = itemQuantity;
        this.itemTotal_1 = itemTotal;
        this.item_sumTotal_1 = sum_total;

        Log.d("adapter_data_receipt",receipt_type_1.toString());
    }
    @NonNull
    @Override
    public POS_subReceipt_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new POS_subReceipt_adapter.viewHolder(LayoutInflater.from(context).inflate(R.layout.pos_subreceipt_adapter,null,false));



    }

    @Override
    public void onBindViewHolder(@NonNull POS_subReceipt_adapter.viewHolder viewHolder, final int i) {

        //Set Object data
        viewHolder.receipt_date.setText(date_1);
        viewHolder.item_quantity.setText(itemQuantity_1);
        viewHolder.item_name.setText(itemName_1);
        viewHolder.item_price.setText(itemPrice_1);
        viewHolder.item_total.setText(itemTotal_1);

        String filtered_total = "";



        //Changes the sumTotal if slit or not
        Log.d("getTheSplitCount",receipt_type_1.toString());
        if(split_count_1.size()>1){
            double split_total = (Double.parseDouble(item_sumTotal_1)/split_count_1.size());
            filtered_total = String.valueOf(split_total);
        }else{
            filtered_total = item_sumTotal_1;
        }

        //logic for displaying the total
        if(receipt_type_1.get(i).toString().equals("regular")){
            Log.d("item_Sum",item_sumTotal_1);
            //Vatable Sales
            double vat_sales = Double.parseDouble(filtered_total);

            //Get VAT
            double VAT = vat_sales * 0.12;

            //sales_witVat
            double total_withVat = vat_sales + VAT;

            viewHolder.receipt_total.setText("Total: Php "+String.format("%.2f",total_withVat));

        }else if(receipt_type_1.get(i).toString().equals("student")){
            //Vatable Sales
            double vat_sales = Double.parseDouble(filtered_total);

            //Get VAT
            double VAT = vat_sales * 0.12;

            //sales_witVat
            double total_withVat = vat_sales + VAT;

            //get Discount
            double discounts = total_withVat * 0.20;

            //sales_withDiscount
            double total_withDiscounts = vat_sales - discounts;

            viewHolder.receipt_total.setText("Total: Php "+String.format("%.2f",total_withDiscounts));

        }else if(receipt_type_1.get(i).toString().equals("senior")){
            //Vatable Sales
            double vat_sales = Double.parseDouble(filtered_total);

            //Get VAT
            double VAT = vat_sales * 0.12;

            //sales_witVat
            double total_withVat = vat_sales + VAT;

            //get Discount
            double discounts = total_withVat * 0.20;

            //sales_withDiscount
            double total_withDiscounts = vat_sales - discounts;

            viewHolder.receipt_total.setText("Total: Php "+String.format("%.2f",total_withDiscounts));
        }



    }

    @Override
    public int getItemCount() {
        return split_count_1!=null ? split_count_1.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        //object declaration
        TextView receipt_date;
        TextView receipt_ticket;
        TextView item_quantity;
        TextView item_name;
        TextView item_price;
        TextView item_total;
        TextView receipt_total;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            //object declaration
            receipt_date = itemView.findViewById(R.id.receipt_date);
            item_quantity = itemView.findViewById(R.id.item_quantity);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            item_total = itemView.findViewById(R.id.item_total);
            receipt_total = itemView.findViewById(R.id.receipt_total);
        }
    }
}
