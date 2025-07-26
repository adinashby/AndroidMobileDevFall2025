# 🎵 Lecture 4: Building the Xylophone App in Java (Android)

## 🎯 Objective

Recreate the Flutter “Xylophone” app using native Android (Java), where each button plays a different musical note when tapped.

---

## 🛠️ Project Overview

This project displays:

* 7 vertically stacked colored buttons
* Each button plays a distinct `.wav` audio file
* Buttons are rounded with custom background drawables

---

## 📂 Folder Structure

```
XylophoneApp/
├── java/com/example/xylophoneapp/
│   └── MainActivity.java
├── res/
│   ├── layout/activity_main.xml
│   ├── drawable/
│   │   ├── button_red.xml to button_purple.xml
│   ├── values/
│   │   ├── strings.xml
│   │   ├── colors.xml
│   │   └── themes.xml
├── res/raw/
│   ├── note1.wav to note7.wav
├── AndroidManifest.xml
```

---

## 🔧 Step-by-Step Instructions

### ✅ Step 1: Add Audio Files

Place `note1.wav` to `note7.wav` in the `res/raw/` directory.

---

### ✅ Step 2: Create `activity_main.xml`

Stack the buttons vertically with equal weight and rounded backgrounds:

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/teal">

    <Button
        android:id="@+id/button1"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginVertical="4dp"
        android:layout_weight="1"
        android:background="@drawable/button_red"
        android:text="C"
        android:textStyle="bold"
        android:textSize="18sp"
        android:textColor="@android:color/white" />

    <!-- Repeat for buttons 2 to 7 with their respective colors and notes -->
</LinearLayout>
```

Each button uses a separate drawable resource like `@drawable/button_red`.

---

### ✅ Step 3: Create Drawable Button Backgrounds

Example: `res/drawable/button_red.xml`

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#F44336" />
    <corners android:radius="12dp" />
</shape>
```

Repeat for other colors (`orange`, `yellow`, `green`, `teal`, `blue`, `purple`).

---

### ✅ Step 4: Add Strings in `strings.xml`

```xml
<string name="note_1">Note 1</string>
<string name="note_2">Note 2</string>
<string name="note_3">Note 3</string>
<string name="note_4">Note 4</string>
<string name="note_5">Note 5</string>
<string name="note_6">Note 6</string>
<string name="note_7">Note 7</string>
```

---

### ✅ Step 5: Java Logic (`MainActivity.java`)

```java
package com.example.xylophoneapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private void playSound(int soundId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, soundId);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
        });
    }

    private void setupButton(int id, int soundResId) {
        Button btn = findViewById(id);
        btn.setOnClickListener(v -> playSound(soundResId));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButton(R.id.button1, R.raw.note1);
        setupButton(R.id.button2, R.raw.note2);
        setupButton(R.id.button3, R.raw.note3);
        setupButton(R.id.button4, R.raw.note4);
        setupButton(R.id.button5, R.raw.note5);
        setupButton(R.id.button6, R.raw.note6);
        setupButton(R.id.button7, R.raw.note7);
    }
}
```

---

## 🧠 Learning Outcomes

By completing this project, students will:

* Play `.wav` sounds using `MediaPlayer`
* Design a responsive vertical layout using `LinearLayout` with `layout_weight`
* Style buttons with rounded corners and custom colors
* Apply accessibility best practices using `contentDescription`
* Understand safe area padding and visual spacing
* Translate simple Flutter UI and logic into native Android Java

---

## 🧾 Extensions (Optional)

* Show musical note labels dynamically on press
* Add animation or sound visualizer
* Let users record custom notes