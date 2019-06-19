package com.example.acer.jm_pos.home_sales.home_sales_fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.acer.jm_pos.R;


public class home_sale_fragment extends Fragment implements home_sale_fragContract.home_saleView{

    //Class Declaration
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    //Object declaration
    Button toOption;
    Button toPOS;

    //presenter declaration
    home_sale_fragPresenter presenter;

    public home_sale_fragment() {

    }

    public static home_sale_fragment newInstance(String param1, String param2) {
        home_sale_fragment fragment = new home_sale_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_sale_fragment, container, false);
        //Mvp declaration
        presenter = new home_sale_fragPresenter(this);


        //Object declaration
        toOption = view.findViewById(R.id.option);
        toPOS = view.findViewById(R.id.to_pos);



        //System start process
        systemStart();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public void systemStart(){

        //When button option click, go to option menu
        toOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    presenter.toPOSOption(getContext());
            }
        });

        //when button pos click, go to pos main
        toPOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.toPOSMain(getContext());
            }
        });




    }

}
