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
