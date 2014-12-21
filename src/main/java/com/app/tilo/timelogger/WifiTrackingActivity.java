package com.app.tilo.timelogger;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WifiTrackingActivity extends Activity {
    private TextView categoryNmeText;
    private TextView timeText;
    private TextView ssidTextView;
    private TextView bssidTextView;

    private String ssid;
    private String bssid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_tracking_activity);

        categoryNmeText = (TextView) findViewById(R.id.categoryTV);
        timeText = (TextView) findViewById(R.id.timerTV);
        ssidTextView = (TextView) findViewById(R.id.ssidTV);
        bssidTextView = (TextView) findViewById(R.id.bssidTV);

        Intent intent = getIntent();

        categoryNmeText.setText("Category: " + intent.getStringExtra("category"));

        ssid = intent.getStringExtra("ssid");
        bssid = intent.getStringExtra("bssid");

        ssidTextView.setText("SSID: " + ssid);
        bssidTextView.setText("BSSID: " + bssid);
    }
}
