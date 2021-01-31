package com.example.androidfundamentalsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TripReadOnly extends AppCompatActivity {
    private TextView tv_nume;
    private TextView tv_startDate;
    private TextView tv_endDate;
    private TextView tv_price;
    private TextView tv_destination;
    private TextView tv_fav;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_readonly);

        Trip trip = TripDatabase.getInstance(this).tripDao().getTripByName(getIntent().getStringExtra("edit"));
        tv_nume = findViewById(R.id.tv_readonly_nume);
        tv_startDate = findViewById(R.id.tv_readonly_dataStart);
        tv_endDate = findViewById(R.id.tv_readonly_dataEnd);
        tv_price = findViewById(R.id.tv_readonly_pret);
        ratingBar = findViewById(R.id.rating_readonly);
        tv_destination = findViewById(R.id.tv_readonly_destination);
        tv_fav = findViewById(R.id.tv_readonly_fav);

        tv_nume.setText(trip.getName());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tv_startDate.setText(dateFormat.format(trip.getStart_date()));
        tv_endDate.setText(dateFormat.format(trip.getEnd_date()));
        tv_price.setText(String.valueOf(trip.getPrice()));
        ratingBar.setRating(trip.getRating());
        tv_destination.setText(trip.getDestination());
        if(trip.isFavourite()){
            tv_fav.setText("Yes");
        } else {
            tv_fav.setText("No");
        }
    }
}