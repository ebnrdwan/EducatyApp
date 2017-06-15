package com.asi.educatyapp.Data.View.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asi.educatyapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressF extends Fragment {

    private static String TAG = "chatActivity";

    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    private String[] xData = {"Mitch", "Jessica", "Mohammad", "Kelsey", "Sam", "Robert", "Ashley"};
    PieChart pieChart;
    PieChart BehaviorChart ;

    public ProgressF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView =  inflater.inflate(R.layout.fragment_progress, container, false);

        Log.d(TAG, "onCreate: starting to create chart");

        pieChart = (PieChart) rootView.findViewById(R.id.ProgressChart);
        BehaviorChart = (PieChart) rootView.findViewById(R.id.BehaviorChart);

//        pieChart.setDescription();
//        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Progress Chart");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("(sum): ");
                String Progress = e.toString().substring(pos1 + 7);

                for (int i = 0; i < yData.length; i++) {
//                    if (yData[i] == Float.parseFloat(Progress)) {
//                        pos1 = i;
//                        break;
//                    }
                }
                String Class = xData[pos1 + 1];
                Toast.makeText(getContext(), "Class " + Class + "\n" + "Progress: $" + Progress + "K", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        BehaviorChart.setHoleRadius(25f);
        BehaviorChart.setTransparentCircleAlpha(0);
        BehaviorChart.setCenterText("BehaviorChart");
        BehaviorChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        BehaviorChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelectedForBhavior: Value select from chart.");
                Log.d(TAG, "onValueSelectedForBhavior: " + e.toString());
                Log.d(TAG, "onValueSelectedForBhavior: " + h.toString());

                int pos1 = e.toString().indexOf("(sum): ");
                String Progress = e.toString().substring(pos1 + 7);

                for (int i = 0; i < yData.length; i++) {
//                    if (yData[i] == Float.parseFloat(Progress)) {
//                        pos1 = i;
//                        break;
//                    }
                }
                String Class = xData[pos1 + 1];
                Toast.makeText(getContext(), "Class " + Class + "\n" + "Progress: $" + Progress + "K", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


        addDataSet();
        addDataSet();
        return rootView;
    }


    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Class Progress");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        Legend legend1= BehaviorChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend1.setForm(Legend.LegendForm.CIRCLE);
        legend1.setPosition(Legend.LegendPosition.LEFT_OF_CHART);


        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
        BehaviorChart.setData(pieData);
        BehaviorChart.invalidate();
    }
}




