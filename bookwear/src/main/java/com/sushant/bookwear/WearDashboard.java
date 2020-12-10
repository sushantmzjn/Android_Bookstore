package com.sushant.bookwear;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class WearDashboard extends WearableActivity {

    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_dashboard);

        username = (TextView) findViewById(R.id.text);
        String name = getIntent().getStringExtra("username");
        username.setText("welcome "+name);
        // Enables Always-on
        setAmbientEnabled();


    }
}
