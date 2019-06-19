package com.example.acer.jm_pos.dashboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.acer.jm_pos.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class dashboard_fragment extends Fragment implements dashboardContract.dashboardView{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public dashboard_fragment() {

    }

    //MVP declaration
    dashboardPresenter presenter;

    //object declaration
    TextView total_sales;

    //view declaration
    View view;


    public static dashboard_fragment newInstance(String param1, String param2) {
        dashboard_fragment fragment = new dashboard_fragment();
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
        view = inflater.inflate(R.layout.fragment_dashboard_fragment, container, false);
        presenter = new dashboardPresenter(this);

        //object declaration
        total_sales = view.findViewById(R.id.total_sales);




        //Run at system start
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

    @Override
    public void displayTotalSales(String total_sales_1) {
    /*
        double amount = Double.parseDouble(total_sales_1);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String formatted = formatter.format(amount);

        total_sales.setText("Total Sales: Php "+total_sales_1);
*/
    }

    @Override
    public void renderChart() {

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(view.findViewById(R.id.progressBar));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("January", 80540));
        data.add(new ValueDataEntry("February", 94190));
        data.add(new ValueDataEntry("March", 102610));
        data.add(new ValueDataEntry("April", 110430));
        data.add(new ValueDataEntry("May", 128000));
        data.add(new ValueDataEntry("June", 143760));
        data.add(new ValueDataEntry("July", 170670));
        data.add(new ValueDataEntry("August", 213210));
        data.add(new ValueDataEntry("September", 249980));
        data.add(new ValueDataEntry("October", 249980));
        data.add(new ValueDataEntry("November", 249980));
        data.add(new ValueDataEntry("December", 249980));

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Sales per month");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Product");
        cartesian.yAxis(0).title("Revenue");

        anyChartView.setChart(cartesian);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    //system start
    public void systemStart(){
        presenter.populateSummary(getContext());
        renderChart();
    }
}
