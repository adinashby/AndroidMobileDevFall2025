# 🎵 Lecture 7 - Audio Example (Using `MediaPlayer` in Android)

## 🎯 Learning Objectives

By the end of this lecture, students will be able to:

* Use the `MediaPlayer` class to play audio files in Android.
* Control playback using Play and Pause buttons.
* Add a SeekBar to scrub through the audio.
* Add a SeekBar to control system volume.
* Manage audio resources properly (e.g., release in `onDestroy()`).

---

## 🧩 App Overview

This app allows users to:

| Feature           | Description                                    |
| ----------------- | ---------------------------------------------- |
| ▶️ Play           | Starts playing a sound from the `raw/` folder  |
| ⏸ Pause           | Pauses the audio playback                      |
| 🔊 Volume Control | Adjusts the system volume using `AudioManager` |
| ⏱ Scrubbing       | Navigate within the track using a SeekBar      |

---

## 🛠 Tools & Components

* `MediaPlayer` – to play audio files.
* `AudioManager` – to manage device volume.
* `SeekBar` – for volume and scrub controls.
* `Handler` – to update the scrub SeekBar in real time.

---

## 🖼️ Layout (`activity_main.xml`)

Uses `ConstraintLayout` and contains:

* Two buttons: `Play` and `Pause`
* Two SeekBars: one for system volume, one for audio progress
* Two `TextView`s as labels for the SeekBars

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/playBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Play"
        android:onClick="play"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="48dp" />

    <Button
        android:id="@+id/pauseBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Pause"
        android:onClick="pause"
        app:layout_constraintTop_toBottomOf="@id/playBtn"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp" />

    <TextView
        android:id="@+id/volumeLabel"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Volume"
        android:textSize="18sp"
        app:layout_constraintTop_toBottomOf="@id/pauseBtn"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="32dp" />

    <SeekBar
        android:id="@+id/volumeSeekBar"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toBottomOf="@id/volumeLabel"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginHorizontal="32dp" />

    <TextView
        android:id="@+id/scrubLabel"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Playback Position"
        android:textSize="18sp"
        app:layout_constraintTop_toBottomOf="@id/volumeSeekBar"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="48dp" />

    <SeekBar
        android:id="@+id/scrubSeekbar"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toBottomOf="@id/scrubLabel"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginHorizontal="32dp" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

---

## 📂 Audio File

Place the audio file in:

```
app/src/main/res/raw/marbles.wav
```

> The file name must be **lowercase**, **no spaces**, and **no special characters**.

---

## 💻 Java Logic (`MainActivity.java`)

### 🔹 1. Initialize Components

```java
mediaPlayer = MediaPlayer.create(this, R.raw.marbles);
audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
```

### 🔹 2. Volume SeekBar

* Sets the system volume

```java
volumeSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
```

### 🔹 3. Scrub SeekBar

* Updates as audio plays
* Allows user to seek to a new position

```java
scrubSeekBar.setMax(mediaPlayer.getDuration());
mediaPlayer.seekTo(progress);  // on user drag
```

### 🔹 4. Button Actions

**Play:**

```java
public void play(View view) {
    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
        mediaPlayer.start();
        handler.post(updateScrub);
    }
}
```

**Pause:**

```java
public void pause(View view) {
    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
        mediaPlayer.pause();
    }
}
```

---

## 🔄 Playback Tracking with Handler

Updates the scrub bar every second while the audio is playing.

```java
Runnable updateScrub = new Runnable() {
    @Override
    public void run() {
        scrubSeekBar.setProgress(mediaPlayer.getCurrentPosition());
        handler.postDelayed(this, 1000);
    }
};
```

## Full Java Code

```java
package com.example.audioexample;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private SeekBar volumeSeekBar;
    private SeekBar scrubSeekBar;
    private Handler handler = new Handler();
    private Runnable updateScrub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playBtn = findViewById(R.id.playBtn);
        Button pauseBtn = findViewById(R.id.pauseBtn);
        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        scrubSeekBar = findViewById(R.id.scrubSeekbar);

        // Initialize MediaPlayer with audio from res/raw
        mediaPlayer = MediaPlayer.create(this, R.raw.marbles);

        // Setup AudioManager for volume control
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(curVolume);

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Setup scrub seekbar
        scrubSeekBar.setMax(mediaPlayer.getDuration());

        updateScrub = new Runnable() {
            @Override
            public void run() {
                scrubSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 1000);
            }
        };

        scrubSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Update the scrub seekbar when audio is playing
        mediaPlayer.setOnPreparedListener(mp -> {
            scrubSeekBar.setMax(mediaPlayer.getDuration());
        });

        mediaPlayer.setOnCompletionListener(mp -> handler.removeCallbacks(updateScrub));
    }

    // Play button click handler
    public void play(View view) {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            handler.post(updateScrub);
        }
    }

    // Pause button click handler
    public void pause(View view) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            handler.removeCallbacks(updateScrub);
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
```

---

## 🧼 Cleanup

Release resources in `onDestroy()`:

```java
@Override
protected void onDestroy() {
    if (mediaPlayer != null) {
        handler.removeCallbacks(updateScrub);
        mediaPlayer.release();
        mediaPlayer = null;
    }
    super.onDestroy();
}
```

---

## 🧪 Debug Tips

* If `MediaPlayer.create(...)` returns `null`, ensure:

  * File is in `res/raw/`
  * File name is lowercase and valid
* If audio doesn’t play:

  * Check if the emulator is muted
  * Check if file format is supported (`.wav`, `.mp3`, etc.)

---

## 🧠 Exercise for Students

1. Add a `Stop` button to completely reset playback.
2. Add a `TextView` showing current time & total time.
3. Add a second sound and toggle between the two.
4. Implement a custom audio player with `RecyclerView` to select from multiple tracks.