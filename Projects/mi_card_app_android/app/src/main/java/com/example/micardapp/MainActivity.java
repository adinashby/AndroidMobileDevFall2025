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