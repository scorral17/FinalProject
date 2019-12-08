package com.example.finalproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    private EditText usernameField,passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = (EditText)findViewById(R.id.editText1);
        passwordField = (EditText)findViewById(R.id.editText2);


    }


    public void register(View view){
        startActivity(new Intent(MainActivity.this, Main2Activity.class));
    }

    public void login(View view) throws ExecutionException, InterruptedException {
        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();
        //add parameter for function
        Connect con = new Connect();
        con.execute("login", username, password);
        String s = con.get();
        final String[] options = s.split(";");
        Log.d("test", options[0]);
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);


        if(s.equals("Failed")){
            dlgAlert.setMessage("Username or password is incorrect.");
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
        else{
            dlgAlert.setMessage("Login successful.");
            dlgAlert.setTitle("Success!");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);

            dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent loginPage = new Intent(MainActivity.this, Main3Activity.class);
                    loginPage.putExtra("info", new String[]{ username,password});
                    startActivity(loginPage);

                }

            });
            dlgAlert.create().show();

        }

    }


}