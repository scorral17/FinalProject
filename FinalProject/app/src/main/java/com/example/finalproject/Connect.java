package com.example.finalproject;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Connect extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params){
        //Post method
        try {
            String method = (String) params[0];
            String data = "";
            switch(method){
                case "register": data = register(params[1],params[2],params[3]); break;
                case "login": data = login(params[1],params[2]); break;
                case "search": data = search(params[1]); break;
                case "logout": data = logout(params[1],params[2]); break;
                case "online": data = online(params[1],params[2],params[3]); break;
                case "update": data = update(params[1],params[2]); break;
                case "add": data = add(params[1],params[2],params[3],params[4]); break;
                case "region": data = region(params[1],params[2]); break;
                default: data = null; break;

            }

            //String link = "http://192.168.0.9:8888/demo/study.php";
            String link = "http://10.0.2.2:8888/demo/study.php";
            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            InputStream connIn = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connIn));
            StringBuilder sb = new StringBuilder();
            String line = null;

            //read server response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        }catch(Exception e){
            return e.getMessage();
        }
    }

    protected String register(String username, String password,String locmac) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode("register", "UTF-8");
        data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
        data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
        data += "&" + URLEncoder.encode("locmac", "UTF-8") + "=" + URLEncoder.encode(locmac, "UTF-8");
        return data;
    }

    protected String login(String username, String password) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode("login", "UTF-8");
        data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
        data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
        return data;
    }

    protected String online(String username, String password, String mac) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode("online", "UTF-8");
        data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
        data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
        data += "&" + URLEncoder.encode("mac", "UTF-8") + "=" + URLEncoder.encode(mac, "UTF-8");
        return data;
    }


    protected String search(String building) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode("search", "UTF-8");
        data += "&" + URLEncoder.encode("building", "UTF-8") + "=" + URLEncoder.encode(building, "UTF-8");
        return data;
    }

    protected String region(String building, String floor) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode("region", "UTF-8");
        data += "&" + URLEncoder.encode("building", "UTF-8") + "=" + URLEncoder.encode(building, "UTF-8");
        data += "&" + URLEncoder.encode("floor", "UTF-8") + "=" + URLEncoder.encode(floor, "UTF-8");
        return data;
    }

    protected String logout(String username, String password) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode("logout", "UTF-8");
        data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
        data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
        return data;
    }
    protected String update(String mac, String region) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode("update", "UTF-8");
        data += "&" + URLEncoder.encode("mac", "UTF-8") + "=" + URLEncoder.encode(mac, "UTF-8");
        data += "&" + URLEncoder.encode("region", "UTF-8") + "=" + URLEncoder.encode(region, "UTF-8");
        return data;
    }
    protected String add(String building, String floor, String mac, String region) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode("add", "UTF-8");
        data += "&" + URLEncoder.encode("building", "UTF-8") + "=" + URLEncoder.encode(building, "UTF-8");
        data += "&" + URLEncoder.encode("floor", "UTF-8") + "=" + URLEncoder.encode(floor, "UTF-8");
        data += "&" + URLEncoder.encode("mac", "UTF-8") + "=" + URLEncoder.encode(mac, "UTF-8");
        data += "&" + URLEncoder.encode("region", "UTF-8") + "=" + URLEncoder.encode(region, "UTF-8");
        return data;
    }

}
