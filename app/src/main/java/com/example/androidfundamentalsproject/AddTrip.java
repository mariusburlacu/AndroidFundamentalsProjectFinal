package com.example.androidfundamentalsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.TypeConverters;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@TypeConverters(DateConverter.class)
public class AddTrip extends AppCompatActivity {

    private EditText et_trip_name;
    private EditText et_destination;
    private RadioGroup rg_type;
    private Slider slider_pret;
    private DatePicker dp_start;
    private DatePicker dp_end;
    private RatingBar ratingBar;
    private Button btn_add;
    private TextView tv_slider_pret;

    private boolean updateOk = false; // inca nu s-a facut update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_trip);

        initViews();

        if(getIntent().hasExtra("edit")){
            btn_add.setText(R.string.edit);
            updateOk = true;
            Trip trip = TripDatabase.getInstance(this).tripDao().getTripByName(getIntent().getStringExtra("edit"));
            valori(trip);
        }

        slider_pret.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                tv_slider_pret.setText("");
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                float value = Math.round(slider_pret.getValue());
                tv_slider_pret.setText(String.valueOf(value));
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateContent()) {
                    Trip trip;
                    if(!updateOk){
                        trip = new Trip();
                        initTrip(trip);
                        trip.setUserId(getIntent().getIntExtra("user_id", 0));
                        TripDatabase.getInstance(AddTrip.this).tripDao().insertTrip(trip);
                    } else {
                        trip = TripDatabase.getInstance(AddTrip.this).tripDao().getTripByName(getIntent().getStringExtra("edit"));
                        initTrip(trip);
                        TripDatabase.getInstance(AddTrip.this).tripDao().updateTrip(trip);
                    }
                    Intent intent = new Intent(AddTrip.this, MainActivity.class);
                    intent.putExtra("user_id", trip.getUserId()); // trimitem pentru a vedea toata lista de tripuri
                    startActivity(intent);
                }
            }
        });
    }

    private void initTrip(Trip trip){
        trip.setName(et_trip_name.getText().toString());
        trip.setDestination(et_destination.getText().toString());
        trip.setPrice(Float.parseFloat(tv_slider_pret.getText().toString()));
        trip.setRating((int) ratingBar.getRating());

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, dp_start.getYear());
        calendar1.set(Calendar.MONTH, dp_start.getMonth());
        calendar1.set(Calendar.DAY_OF_MONTH, dp_start.getDayOfMonth());
        Date date1 = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, dp_end.getYear());
        calendar2.set(Calendar.MONTH, dp_end.getMonth());
        calendar2.set(Calendar.DAY_OF_MONTH, dp_end.getDayOfMonth());
        Date date2 = calendar2.getTime();

        trip.setStart_date(date1);
        trip.setEnd_date(date2);

