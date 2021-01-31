package com.example.androidfundamentalsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN_ACTIVITY = "loginActivity";
    public static final String NAME = "name";
    public static final String PASS = "pass";
    public static final String CHECK = "CHECK";
    private EditText et_username;
    private EditText et_parola;
    private Button btn_login;
    private Button btn_signup;
    private CheckBox checkBox;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(LOGIN_ACTIVITY, MODE_PRIVATE);

        init();

        incarcaPreferinte();

        btn_login.setOnClickListener(view -> {
            if(validateContent()) {
                String username = et_username.getText().toString();
                String pass = et_parola.getText().toString();
                User user = TripDatabase.getInstance(this).userDao().getByUsernamePass(username,pass);

                if(checkBox.isChecked()) {
                    Boolean isChecked = checkBox.isChecked();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(NAME, username);
                    editor.putString(PASS, pass);
                    editor.putBoolean(CHECK, isChecked);
                    editor.apply();
                } else {
                    preferences.edit().clear().apply();
                }
                if(user!=null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user_id",user.getId());
                    intent.putExtra("nume_user", user.getUsername());
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(this,"Username or password do not exist",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        et_username = findViewById(R.id.username);
        et_parola = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_sign_up);
        checkBox = findViewById(R.id.cb_remember);
    }

    private boolean validateContent() {
        String username = et_username.getText().toString();
        String parola = et_parola.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(this, "Username is empty!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(parola.isEmpty()) {
            Toast.makeText(this, "Password is empty!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void incarcaPreferinte() {
        SharedPreferences preferences = getSharedPreferences(LOGIN_ACTIVITY, MODE_PRIVATE);
        if(preferences.contains(NAME)) {
            String nume_pref = preferences.getString(NAME, "");
            et_username.setText(nume_pref);
        }
        if(preferences.contains(PASS)) {
            String pass_pref = preferences.getString(PASS, "");
            et_parola.setText(pass_pref);
        }
        if(preferences.contains(CHECK)) {
            Boolean checked = preferences.getBoolean(CHECK, false);
            checkBox.setChecked(checked);
        }
    }
}