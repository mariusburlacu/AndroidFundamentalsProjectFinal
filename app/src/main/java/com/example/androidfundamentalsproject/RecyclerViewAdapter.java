package com.example.androidfundamentalsproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.io.InputStreamReader;
import java.util.List;


public class RecyclerViewAdapter extends Adapter<RecyclerViewAdapter.RecycleViewHolder>{
    private List<Trip> trips;
    private Context context;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(List<Trip> trips, Context context) {
        this.trips = trips;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trip_layout, parent, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        Trip trip = trips.get(position);

        holder.tv_nume.setText(trip.getName());
        holder.tv_destination.setText(trip.getDestination());
        holder.tv_price.setText(String.valueOf(trip.getPrice()));
        holder.ratingBar.setRating(trip.getRating());

//        if(!trip.isFavourite()){
//            holder.btn_favourite.setText(R.string.trip_bookmark);
//            holder.btn_favourite.setBackgroundColor(context.getResources().getColor(R.color.purple_500));
//            holder.btn_favourite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    holder.btn_favourite.setText(R.string.added_favourite);
//                    holder.btn_favourite.setBackgroundColor(v.getResources().getColor(R.color.green));
//                    trip.setFavourite(true);
//                    TripDatabase.getInstance(v.getContext()).tripDao().updateTrip(trip);
//                }
//            });
//        } else {
//            holder.btn_favourite.setText(R.string.added_favourite);
//            holder.btn_favourite.setBackgroundColor(context.getResources().getColor(R.color.green));
//            holder.btn_favourite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    holder.btn_favourite.setText(R.string.trip_bookmark);
//                    holder.btn_favourite.setBackgroundColor(v.getResources().getColor(R.color.purple_500));
//                    trip.setFavourite(false);
//                    TripDatabase.getInstance(v.getContext()).tripDao().updateTrip(trip);
//                }
//            });
//        }


        if (trip.isFavourite()) {
            holder.btn_favourite.setText(R.string.added_favourite);
            holder.btn_favourite.setBackgroundColor(holder.itemView.getResources().getColor(R.color.green));
            holder.isFav = true;
        } else {
            holder.btn_favourite.setText(R.string.trip_bookmark);
            holder.btn_favourite.setBackgroundColor(holder.itemView.getResources().getColor(R.color.purple_500));
            holder.isFav = false;
        }


        holder.btn_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trip.isFavourite()){
                    holder.btn_favourite.setText(R.string.trip_bookmark);
                    holder.btn_favourite.setBackgroundColor(view.getResources().getColor(R.color.purple_500));
                    trip.setFavourite(false);
                    TripDatabase.getInstance(view.getContext()).tripDao().updateTrip(trip);
                    holder.isFav = false;
                } else {
                    holder.btn_favourite.setText(R.string.added_favourite);
                    holder.btn_favourite.setBackgroundColor(view.getResources().getColor(R.color.green));
                    trip.setFavourite(true);
                    TripDatabase.getInstance(view.getContext()).tripDao().updateTrip(trip);
                    holder.isFav = true;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_nume;
        public TextView tv_destination;
        public RatingBar ratingBar;
        public TextView tv_price;
        public Button btn_favourite;
        public boolean favourite;
        public static boolean isFav;


        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nume = itemView.findViewById(R.id.tv_tripName);
            tv_destination = itemView.findViewById(R.id.tv_trip_destination);
            tv_price = itemView.findViewById(R.id.tv_trip_price);
            ratingBar = itemView.findViewById(R.id.trip_rating);
            btn_favourite = itemView.findViewById(R.id.btn_bookmark);
            favourite = false;

            if(isFav){
                btn_favourite.setText(R.string.added_favourite);
                btn_favourite.setBackgroundColor(itemView.getResources().getColor(R.color.green));
            } else {
                btn_favourite.setText(R.string.trip_bookmark);
                btn_favourite.setBackgroundColor(itemView.getResources().getColor(R.color.purple_500));
            }

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent = new Intent(view.getContext(), AddTrip.class);
                    intent.putExtra("edit", tv_nume.getText().toString());
                    view.getContext().startActivity(intent);
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), TripReadOnly.class);
                    intent.putExtra("edit", tv_nume.getText().toString());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
