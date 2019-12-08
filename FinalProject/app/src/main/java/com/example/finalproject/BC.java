package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import static java.io.File.separator;

public class BC extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // Declare Variables
    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    ArrayList<Floor> arraylist;
    private String building = "Building C";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc);
        list = (ListView) findViewById(R.id.listview);

        arraylist = (ArrayList<Floor>) getIntent().getSerializableExtra("floors");

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Floor selectedItem = (Floor) parent.getItemAtPosition(position);
                Connect con  = new Connect();
                con.execute("region", building, selectedItem.getName());
                String[] entries;
                String[] r;
                String s = null;
                try {
                    s = con.get();
                    if (!s.equals("Failed")) {
                        ArrayList<Floor> regions = new ArrayList<Floor>();
                        entries = s.split(Pattern.quote("\\"));
                        for(int i=0; i<entries.length; i++){
                            Floor add;
                            r = entries[i].split(";");
                            if(r[0].equals("")) {
                                add = new Floor("Other", Integer.parseInt(r[1]));
                            }
                            else{
                                add = new Floor(r[0],Integer.parseInt(r[1]));
                            }
                            regions.add(add);

                        }
                        final Intent config = new Intent(BC.this, Main4Activity.class);
                        config.putExtra("regions", regions);
                        startActivity(config);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}


