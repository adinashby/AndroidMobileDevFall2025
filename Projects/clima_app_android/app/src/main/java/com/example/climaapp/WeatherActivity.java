package com.example.climaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class WeatherActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude = 45.5017;  // Default Montreal
    private double longitude = -73.5673;

    private TextView temperatureText;
    private TextView cityText;
    private TextView weatherMessageText;
    private ImageView weatherIcon;
    private View searchCityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // 🌡️ Weather UI Elements
        temperatureText = findViewById(R.id.temperature_text);
        cityText = findViewById(R.id.city_name_text);
        weatherMessageText = findViewById(R.id.weather_message_text);
        weatherIcon = findViewById(R.id.weather_icon);
        searchCityButton = findViewById(R.id.search_city_button);
        Button backButton = findViewById(R.id.back_button);

        // 🗺️ Get map coordinates
        latitude = getIntent().getDoubleExtra("lat", latitude);
        longitude = getIntent().getDoubleExtra("lon", longitude);

        // 🌐 Load weather data
        String weatherJson = getIntent().getStringExtra("weatherData");
        if (weatherJson != null) {
            updateUI(weatherJson);
        }

        // 🔙 Back button
        backButton.setOnClickListener(v -> finish());

        // 🔍 Search city button
        searchCityButton.setOnClickListener(v -> {
            Intent intent = new Intent(WeatherActivity.this, CityInputActivity.class);
            startActivity(intent);
        });

        // 🗺️ Set up map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void updateUI(String weatherJson) {
        WeatherDataParser parser = new WeatherDataParser(weatherJson);
        temperatureText.setText(parser.getTemperature() + "°");
        cityText.setText(parser.getCityName());
        weatherMessageText.setText(parser.getMessage());
        weatherIcon.setImageResource(parser.getWeatherIcon());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng selectedLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(selectedLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 10f));
    }
}
