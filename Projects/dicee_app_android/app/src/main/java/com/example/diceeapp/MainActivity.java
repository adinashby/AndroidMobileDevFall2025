package com.example.diceeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView diceImage1;
    private ImageView diceImage2;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diceImage1 = findViewById(R.id.diceImage1);
        diceImage2 = findViewById(R.id.diceImage2);

        Button rollButton = findViewById(R.id.rollButton);
        random = new Random();

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });
    }

    private void rollDice() {
        int diceNumber1 = random.nextInt(6) + 1;
        int diceNumber2 = random.nextInt(6) + 1;

        int resIdImage1 = getResources().getIdentifier(
                "dice" + diceNumber1, "drawable", getPackageName());
        int resIdImage2 = getResources().getIdentifier(
                "dice" + diceNumber2, "drawable", getPackageName());

        diceImage1.setImageResource(resIdImage1);
        diceImage2.setImageResource(resIdImage2);
    }
}
