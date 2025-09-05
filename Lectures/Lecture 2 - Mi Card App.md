# 📘 Lecture 2: Building the "Mi Card" App in Native Android (Java)

## 🎯 Objective
Recreate the Flutter "Mi Card" app using Java and Android Studio by displaying a user profile card with web-loaded image, custom fonts, and contact cards.

---

## 🛠️ Project Overview

This project displays:
- A circular profile photo loaded from the web
- User name and title with custom fonts
- Divider line
- Two cards: phone number and email address

---

## 📂 Folder Structure Summary

```
app/
├── java/com/example/iampoor/
│   └── MainActivity.java
├── res/
│   ├── layout/activity_main.xml
│   ├── drawable/            ← Icons, backgrounds (optional)
│   ├── font/
│   │   ├── pacifico_regular.ttf
│   │   └── sourcesanspro_regular.ttf
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   └── themes.xml
├── AndroidManifest.xml
libs.versions.toml
```

---

## 🔧 Step-by-Step Instructions

### ✅ Step 1: Setup Fonts
1. Place `Pacifico-Regular.ttf` and `SourceSansPro-Regular.ttf` inside `res/font/`.
2. Reference fonts in your XML using `android:fontFamily="@font/pacifico_regular"`.

---

### ✅ Step 2: Add Internet Permission
In `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

### ✅ Step 3: Use Glide to Load Web Image

**In `MainActivity.java`:**

```java
package com.example.micardapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.profile_image);
        String imageUrl = "https://avatars.githubusercontent.com/u/47254289?s=400&u=d58131e2fa3e99eb1e36480bb5859c9e07c5c968&v=4"; // 🔗 Your image URL

        Glide.with(this)
                .load(imageUrl)
                .circleCrop() // for circular display
                .into(imageView);
    }
}
```

**Add dependencies (in `build.gradle.kts`):**

```kotlin
implementation("com.github.bumptech.glide:glide:4.16.0")
annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
```

Or if you're using Version Catalog:

```toml
[libraries]
glide = { group = "com.github.bumptech.glide", name = "glide", version = "4.16.0" }
glideCompiler = { group = "com.github.bumptech.glide", name = "compiler", version = "4.16.0" }
```

Then in your `dependencies {}` block:

```kotlin
implementation(libs.glide)
annotationProcessor(libs.glideCompiler)
```

---

### ✅ Step 4: Create `activity_main.xml`

- Use `LinearLayout` for vertical stacking
- Add `ImageView`, `TextView`s, `View` (for divider), and `CardView`s

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:background="@color/teal"
    tools:context=".MainActivity">

    <ImageView
        android:id="@+id/profile_image"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:scaleType="centerCrop"
        android:layout_marginBottom="16dp" />

    <TextView
        android:id="@+id/name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Adin Ashby"
        android:textColor="@android:color/white"
        android:textSize="36sp"
        android:fontFamily="@font/pacifico_regular" />

    <TextView
        android:id="@+id/job"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="FLUTTER DEVELOPER"
        android:textColor="@android:color/white"
        android:letterSpacing="0.2"
        android:textSize="16sp"
        android:fontFamily="@font/sourcesanspro_regular" />

    <View
        android:layout_width="200dp"
        android:layout_height="1dp"
        android:background="@android:color/white"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp" />

    <androidx.cardview.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="32dp"
        android:layout_marginBottom="16dp"
        android:elevation="4dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="16dp"
            android:orientation="horizontal">

            <ImageView
                android:layout_width="24dp"
                android:layout_height="24dp"
                android:src="@android:drawable/ic_menu_call" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="+1 123 456 7890"
                android:layout_marginStart="16dp"
                android:textColor="#000"
                android:textSize="16sp"
                android:fontFamily="@font/sourcesanspro_regular" />
        </LinearLayout>
    </androidx.cardview.widget.CardView>

    <androidx.cardview.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="32dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="16dp"
            android:orientation="horizontal">

            <ImageView
                android:layout_width="24dp"
                android:layout_height="24dp"
                android:src="@android:drawable/ic_dialog_email" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="adin@gmail.com"
                android:layout_marginStart="16dp"
                android:textColor="#000"
                android:textSize="16sp"
                android:fontFamily="@font/sourcesanspro_regular" />
        </LinearLayout>
    </androidx.cardview.widget.CardView>
</LinearLayout>
```

---

### ✅ Step 5: Colors and Strings

**colors.xml**
```xml
<color name="teal">#009688</color>
```

**strings.xml**
```xml
<string name="app_name">Mi Card</string>
<string name="name">Adin Ashby</string>
<string name="title">FLUTTER DEVELOPER</string>
<string name="phone">+1 514 000 0000</string>
<string name="email">adin@example.com</string>
```

---

## ✅ Final Result

The app shows:
- A teal background
- A circular avatar loaded from a web URL
- Beautiful fonts for name and title
- Phone and email contact info using Material `CardView`

---

## 🧠 Learning Outcomes

By completing this project, students will:
- Understand layout creation using XML
- Integrate web image loading using Glide
- Apply custom fonts in Android
- Use `CardView`, `LinearLayout`, and `TextView` effectively
- Structure modern Android apps using Material components