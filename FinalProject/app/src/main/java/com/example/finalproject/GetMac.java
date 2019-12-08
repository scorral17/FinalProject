package com.example.finalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by xincoder on 1/11/16.
 */
public class GetMac {
    Context current_context;
    WifiManager wifiManager;
    BroadcastReceiver wifiScanReceiver;


    String Local_Mac; // mobile device mac address
    String bssid;
    String[] Con_Macs;
    boolean ready = false;

    public GetMac(Context context){
        this.current_context = context;
        init();
    }

    public String[] getCon_Macs(){
        return this.Con_Macs;
    }

    public String getBssid(){
        return this.bssid;
    }

    private void init(){
        wifiManager = (WifiManager) current_context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    scan();
                } else {
                    Log.d("test", "failed");
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        current_context.registerReceiver(wifiScanReceiver, intentFilter);
        boolean success = wifiManager.startScan();

    }

    public void scan(){
        ScanResult bestSignal = null;
        List<ScanResult> results = wifiManager.getScanResults();
        String message = "No results. Check wireless is on";
        if (results != null) {
            final int size = results.size();
            Con_Macs = new String[size];
            if (size == 0) message = "No access points in range";
            else {
                bestSignal = results.get(0);
                int count = 0;
                for (ScanResult result : results) {
                    this.Con_Macs[count++] = result.BSSID;
                    if (WifiManager.compareSignalLevel(bestSignal.level,
                            result.level) < 0) {
                        bestSignal = result;
                    }
                }

            }
            this.ready = true;
        }

        this.bssid = bestSignal.BSSID;

    }

    public String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0") && !nif.getName().equalsIgnoreCase("eth0"))
                    continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

}

