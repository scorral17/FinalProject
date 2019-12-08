package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class Main2Activity extends AppCompatActivity {

    private EditText usernameField,passwordField, passwordField2;
    private GetMac local_mac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        usernameField = (EditText)findViewById(R.id.editText1);
        passwordField = (EditText)findViewById(R.id.editText2);
        passwordField2 = (EditText)findViewById(R.id.editText3);
        local_mac = new GetMac(Main2Activity.this);
    }

    public void register(View view) throws ExecutionException, InterruptedException {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();
        final String cpassword = passwordField2.getText().toString();

        if (password.equals(cpassword)){
            final String getMacAdd = local_mac.getMacAddr();
            Connect con = new Connect();
            con.execute("register",username,password,getMacAdd);

            String s = con.get();
            Log.d("test",s);
            if(s.equals("Failed")) {
                dlgAlert.setMessage("Registration failed. Username invalid or passwords don't match");
                dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dlgAlert.create().show();
            }
            else{

                final Intent config = new Intent(Main2Activity.this, Login.class);

                dlgAlert.setMessage("Registration successful.");
                dlgAlert.setTitle("Success!");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(config);
                            }
                        });
                dlgAlert.create().show();
            }
        }
        else{
            dlgAlert.setMessage("Passwords don't match!");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }

    }
}
