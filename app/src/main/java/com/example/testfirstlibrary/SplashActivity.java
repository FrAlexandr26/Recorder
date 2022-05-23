package com.example.testfirstlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        getWindow().setStatusBarColor(getColor(R.color.white));
        Toast.makeText(getApplicationContext(), "SPLASHHH!!!", Toast.LENGTH_SHORT).show();

        /*Intent startMainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(startMainActivityIntent);
        finish();*/
    }
}