//                    Date date2 = new Date(dp_end.getYear()-1900, dp_end.getMonth(), dp_end.getDayOfMonth());

        int tip = rg_type.getCheckedRadioButtonId();
        RadioButton selected = findViewById(tip);
        trip.setType(selected.getText().toString());
        trip.setFavourite(false);
    }

    private void initViews() {
        et_trip_name = findViewById(R.id.et_signup_trip_name);
        et_destination = findViewById(R.id.et_signup_destination);
        rg_type = findViewById(R.id.rg_type);
        slider_pret = findViewById(R.id.slider_signup_pret);
        dp_start = findViewById(R.id.dp_start);
        dp_end = findViewById(R.id.dp_end);
        ratingBar = findViewById(R.id.signup_rating);
        btn_add = findViewById(R.id.btn_signup_save);
        tv_slider_pret = findViewById(R.id.tv_signup_pret);
    }

    private void valori(Trip trip){
        et_trip_name.setText(trip.getName());
        et_destination.setText(trip.getDestination());
        rg_type.clearCheck();
        String tip = trip.getType();
        if(tip.equals("City Break")) {
            RadioButton radioButton = findViewById(R.id.rb_city);
            radioButton.setChecked(true);
        } else if (tip.equals("Sea Side")) {
            RadioButton radioButton = findViewById(R.id.rb_seaside);
            radioButton.setChecked(true);
        } else if (tip.equals("Mountains")) {
            RadioButton radioButton = findViewById(R.id.rb_mountain);
            radioButton.setChecked(true);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(trip.getStart_date());
        dp_start.updateDate(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        calendar.setTime(trip.getEnd_date());
        dp_end.updateDate(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        tv_slider_pret.setText(String.valueOf(trip.getPrice()));
        slider_pret.setValue(trip.getPrice());
        ratingBar.setRating(trip.getRating());
    }

    private boolean validateContent() {

        if(et_trip_name.getText().toString().isEmpty()) {
            Toast.makeText(this,"Numele trebuie completat",Toast.LENGTH_LONG).show();
            return false;
        } else {
            Trip t1 = TripDatabase.getInstance(getApplicationContext()).tripDao().getTripByNameAndUserID(et_trip_name.getText().toString(),
                    getIntent().getIntExtra("user_id", 0));

            // IA TRIP UL CARE CONTINE NUMELE DIN EDIT TEXT SI ID UL DAT CA EXTRA

            if(getIntent().hasExtra("edit")){
                int id = TripDatabase.getInstance(this).tripDao().getTripByName(getIntent().getStringExtra("edit")).getUserId();
                t1 = TripDatabase.getInstance(getApplicationContext()).tripDao().getTripByNameAndUserID(et_trip_name.getText().toString(), id);
            }
            // MODIFICAM T1 PENTRU CA ATUNCI CAND ERAM IN FUNCTIA DE EDIT, T1 RAMANEA NULL PENTRU CA ID UL ERA 0

            // MERGE VALIDAREA PENTRU INTRODUCERE TRIP NOU

            if(t1 != null){
                if(getIntent().hasExtra("edit")){
                    // FUNCTIA ASTA FACE CA DACA NUMELE PRIMIT ESTE ACELASI CU NUMELE EDITAT SA PERMITA ADAUGAREA !!!
                    Trip t2 = TripDatabase.getInstance(this).tripDao().getTripByName(getIntent().getStringExtra("edit"));
                    // TRIP T2 ARE ID DE USER LA FEL CA T1, NU TREBUIE DAT
                    if(t1.getName().equals(t2.getName())){
                        if(et_destination.getText().toString().isEmpty()) {
                            Toast.makeText(this,"Destinatia trebuie completata",Toast.LENGTH_LONG).show();
                            return false;
                        }
                        if(rg_type.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(this,"Selectati tipul destinatiei",Toast.LENGTH_LONG).show();
                            return false;
                        }

                        Date date1 = new Date(dp_start.getYear(), dp_start.getMonth(), dp_start.getDayOfMonth());
                        Date date2 = new Date(dp_end.getYear(), dp_end.getMonth(), dp_end.getDayOfMonth());
                        if(date1.compareTo(date2) > 0){
                            Toast.makeText(this,"Data de start nu poate fi mai recenta decat data de sfarsit",Toast.LENGTH_LONG).show();
                            return false;
                        }
                        if(ratingBar.getRating() == 0) {
                            Toast.makeText(this,"Introduceti un rating",Toast.LENGTH_LONG).show();
                            return false;
                        }
                        return true; // AICI RETURNEAZA TRUE SI ESTE VALID FARA SA MAI VERIFICE DACA CELELALTE CAMPURI SUNT COMPLETATE
                    }
                }
                Toast.makeText(this, "Numele calatoriei exista deja!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if(et_destination.getText().toString().isEmpty()) {
            Toast.makeText(this,"Destinatia trebuie completata",Toast.LENGTH_LONG).show();
            return false;
        }
        if(rg_type.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,"Selectati tipul destinatiei",Toast.LENGTH_LONG).show();
            return false;
        }

        Date date1 = new Date(dp_start.getYear(), dp_start.getMonth(), dp_start.getDayOfMonth());
        Date date2 = new Date(dp_end.getYear(), dp_end.getMonth(), dp_end.getDayOfMonth());
        if(date1.compareTo(date2) > 0){
            Toast.makeText(this,"Data de start nu poate fi mai recenta decat data de sfarsit",Toast.LENGTH_LONG).show();
            return false;
        }
        if(ratingBar.getRating() == 0) {
            Toast.makeText(this,"Introduceti un rating",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}