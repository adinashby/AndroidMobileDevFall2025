package com.example.bmicalculatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {
    private int height = 170;
    private int weight = 60;
    private TextView heightValue;
    private TextView weightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        heightValue = findViewById(R.id.height_value);
        weightValue = findViewById(R.id.weight_value);

        SeekBar heightSeekBar = findViewById(R.id.height_seekbar);
        heightSeekBar.setProgress(height);
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                height = progress;
                heightValue.setText(height + " cm");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        findViewById(R.id.weight_plus).setOnClickListener(v -> updateWeight(1));
        findViewById(R.id.weight_minus).setOnClickListener(v -> updateWeight(-1));

        findViewById(R.id.calculate_button).setOnClickListener(v -> {
            BMICalculator calculator = new BMICalculator(height, weight);
            Intent intent = new Intent(InputActivity.this, ResultsActivity.class);
            intent.putExtra("bmi", calculator.getBMI());
            intent.putExtra("category", calculator.getCategory());
            startActivity(intent);
        });

        updateWeight(0);
    }

    private void updateWeight(int delta) {
        weight += delta;
        weightValue.setText(weight + " kg");
    }
}
