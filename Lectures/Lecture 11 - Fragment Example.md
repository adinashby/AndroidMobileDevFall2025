# 📘 Lecture 11 - Fragment Example

## 🎯 Learning Objectives

By the end of this lecture, students will be able to:

* Understand the concept of **fragments** and their role in Android applications.
* Use **FragmentManager** and **FragmentTransaction** to switch fragments.
* Design a responsive layout using **ConstraintLayout**.
* Improve UI with modern XML design practices.

---

## 🧩 What Is a Fragment?

A **Fragment** is a modular section of an activity:

* It has its own lifecycle and layout.
* It is often used for UI reuse, responsiveness, and multi-pane support.

---

## 📱 App Overview

This app demonstrates switching between **two fragments** using buttons.

| Feature             | Description                            |
| ------------------- | -------------------------------------- |
| 🧩 Fragment 1       | Displays a welcome message             |
| 🧩 Fragment 2       | Displays a different message           |
| 🔁 Switch Fragments | Buttons load each fragment dynamically |
| 🎨 ConstraintLayout | Provides clean, responsive layout      |

---

## 🖼️ UI Layouts

### `activity_main.xml`

* Uses `ConstraintLayout`
* Contains:

  * A `FrameLayout` container for fragments
  * Two `Button`s to switch fragments

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/mainLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <FrameLayout
        android:id="@+id/frameLayout"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:layout_margin="16dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toTopOf="@+id/fragment1btn"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:background="@android:color/darker_gray" />

    <Button
        android:id="@+id/fragment1btn"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Show Fragment 1"
        app:layout_constraintBottom_toTopOf="@+id/fragment2btn"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginHorizontal="32dp"
        android:layout_marginBottom="16dp" />

    <Button
        android:id="@+id/fragment2btn"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Show Fragment 2"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginHorizontal="32dp"
        android:layout_marginBottom="32dp" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### `fragment_fragment1.xml` and `fragment_fragment2.xml`

* Use `LinearLayout`
* Centered `TextView` displaying fragment identity

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/teal_200"
    android:gravity="center"
    android:orientation="vertical">

    <TextView
        android:text="Welcome to Fragment 1"
        android:textSize="26sp"
        android:textStyle="bold"
        android:textColor="@android:color/white"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"/>
</LinearLayout>
```

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/purple_200"
    android:gravity="center"
    android:orientation="vertical">

    <TextView
        android:text="This is Fragment 2"
        android:textSize="26sp"
        android:textStyle="bold"
        android:textColor="@android:color/white"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"/>
</LinearLayout>
```

---

## 💻 Java Code Explanation

### 🔹 `MainActivity.java`

Initializes UI, loads Fragment 1 by default, and handles fragment switching.

```java
package com.example.fragmentsexample;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private Button fragment1btn, fragment2btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1btn = findViewById(R.id.fragment1btn);
        fragment2btn = findViewById(R.id.fragment2btn);

        // Load default fragment
        loadFragment(new Fragment1());

        fragment1btn.setOnClickListener(v -> loadFragment(new Fragment1()));
        fragment2btn.setOnClickListener(v -> loadFragment(new Fragment2()));
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}
```

> 💡 `replace()` automatically removes the previous fragment and adds the new one.

### 🔹 `Fragment1.java` and `Fragment2.java`

```java
package com.example.fragmentsexample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }
}
```

---

## 📂 Project Structure

```
com.example.fragmentapp
├── MainActivity.java
├── Fragment1.java
├── Fragment2.java
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── fragment_fragment1.xml
│   │   └── fragment_fragment2.xml
```

---

## 🛠 Manifest Configuration

Nothing additional is needed in `AndroidManifest.xml` for fragments, only the activity:

```xml
<activity android:name=".MainActivity" />
```

---

## 🔁 Fragment Transaction Flow

1. User clicks a button.
2. Activity creates a new Fragment instance.
3. Fragment replaces the previous one in the `FrameLayout`.

---

## 🎯 Key Benefits of Fragments

* UI reusability
* Improved modularity
* Support for tablet/multi-pane layouts
* Easier navigation with Navigation Components

---

## 🧪 Testing Checklist

* ✅ Fragment 1 appears by default
* ✅ Pressing “Fragment 2” shows the second fragment
* ✅ Pressing “Fragment 1” brings it back
* ✅ No app crashes on rotation

---

## 💡 Student Exercises

1. Add a third fragment and integrate it.
2. Use `BottomNavigationView` instead of buttons.
3. Animate the fragment transitions.
4. Use a `TextView` in the fragment to display time when loaded.
5. Change background color dynamically in each fragment.