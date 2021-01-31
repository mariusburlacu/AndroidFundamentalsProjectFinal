package com.example.androidfundamentalsproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Trip.class, User.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract  class TripDatabase extends RoomDatabase {
    // clasa care ne da acces la baza de date

    private static TripDatabase INSTANCE;

    public abstract TripDao tripDao();

    public abstract UserDao userDao();

    public static TripDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TripDatabase.class, "trip-db").allowMainThreadQueries().build();
        }

        return INSTANCE;
    }
}
