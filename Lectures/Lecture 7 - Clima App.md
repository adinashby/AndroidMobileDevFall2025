# 🌦️ Lecture 7: Building the Clima Weather App in Java (Android)

## 🎯 Objective

Recreate the Flutter “Clima” app using native Android (Java), where users can fetch weather information based on their current location or by entering a city name. The app displays temperature, a weather message, an appropriate weather icon, and the location on a Google Map.

---

## 🛠️ Project Overview

This project implements:

* Weather fetching from OpenWeatherMap API by GPS or city name
* JSON parsing and UI rendering of temperature, icon, and message
* Navigation between loading, city input, and weather display screens
* Google Maps integration to show the city on a map
* Runtime permissions and HTTP networking

---

## 📂 Folder Structure

```
ClimaApp/
├── java/com/example/climaapp/
│   ├── MainActivity.java
│   ├── LoadingActivity.java
│   ├── CityInputActivity.java
│   ├── WeatherActivity.java
│   ├── WeatherNetworkClient.java
│   ├── WeatherDataParser.java
│   ├── WeatherIconMapper.java
│   └── LocationHelper.java
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── activity_loading.xml
│   │   ├── activity_city_input.xml
│   │   └── activity_weather.xml
│   ├── drawable/
│   │   ├── clear.png
│   │   ├── clouds.png
│   │   ├── drizzle.png
│   │   ├── rain.png
│   │   ├── snow.png
│   │   ├── thunderstorm.png
│   │   ├── atmosphere.png
│   │   └── default_weather.png
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   └── themes.xml
├── AndroidManifest.xml
```

---

## 🔧 Step-by-Step Instructions

### ✅ Step 1: Create `WeatherDataParser.java`

Parses weather JSON and converts it to city name, temperature, and icon.

```java
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
```

---

### ✅ Step 2: WeatherActivity with Google Map

```java
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
```

---

### ✅ Step 3: Google Maps API Setup in `AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.climaapp">

    <!-- Permissions -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="Clima App"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.ClimaApp">

        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="AIzaSyCEcjDYah47b-uFSkD49wCye2Ttlacp5Mk" />

        <!-- Entry point -->
        <activity android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Other screens -->
        <activity android:name=".LoadingActivity" />
        <activity android:name=".WeatherActivity" />
        <activity android:name=".CityInputActivity" />

    </application>

</manifest>
```

---

### ✅ Step 4: Enter City with Keyboard in `CityInputActivity`

```java
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
```

---

### ✅ Step 5: Display Icons Based on Condition

```java
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
```

---

### ✅ Step 6: Fetch the Weather Data in `WeatherNetworkClient`

```java
package com.example.climaapp;

import android.os.Handler;
import android.os.Looper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

public class WeatherNetworkClient {

    private static final String API_KEY = "YOUR_API_KEY";
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
```

---

### ✅ Step 7: Get the Location of the Device in `LocationHelper`

```java
package com.example.climaapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;

import java.util.function.Consumer;

public class LocationHelper {

    private final Context context;

    public LocationHelper(Context context) {
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(@NonNull Consumer<Location> callback) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            callback.accept(null);
            return;
        }

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                callback.accept(location);
                locationManager.removeUpdates(this);
            }

            @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override public void onProviderEnabled(@NonNull String provider) {}
            @Override public void onProviderDisabled(@NonNull String provider) {}
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
}
```

---

### ✅ Step 8: Have a Loading Screen While Waiting for the API Call `LoadingActivity`

```java
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
```

---

### ✅ Step 9: Update the `MainActivity`

```java
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
```

---

### ✅ Step 10: Add the Google Map Dependency in `Build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.climaapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.climaapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
```

---

### ✅ Step 11: Create the XML file for `Activity_weather`

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/sky_blue"
    android:padding="24dp">

    <Button
        android:id="@+id/back_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="← Back"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_margin="12dp"/>

    <!-- Weather Icon -->
    <ImageView
        android:id="@+id/weather_icon"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="125dp"
        android:src="@drawable/default_weather" />

    <!-- Temperature Text -->
    <TextView
        android:id="@+id/temperature_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="21°C"
        android:textSize="64sp"
        android:textColor="@android:color/white"
        android:layout_below="@id/weather_icon"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="16dp"/>

    <!-- City Name -->
    <TextView
        android:id="@+id/city_name_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Montreal"
        android:textSize="24sp"
        android:textColor="@android:color/white"
        android:layout_below="@id/temperature_text"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="8dp" />

    <!-- Weather Message -->
    <TextView
        android:id="@+id/weather_message_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Bring a jacket!"
        android:textSize="20sp"
        android:textColor="@android:color/white"
        android:layout_below="@id/city_name_text"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="12dp"/>

    <fragment
        android:id="@+id/map"
        android:name="com.google.android.gms.maps.SupportMapFragment"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:layout_marginTop="12dp"
        android:layout_below="@id/weather_message_text"/>


    <!-- Search Button -->
    <Button
        android:id="@+id/search_city_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Search City"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="15dp" />

</RelativeLayout>
```

---

### ✅ Step 12: Create the XML file for `Activity_loading`

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:background="@color/sky_blue">

    <ProgressBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:indeterminate="true"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Loading weather..."
        android:textColor="@android:color/white"
        android:textSize="20sp"
        android:layout_below="@android:id/progress"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="24dp"/>

</RelativeLayout>
```

---

### ✅ Step 13: Create the XML file for `Activity_city_input`

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="24dp"
    android:background="@color/sky_blue">

    <EditText
        android:id="@+id/city_edit_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter City Name"
        android:background="@android:drawable/editbox_background"
        android:padding="12dp"
        android:textColor="@android:color/black"
        android:layout_centerVertical="true"
        android:layout_marginBottom="16dp"
        android:imeOptions="actionDone"
        android:inputType="text"
        android:singleLine="true"/>

    <Button
        android:id="@+id/get_weather_button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Get Weather"
        android:layout_below="@id/city_edit_text"
        android:layout_marginTop="12dp"/>

</RelativeLayout>
```

---

## 🧠 Learning Outcomes

By completing this project, students will:

* Understand how to work with HTTP requests and APIs
* Parse JSON in Java using Android SDK
* Handle runtime permissions (Location)
* Use `Intent` and extras for inter-activity communication
* Integrate Google Maps SDK into Android apps

---

## 🧾 Extensions (Optional)

* Use reverse geocoding to get city from GPS
* Implement dark/light theme toggle
* Show weekly forecast using RecyclerView
* Allow saving favorite cities with SharedPreferences
