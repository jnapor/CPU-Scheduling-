package com.sharearide.research.jnapor.osprojectqueue;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sharearide.research.jnapor.osprojectqueue.model.ProcessModel;

import java.util.ArrayList;
import java.util.Collections;

public class DataInputtingActivity extends AppCompatActivity implements View.OnClickListener{
    int counter = 0;
    private FloatingActionButton add;
    private FloatingActionButton simulate;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private EditText arrivalTime;
    private EditText cpuBurst;
    private ImageButton icon;
    private TextView textView;

    ArrayList<ProcessModel> processModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_gathering_layout);

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        tableLayout = (TableLayout) scrollView.findViewById(R.id.table);


        simulate = (FloatingActionButton) findViewById(R.id.simulate);
        simulate.setEnabled(false);
        simulate.setOnClickListener(this);
        add = (FloatingActionButton) findViewById(R.id.add_process);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_process:{
                add.setEnabled(false);
                Toast.makeText(view.getContext(), "Adding Process", Toast.LENGTH_SHORT).show();
                addElementsInTable(view);
                break;
            }
            case R.id.simulate: {
                Toast.makeText(view.getContext(), "Simulate", Toast.LENGTH_SHORT).show();
                for (ProcessModel p: processModelArrayList) {
                    Log.e("Simulation: Be Sorting", "Process: "+p.getProcessId() + " " + p.getArrivalTime() + " "+p.getCpuBurst());
                }
                Collections.sort(processModelArrayList);
                for (ProcessModel p: processModelArrayList) {

                    Log.e("Simulation: Af Sorting", "Process: "+p.getProcessId() + " " + p.getArrivalTime() + " "+p.getCpuBurst());
                }
                Intent intent = new Intent(DataInputtingActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ProcessModel.KEY, processModelArrayList);
                intent.putExtras(bundle);

                startActivity(intent);
                break;
            }
            case R.id.save: {
                validateAndSave(view);
                break;
            }
        }
    }



    private void addElementsInTable(View view){
        counter++;
        simulate.setEnabled(false);
        //Inflating the row data to be added in the table
        tableRow = (TableRow) LayoutInflater.from(view.getContext())
                .inflate(R.layout.row_data,tableLayout,false);


        /***************Creating references to table row elements*****************/
        textView = (TextView) tableRow.findViewById(R.id.process_label);
        textView.setText(String.valueOf(counter));
        arrivalTime = (EditText) tableRow.findViewById(R.id.arrival_time);
        cpuBurst = (EditText) tableRow.findViewById(R.id.cpu_burst);
        icon = (ImageButton) tableRow.findViewById(R.id.save);
        icon.setOnClickListener(this);


        tableLayout.addView(tableRow);
    }

    private void validateAndSave(View view){
        /***************Validating if arrival time and cpu burst is empty*****************/
        if(!arrivalTime.getText().toString().isEmpty() && !cpuBurst.getText().toString().isEmpty()){

            if(Integer.parseInt(cpuBurst.getText().toString()) >= 1
                    && Integer.parseInt(cpuBurst.getText().toString()) >= 1 ){

                ProcessModel processModel = new ProcessModel(counter,
                        Integer.parseInt(arrivalTime.getText().toString()),
                        Integer.parseInt(cpuBurst.getText().toString())
                );
                
                cpuBurst.setEnabled(false); //To make it not editable
                arrivalTime.setEnabled(false); //To make it not editable
                icon.setImageResource(R.drawable.button_pressed);

                Toast.makeText(view.getContext(), "Saved", Toast.LENGTH_SHORT).show();
                processModelArrayList.add(processModel);
                simulate.setEnabled(true);
                add.setEnabled(true);
                icon.setEnabled(false);

            }else{
                Toast.makeText(view.getContext(), "Input a valid CPU Burst or Arrival Time",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(view.getContext(), "CPU Burst or Arrival Time is Empty",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
