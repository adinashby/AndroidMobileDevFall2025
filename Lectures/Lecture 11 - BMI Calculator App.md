# ⚖️ Lecture 11: Building the BMI Calculator App in Java (Android)

## 🎯 Objective

Recreate the Flutter “BMI Calculator” app using native Android (Java), where users input their height and weight, calculate their BMI, and view the result with a health category.

---

## 🛠️ Project Overview

This project implements:

* A height/weight form with a SeekBar and custom increment/decrement buttons
* A bottom-aligned “CALCULATE” button
* BMI logic and classification
* Navigation to a results screen
* A styled UI using navy and grey color schemes with rounded corners

---

## 📂 Folder Structure

```
BMICalculatorApp/
├── java/com/example/bmicalculator/
│   ├── MainActivity.java
│   ├── InputActivity.java
│   ├── ResultsActivity.java
│   └── BMICalculator.java
├── res/
│   ├── layout/
│   │   ├── activity_input.xml
│   │   └── activity_results.xml
│   ├── drawable/
│   │   ├── round_button_background.xml
│   │   └── rounded_container.xml
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   ├── dimens.xml
│   │   └── themes.xml
├── AndroidManifest.xml
```

---

## 🔧 Step-by-Step Instructions

### ✅ Step 1: BMI Logic in `BMICalculator.java`

```java
public class BMICalculator {
    private final int height; // in cm
    private final int weight; // in kg

    public BMICalculator(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }

    public float getBMI() {
        float heightInMeters = height / 100f;
        return weight / (heightInMeters * heightInMeters);
    }

    public String getCategory() {
        float bmi = getBMI();
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Normal";
        else if (bmi < 30) return "Overweight";
        else return "Obese";
    }
}
```

---

### ✅ Step 2: Input Screen UI in `activity_input.xml`

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="24dp"
    android:background="@color/dark_navy">

    <!-- Centered form layout -->

    <!-- Bottom button -->

    <LinearLayout
        android:id="@+id/form_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_above="@+id/calculate_button"
        android:layout_alignParentEnd="true"
        android:layout_centerVertical="true"
        android:layout_marginEnd="1dp"
        android:layout_marginBottom="233dp"
        android:background="@drawable/rounded_container"
        android:gravity="center_horizontal"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="4dp"
            android:text="Height"
            android:textColor="@color/white"
            android:textSize="20sp" />

        <TextView
            android:id="@+id/height_value"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="8dp"
            android:text="170 cm"
            android:textColor="@color/white"
            android:textSize="28sp" />

        <SeekBar
            android:id="@+id/height_seekbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="24dp"
            android:max="250"
            android:progress="170" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="4dp"
            android:text="Weight"
            android:textSize="20sp"
            android:textColor="@color/white"/>

        <TextView
            android:id="@+id/weight_value"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="8dp"
            android:text="60 kg"
            android:textSize="28sp"
            android:textColor="@color/white"/>

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:orientation="horizontal">

            <ImageButton
                android:id="@+id/weight_minus"
                android:layout_width="48dp"
                android:layout_height="48dp"
                android:layout_marginEnd="8dp"
                android:background="@drawable/round_button_background"
                android:contentDescription="Decrease weight"
                android:src="@android:drawable/ic_media_previous" />

            <ImageButton
                android:id="@+id/weight_plus"
                android:layout_width="48dp"
                android:layout_height="48dp"
                android:background="@drawable/round_button_background"
                android:contentDescription="Increase weight"
                android:src="@android:drawable/ic_media_next" />
        </LinearLayout>
    </LinearLayout>

    <Button
        android:id="@+id/calculate_button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="CALCULATE"
        android:layout_alignParentBottom="true"
        android:layout_marginBottom="20dp"/>
</RelativeLayout>
```

---

### ✅ Step 3: Java Code in `InputActivity.java`

```java
import android.content.Intent;
import android.os.Bundle;
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
```

---

### ✅ Step 4: Result Screen in `ResultsActivity.java`

```java
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
```

---

### ✅ Step 5: Input Screen UI in `activity_results.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:gravity="center"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="24dp"
    android:background="@color/medium_navy">

    <TextView
        android:text="Your BMI"
        android:textSize="24sp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="@color/white"/>

    <TextView
        android:id="@+id/bmi_result"
        android:text="0.0"
        android:textSize="48sp"
        android:textStyle="bold"
        android:layout_marginTop="16dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="@color/white"/>

    <TextView
        android:id="@+id/bmi_category"
        android:text="Category"
        android:textSize="24sp"
        android:layout_marginTop="8dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="@color/white"/>
</LinearLayout>
```

---

### ✅ Step 6: Add Colors in `colors.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="white">#FFFFFF</color>
    <color name="black">#000000</color>
    <color name="teal">#008080</color>

    <color name="dark_navy">#000030</color>
    <color name="medium_navy">#001F3F</color>
</resources>
```

---

### ✅ Step 7: Add Themes in `themes.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools">
    <style name="Theme.BMICalculatorApp" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
        <item name="colorPrimary">@color/medium_navy</item>
        <item name="colorPrimaryVariant">@color/medium_navy</item>
        <item name="colorOnPrimary">@color/white</item>
        <item name="android:statusBarColor">@color/white</item>
    </style>
</resources>
```

---

### ✅ Step 8: Dimensions (`res/values/dimens.xml`)

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="padding_standard">16dp</dimen>
</resources>
```

---

### ✅ Step 9: Rounded Container Background (`res/drawable/rounded_container.xml`)

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/medium_navy" />
    <corners android:radius="16dp" />
</shape>
```

---

### ✅ Step 10: Rounded Button Background (`res/drawable/rounded_button_background.xml`)

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval">
    <solid android:color="@color/dark_navy"/>
</shape>

```

---

## 🧠 Learning Outcomes

By completing this project, students will:

* Calculate BMI based on user input
* Navigate between multiple screens in Android
* Use `SeekBar`, `ImageButton`, and styled buttons
* Design responsive UIs with RelativeLayout and LinearLayout
* Style layouts using custom drawables and color resources

---

## 🧾 Extensions (Optional)

* Show BMI range chart (e.g., normal, overweight) visually
* Save user data with `SharedPreferences`
* Add gender selection and age input
* Add animation or transition between pages
* Validate input and prevent zero/negative height or weight