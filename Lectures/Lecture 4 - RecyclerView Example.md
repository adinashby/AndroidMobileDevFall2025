# 📘 Lecture 4 - RecyclerView Example

## 🎯 Learning Objectives

By the end of this lecture, students will be able to:

* Understand what a **RecyclerView** is and why it's used.
* Create a **custom item layout** for list items.
* Implement a **RecyclerView Adapter** and bind data to views.
* Handle **click events** for individual list items.
* Use **CardView** for a modern and clean UI presentation.

---

## 📦 What Is a RecyclerView?

A `RecyclerView` is a more advanced and flexible version of `ListView`. It is used to display large data sets efficiently by:

* **Reusing (recycling)** item views
* Supporting **custom layout managers**
* Supporting **animations and item decorations**

---

## 📱 App Overview

This app demonstrates how to show a scrollable list of **month names** using `RecyclerView`.

| Feature            | Description                          |
| ------------------ | ------------------------------------ |
| 📜 RecyclerView    | Displays a scrollable list of months |
| 🖼️ Custom Layout  | Each item styled with `CardView`     |
| 🎯 Clickable Items | Toast message on click               |
| 🧰 Material Design | Includes Toolbar and modern design   |

---

## 🖼️ UI Layouts

### `activity_main.xml`

* Uses `ConstraintLayout`
* Includes a `Toolbar` and `RecyclerView`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/white"
    tools:context=".MainActivity">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="?attr/colorPrimary"
        android:title="Month List"
        android:titleTextColor="@android:color/white"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerview"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:padding="8dp"
        app:layout_constraintTop_toBottomOf="@+id/toolbar"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```

---

### `custom_design.xml`

Defines the layout of each item inside the list:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:card_view="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    card_view:cardCornerRadius="8dp"
    card_view:cardElevation="4dp"
    android:layout_margin="8dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="12dp">

        <TextView
            android:id="@+id/textNames"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="January"
            android:textSize="20sp"
            android:textColor="@android:color/black"
            android:textStyle="bold"
            android:gravity="center"/>

    </LinearLayout>
</androidx.cardview.widget.CardView>
```

---

## 💻 Java Code Explanation

### 🔹 `MainActivity.java`

Handles UI initialization and sets up the adapter:

```java
package com.example.recyclerviewexample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Month List");

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Data source
        List<String> months = Arrays.asList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );

        // Adapter with item click listener
        adapter = new RecyclerAdapter(months, item -> {
            Toast.makeText(MainActivity.this, "Clicked: " + item, Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }
}
```

---

### 🔹 `RecyclerAdapter.java`

Creates item views and binds data:

```java
package com.example.recyclerviewexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    private List<String> items;
    private OnItemClickListener listener;

    public RecyclerAdapter(List<String> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textNames);
        }

        public void bind(String item, OnItemClickListener listener) {
            textView.setText(item);
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_design, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
```

---

## 📂 Project Structure

```
com.example.recyclerviewapp
├── MainActivity.java
├── RecyclerAdapter.java
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   └── custom_design.xml
│   ├── values/
│   │   ├── colors.xml
│   │   └── styles.xml
```

---

## 🛠 Manifest Configuration

Ensure your manifest declares the activity:

```xml
<activity android:name=".MainActivity" />
```

---

## 🔁 RecyclerView Workflow

1. `MainActivity` sets up `RecyclerView` and adapter.
2. Adapter inflates `custom_design.xml` for each item.
3. Data is bound to each `TextView`.
4. Click listener shows `Toast`.

---

## 🧪 Testing Checklist

* ✅ List displays all months
* ✅ Clicking an item shows a Toast
* ✅ Scroll works smoothly
* ✅ No layout overflow or clipping
* ✅ Works in portrait and landscape

---

## 💡 Student Exercises

1. Replace months with student names.
2. Add a second `TextView` for a description under each month.
3. Use `GridLayoutManager` for a 2-column layout.
4. Implement a long-click listener that removes the item.
5. Create a detail screen and use `Intent` to navigate on click.