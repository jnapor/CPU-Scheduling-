package com.sharearide.research.jnapor.osprojectqueue;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private float[] waiting_time;
    private float[] turn_around;
    private String[] waitingTimeText;
    private String[] turnAroundTimeText;

    private ArrayList<ProcessModel> arrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            arrayList = (ArrayList<ProcessModel>) bundle.getSerializable(ProcessModel.KEY);

            arrivalTime = new float[arrayList.size()];
            cpuBurst = new float[arrayList.size()];
            waiting_time = new float[arrayList.size()];
            turn_around = new float[arrayList.size()];
            waitingTimeText = new String[arrayList.size()];
            turnAroundTimeText = new String[arrayList.size()];

            for (int count = 0; count < arrayList.size(); count++) {
                arrivalTime[count] = (float) arrayList.get(count).getArrivalTime();
                cpuBurst[count] = (float) arrayList.get(count).getCpuBurst();
            }

            initializeChart();
            calculateWaitingTimeAndTurnAroundTime();
            inflateRowData();

            bundle.clear();
        }
    }

    private void initializeChart(){

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLayoutDisplay);
        HorizontalBarChart horizontalBarChart = (HorizontalBarChart) linearLayout.findViewById(R.id.bar_chart);

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0f, cpuBurst));

        BarDataSet dataSet = new BarDataSet(entries, "BarDataSet");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setEnabled(false);

        YAxis yAxis = horizontalBarChart.getAxisLeft();
        yAxis.setDrawLabels(true);
        yAxis.setDrawGridLines(false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setAxisLineWidth(3f);
        yAxis.setAxisMinValue(0f);
        yAxis.setTextSize(20);
        yAxis.setTypeface(Typeface.SANS_SERIF);
        yAxis.setAxisLineColor(Color.RED);
        yAxis.setCenterAxisLabels(false);

        YAxis yAxis1 = horizontalBarChart.getAxisRight();
        yAxis1.setEnabled(false);

        horizontalBarChart.setDescription("");
        horizontalBarChart.setDrawBorders(false);
        horizontalBarChart.setData(data);
        horizontalBarChart.setPinchZoom(false);
        horizontalBarChart.setFitBars(true);
        horizontalBarChart.setDoubleTapToZoomEnabled(false);
        horizontalBarChart.animateY(3000);
        horizontalBarChart.setDrawValueAboveBar(true);
    }

    private void inflateRowData(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLayoutDisplay);
        LinearLayout startOfTableRow = (LinearLayout) linearLayout.findViewById(R.id.startOfTableRow);


        /****************************ROW DATA************************************/
        for(int count = 0; count < arrayList.size(); count++){
            LinearLayout rawData = (LinearLayout) LayoutInflater.from(this)
                    .inflate(R.layout.table_of_data_row, startOfTableRow, false);


            TextView waiting_time = (TextView) rawData.findViewById(R.id.waiting_time_process);
            waiting_time.setText(waitingTimeText[count]);

            TextView turnAround_time = (TextView) rawData.findViewById(R.id.turn_around_process);
            turnAround_time.setText(turnAroundTimeText[count]);

            startOfTableRow.addView(rawData);
        }

        /*******************************TOTAL LINEAR LAYOUT**********************************/
        LinearLayout averageWaitingAndTurnAround = (LinearLayout) LayoutInflater.from(this)
                .inflate(R.layout.table_of_data_total_row, linearLayout, false);

        LinearLayout aveText = (LinearLayout) averageWaitingAndTurnAround
                .findViewById(R.id.linearLayoutTableRowAverage);


        String averageWaitingTime = "Average: "+getAverage(waiting_time)+" ms";
        String averageturnAroundTime = "Average: "+getAverage(turn_around)+" ms";


        TextView aveWT = (TextView) aveText.findViewById(R.id.average_waititng_time);
        aveWT.setText(averageWaitingTime);

        TextView aveTA = (TextView) aveText.findViewById(R.id.average_turn_around_time);
        aveTA.setText(averageturnAroundTime);

        linearLayout.addView(averageWaitingAndTurnAround);
        
    }

    private void calculateWaitingTimeAndTurnAroundTime(){
        float currentCPUBurst = 0;
        for (int x = 0; x < arrayList.size(); x++){
            if(x == 0){
                waiting_time[x] = 0;
                waitingTimeText[x] = "P" + (x + 1) + ": " +arrivalTime[x]+" - "+arrivalTime[x]
                        +" = "+ waiting_time[x];
            }else{
                waiting_time[x] = currentCPUBurst - arrivalTime[x];
                waitingTimeText[x] = "P" + (x + 1) + ": " +cpuBurst[x]+" - "+arrivalTime[x]
                        +" = "+waiting_time[x];
            }
            currentCPUBurst += cpuBurst[x];
            turn_around[x] = currentCPUBurst - arrivalTime[x];

            turnAroundTimeText[x] = "P" + (x + 1) + ": " +currentCPUBurst+" - "+arrivalTime[x]
                    +" = "+turn_around[x];
        }
    }
    
    private float getAverage(float[] array){
        int sum = 0;
        for (float data:array) {
            sum+=data;
        }
        return (1.0f*sum)/array.length;
    }
}
