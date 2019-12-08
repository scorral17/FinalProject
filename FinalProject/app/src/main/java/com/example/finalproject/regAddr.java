package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class regAddr extends AppCompatActivity{
    private String mac;
    private String region;
    private TextView building;
    private TextView floor;
    private Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_addr);
        building = (TextView)findViewById(R.id.textView1);
        floor = (TextView)findViewById(R.id.textView2);
        save = (Button)findViewById(R.id.button);

        String[] info = getIntent().getStringArrayExtra("info");
        building.setText(info[0]);
        floor.setText(info[1]);
        mac = info[2];
        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"Quiet Area" , "Group Study", "Computers","C1 Bay","C2 Bay","C3 Bay", "Classroom"};

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connect con = new Connect();
                con.execute("update",mac, region);
                finish();
            }
        });

    }

}
