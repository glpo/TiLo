package com.app.tilo.timelogger;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class WifiChooseActivity extends Activity{
    private WifiManager wifiManager;
    private WifiScanReceiver wifiReceiver;

    private ListView lv;
    List<String> wifiSSID;
    List<String> wifiBSSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_choose_activity);

        lv = (ListView) findViewById(R.id.wifi_list_view);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ssid = wifiSSID.get(position);
                String bssid = wifiBSSID.get(position);

                Intent intent = new Intent(getApplicationContext(), WifiTrackingActivity.class);
                intent.putExtra("category", getIntent().getStringExtra("category"));
                intent.putExtra("ssid", ssid);
                intent.putExtra("bssid", bssid);

                startActivity(intent);
            }
        });

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if(!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "Enabling WiFi...", Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }
        wifiReceiver = new WifiScanReceiver();
        wifiManager.startScan();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(wifiReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    class WifiScanReceiver  extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> scanList = wifiManager.getScanResults();
            wifiSSID = new ArrayList<String>();
            wifiBSSID = new ArrayList<String>();

            for(int i = 0; i < scanList.size(); i++) {
                wifiSSID.add(" - " + scanList.get(i).SSID);
                wifiBSSID.add(scanList.get(i).BSSID);
            }
            lv.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, wifiSSID));
        }
    }
}
