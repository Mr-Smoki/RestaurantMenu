package com.example.restaurantmenu.repository.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.restaurantmenu.repository.database.dao.DishDao;
import com.example.restaurantmenu.repository.database.entity.Dish;

@Database(entities = {Dish.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DishDao dishDao();
}