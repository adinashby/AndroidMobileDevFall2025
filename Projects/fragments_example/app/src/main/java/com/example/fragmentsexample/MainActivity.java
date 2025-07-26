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
