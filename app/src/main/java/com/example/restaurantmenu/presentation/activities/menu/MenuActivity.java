package com.example.restaurantmenu.presentation.activities.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.restaurantmenu.R;
import com.example.restaurantmenu.databinding.ActivityMainBinding;
import com.example.restaurantmenu.databinding.ActivityMenuBinding;
import com.example.restaurantmenu.databinding.ItemDishBinding;
import com.example.restaurantmenu.presentation.activities.detailed.DetailedActivity;
import com.example.restaurantmenu.presentation.activities.edit.EditActivity;
import com.example.restaurantmenu.presentation.activities.main.MainActivity;
import com.example.restaurantmenu.repository.AppData;
import com.example.restaurantmenu.repository.database.entity.Dish;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    AppData appData;
    List<Dish> localDishes = new ArrayList<>();
    DishesAdapter adapter;
    LayoutInflater inflater;

    ActivityMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        inflater = getLayoutInflater();
        appData = AppData.getInstance(getApplicationContext());
        initDishesAdapter();
        adapter = new DishesAdapter();
        binding.dishesView.setAdapter(adapter);

        appData.db.dishDao().getAll().observe(this, new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishes) {
                localDishes = dishes;
                adapter.notifyDataSetChanged();
            }
        });
        binding.goAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editActivity =new Intent(MenuActivity.this, EditActivity.class);
                startActivity(editActivity);
            }
        });


        if(appData.admin)
        {
            binding.goAddButton.setVisibility(View.VISIBLE);
        }


    }

    private void initDishesAdapter() {

    }

    private class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemDishBinding.inflate(inflater,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            final Dish itemDish = localDishes.get(position);
            holder.dishBinding.nameView.setText(itemDish.name);
            holder.dishBinding.priceView.setText(itemDish.getPrice());
           holder.dishBinding.descriptionView.setText(itemDish.description);
            appData.loadImage(itemDish.url,holder.dishBinding.photoView);

            holder.dishBinding.detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailedActivity = new Intent(MenuActivity.this, DetailedActivity.class);
                    detailedActivity.putExtra(AppData.ID, itemDish.IdDish);

                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(MenuActivity.this,
                                    holder.dishBinding.photoView,
                                    getResources().getString(R.string.trans));

                    startActivity(detailedActivity, options.toBundle());
                }
            });

        }

        @Override
        public int getItemCount() {
            return localDishes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ItemDishBinding dishBinding;
            public ViewHolder(@NonNull ItemDishBinding dishBinding) {
                super(dishBinding.getRoot());
                this.dishBinding = dishBinding;
            }
        }
    }
}