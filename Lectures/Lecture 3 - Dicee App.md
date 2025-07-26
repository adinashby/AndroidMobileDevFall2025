# 🎲 Lecture 3: Building the Dicee App in Java (Android)

## 🎯 Objective
Recreate the Flutter “Dicee” app using native Android (Java), featuring two dice images that update randomly when the user taps a button.

---

## 🛠️ Project Overview

This project displays:
- Two dice images side by side
- A “Roll Dice” button
- Randomly updates dice faces on button click

---

## 📂 Folder Structure

```
DiceApp/
├── java/com/example/diceapp/
│   └── MainActivity.java
├── res/
│   ├── layout/activity_main.xml
│   ├── drawable/dice1.png to dice6.png
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   └── themes.xml
├── AndroidManifest.xml
```

---

## 🔧 Step-by-Step Instructions

### ✅ Step 1: Prepare Dice Images
Add `dice1.png` through `dice6.png` to `res/drawable/`.

### ✅ Step 2: Create `activity_main.xml`

Use `LinearLayout` for vertical stacking and horizontal alignment for dice:

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/mainLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="16dp"
    android:background="#FF0000"
    tools:context=".MainActivity">

    <!-- Horizontal container for both dice -->

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="321dp"
        android:layout_marginBottom="24dp"
        android:gravity="center"
        android:orientation="horizontal">

        <ImageView
            android:id="@+id/diceImage1"
            android:layout_width="150dp"
            android:layout_height="150dp"
            android:layout_marginBottom="24dp"
            android:layout_marginEnd="16dp"
            android:contentDescription="@string/dice_image"
            android:src="@drawable/dice1" />

        <ImageView
            android:id="@+id/diceImage2"
            android:layout_width="150dp"
            android:layout_height="150dp"
            android:layout_marginBottom="24dp"
            android:layout_marginEnd="16dp"
            android:contentDescription="@string/dice_image"
            android:src="@drawable/dice1" />

    </LinearLayout>

    <Button
        android:id="@+id/rollButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Roll Dice" />
</LinearLayout>
```

Tip: Use `layout_marginEnd` to add space between the dice.

---

### ✅ Step 3: Add Strings in `strings.xml`

```xml
<string name="app_name">Dicee</string>
<string name="dice_image">Dice Image</string>
```

---

### ✅ Step 4: Java Logic (`MainActivity.java`)

```java
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

```

---

## 🧠 Learning Outcomes

By completing this project, students will:
- Implement click listeners in Java
- Use `ImageView` to update images dynamically
- Use Java’s `Random` class to simulate dice rolls
- Design horizontal layouts using nested `LinearLayout`
- Dynamically load drawables with `getIdentifier()`
- Translate Flutter app logic into native Android Java code

---

## 🧾 Extensions (Optional)

- Add dice roll animation using `AnimationUtils`
- Display total score or toast message
- Customize background color or font
