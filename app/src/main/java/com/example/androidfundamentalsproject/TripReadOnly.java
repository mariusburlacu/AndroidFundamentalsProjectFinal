package com.example.androidfundamentalsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class TripReadOnly extends AppCompatActivity{
    private TextView tv_nume;
    private TextView tv_startDate;
    private TextView tv_endDate;
    private TextView tv_price;
    private TextView tv_destination;
    private TextView tv_fav;
    private TextView tv_weather_condition;
    private TextView tv_weather_description;
    private RatingBar ratingBar;
    public JSONObject data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_readonly);
        Trip trip = TripDatabase.getInstance(this).tripDao().getTripByName(getIntent().getStringExtra("edit"));
        getJSON(trip.getDestination());

        tv_nume = findViewById(R.id.tv_readonly_nume);
        tv_startDate = findViewById(R.id.tv_readonly_dataStart);
        tv_endDate = findViewById(R.id.tv_readonly_dataEnd);
        tv_price = findViewById(R.id.tv_readonly_pret);
        ratingBar = findViewById(R.id.rating_readonly);
        tv_destination = findViewById(R.id.tv_readonly_destination);
        tv_fav = findViewById(R.id.tv_readonly_fav);
        tv_weather_condition = findViewById(R.id.tv_readonly_weather_conditions);
        tv_weather_description = findViewById(R.id.tv_readonly_weather_description);

        tv_nume.setText(trip.getName());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tv_startDate.setText(dateFormat.format(trip.getStart_date()));
        tv_endDate.setText(dateFormat.format(trip.getEnd_date()));
        tv_price.setText(String.valueOf(trip.getPrice()));
        ratingBar.setRating(trip.getRating());
        tv_destination.setText(trip.getDestination());
        if (trip.isFavourite()) {
            tv_fav.setText("Yes");
        } else {
            tv_fav.setText("No");
        }
    }

    private ArrayList<String> getINFO(String result){
        ArrayList<String> list = new ArrayList<>();
        try {
            JSONObject weatherAPI = new JSONObject(result);

            JSONArray weather = weatherAPI.getJSONArray("weather");
            for(int i=0; i<weather.length(); i++){
                JSONObject currentObject = weather.getJSONObject(i);
                String main = currentObject.getString("main");
                list.add(main);

                String description = currentObject.getString("description");
                list.add(description);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void getJSON(String city){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=8431fe7c34c2b3c09d17e4d4c29b6840");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream stream = connection.getInputStream();
                    InputStreamReader streamReader = new InputStreamReader(stream);
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder result = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                    streamReader.close();
                    stream.close();

                    ArrayList<String> list = getINFO(result.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_weather_condition.setText(list.get(0));
                            tv_weather_description.setText(list.get(1));
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}