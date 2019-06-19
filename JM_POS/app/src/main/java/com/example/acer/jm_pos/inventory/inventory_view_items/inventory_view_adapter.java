package com.example.acer.jm_pos.inventory.inventory_view_items;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.localhost;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class inventory_view_adapter extends RecyclerView.Adapter<inventory_view_adapter.viewHolder> {
    private Context context;

    //Object Declaration
    ArrayList<String> item_id_list_1;
    ArrayList<String> category_id_list_1;
    ArrayList<String> item_price_list_1;
    ArrayList<String> inventory_id_list_1;
    ArrayList<String> item_name_list_1;
    ArrayList<String> item_desc_list_1;
    ArrayList<String> item_stock_list_1;
    ArrayList<String> item_image_list_1;

    //callback declaration
    //network address Declaration
    String localhost = "";
    String image_url = localhost + "/MEATSHOP_POS_SALE/image_upload/";
    com.example.acer.jm_pos.localhost lc = new localhost();


    public inventory_view_adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<String> item_id_list,
                        ArrayList<String> category_id_list,
                        ArrayList<String> item_price_list,
                        ArrayList<String> inventory_id_list,
                        ArrayList<String> item_name_list,
                        ArrayList<String> item_desc_list,
                        ArrayList<String> item_stock,
                        ArrayList<String> item_image_list){

        this.item_id_list_1        = item_id_list;
        this.category_id_list_1    = category_id_list;
        this.item_price_list_1     = item_price_list;
        this.inventory_id_list_1   = inventory_id_list;
        this.item_name_list_1      = item_name_list;
        this.item_desc_list_1      = item_desc_list;
        this.item_stock_list_1     = item_stock;
        this.item_image_list_1     = item_image_list;

        notifyDataSetChanged();

        Log.d("data_set_adapter",item_stock_list_1.toString());
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.inventory_viewadapter,null,false));

    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, final int i) {
        localhost = lc.getLocalhost();

        //set Object Data
        viewHolder.item_name.setText(item_name_list_1.get(i));
        viewHolder.item_price.setText(item_price_list_1.get(i));
        viewHolder.item_stock.setText(item_stock_list_1.get(i));

        /*
        Picasso.with(context)
                .load(localhost+image_url+item_image_list_1.get(i))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(viewHolder.menu_icon);
*/

        Glide
                .with(context)
                .load(localhost+image_url+item_image_list_1.get(i))
                .centerCrop()
                .into(viewHolder.item_image);

        //cardView click
        viewHolder.menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu menu = new PopupMenu(context,viewHolder.menu_icon);
                menu.getMenuInflater().inflate(R.menu.inventory_view_option_popup_menu,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        inventory_view_items iv = inventory_view_items.instance;

                        //condition if item is equals to update and delete
                        if(item.getTitle().toString().equals("Delete")){
                            if(iv!=null){
                                iv.deleteItem(item_name_list_1.get(i));
                            }
                        }if(item.getTitle().toString().equals("Update")){
                            if(iv!=null){
                                iv.updateItem(item_name_list_1.get(i));
                            }
                        }



                        return true;
                    }
                });
                menu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_id_list_1.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView item_name;
        TextView item_price;
        TextView item_stock;
        ImageView menu_icon;
        CardView card;

        ImageView item_image;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            item_name  = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            item_stock = itemView.findViewById(R.id.item_stock);
            card       = itemView.findViewById(R.id.card);
            menu_icon  = itemView.findViewById(R.id.menu_icon);
            item_image = itemView.findViewById(R.id.item_image);
        }
    }





}
