package com.example.androidfundamentalsproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SubtitleData;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private TextView tv_username_header;
    private TextView tv_email_header;
    private static final String HEADER = "header";
    private NavController navController;
    public static int USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int userId = getIntent().getIntExtra("user_id", 0);
        USER_ID = getIntent().getIntExtra("user_id", 0);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = getIntent().getIntExtra("user_id", 0);
                Intent intent = new Intent(MainActivity.this, AddTrip.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_about, R.id.nav_contact, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // SETAM NUMELE SI EMAILUL IN HEADER CU SHARED PREFERENCES

        if(getIntent().hasExtra("user_id")) {
            userId = getIntent().getIntExtra("user_id", 0);
            User user = TripDatabase.getInstance(this).userDao().getById(userId);

            setStringValueInSharedPref(this, "nume", "email", user.getFullName(), user.getEmail());
        }

        View navHeader = navigationView.getHeaderView(0);
        tv_username_header = navHeader.findViewById(R.id.tv_username_header);
        tv_email_header = navHeader.findViewById(R.id.textView_email);

        ArrayList<String> list = getStringValueFromSharedPref(this, "nume", "email");
        tv_username_header.setText(list.get(0));
        tv_email_header.setText(list.get(1));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favorite) {
            Intent intent = new Intent(this, FavouriteTrips.class);
            intent.putExtra("user_id", getIntent().getIntExtra("user_id", 0));
            startActivity(intent);
            return true;
        }
        return false;
    }

    public static void setStringValueInSharedPref(Context context, String key1, String key2, String value1, String value2){
        SharedPreferences sharedPreferences = context.getSharedPreferences(HEADER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key1, value1);
        editor.putString(key2, value2);
        editor.apply();
    }

    public static ArrayList<String> getStringValueFromSharedPref(Context context, String key1, String key2){
        ArrayList<String> list = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(HEADER, Context.MODE_PRIVATE);
        list.add(sharedPreferences.getString(key1, ""));
        list.add(sharedPreferences.getString(key2, ""));
        return list;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}