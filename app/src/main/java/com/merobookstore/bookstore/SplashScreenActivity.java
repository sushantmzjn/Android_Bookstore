package com.merobookstore.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.bll.LoginBll;
import com.merobookstore.bookstore.model.User;
import com.merobookstore.bookstore.strictmode.StrictModeClass;
import com.merobookstore.bookstore.url.Url;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    TextView welcome;
    private ProgressBar progressBar;
    private int counter = 0;
    String username, password;
    private ImageView imageV;

    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar_horizontal);
        imageV = findViewById(R.id.imageViewH);

        prog();
        welcome = findViewById(R.id.welcome);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();

            }
        }, 2000);

        proximity();


    }

    private void checkUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);
        password = sharedPreferences.getString("password", null);


        if (username != null && password != null) {
            Login();
            Intent intent = new Intent(SplashScreenActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
//            Toast.makeText(this, "username and password didn't matched", Toast.LENGTH_SHORT).show();
        }
    }

    public void prog() {
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                progressBar.setProgress(counter);

                if (counter == 100) {
                    t.cancel();
                }
            }
        };
        t.schedule(tt, 0, 20);
    }

    private void Login() {
        String username1 = username;
        String password1 = password;

        LoginBll loginBll = new LoginBll();
        StrictModeClass.StrictMode();
        if (loginBll.checkUser(username1, password1)) {
            Intent intent = new Intent(SplashScreenActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "token expired", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void proximity() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0] <= 1) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        if (sensor != null) {
            sensorManager.registerListener(sensorEventListener, sensor, sensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "no sensor", Toast.LENGTH_SHORT).show();

        }
    }

//    private void loadImage() {
//        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
//        Call<User> userCall = usersAPI.getUserDetails(Url.token);
//
//        userCall.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//
//                if (!response.isSuccessful()) {
//                    Toast.makeText(SplashScreenActivity.this, "", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                String imgPath = Url.imagePath + response.body().getImage();
//
//                Picasso.get().load(imgPath).into(imageV);
//
//                StrictModeClass.StrictMode();
//                try {
//
//                    URL url = new URL(imgPath);
//                    imageV.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//
//            }
//        });
//    }
}
