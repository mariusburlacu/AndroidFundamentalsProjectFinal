package com.example.androidfundamentalsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class FavouriteTrips extends AppCompatActivity {

    private RecyclerView rv_trips;
    private TextView tv_fav_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_fragment);

        List<Trip> trips = TripDatabase.getInstance(this).tripDao().getFavTripsByUserID(true, getIntent().getIntExtra("user_id", 0));
        Log.v("trips", trips.toString());
        rv_trips = findViewById(R.id.rv_trips_fav);
        tv_fav_list = findViewById(R.id.tv_fara_tripuri_fav);

        if(trips.size() == 0){
            rv_trips.setVisibility(View.GONE);
        } else {
            tv_fav_list.setVisibility(View.GONE);
        }

        rv_trips.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(trips, this);
        rv_trips.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FavouriteTrips.this, MainActivity.class);
        intent.putExtra("user_id", getIntent().getIntExtra("user_id", 0));
        startActivity(intent);
    }
}