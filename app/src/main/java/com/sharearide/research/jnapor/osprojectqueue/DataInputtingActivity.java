package com.sharearide.research.jnapor.osprojectqueue;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DataInputtingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_gathering_layout);
//
//        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add_process);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Add Process", Toast.LENGTH_SHORT).show();


            }
        });

        FloatingActionButton simulate = (FloatingActionButton) findViewById(R.id.simulate);
        simulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Simulate", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
