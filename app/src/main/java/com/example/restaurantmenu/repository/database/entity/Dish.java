package com.example.restaurantmenu.repository.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;

@Entity
public class Dish {
    @PrimaryKey(autoGenerate = true)
    public int IdDish;

//    Картинка
    public String url;
//    Название
    public String name;
//    Цена
    public double price;
//    Описание
    public String description;

    public String getPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("0 руб");
        return decimalFormat.format(price) + ".";
    }
}
