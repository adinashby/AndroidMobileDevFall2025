package com.example.quizzlerapp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView questionTextView;
    private LinearLayout scoreLayout;
    private QuizBrain quizBrain;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quizBrain = new QuizBrain();

        questionTextView = findViewById(R.id.question_text);
        scoreLayout = findViewById(R.id.score_layout);

        updateQuestion();

        findViewById(R.id.true_button).setOnClickListener(v -> checkAnswer(true));
        findViewById(R.id.false_button).setOnClickListener(v -> checkAnswer(false));
    }

    private void updateQuestion() {
        questionTextView.setText(quizBrain.getCurrentQuestion().getQuestionText());
    }

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = quizBrain.getCurrentQuestion().isAnswer();

        ImageView icon = new ImageView(this);
        icon.setImageResource(userAnswer == correctAnswer ? android.R.drawable.checkbox_on_background : android.R.drawable.ic_delete);
        scoreLayout.addView(icon);

        if (quizBrain.hasMoreQuestions()) {
            quizBrain.nextQuestion();
            updateQuestion();
        } else {
            Toast.makeText(this, "Quiz finished! Resetting...", Toast.LENGTH_SHORT).show();

            // Disable buttons
            findViewById(R.id.true_button).setEnabled(false);
            findViewById(R.id.false_button).setEnabled(false);

            // Delay reset so user can see the last result
            new Handler().postDelayed(() -> {
                scoreLayout.removeAllViews();
                quizBrain.reset();
                updateQuestion();

                // Re-enable buttons after reset
                findViewById(R.id.true_button).setEnabled(true);
                findViewById(R.id.false_button).setEnabled(true);

            }, 1500); // 1.5 seconds delay
        }
    }

    public void reset() {
        currentIndex = 0;
    }

}
