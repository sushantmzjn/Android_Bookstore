package com.merobookstore.bookstore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.merobookstore.bookstore.bll.LoginBll;
import com.merobookstore.bookstore.strictmode.StrictModeClass;

public class LoginActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    private EditText etusername, etpassword;
    private Button login;
    private CheckBox checkbox;

    private TextView register, tvLogin;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sensorGyro();
        register = findViewById(R.id.tvRegister);

        SpannableString content = new SpannableString(" Register");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        register.setText(content);

        tvLogin = findViewById(R.id.tvLogin);
        etusername = findViewById(R.id.etUsername);
        etpassword = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);
        checkbox = findViewById(R.id.checkBox);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(etusername.getText().toString())) {
                    etusername.setError("Input Username");
                    return;
                }
                if (TextUtils.isEmpty(etpassword.getText().toString())) {
                    etpassword.setError("Input Password");
                    return;
                }
                login();


            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (v == register) {
                    Intent a = new Intent(LoginActivity.this, SignUpActivity.class);
                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(tvLogin, "login");
                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                    startActivity(a, activityOptions.toBundle());
                }
            }
        });


    }

    private void login() {
        String username = etusername.getText().toString();
        String password = etpassword.getText().toString();

        LoginBll loginBLL = new LoginBll();

        StrictModeClass.StrictMode();
        if (loginBLL.checkUser(username, password)) {

            if (checkbox.isChecked()) {
                SaveIntoSharedPreference();
                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
                return;
            }
            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
            startActivity(intent);
            finish();

        } else {
            etpassword.setError("Incorrect password");
            // Toast.makeText(this, "Either username or password is incorrect", Toast.LENGTH_SHORT).show();
            etpassword.requestFocus();
        }
    }


    private void SaveIntoSharedPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", etusername.getText().toString().trim());
        editor.putString("password", etpassword.getText().toString().trim());
        editor.commit();

        Toast.makeText(this, "password saved", Toast.LENGTH_LONG).show();
    }


    private void sensorGyro() {
        sensorManager = (SensorManager) getSystemService((SENSOR_SERVICE));
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        SensorEventListener gyroSensor = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[1] < 0) {
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                } else if (event.values[1] > 0){
                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        if (sensor != null) {
            sensorManager.registerListener(gyroSensor, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "no sensor found", Toast.LENGTH_SHORT).show();
        }
    }

}
