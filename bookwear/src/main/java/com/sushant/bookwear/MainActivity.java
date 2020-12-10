package com.sushant.bookwear;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sushant.bookwear.bll.LoginBllwear;

public class MainActivity extends WearableActivity {

    private EditText wearUsername, wearPassword;
    private Button wearLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enables Always-on
        setAmbientEnabled();

        wearUsername = findViewById(R.id.wUsername);
        wearPassword = findViewById(R.id.wPassword);

        wearLogin = findViewById(R.id.wLogin);

        wearLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wLogin();
            }
        });

    }

    private void wLogin() {
        String username = wearUsername.getText().toString().trim();
        String password = wearPassword.getText().toString().trim();

        LoginBllwear loginBllwear = new LoginBllwear();
        StrictModeClasswear.StrictMode();

        if (loginBllwear.checkUser(username, password)) {
            Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, WearDashboard.class);
            intent.putExtra("username",username);
            startActivity(intent);
        } else {
            Toast.makeText(this, "wrong password", Toast.LENGTH_SHORT).show();
        }

    }
}
