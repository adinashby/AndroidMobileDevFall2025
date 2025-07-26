package com.example.climaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class CityInputActivity extends AppCompatActivity {

    private EditText cityEditText;
    private Button getWeatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_input);

        cityEditText = findViewById(R.id.city_edit_text);
        getWeatherButton = findViewById(R.id.get_weather_button);

        cityEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getWeatherButton.performClick();
                return true;
            }
            return false;
        });

        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = cityEditText.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    WeatherNetworkClient.fetchWeatherByCity(cityName, weatherJson -> {
                        WeatherDataParser parser = new WeatherDataParser(weatherJson);
                        double lat = parser.getLatitude();
                        double lon = parser.getLongitude();

                        Intent intent = new Intent(CityInputActivity.this, WeatherActivity.class);
                        intent.putExtra("weatherData", weatherJson);
                        intent.putExtra("lat", lat);
                        intent.putExtra("lon", lon);
                        startActivity(intent);
                        finish();
                    });
                }
            }
        });
    }
}
