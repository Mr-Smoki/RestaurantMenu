package com.example.restaurantmenu.presentation.activities.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.SplineSet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.restaurantmenu.R;
import com.example.restaurantmenu.presentation.activities.main.MainActivity;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        },10);
    }
}