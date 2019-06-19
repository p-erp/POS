package com.example.acer.jm_pos.home_sales.POS_main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_subSearch.POS_subSearchDialog;
import com.example.acer.jm_pos.localhost;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class POS_mainCartItem_Adapter extends RecyclerView.Adapter<POS_mainCartItem_Adapter.viewHolder> {
    private Context context;

    //List Declaration
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> customer_id_1 = new ArrayList<>();
    ArrayList<String> item_name_1 = new ArrayList<>();
    ArrayList<String> item_quantity_1 = new ArrayList<>();
    ArrayList<String> item_price_1 = new ArrayList<>();
    ArrayList<String> item_image_1 = new ArrayList<>();

    //network address Declaration
    String localhost = "";
    String image_url = localhost + "/MEATSHOP_POS_SALE/image_upload/";
    com.example.acer.jm_pos.localhost lc = new localhost();


    public POS_mainCartItem_Adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<String> cart_id, ArrayList<String> customer_id,
                        ArrayList<String> item_name,ArrayList<String> item_quantity,
                        ArrayList<String> item_price,ArrayList<String> item_image){
        this.id = cart_id;
        this.customer_id_1 = customer_id;
        this.item_name_1 = item_name;
        this.item_quantity_1 = item_quantity;
        this.item_price_1 = item_price;
        this.item_image_1 = item_image;

        Log.d("adapter_data",cart_id.toString());

    }
    @NonNull
    @Override
    public POS_mainCartItem_Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new POS_mainCartItem_Adapter.viewHolder(LayoutInflater.from(context).inflate(R.layout.pos_maincartitem_adapter,null,false));


    }

    @Override
    public void onBindViewHolder(@NonNull POS_mainCartItem_Adapter.viewHolder viewHolder, final int i) {
            localhost = lc.getLocalhost();

            //Computation for price
            double total = (Double.parseDouble(item_quantity_1.get(i)))*(Double.parseDouble(item_price_1.get(i)));

            //Set Object data
            viewHolder.item_name.setText(item_name_1.get(i));
            viewHolder.item_price.setText(item_quantity_1.get(i)+" x "+item_price_1.get(i));
            viewHolder.item_total.setText("Php "+String.format("%.2f",total));

            //when editItem Clicked
            viewHolder.editItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    POS_main pos = POS_main.instance;
                    if(pos!=null){
                        pos.openCartDialog(id.get(i),customer_id_1.get(i));
                    }

                }
            });

            //Load Image
            Glide
                .with(context)
                .load(localhost+image_url+item_image_1.get(i))
                .centerCrop()
                .into(viewHolder.item_image);

            //when cart container
            viewHolder.cart_item_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

    }

    @Override
    public int getItemCount() {
        return id!=null ? id.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        //Object declaration
        ImageView item_image;
        ImageView editItem;
        TextView item_name;
        TextView item_price;
        TextView item_total;

        LinearLayout cart_item_container;




        public viewHolder(@NonNull View itemView) {
            super(itemView);

            //object declaration
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            item_total = itemView.findViewById(R.id.item_total);
            cart_item_container = itemView.findViewById(R.id.cart_item_container);
            editItem = itemView.findViewById(R.id.edit_item);

        }
    }
}
