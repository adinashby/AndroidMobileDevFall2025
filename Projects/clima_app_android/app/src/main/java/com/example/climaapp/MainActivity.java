package com.example.climaapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No need to set a layout, redirect immediately to loading screen
        Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
        startActivity(intent);
        finish();
    }
}
