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
