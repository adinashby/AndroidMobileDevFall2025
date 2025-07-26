package com.example.climaapp;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataParser {

    private final JSONObject weatherJson;

    public WeatherDataParser(String jsonString) {
        try {
            this.weatherJson = new JSONObject(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException("Invalid JSON", e);
        }
    }

    public int getTemperature() {
        try {
            return (int) Math.round(weatherJson.getJSONObject("main").getDouble("temp"));
        } catch (JSONException e) {
            return 0;
        }
    }

    public String getCityName() {
        try {
            return weatherJson.getString("name");
        } catch (JSONException e) {
            return "";
        }
    }

    public int getWeatherIcon() {
        try {
            int condition = weatherJson.getJSONArray("weather").getJSONObject(0).getInt("id");
            return WeatherIconMapper.getIconResource(condition);
        } catch (JSONException e) {
            return R.drawable.default_weather;
        }
    }

    public String getMessage() {
        int temp = getTemperature();
        if (temp > 25) return "It’s 🍦 time";
        else if (temp > 20) return "Time for shorts and 👕";
        else if (temp < 10) return "You’ll need 🧣 and 🧤";
        else return "Bring a 🧥 just in case";
    }

    public double getLatitude() {
        try {
            return weatherJson.getJSONObject("coord").getDouble("lat");
        } catch (JSONException e) {
            return 0;
        }
    }

    public double getLongitude() {
        try {
            return weatherJson.getJSONObject("coord").getDouble("lon");
        } catch (JSONException e) {
            return 0;
        }
    }
}
