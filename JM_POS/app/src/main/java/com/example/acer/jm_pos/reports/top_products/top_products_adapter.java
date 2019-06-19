package com.example.acer.jm_pos.reports.top_products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.inventory.inventory_view_items.inventory_view_adapter;
import com.example.acer.jm_pos.localhost;

import java.util.ArrayList;

public class top_products_adapter extends RecyclerView.Adapter<top_products_adapter.viewHolder> {
    private Context context;

    //Object Declaration
    ArrayList<String> item_id_list_1;
    ArrayList<String> item_name_1;
    ArrayList<String> item_sales_1;
    ArrayList<String> item_image_1;



    //network address Declaration
    String localhost = "";
    String image_url = localhost + "/MEATSHOP_POS_SALE/image_upload/";
    com.example.acer.jm_pos.localhost lc = new localhost();


    public top_products_adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<String> item_id_list,ArrayList<String> item_sales,ArrayList<String> item_image,ArrayList<String> item_name){
        this.item_id_list_1        = item_id_list;
        this.item_sales_1          = item_sales;
        this.item_name_1           = item_name;
        this.item_image_1          = item_image;

        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public top_products_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new top_products_adapter.viewHolder(LayoutInflater.from(context).inflate(R.layout.top_products_adapter,null,false));



    }

    @Override
    public void onBindViewHolder(@NonNull final top_products_adapter.viewHolder viewHolder, final int i) {
        localhost = lc.getLocalhost();

        viewHolder.item_name.setText(item_name_1.get(i));
        viewHolder.item_sales.setText("Total Sales: Php "+item_sales_1.get(i));

        //Set the image from glide
        Glide
                .with(context)
                .load(localhost+image_url+item_image_1.get(i))
                .centerCrop()
                .into(viewHolder.item_image);

    }

    @Override
    public int getItemCount() {
        return item_id_list_1.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView item_name;
        TextView item_sales;

        ImageView item_image;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name);
            item_sales = itemView.findViewById(R.id.item_sales);
            item_image = itemView.findViewById(R.id.item_image);
        }
    }





}
