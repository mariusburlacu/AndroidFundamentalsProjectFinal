package com.example.androidfundamentalsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_full_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_retype_password;
    private Button et_btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();

        et_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateContent()){
                    User user = new User();
                    String username = et_username.getText().toString();
                    String full_username = et_full_username.getText().toString();
                    String email = et_email.getText().toString();
                    String pass1 = et_password.getText().toString();
                    user.setUsername(username);
                    user.setFullName(full_username);
                    user.setPassword(pass1);
                    user.setEmail(email);

                    List<User> usersByUsername = TripDatabase.getInstance(view.getContext()).userDao().getAllByName(username);
                    if(usersByUsername.size() == 0) {
                        List<User> users = TripDatabase.getInstance(SignupActivity.this).userDao().getAllUsers();
                        Log.v("users_from_db", users.toString());
                        TripDatabase.getInstance(view.getContext()).userDao().insertUser(user);
                        Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void init(){
        et_username = findViewById(R.id.et_username);
        et_full_username = findViewById(R.id.et_full_username);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_retype_password = findViewById(R.id.et_retype_password);
        et_btn_signup = findViewById(R.id.sign_up_btn);
    }


    private boolean validateContent(){
        String username = et_username.getText().toString();
        String full_username = et_full_username.getText().toString();
        String email = et_email.getText().toString();
        String pass1 = et_password.getText().toString();
        String pass2 = et_retype_password.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(this, "Username must not be empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if(full_username.isEmpty()){
            Toast.makeText(this, "Username must not be empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if(email.isEmpty()){
            Toast.makeText(this, "Email must not be empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if(pass1.isEmpty() || pass1.length() < 8){
            Toast.makeText(this, "Password must not be empty and must be 8+ characters", Toast.LENGTH_LONG).show();
            return false;
        }

        if(pass2.isEmpty()){
            Toast.makeText(this, "Password must not be empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email format invalid", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!pass1.equals(pass2)){
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}