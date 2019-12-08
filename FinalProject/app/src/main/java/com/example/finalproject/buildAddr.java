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

public class buildAddr extends AppCompatActivity{
    private String mac;
    private String region;
    private EditText building;
    private Button save;
    private Spinner dropdown;
    private EditText floor;
    private String[] items;
    private String[] fmlreg;
    private String[] bcreg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_addr);
        building = (EditText)findViewById(R.id.editText1);
        floor = (EditText)findViewById(R.id.editText2);
        save = (Button)findViewById(R.id.button);


        String info = getIntent().getStringExtra("info");
        mac = info;

        //get the spinner from the xml.
        dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        items = new String[]{"Quiet Area" , "Group Study", "Computers","C1 Bay","C2 Bay","C3 Bay", "Classroom"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,items);
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
                con.execute("add", building.getText().toString(), floor.getText().toString(),mac, region);
                finish();
            }
        });

    }


}
