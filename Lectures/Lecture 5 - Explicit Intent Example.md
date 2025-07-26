# 📘 Lecture 5 - Explicit Intent Example

## 🎯 Learning Objectives

By the end of this lecture, students will be able to:

* Understand and implement **Explicit Intents** in Android.
* Pass data between two activities.
* Receive and display data in the second activity.
* Add an **AppBar with a back button** using `Toolbar`.

---

## 🧩 What Is an Explicit Intent?

An **explicit intent** is used to start a specific activity or service **within the same app**.

Use it when:

* You know the exact class you want to launch.
* You want to pass data to another screen (activity).

---

## 📱 App Overview

This app allows users to:

| Feature             | Description                                          |
| ------------------- | ---------------------------------------------------- |
| 📝 Enter Info       | Name, Email, and Phone in `MainActivity`             |
| 🔁 Start New Screen | Clicking "Save and Continue" starts `SecondActivity` |
| 📦 Pass Data        | User info is passed using intent extras              |
| 📋 Display Info     | `SecondActivity` shows the received data             |
| 🔙 Back Button      | AppBar back arrow returns to the previous screen     |

---

## 🖼️ UI Layouts

### `activity_main.xml` – Data Input

* 3 `EditText`s for user input
* 1 `Button` to trigger the explicit intent

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="24dp"
    android:gravity="center"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <TextView
        android:text="User Information"
        android:textSize="22sp"
        android:textStyle="bold"
        android:layout_marginBottom="16dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

    <EditText
        android:id="@+id/nameEt"
        android:hint="Enter Name"
        android:inputType="textPersonName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="12dp" />

    <EditText
        android:id="@+id/emailEt"
        android:hint="Enter Email"
        android:inputType="textEmailAddress"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="12dp" />

    <EditText
        android:id="@+id/phoneEt"
        android:hint="Enter Phone"
        android:inputType="phone"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="20dp" />

    <Button
        android:id="@+id/saveBtn"
        android:text="Save and Continue"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

</LinearLayout>
```

### `activity_second.xml` – Display Screen

* `Toolbar` for AppBar and back navigation
* `TextView` to display the passed data

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp"
    tools:context=".SecondActivity">

    <!-- Toolbar for AppBar with back button -->
    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="?attr/colorPrimary"
        android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar" />

    <TextView
        android:text="Received Info"
        android:textSize="22sp"
        android:textStyle="bold"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="20dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

    <TextView
        android:id="@+id/resultTv"
        android:textSize="18sp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textColor="#000000" />

</LinearLayout>
```

---

## 💻 Java Code Explanation

### 🔹 `MainActivity.java`

* Gathers user input
* Sends it to `SecondActivity` using an explicit intent

```java
package com.example.explicitintentexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameEt, emailEt, phoneEt;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        phoneEt = findViewById(R.id.phoneEt);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEt.getText().toString().trim();
                String email = emailEt.getText().toString().trim();
                String phone = phoneEt.getText().toString().trim();

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                startActivity(intent);
            }
        });
    }
}
```

---

### 🔹 `SecondActivity.java`

* Receives the intent
* Extracts data using `getStringExtra`
* Displays the data in a `TextView`

```java
package com.example.explicitintentexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SecondActivity extends AppCompatActivity {

    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Set up the toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Details");
        }

        resultTv = findViewById(R.id.resultTv);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");

        resultTv.setText("👤 Name: " + name + "\n📧 Email: " + email + "\n📱 Phone: " + phone);
    }

    // Handle back button press in the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close this activity and return to the previous one
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
```

---

## 🛠 Manifest Configuration

Ensure both activities are registered in `AndroidManifest.xml`:

```xml
<activity android:name=".SecondActivity" android:exported="false" />
<activity android:name=".MainActivity" android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

---

## 🔁 Full Data Flow

1. User enters data in `MainActivity`
2. Presses “Save and Continue”
3. `Intent` carries the data to `SecondActivity`
4. `SecondActivity` receives and displays the data
5. User can return using the AppBar back arrow

---

## 🧪 Testing Checklist

* ✅ Enter name/email/phone and press save
* ✅ See new screen with the correct info
* ✅ Press back arrow in the AppBar
* ✅ Return to the main screen

---

## 💡 Student Exercises

1. Add a new field for "Address" and pass it as well.
2. Show a profile image in `SecondActivity` using `ImageView`.
3. Add a `Clear` button in `MainActivity` to reset all fields.
4. Add validation to prevent starting the second activity if a field is empty.
