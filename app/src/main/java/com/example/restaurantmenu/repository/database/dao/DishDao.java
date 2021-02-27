package com.example.restaurantmenu.repository.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.restaurantmenu.repository.database.entity.Dish;

import java.util.List;


@Dao
public interface DishDao {

    @Query("SELECT * FROM dish")
   LiveData<List<Dish>>  getAll();

    @Query("SELECT * FROM dish WHERE IdDish == :Id")
    LiveData<Dish> findById(int Id);

    @Insert
    void insertAll(Dish... dishes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Dish dish);

    @Delete
    void delete(Dish dish);


}
