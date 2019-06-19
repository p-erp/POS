package com.example.acer.jm_pos.home_sales.POS_main;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
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
import com.example.acer.jm_pos.home_sales.POS_main.POS_subCategory.POS_subCategoryDialog_Adapter;
import com.example.acer.jm_pos.localhost;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class POS_mainCategoryItem_Adapter extends RecyclerView.Adapter<POS_mainCategoryItem_Adapter.viewHolder> {
    private Context context;

    //List Declaration
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> item_name_1 = new ArrayList<>();
    ArrayList<String> item_price_1 = new ArrayList<>();
    ArrayList<String> item_desc_1 = new ArrayList<>();
    ArrayList<String> item_image_1 = new ArrayList<>();
    ArrayList<String> item_stock_1 = new ArrayList<>();

    //network address Declaration
    String localhost = "";
    String image_url = localhost + "/MEATSHOP_POS_SALE/image_upload/";
    com.example.acer.jm_pos.localhost lc = new localhost();


    public POS_mainCategoryItem_Adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<String> category_id,ArrayList<String> item_name,
                        ArrayList<String> item_price,ArrayList<String> item_desc,
                        ArrayList<String> item_image,
                        ArrayList<String> item_stock){
        this.id = category_id;
        this.item_name_1 = item_name;
        this.item_price_1 = item_price;
        this.item_desc_1 = item_desc;
        this.item_image_1 = item_image;
        this.item_stock_1 = item_stock;

        Log.d("adapter_data",category_id.toString());

    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.pos_maincategoryitem_adapter,null,false));


    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, final int i) {
        localhost = lc.getLocalhost();

        viewHolder.item_name.setText(item_name_1.get(i));
        viewHolder.item_price.setText("Php "+item_price_1.get(i));
        viewHolder.item_stock.setText("Stock: "+item_stock_1.get(i));

        Log.d("item_imageLink",localhost+image_url+item_image_1.get(i));
        Glide
                .with(context)
                .load(localhost+image_url+item_image_1.get(i))
                .centerCrop()
                .into(viewHolder.item_image);

        //When Container click
        viewHolder.item_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //This generate the customer id
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String currentDateandTime = sdf.format(new Date());


                //Store deviceId/cart id
                SharedPreferences store_home_sale_state = context.getSharedPreferences("cart_id", Context.MODE_PRIVATE);
                SharedPreferences.Editor store_home_sale_stateEdit = store_home_sale_state.edit();
                store_home_sale_stateEdit.putString("cart_id",currentDateandTime);
                store_home_sale_stateEdit.commit();


                //insert item to cart
                POS_main pm = POS_main.instance;
                if(pm!=null){
                    pm.insertToCart(currentDateandTime,item_name_1.get(i),
                                    "1",item_price_1.get(i));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return id!=null ? id.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        //Object declaration
        TextView item_name;
        TextView item_price;
        TextView item_stock;
        ImageView item_image;


        RelativeLayout item_container;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            //object declaration
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            item_stock = itemView.findViewById(R.id.item_stock);
            item_image = itemView.findViewById(R.id.item_image);

            item_container = itemView.findViewById(R.id.item_container);


        }
    }
}
