package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private TextView fml;
    private TextView bc;
    private ArrayList<Floor> fmlfloors = null;
    private ArrayList<Floor> bcfloors = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fml = (TextView)findViewById(R.id.textView2);
        bc = (TextView)findViewById(R.id.textView4);
        Connect con = new Connect();
        con.execute("search", "FML");
        fmlfloors = new ArrayList<Floor>();
        bcfloors = new ArrayList<Floor>();

        retrieve(con, fmlfloors);
        Connect con2 = new Connect();
        con2.execute("search", "Building C");
        retrieve(con2, bcfloors);

        if(!fmlfloors.isEmpty()) {
            fml.setText(Integer.toString(fmlfloors.size()));
        }
        else{
            fml.setText("0");
        }
        if(!bcfloors.isEmpty()) {
            bc.setText(Integer.toString(bcfloors.size()));
        }
        else{
            bc.setText("0");
        }

    }

    public void expandFML(View view) {
        final Intent config = new Intent(Login.this, FML.class);
        config.putExtra("floors", fmlfloors);
        startActivity(config);
    }

    public void expandBC(View view) {
        final Intent config = new Intent(Login.this, BC.class);
        config.putExtra("floors", bcfloors);
        startActivity(config);
    }

    public void retrieve(Connect con, ArrayList<Floor> floors) {

        String[] entries;
        String s = null;
        try {
            s = con.get();
            if(!s.equals("Failed")) {
                entries = s.split(Pattern.quote("\\"));
                char floor = '1';
                String floorname = "";
                int count = 0;
                String[] online = null;
                for (int i = 0; i < entries.length; i++) {
                    online = entries[i].split(";");
                    if (floor != online[1].charAt(0)) {

                        if(count >0){
                            Floor add = new Floor(floorname , count);
                            floors.add(add);
                            count =0;
                        }

                        floorname = online[1];
                        floor = floorname.charAt(0);


                    }
                    if(i == entries.length-1){
                        count++;
                        Floor add = new Floor(floorname , count);
                        floors.add(add);
                        count =0;
                    }
                    else {
                        count++;

                    }
                    floorname = online[1];

                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
