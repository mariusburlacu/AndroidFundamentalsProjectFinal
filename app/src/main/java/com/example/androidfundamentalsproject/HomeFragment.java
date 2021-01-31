package com.example.androidfundamentalsproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeFragment extends Fragment {

    public static int USER_ID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv_fara_trip = view.findViewById(R.id.tv_fara_tripuri);
        RecyclerView rv_trips = view.findViewById(R.id.rv_trips);

        USER_ID = MainActivity.USER_ID;

//        Bundle bundle = getArguments();
//        if(bundle != null){
//            USER_ID = bundle.getInt("user_id", 0);
//        } else {
//            Toast.makeText(view.getContext(), "Bundle is null", Toast.LENGTH_LONG).show();
//        }

        Log.v("USER_ID", String.valueOf(USER_ID));

        List<Trip> trips = TripDatabase.getInstance(getContext()).tripDao().getTripByUserId(USER_ID);
        Log.v("trips", trips.toString());

        if(trips.size() == 0){
            rv_trips.setVisibility(View.GONE);
        } else {
            tv_fara_trip.setVisibility(View.GONE);
        }


        rv_trips.setLayoutManager(new LinearLayoutManager(this.getContext()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(trips, this.getContext());
        rv_trips.setAdapter(adapter);
    }
}
