# 🧠 Lecture 5: Building the Quizzler App in Java (Android)

## 🎯 Objective

Recreate the Flutter “Quizzler” app using native Android (Java), where users answer true/false questions, receive instant feedback, and see their score progress.

---

## 🛠️ Project Overview

This project implements:

* A quiz interface with questions and true/false buttons
* Feedback via icons (✔️ or ❌) shown as the user answers
* A dynamic reset once all questions have been answered
* Input disabled when quiz ends, auto-enabled after reset

---

## 📂 Folder Structure

```
QuizzlerApp/
├── java/com/example/quizzlerapp/
│   ├── MainActivity.java
│   ├── Question.java
│   └── QuizBrain.java
├── res/
│   ├── layout/activity_main.xml
│   ├── drawable/
│   │   ├── correct.png
│   │   └── incorrect.png
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   └── themes.xml
├── AndroidManifest.xml
```

---

## 🔧 Step-by-Step Instructions

### ✅ Step 1: Create `Question.java`

```java
public class Question {
    private final String questionText;
    private final boolean answer;

    public Question(String questionText, boolean answer) {
        this.questionText = questionText;
        this.answer = answer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public boolean isAnswer() {
        return answer;
    }
}
```

---

### ✅ Step 2: Create `QuizBrain.java`

```java
public class QuizBrain {
    private final List<Question> questionBank;
    private int currentIndex = 0;

    public QuizBrain() {
        questionBank = new ArrayList<>();
        questionBank.add(new Question("Some cats are actually allergic to humans", true));
        questionBank.add(new Question("You can lead a cow down stairs but not up stairs.", false));
        questionBank.add(new Question("Approximately one quarter of human bones are in the feet.", true));
        questionBank.add(new Question("A slug's blood is green.", true));
        questionBank.add(new Question("Buzz Aldrin's mother's maiden name was \"Moon\".", true));
        questionBank.add(new Question("It is illegal to pee in the Ocean in Portugal.", true));
        questionBank.add(new Question("No piece of square dry paper can be folded in half more than 7 times.", false));
        questionBank.add(new Question("In London, UK, if you happen to die in the House of Parliament, you are entitled to a state funeral.", false));
        questionBank.add(new Question("The loudest sound produced by any animal is 188 decibels. That animal is the African Elephant.", false));
        questionBank.add(new Question("The total surface area of two human lungs is approximately 70 square metres.", true));
        questionBank.add(new Question("Google was originally called 'Backrub'.", true));
        questionBank.add(new Question("Chocolate affects a dog's heart and nervous system; a few ounces are enough to kill a small dog.", true));
        questionBank.add(new Question("In West Virginia, USA, if you accidentally hit an animal with your car, you are free to take it home to eat.", true));
    }

    public Question getCurrentQuestion() {
        return questionBank.get(currentIndex);
    }

    public void nextQuestion() {
        if (currentIndex < questionBank.size() - 1) currentIndex++;
    }

    public boolean hasMoreQuestions() {
        return currentIndex < questionBank.size() - 1;
    }

    public void reset() {
        currentIndex = 0;
    }
}
```

---

### ✅ Step 3: Layout UI in `activity_main.xml`

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:padding="24dp">

<TextView
    android:id="@+id/question_text"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Question Text"
    android:textSize="24sp"
    android:layout_centerHorizontal="true"
    android:layout_marginTop="70dp" />

    <LinearLayout
        android:id="@+id/score_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_above="@id/button_container"
        android:layout_marginBottom="16dp"
        android:gravity="center"
        android:orientation="horizontal" />

    <LinearLayout
        android:id="@+id/button_container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:orientation="vertical"
        android:layout_marginBottom="20dp">

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/true_button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="True"
        android:background="@color/green"
        android:layout_marginBottom="8dp" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/false_button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="False"
        android:background="@color/red"/>
    </LinearLayout>
</RelativeLayout>
```

---

### ✅ Step 4: Add Colors in `colors.xml`

```xml
<resources>
    <color name="green">#4CAF50</color>
    <color name="red">#F44336</color>
    <color name="teal">#008080</color>
    <color name="white">#FFFFFF</color>
</resources>
```

---

### ✅ Step 5: Java Logic in `MainActivity.java`

```java
public class MainActivity extends AppCompatActivity {
    private TextView questionTextView;
    private LinearLayout scoreLayout;
    private QuizBrain quizBrain;

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
        icon.setImageResource(userAnswer == correctAnswer ? R.drawable.correct : R.drawable.incorrect);
        scoreLayout.addView(icon);

        if (quizBrain.hasMoreQuestions()) {
            quizBrain.nextQuestion();
            updateQuestion();
        } else {
            Toast.makeText(this, "Quiz finished! Resetting...", Toast.LENGTH_SHORT).show();
            disableButtons();

            new Handler().postDelayed(() -> {
                scoreLayout.removeAllViews();
                quizBrain.reset();
                updateQuestion();
                enableButtons();
            }, 1500);
        }
    }

    private void disableButtons() {
        findViewById(R.id.true_button).setEnabled(false);
        findViewById(R.id.false_button).setEnabled(false);
    }

    private void enableButtons() {
        findViewById(R.id.true_button).setEnabled(true);
        findViewById(R.id.false_button).setEnabled(true);
    }
}
```

---

## 🧠 Learning Outcomes

By completing this project, students will:

* Manage quiz state and logic using Java classes
* Update UI dynamically and interactively
* Handle button enable/disable logic
* Visually represent score progression
* Translate interactive Flutter apps into native Android

---

## 🧾 Extensions (Optional)

* Show final score in a dialog before reset
* Add animations when icons appear
* Save progress using SharedPreferences
* Add support for random question order or categories