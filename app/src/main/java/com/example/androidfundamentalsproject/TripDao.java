package com.example.androidfundamentalsproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface TripDao {

    @Query("SELECT * FROM Trip")
    List<Trip> getAllTrips();

    @Query("SELECT * FROM Trip WHERE ID=:id")
    Trip getTripById(int id);

    @Query("SELECT * FROM Trip WHERE `User ID`=:id")
    List<Trip> getTripByUserId(int id);

    @Query("SELECT * FROM Trip WHERE Name=:name")
    Trip getTripByName(String name);

    @Query("SELECT * FROM Trip WHERE Name=:name AND `User ID`=:id")
    Trip getTripByNameAndUserID(String name, int id);

    @Query("SELECT * FROM Trip WHERE Favourite=:favourite")
    List<Trip> getFavTrips(boolean favourite);

    @Query("SELECT * FROM Trip WHERE Favourite=:favourite AND `User ID`=:id")
    List<Trip> getFavTripsByUserID(boolean favourite, int id);

    @Insert
    void insertTrip(Trip trip);

    @Update
    void updateTrip(Trip trip);

    @Delete
    void deleteTrip(Trip trip);
}
