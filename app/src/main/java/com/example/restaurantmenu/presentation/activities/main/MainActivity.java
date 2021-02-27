package com.example.restaurantmenu.presentation.activities.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.restaurantmenu.databinding.ActivityMainBinding;
import com.example.restaurantmenu.presentation.activities.menu.MenuActivity;

import java.nio.channels.InterruptedByTimeoutException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_main);

        binding.userTypePicker.setMaxValue(1);
        binding.userTypePicker.setDisplayedValues(new String[]{"Администратор","Клиент"});

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                boolean admin = binding.userTypePicker.getValue()==0;
            }
        });
    }
}