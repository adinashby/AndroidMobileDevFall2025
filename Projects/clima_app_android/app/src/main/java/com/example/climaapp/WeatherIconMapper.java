package com.example.climaapp;

public class WeatherIconMapper {

    public static int getIconResource(int condition) {
        if (condition < 300) {
            return R.drawable.thunderstorm;
        } else if (condition < 400) {
            return R.drawable.drizzle;
        } else if (condition < 600) {
            return R.drawable.rain;
        } else if (condition < 700) {
            return R.drawable.snow;
        } else if (condition < 800) {
            return R.drawable.atmosphere;
        } else if (condition == 800) {
            return R.drawable.clear;
        } else if (condition <= 804) {
            return R.drawable.clouds;
        } else {
            return R.drawable.default_weather;
        }
    }
}
