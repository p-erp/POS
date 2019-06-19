package com.example.acer.jm_pos.inventory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.home_sales.home_sales;
import com.example.acer.jm_pos.inventory.inventory_view_items.inventory_view_items;

public class inventory_fragment extends Fragment implements inventory_fragContract.inventory_fragView{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    // Object declaration
    inventory_fragPresenter fragPresenter;
    ImageView onViewItems;
    ImageView onAddItems;
    ImageView onAddCategory;


    static inventory_fragment instance;

    public inventory_fragment() {

    }

    public static inventory_fragment newInstance(String param1, String param2) {
        inventory_fragment fragment = new inventory_fragment();
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
        View view = inflater.inflate(R.layout.fragment_inventory_fragment, container, false);
        fragPresenter = new inventory_fragPresenter(this);
        instance = this;

        onViewItems = view.findViewById(R.id.view_items);
        onAddItems  = view.findViewById(R.id.add_items);
        onAddCategory = view.findViewById(R.id.update_items);


        onClicks();
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

    public void onClicks(){
        onViewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragPresenter.onViewItems(getContext());
            }
        });

        onAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             fragPresenter.onAddItems(getContext());
            }
        });


        onAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragPresenter.onAddCategory(getContext());
            }
        });
    }


}
