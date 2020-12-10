package com.merobookstore.bookstore;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.PowerManager;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.model.User;
import com.merobookstore.bookstore.strictmode.StrictModeClass;
import com.merobookstore.bookstore.url.Url;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_myOrder,
                R.id.nav_comic, R.id.nav_novel, R.id.nav_thriller, R.id.nav_romantic, R.id.nav_myCart, R.id.nav_share, R.id.nav_aboutus)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(Dashboard.this, LoginActivity.class);
            startActivity(intent);

        }
        return false;

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


//    private void proximity() {
//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//
//        SensorEventListener sensorEventListener = new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//
//                if (event.values[0] == 0) { //Sleep
//                    sleepScreen(true);
//                } else { //Wake
//                    sleepScreen(false);
//                }
//
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//            }
//        };
//
//        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    protected void sleepScreen(boolean on) {
//        if (on == true) {
//            WindowManager.LayoutParams params = getWindow().getAttributes();
//            params.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
//            getWindow().setAttributes(params);
//        } else {
//            WindowManager.LayoutParams params = getWindow().getAttributes();
//            params.flags |= WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
//            getWindow().setAttributes(params);
//        }
//
//    }



}
