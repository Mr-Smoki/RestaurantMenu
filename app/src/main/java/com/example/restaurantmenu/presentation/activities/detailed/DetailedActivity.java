package com.example.restaurantmenu.presentation.activities.detailed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.restaurantmenu.R;
import com.example.restaurantmenu.databinding.ActivityDetailedBinding;
import com.example.restaurantmenu.presentation.activities.edit.EditActivity;
import com.example.restaurantmenu.presentation.activities.main.MainActivity;
import com.example.restaurantmenu.presentation.activities.menu.MenuActivity;
import com.example.restaurantmenu.repository.AppData;
import com.example.restaurantmenu.repository.database.entity.Dish;

public class DetailedActivity extends AppCompatActivity {

    AppData appData;
    Dish localDish;
    ActivityDetailedBinding binding;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        binding = ActivityDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appData = AppData.getInstance(getApplicationContext());
        id = getIntent().getIntExtra(AppData.ID,-1);
        appData.db.dishDao().findById(id).observe(this, new Observer<Dish>() {
            @Override
            public void onChanged(Dish dish) {
                if(dish==null)
                    finish();
                localDish  = dish;
                binding.setDish(dish);
                appData.glide.load(dish.url)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                binding.photoView.setImageDrawable(resource);
                                startPostponedEnterTransition();
                            }
                        });
            }
        });



        binding.goEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editActivity =new Intent(DetailedActivity.this, EditActivity.class);
                editActivity.putExtra(AppData.ID,id);
                startActivity(editActivity);
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuActivity =new Intent(DetailedActivity.this, MenuActivity.class);
                startActivity(menuActivity);
            }
        });

        if(appData.admin)
        {
            binding.goEditButton.setVisibility(View.VISIBLE);
        }

    }
}