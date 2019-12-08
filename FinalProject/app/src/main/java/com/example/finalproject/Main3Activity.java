package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class Main3Activity extends AppCompatActivity implements Runnable{
    private String username;
    private String password;
    private boolean click;
    private Main3Activity mn;
    int Current_user_update_frequency = 15;
    Thread update_thread = new Thread(Main3Activity.this); //Thread

    GetMac con_mac;
    boolean show_info_flag=true;

    public void onComplete(String response, Object state) {
        final String response_complete = response;
        Main3Activity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Main3Activity.this, "Registration Successful", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        String[] info = getIntent().getStringArrayExtra("info");
        username = info[0];
        password = info[1];
        mn = this;

        String[] PERMS_INITIAL = {
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        ActivityCompat.requestPermissions(this, PERMS_INITIAL, 127);

        con_mac = new GetMac(Main3Activity.this);

        if (this.update_thread.getState() == Thread.State.NEW) {
            this.update_thread.start();
        }
    }

    public void online(View view) {
        this.click = true;
    }

    public void space(View view){
        Intent buildings = new Intent(Main3Activity.this, Login.class);
        startActivity(buildings);
    }

    public void logout(View view) throws ExecutionException, InterruptedException {
        Connect con = new Connect();
        con.execute("logout",username,password);
        String s = con.get();
        if(!s.equals("Failed")){
            mn.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Main3Activity.this.getApplicationContext(), "Logged out! You are now offline", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



    @Override
    protected void onPause() {
        super.onPause();
        this.show_info_flag = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.show_info_flag = true;
    }
    @Override
    public void run() {

        while(true) {
            try {
                if(this.con_mac.ready) {
                    // Get Mac Address of this local device.
                    String prev = "";
                    String c = "";
                    for (int i = 0; i < con_mac.getCon_Macs().length; i++) {
                        c += con_mac.getCon_Macs()[i];
                        if (i != con_mac.getCon_Macs().length - 1) {
                            c += "_";
                        }
                    }
                    if(this.click){
                        Connect con = new Connect();
                        final String getMacAdd = con_mac.getBssid();
                        con.execute("online", username, password, getMacAdd);
                        String s = con.get();
                        String[] options = s.split(";");
                        Log.d("online", options[0]);
                        if (!options[0].equals("Failed") && options.length == 2) {
                            Intent regAd = new Intent(Main3Activity.this, regAddr.class);
                            regAd.putExtra("info", new String[]{options[0],options[1], getMacAdd});
                            startActivity(regAd);
                        }
                        else if(options[0].equals("Failed")){
                            Intent buildAd = new Intent(Main3Activity.this, buildAddr.class);
                            buildAd.putExtra("info", getMacAdd);
                            startActivity(buildAd);

                        }
                        else{
                            mn.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Main3Activity.this.getApplicationContext(), "Checked in!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        this.click = false;
                    }
                    this.con_mac.ready = false;

                }


                // Sleep 5 seconds.
                Thread.sleep(this.Current_user_update_frequency * 1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
