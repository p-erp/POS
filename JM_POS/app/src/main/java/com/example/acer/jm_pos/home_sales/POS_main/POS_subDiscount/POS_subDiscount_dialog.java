package com.example.acer.jm_pos.home_sales.POS_main.POS_subDiscount;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.POS_main.POS_main;

import java.util.ArrayList;

public class POS_subDiscount_dialog extends AppCompatDialogFragment {

    //Object declaration
    Spinner split_receipt_count;
    Spinner student_discount_count;
    Spinner senior_discount_count;

    ArrayList<String> student_list_count = new ArrayList<>();
    ArrayList<String> senior_list_count = new ArrayList<>();

    int total_split_value = 0;
    int stud_and_sen_value = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.pos_subdiscount_dialog, null);

        //object declaration
        split_receipt_count = view.findViewById(R.id.split_receipt_count);
        student_discount_count = view.findViewById(R.id.student_discount_count);
        senior_discount_count = view.findViewById(R.id.senior_discount_count);

        //Split Discount
        split_receipt_count.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Clear list when system start
                student_list_count.clear();
                senior_list_count.clear();

                //Condition if split count is not equal to 1
                if(Integer.parseInt(split_receipt_count.getSelectedItem().toString())>=1){
                    //get the selected value for split
                    final int length = Integer.parseInt(split_receipt_count.getSelectedItem().toString());

                    //store value for stud_and_sen_value
                    stud_and_sen_value = length;

                    //store value for total available slot
                    total_split_value = length;

                    //Populate the student list base on the receipt
                    for(int i=0;i<=length;i++){
                        student_list_count.add(String.valueOf(i));
                        senior_list_count.add(String.valueOf(i));
                    }


                    //Declare the value for student spinner based on the value of split count
                    Log.d("student_list_count",""+student_list_count.toString());
                    final ArrayAdapter studentAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,student_list_count);
                    studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    student_discount_count.setAdapter(studentAdapter);

                    //Declare the value for student spinner based on the value of split count
                    Log.d("senior_list_count",""+senior_list_count.toString());
                    ArrayAdapter seniorAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,senior_list_count);
                    seniorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    senior_discount_count.setAdapter(seniorAdapter);




                    //Under edit
                    /*student_discount();
                    senior_discount();*/



                }else{

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //Start of dialog builder view
        builder.setView(view)
                .setTitle("Discount")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int receipt_split = Integer.parseInt(split_receipt_count.getSelectedItem().toString());
                        int stud_count = Integer.parseInt(student_discount_count.getSelectedItem().toString());
                        int sen_count = Integer.parseInt(senior_discount_count.getSelectedItem().toString());

                        //logic computation
                        int total_value = stud_count + sen_count;

                        if(total_value>receipt_split){
                            Toast.makeText(getContext(),"Please Insert Valid Data: ",Toast.LENGTH_LONG).show();
                        }else{

                            //Store the data to local
                            SharedPreferences store_user_id = getContext().getSharedPreferences("discount_data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor store_username_editor = store_user_id.edit();
                            store_username_editor.putString("split_receipt",String.valueOf(split_receipt_count.getSelectedItem().toString()));
                            store_username_editor.putString("senior_discount_count",String.valueOf(senior_discount_count.getSelectedItem().toString()));
                            store_username_editor.putString("student_discount_count",String.valueOf(student_discount_count.getSelectedItem().toString()));
                            store_username_editor.commit();


                            //Throw the value to POS_main
                            POS_main pom = POS_main.instance;
                            if(pom!=null){
                                int s1 = Integer.parseInt(split_receipt_count.getSelectedItem().toString());
                                int st = Integer.parseInt(student_discount_count.getSelectedItem().toString());
                                int sn = Integer.parseInt(senior_discount_count.getSelectedItem().toString());
                                pom.applyDiscountData(s1,st,sn);
                            }
                        }

                    }
                });
        return builder.create();
    }

    //METHOD FOR SENIOR DISCOUNT
    public void student_discount(){
        student_discount_count.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> sen_test = new ArrayList<>();

                //Get selected value of student discount
                int stud_get_num = student_discount_count.getSelectedItemPosition();

                //Get the total value of stud and sen value
                stud_and_sen_value = stud_and_sen_value - stud_get_num;

                //Populate the new list of student
                for(int a = 0 ; a <= stud_and_sen_value; a ++){
                    sen_test.add(String.valueOf(a));
                }

                Log.d("stud_and_sen_value","sen: "+stud_and_sen_value);

                //Set the adapter for senior
                ArrayAdapter spinnerSeniorAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,sen_test);
                spinnerSeniorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                senior_discount_count.setAdapter(spinnerSeniorAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
