package com.example.xylophoneapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xylophoneapp.R;

public class MainActivity extends AppCompatActivity {

    private void playNote(int noteId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, noteId);
        mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(v -> playNote(R.raw.note1));
        findViewById(R.id.button2).setOnClickListener(v -> playNote(R.raw.note2));
        findViewById(R.id.button3).setOnClickListener(v -> playNote(R.raw.note3));
        findViewById(R.id.button4).setOnClickListener(v -> playNote(R.raw.note4));
        findViewById(R.id.button5).setOnClickListener(v -> playNote(R.raw.note5));
        findViewById(R.id.button6).setOnClickListener(v -> playNote(R.raw.note6));
        findViewById(R.id.button7).setOnClickListener(v -> playNote(R.raw.note7));
    }
}
