package com.example.bmicalculatorapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Closes the activity and goes back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Show back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        float bmi = getIntent().getFloatExtra("bmi", 0);
        String category = getIntent().getStringExtra("category");

        ((TextView) findViewById(R.id.bmi_result)).setText(String.format("%.1f", bmi));
        ((TextView) findViewById(R.id.bmi_category)).setText(category);
    }
}
