package com.app.tilo.timelogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class PlainTrackingActivity extends Activity {
    private String LOG_TAG = "TrackingActivityLog";

    private TextView categoryNmeText;
    private TextView timeText;

    private Button startBtn;
    private Button stopBtn;

    private Handler handler = new Handler();

    private long startTime = 0L;
    private long timeInMillis = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;

    private boolean isPause = false;

    private ListView historyListView;
    private ArrayAdapter<String> adapter;
    private List<String> historyRecords;

    private CheckBox resetChBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_activity);

        Intent intent = getIntent();

        categoryNmeText = (TextView) findViewById(R.id.categoryNameText);
        timeText = (TextView) findViewById(R.id.timeText);

        startBtn = (Button) findViewById(R.id.startBtn);
        stopBtn = (Button) findViewById(R.id.stopBtn);
        resetChBox = (CheckBox) findViewById(R.id.resetChBox);

        categoryNmeText.setText(" Category: " + intent.getStringExtra("category"));

        historyListView = (ListView) findViewById(R.id.historyListView);
        historyRecords = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historyRecords);
        historyListView.setAdapter(adapter);

        Toast.makeText(this, "Category: " + intent.getStringExtra("category"), Toast.LENGTH_SHORT).show();
    }

    public void startButtonHandler(View view) {
        if (!isPause) {
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(updateTimerThread, 0);
            isPause = true;
            startBtn.setText("Pause");
        } else {
            timeSwapBuff += timeInMillis;
            handler.removeCallbacks(updateTimerThread);
            startBtn.setText("Start");
            isPause = false;
        }
    }

    public void stopButtonHandler(View view) {
        handler.removeCallbacks(updateTimerThread);
        startBtn.setText("Start");
        historyRecords.add("Track#" + (historyRecords.size() + 1) + " Time: " + parseTime());
        adapter.notifyDataSetChanged();

        isPause = false;
        if (resetChBox.isChecked()) {
            resetTimer();
        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMillis = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMillis;

            timeText.setText(parseTime());
            handler.postDelayed(this, 0);
        }
    };

    private void resetTimer() {
        this.startTime = 0L;
        this.timeInMillis = 0L;
        this.timeSwapBuff = 0L;
        this.updatedTime = 0L;

        timeText.setText(R.string.timerVal);
    }

    private String parseTime() {
        int secs = (int) (updatedTime / 1000);
        int mins = secs / 60;
        int hours = mins / 60;
        secs = secs % 60;
        int milliseconds = (int) (updatedTime % 1000);

        return hours + "h:" + mins + "m:"
                + String.format("%02d", secs) + "s:"
                + String.format("%03d", milliseconds);
    }
}
