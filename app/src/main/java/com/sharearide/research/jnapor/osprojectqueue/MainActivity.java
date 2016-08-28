package com.sharearide.research.jnapor.osprojectqueue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sharearide.research.jnapor.osprojectqueue.model.ProcessModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private float[] arrivalTime;
    private float[] cpuBurst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            ArrayList<ProcessModel> arrayList = (ArrayList<ProcessModel>)
                    bundle.getSerializable(ProcessModel.KEY);

            arrivalTime = new float[arrayList.size()];
            cpuBurst = new float[arrayList.size()];
            for (int count = 0; count < arrayList.size();count++) {
                arrivalTime[count] = (float) arrayList.get(count).getArrivalTime();
                cpuBurst[count] = (float) arrayList.get(count).getCpuBurst();
            }
            Log.e("MAIN ACTIVITY", "Size is"+arrayList.size());
        }else{
            Log.e("MAIN ACTIVITY", "Bundle is empty");
        }


        HorizontalBarChart horizontalBarChart = (HorizontalBarChart) findViewById(R.id.bar_chart);

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0f,cpuBurst));

        BarDataSet dataSet = new BarDataSet(entries, "BarDataSet");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinValue(0);
        xAxis.setEnabled(false);

        YAxis yAxis = horizontalBarChart.getAxisLeft();
        yAxis.setEnabled(false);

        YAxis yAxis1 = horizontalBarChart.getAxisRight();
        yAxis1.setEnabled(false);


        horizontalBarChart.setDescription("");
        horizontalBarChart.getLegend().setEnabled(true);
        horizontalBarChart.setDrawBorders(false);
        horizontalBarChart.setData(data);
        horizontalBarChart.setFitBars(true);
        horizontalBarChart.setDoubleTapToZoomEnabled(false);
        horizontalBarChart.animateY(3000);

    }
}
