package com.sharearide.research.jnapor.osprojectqueue;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.sharearide.research.jnapor.osprojectqueue.model.ProcessModel;

import java.util.ArrayList;

public class DataOutputActivity extends AppCompatActivity {
    private float[] waiting_time;
    private float[] turn_around;
    private float[] ganttChartData;
    private String[] waitingTimeText;
    private String[] turnAroundTimeText;

    private ArrayList<ProcessModel> arrayList;
    private ArrayList<Float> timeline;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            arrayList = (ArrayList<ProcessModel>) bundle.getSerializable(ProcessModel.KEY);

            waiting_time = new float[arrayList.size()];
            turn_around = new float[arrayList.size()];
            waitingTimeText = new String[arrayList.size()];
            turnAroundTimeText = new String[arrayList.size()];


            calculateWaitingTimeAndTurnAroundTime();
            initializeChart();
            inflateRowData();

            bundle.clear();
        }
    }

    private void initializeChart(){

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLayoutDisplay);
        HorizontalBarChart horizontalBarChart = (HorizontalBarChart) linearLayout.findViewById(R.id.bar_chart);

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0f, ganttChartData));

        BarDataSet dataSet = new BarDataSet(entries, "BarDataSet");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        BarData data = new BarData(dataSet);
        data.setValueFormatter(new MyValueFormatter());
        data.setBarWidth(0.9f);
        data.setDrawValues(false);


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

        LinearLayout rawDataLayout = (LinearLayout) averageWaitingAndTurnAround
                .findViewById(R.id.rawDataTable);


        String averageWaitingTime = "Average: "+getAverage(waiting_time)+" ms";
        String averageturnAroundTime = "Average: "+getAverage(turn_around)+" ms";


        TextView aveWT = (TextView) aveText.findViewById(R.id.average_waititng_time);
        aveWT.setText(averageWaitingTime);

        TextView aveTA = (TextView) aveText.findViewById(R.id.average_turn_around_time);
        aveTA.setText(averageturnAroundTime);

        linearLayout.addView(averageWaitingAndTurnAround);


        for (ProcessModel p: arrayList) {
            LinearLayout rawData = (LinearLayout) LayoutInflater.from(this)
                    .inflate(R.layout.row_of_raw_data, linearLayout, false);

            TextView processId = (TextView) rawData.findViewById(R.id.process_id_raw_data);
            processId.setText(String.valueOf(p.getProcessId()));

            TextView processArrival = (TextView) rawData.findViewById(R.id.process_arrival_raw_data);
            processArrival.setText(String.valueOf(p.getArrivalTime()));

            TextView processCPU = (TextView) rawData.findViewById(R.id.process_cpuBurst_raw_data);
            processCPU.setText(String.valueOf(p.getCpuBurst()));

            linearLayout.addView(rawData);
        }
    }

    private void calculateWaitingTimeAndTurnAroundTime(){
        float currentCpuBurst = 0;
        timeline = new ArrayList<>();
        int timeLineCounter = -1;
        for(int x = 0; x < arrayList.size(); x++){
            if(x == 0){
                if (arrayList.get(x).getArrivalTime() > 0){
                    timeline.add(1.0f * arrayList.get(x).getArrivalTime());
                }else {
                    timeline.add(0f);
                }

                timeLineCounter++;
                waiting_time[x] = timeline.get(timeLineCounter) - arrayList.get(x).getArrivalTime();
                waitingTimeText[x] = "Pid " + arrayList.get(x).getProcessId() + ": " +timeline.get(timeLineCounter)
                        +" - "+timeline.get(timeLineCounter) +" = "+ waiting_time[x];

                currentCpuBurst = timeline.get(timeLineCounter) + arrayList.get(x).getCpuBurst();

                turn_around[x] = currentCpuBurst - arrayList.get(x).getArrivalTime();
                turnAroundTimeText[x] = "Pid " + arrayList.get(x).getProcessId() + ": " +currentCpuBurst
                        +" - "+arrayList.get(x).getArrivalTime() +" = "+turn_around[x];


                timeline.add(currentCpuBurst);
                timeLineCounter++;
            }else{
                if (timeline.get(timeLineCounter) < arrayList.get(x).getArrivalTime()){
                    timeline.add(1.0f * arrayList.get(x).getArrivalTime());
                    timeLineCounter++;
                }
                waiting_time[x] = timeline.get(timeLineCounter) - arrayList.get(x).getArrivalTime();
                waitingTimeText[x] = "Pid " + arrayList.get(x).getProcessId() + ": " +timeline.get(timeLineCounter)
                        +" - "+arrayList.get(x).getArrivalTime() +" = "+ waiting_time[x];


                currentCpuBurst = timeline.get(timeLineCounter) +  arrayList.get(x).getCpuBurst();

                if(timeline.get(timeLineCounter) > arrayList.get(x).getArrivalTime()){

                    turn_around[x] = currentCpuBurst - timeline.get(timeLineCounter)
                            + (timeline.get(timeLineCounter) - arrayList.get(x).getArrivalTime());

                    turnAroundTimeText[x] = "Pid " + arrayList.get(x).getProcessId() + ": "+
                            currentCpuBurst + " - "+arrayList.get(x).getArrivalTime() +" = "+turn_around[x];
                }else{
                    turn_around[x] = currentCpuBurst - timeline.get(timeLineCounter);
                    turnAroundTimeText[x] = "Pid " + arrayList.get(x).getProcessId() + ": "+ currentCpuBurst
                            + " - "+timeline.get(timeLineCounter) +" = "+turn_around[x];
                }

                timeline.add(currentCpuBurst);
                timeLineCounter++;
            }
        }

        ganttChartData = new float[timeline.size()];
        for (int i = 0; i < timeline.size(); i++) {
            ganttChartData[i] = timeline.get(i);
        }
    }
    
    private float getAverage(float[] array){
        int sum = 0;
        for (float data:array) {
            sum+=data;
        }
        return (1.0f*sum)/array.length;
    }


    private class MyValueFormatter implements ValueFormatter{


        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return Math.round(value)+"";
        }
    }
}
