package com.example.restaurantmenu.presentation.activities.edit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.restaurantmenu.databinding.ActivityDetailedBinding;
import com.example.restaurantmenu.databinding.ActivityEditBinding;
import com.example.restaurantmenu.repository.AppData;
import com.example.restaurantmenu.repository.database.entity.Dish;

public class EditActivity extends AppCompatActivity {

    Dish localDish;
    AppData appData;
    ActivityEditBinding binding;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.setPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.formSetPhoto.setVisibility(View.VISIBLE);
            }
        });

        binding.CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "";
                binding.urlView.setText("");
                binding.formSetPhoto.setVisibility(View.INVISIBLE);
            }
        });

        binding.saveUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.formSetPhoto.setVisibility(View.INVISIBLE);
            }
        });

        binding.urlView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                url = binding.urlView.getText().toString();
                appData.loadImage(url,binding.photoView);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.addOrEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(localDish!=null) {
                    localDish.name = binding.nameView.getText().toString();
                    localDish.url = url;
                    localDish.description = binding.descriptionView.getText().toString();
                    localDish.price= Double.parseDouble(binding.priceView.getText().toString());
                    appData.db.dishDao().insert(localDish);
                    finish();
                }
            }
        });

        appData = AppData.getInstance(getApplicationContext());

        appData.loadImage("",binding.photoView);
        int id =getIntent().getIntExtra(AppData.ID,-1);
        if(id==-1){
            localDish = new Dish();
        }
        else
        {
            binding.deleteButton.setVisibility(View.VISIBLE);
            binding.addOrEditButton.setText("Редактировать");
            appData.db.dishDao().findById(id).observe(this, new Observer<Dish>() {
                @Override
                public void onChanged(Dish dish) {
                    if(dish==null)
                        finish();
                    localDish = dish;
                    binding.nameView.setText(dish.name);
                    binding.descriptionView.setText(dish.description);
                    binding.priceView.setText(String.valueOf(dish.price));
                }
            });
            binding.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteMessage();
                }
            });
        }
    }

    private void showDeleteMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Вы уверены?")
                .setTitle("Удаление блюда")
                .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appData.db.dishDao().delete(localDish);
                        finish();
                    }
                }).setNegativeButton("Отмена",null);
        builder.show();
    }
}