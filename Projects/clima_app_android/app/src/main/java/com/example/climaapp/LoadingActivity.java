package com.example.climaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class LoadingActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getWeatherData();
        }
    }

    private void getWeatherData() {
        LocationHelper locationHelper = new LocationHelper(this);
        locationHelper.getCurrentLocation(location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                WeatherNetworkClient.fetchWeatherByCoordinates(latitude, longitude, this::goToWeatherActivity);
            } else {
                Toast.makeText(this, "Unable to retrieve location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToWeatherActivity(String weatherDataJson) {
        WeatherDataParser parser = new WeatherDataParser(weatherDataJson);
        double lat = parser.getLatitude();
        double lon = parser.getLongitude();

        Intent intent = new Intent(LoadingActivity.this, WeatherActivity.class);
        intent.putExtra("weatherData", weatherDataJson);
        intent.putExtra("lat", lat);
        intent.putExtra("lon", lon);
        startActivity(intent);
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeatherData();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}