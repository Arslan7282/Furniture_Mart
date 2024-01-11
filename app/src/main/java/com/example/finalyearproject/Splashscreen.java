package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {

    static int SPLASH_SCREEN_TIME_OUT=4000;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splashscreen);



        SharedPreferences sharedPreferences = getSharedPreferences("RegisterDetails", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("Email","");
        password = sharedPreferences.getString("Password","");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (email != "") {
                    Intent intent = new Intent(Splashscreen.this, Firstpage.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(Splashscreen.this, Login.class);
                    startActivity(intent);
                }
                finish();

            }
        }, SPLASH_SCREEN_TIME_OUT);



    }
}