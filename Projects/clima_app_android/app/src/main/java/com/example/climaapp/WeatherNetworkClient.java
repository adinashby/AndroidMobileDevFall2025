package com.example.climaapp;

import android.os.Handler;
import android.os.Looper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

public class WeatherNetworkClient {

    private static final String API_KEY = "797e4d8ebe0f188d30df398159ffb418";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void fetchWeatherByCoordinates(double lat, double lon, Consumer<String> callback) {
        String url = BASE_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=metric";
        fetchData(url, callback);
    }

    public static void fetchWeatherByCity(String cityName, Consumer<String> callback) {
        String url = BASE_URL + "?q=" + cityName + "&appid=" + API_KEY + "&units=metric";
        fetchData(url, callback);
    }

    private static void fetchData(String urlString, Consumer<String> callback) {
        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                new Handler(Looper.getMainLooper()).post(() -> callback.accept(response.toString()));

            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> callback.accept(null));
            }
        }).start();
    }
}
