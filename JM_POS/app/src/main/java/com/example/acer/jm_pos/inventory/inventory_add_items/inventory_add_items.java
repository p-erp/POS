package com.example.acer.jm_pos.inventory.inventory_add_items;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.acer.jm_pos.R;
import com.vlk.multimager.activities.MultiCameraActivity;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;


import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class inventory_add_items extends AppCompatActivity implements inventory_addContract.inventory_view {

    //Object MVP Declaration
    inventory_addPresenter presenter;

    //Layout Object Declaration
    Button save_add_product;

    //EditText declaration
    EditText product_name;
    EditText product_price;
    EditText product_stock;
    EditText product_desc;

    //ImageView declaration
    ImageView back_button;
    ImageView add_product_image;

    //String Declaration
    String product_imageString;

    //Spinner declaration
    Spinner item_category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_add_items);
        presenter = new inventory_addPresenter(this);

        //Layout Object Declaration
        save_add_product    = findViewById(R.id.save_add_product);
        back_button         = findViewById(R.id.back_button);
        add_product_image   = findViewById(R.id.add_product_image);

        product_name        = findViewById(R.id.product_name);
        product_price       = findViewById(R.id.product_price);
        product_stock       = findViewById(R.id.product_stock);
        product_desc        = findViewById(R.id.product_desc);

        item_category       = findViewById(R.id.item_category);

        systemStart();
        onClick();

    }


    public void onClick(){
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackPressed();
                finish();
            }
        });


        add_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImagePicker.Builder(inventory_add_items.this)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.PNG)
                        .scale(600, 600)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
            }
        });


        save_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation for empty field and data
                if(!item_category.getSelectedItem().toString().equals("-Select Categories-")){
                    if(!product_name.getText().toString().equals("")&&!product_price.getText().toString().equals("")&&
                            !product_stock.getText().toString().equals("")&&!product_desc.getText().toString().equals("")){

                                //Start addItem Process in presenter
                                presenter.onAddItem(product_imageString,item_category.getSelectedItem().toString(),product_name.getText().toString(),
                                product_price.getText().toString(),product_stock.getText().toString(),product_desc.getText().toString());
                    }else{
                            Toast.makeText(getApplicationContext(),"Please fill up all the empty fields",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please select categories",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            Log.d("Path",mPaths.toString() + " Size "+mPaths.size());
            Bitmap bmImg_0 = BitmapFactory.decodeFile(mPaths.get(0));

            add_product_image.setImageBitmap(bmImg_0);

            Log.d("bitmap_data",bmImg_0.toString());
            product_imageString = getStringImage(bmImg_0);

        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();


        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void systemStart(){
        presenter.onReadCategories();
    }

    @Override
    public void populateCategoriesList(ArrayList<String> category_id, ArrayList<String> category_name, ArrayList<String> category_added) {
        category_name.add(" + Add Category");
        ArrayAdapter item_categories_spinnerAdapter = new ArrayAdapter(getApplicationContext(),R.layout.activity_inventory_add_items_spinner_design,category_name);
        item_categories_spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        item_category.setAdapter(item_categories_spinnerAdapter);
    }
}
