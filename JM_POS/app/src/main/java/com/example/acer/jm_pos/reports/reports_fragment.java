package com.example.acer.jm_pos.reports;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.acer.jm_pos.R;

public class reports_fragment extends Fragment implements report_contract.report_view{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Presenter declaration
    report_presenter presenter;

    //object declaration
    Button sales_profits_report;
    Button top_items_report;
    Button item_stock_inventory;

    public reports_fragment() {

    }

    public static reports_fragment newInstance(String param1, String param2) {
        reports_fragment fragment = new reports_fragment();
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
        View view = inflater.inflate(R.layout.fragment_reports_fragment, container, false);
        presenter = new report_presenter(this);

        //object declaration
        sales_profits_report = view.findViewById(R.id.sales_profits_report);
        top_items_report = view.findViewById(R.id.top_items_report);
        item_stock_inventory = view.findViewById(R.id.item_stock_inventory);

        //system start
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


    //System start
    public void systemStart(){
        //Go to sales and profits report
        sales_profits_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.to_salesAndProfitsReport(getContext());
            }
        });

        //go to top items report
        top_items_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.top_products(getContext());
            }
        });

        //
        item_stock_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
