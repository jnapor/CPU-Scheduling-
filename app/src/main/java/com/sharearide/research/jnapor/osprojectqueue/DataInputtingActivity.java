package com.sharearide.research.jnapor.osprojectqueue;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DataInputtingActivity extends AppCompatActivity {
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_gathering_layout);

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        final TableLayout tableLayout = (TableLayout) scrollView.findViewById(R.id.table);

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add_process);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Add Process", Toast.LENGTH_SHORT).show();
                counter++;
                TableRow tableRow = (TableRow) LayoutInflater.from(view.getContext())
                        .inflate(R.layout.row_data,tableLayout,false);

                TextView textView = (TextView) tableRow.findViewById(R.id.process_label);
                textView.setText(String.valueOf(counter));



//                TableRow tableRow = new TableRow(view.getContext());
//                tableRow.setLayoutParams(new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
//
//
//
//                TextView textView = new TextView(view.getContext());
//                textView.setGravity(Gravity.CENTER_HORIZONTAL);
//                textView.setTypeface(null, Typeface.BOLD);
//                textView.setText(String.valueOf(counter));
//                textView.setTextSize(50.0f);
//
//                TextView textView1 = new TextView(view.getContext());
//                textView1.setText(String.valueOf(counter));
//                textView1.setGravity(Gravity.CENTER);
//                textView1.setTypeface(null, Typeface.BOLD);
//
//                TextView textView2 = new TextView(view.getContext());
//                textView2.setText(String.valueOf(counter));
//                textView2.setGravity(Gravity.CENTER);
//                textView2.setTypeface(null, Typeface.BOLD);
//                tableRow.addView(textView);
//                tableRow.addView(textView1);
//                tableRow.addView(textView2);

//                EditText editText = new EditText(view.getContext());
//                editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
//                editText.setGravity(Gravity.CENTER);
//                editText.setTypeface(null, Typeface.BOLD);
//
//
//                EditText editText1 = new EditText(view.getContext());
//                editText1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
//                editText1.setGravity(Gravity.CENTER);
//                editText1.setTypeface(null, Typeface.BOLD);




//                TextView textView = new TextView(view.getContext());
//
//                EditText editText = new EditText(view.getContext());
//                EditText editText1 = new EditText(view.getContext());
//
//                tableRow.addView(getTextView(textView));
//                tableRow.addView(getEditTextView(editText));
//                tableRow.addView(getEditTextView(editText1));

//                tableRow.addView(textView);
//                tableRow.addView(editText);
//                tableRow.addView(editText1);

                tableLayout.addView(tableRow);
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

    private TextView getTextView(TextView textView){
        counter++;
        textView.setText(String.valueOf(counter));

        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(null, Typeface.BOLD);
        //textView.setTextAppearance( android.R.style.TextAppearance_Medium);

        return textView;
    }

    private EditText getEditTextView(EditText editText){
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        editText.setGravity(Gravity.CENTER);
        editText.setTypeface(null, Typeface.BOLD);
        //editText.setTextAppearance(null, android.R.style.TextAppearance_Medium);

        return editText;
    }

}
