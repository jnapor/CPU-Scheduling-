package com.sharearide.research.jnapor.osprojectqueue;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sharearide.research.jnapor.osprojectqueue.model.ProcessModel;

import java.util.ArrayList;
import java.util.Collections;

public class DataInputtingActivity extends AppCompatActivity implements View.OnClickListener{
    int counter = 0,rowCount;
    private FloatingActionButton simulate;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private EditText arrivalTime;
    private EditText cpuBurst;
    private ImageButton icon;
    private TextView textView;
    private MenuItem addProcess;
    private MenuItem removeRows;

    ArrayList<ProcessModel> processModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_gathering_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        tableLayout = (TableLayout) scrollView.findViewById(R.id.table);


        simulate = (FloatingActionButton) findViewById(R.id.simulate);
        simulate.setEnabled(false);
        simulate.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        removeRows = menu.findItem(R.id.clear_table);
        addProcess = menu.findItem(R.id.add_item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.settings : {
                return true;
            }
            case R.id.clear_table:{
                if(counter > 0){
                    rowCount = tableLayout.getChildCount();
                    showAlertDialog();
                    counter = 0;
                }else{
                    Snackbar.make( getWindow().getDecorView(), "Table is empty", Snackbar.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.add_item:{
                addProcess.setEnabled(false);
                removeRows.setEnabled(false);
                Snackbar.make(getWindow().getDecorView(),"Adding Process", Snackbar.LENGTH_SHORT).show();
                addElementsInTable(getWindow().getDecorView());
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.simulate: {
                if(counter > 0){
                    Snackbar.make(view,"Simulating", Snackbar.LENGTH_SHORT).show();
                    Collections.sort(processModelArrayList);

                    /***************Creating intent and bundle to send to next activity*****************/
                    Intent intent = new Intent(DataInputtingActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ProcessModel.KEY, processModelArrayList);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }else{
                    Snackbar.make(view,"Unable to Simulate. Table of Processes is Empty.", Snackbar.LENGTH_SHORT).show();
                }
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

                Snackbar.make(view, "Saved", Snackbar.LENGTH_SHORT).show();

                processModelArrayList.add(processModel);
                removeRows.setEnabled(true);
                simulate.setEnabled(true);
                addProcess.setEnabled(true);
                icon.setEnabled(false);

            }else{
                Snackbar.make(view, "Input a valid CPU Burst or Arrival Time",
                        Snackbar.LENGTH_SHORT).show();
            }
        }else{
            Snackbar.make(view, "CPU Burst or Arrival Time is Empty",
                    Snackbar.LENGTH_SHORT).show();
        }
    }


    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Table rows")
                .setMessage("Are you sure you want to clear the table entries?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tableLayout.removeViews(1, (rowCount - 1));

                        Snackbar.make(getWindow().getDecorView(), "CPU Burst or Arrival Time is Empty",
                                Snackbar.LENGTH_SHORT).show();

                        processModelArrayList.clear();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
