package com.app.tilo.timelogger;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ModeActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_activity);

        listView = (ListView) findViewById(R.id.ch_list_view);

        List<String> chooseList = new ArrayList<>();
        chooseList.add("Plain timer tracking");
        chooseList.add("WiFi event timer tracking");
        chooseList.add("GPS location event timer tracking");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, chooseList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                    {
                        Intent intent = new Intent(getApplicationContext(), PlainTrackingActivity.class);
                        intent.putExtra("category", getIntent().getStringExtra("category"));
                        startActivity(intent);
                        break;
                    }
                    case 1:
                    {
                        Intent intent = new Intent(getApplicationContext(), WifiChooseActivity.class);
                        intent.putExtra("category", getIntent().getStringExtra("category"));
                        startActivity(intent);
                        break;
                    }
                }
            }

        });

        listView.setAdapter(adapter);
    }
}
