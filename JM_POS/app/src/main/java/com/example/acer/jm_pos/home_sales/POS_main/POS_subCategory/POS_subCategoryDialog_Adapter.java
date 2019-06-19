package com.example.acer.jm_pos.home_sales.POS_main.POS_subCategory;

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
import android.widget.Toast;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;

import java.util.ArrayList;

public class POS_subCategoryDialog_Adapter extends RecyclerView.Adapter<POS_subCategoryDialog_Adapter.viewHolder> {
    private Context context;

    //List Declaration
    ArrayList<String> id = new ArrayList<>();


    public POS_subCategoryDialog_Adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<String> category_id){
        this.id = category_id;
        Log.d("adapter_data",category_id.toString());

    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.pos_subcategorydialog_adapter,null,false));

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, final int i) {

        //Object declaration
        viewHolder.category_name.setText(id.get(i));

        //When Object clicked
        viewHolder.cat_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                POS_subCategoryDialog pd = POS_subCategoryDialog.instance;
                if(pd!=null){
                    pd.dismiss(id.get(i));
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        //Object declaration
        TextView category_name;

        LinearLayout cat_container;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            //object declaration
            category_name = itemView.findViewById(R.id.category_name);
            cat_container = itemView.findViewById(R.id.cat_container);
        }
    }
}
