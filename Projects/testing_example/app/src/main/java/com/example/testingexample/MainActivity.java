package com.example.testingexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    Button reverseButton;
    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        reverseButton = findViewById(R.id.reverseButton);
        resultView = findViewById(R.id.resultView);

        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputField.getText().toString();
                String reversed = StringUtil.reverse(input);
                resultView.setText(reversed);
            }
        });
    }
}
