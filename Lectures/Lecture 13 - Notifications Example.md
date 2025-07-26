# 📘 Lecture 13 - Notifications Example

## 🎯 Learning Objectives

By the end of this lecture, students will be able to:

* Understand how **notifications** work in Android.
* Configure **notification channels** for Android 8.0+.
* Request **runtime notification permission** (Android 13+).
* Build and trigger a **basic notification** using `NotificationCompat`.

---

## 🔔 What Is a Notification?

A **notification** is a message that Android apps display outside their UI to:

* Inform users about important events (e.g., messages, reminders).
* Allow users to open or act on the event directly.

---

## 📱 App Overview

This app demonstrates how to send a **system notification** when a user presses a button.

| Feature                 | Description                                        |
| ----------------------- | -------------------------------------------------- |
| 🛎 Notification Button  | User taps to trigger a notification                |
| 🔒 Permission Support   | Asks for POST\_NOTIFICATIONS (Android 13+)         |
| 📢 Notification Channel | Compatible with Android 8.0 and above              |
| 🎨 Modern UI            | Clean design with `ConstraintLayout` and `Toolbar` |

---

## 🖼️ UI Layout

### `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:background="@android:color/white">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="?attr/colorPrimary"
        android:title="Notification Demo"
        android:titleTextColor="@android:color/white"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <Button
        android:id="@+id/notify_btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Get Notified"
        android:backgroundTint="@android:color/holo_blue_dark"
        android:textColor="@android:color/white"
        app:layout_constraintTop_toBottomOf="@id/toolbar"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="100dp"
        />

</androidx.constraintlayout.widget.ConstraintLayout>
```

---

## 💻 Java Code Overview

### 🔹 `MainActivity.java`

```java
package com.example.notificationsexample;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "notify_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        Button notifyBtn = findViewById(R.id.notify_btn);

        createNotificationChannel();

        notifyBtn.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                            REQUEST_NOTIFICATION_PERMISSION);
                } else {
                    showNotification();
                }
            } else {
                showNotification();
            }
        });
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Hello from Android")
                .setContentText("This is a custom notification.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotifyChannel";
            String description = "Channel for notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    // Handle user response to permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showNotification();
            } else {
                Toast.makeText(this, "Notification permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
```

---

## 📂 Manifest Configuration

### `AndroidManifest.xml`

```xml
<manifest ... >

    <!-- Required for Android 13+ -->
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

    <application ... >
        <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

---

## 🔁 Notification Flow

1. User presses the **Get Notified** button.
2. App checks if **permission** is needed (Android 13+).
3. If granted, a **notification channel** is created (Android 8+).
4. The app shows a system notification using `NotificationCompat`.

---

## 🧪 Testing Checklist

* ✅ App launches without crash
* ✅ Notification appears after tapping the button
* ✅ Permission prompt appears on Android 13+
* ✅ Notification channel works on Android 8+
* ✅ App has proper toolbar and clean layout

---

## 💡 Student Exercises

1. Change the notification icon and text.
2. Add a second button to trigger a **different notification**.
3. Use `PendingIntent` to open a new activity when notification is clicked.
4. Create a **daily reminder** using `AlarmManager`.
5. Use `NotificationCompat.BigTextStyle` for longer messages.
