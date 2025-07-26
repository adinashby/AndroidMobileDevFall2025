# 📘 Lecture 14 – Testing Example in Android (Java)

## 🎯 Learning Objectives

By the end of this lecture, students will be able to:

* Understand the difference between **unit tests** and **UI (instrumented) tests** in Android
* Use `JUnit` to test logic classes
* Use `Espresso` to test UI interactions
* Properly structure an Android project for testability
* Run tests using both Android Studio and Gradle CLI

---

## 🧪 What Is Testing in Android?

| Type      | Purpose                    | Runs On                 | Use Case                         |
| --------- | -------------------------- | ----------------------- | -------------------------------- |
| Unit Test | Test business logic        | JVM (locally)           | Functions, algorithms, utilities |
| UI Test   | Test UI interaction & flow | Emulator or real device | Button clicks, form validation   |

---

## 🧩 App Overview – String Reversal

This app contains:

* `StringUtil`: a class to reverse strings
* `MainActivity`: UI with input field, reverse button, result view
* Tests:

  * `StringUtilTest` – **unit test** for `StringUtil`
  * `MainActivityTest` – **UI test** for interaction

---

## 🖼️ UI Layout – `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:padding="24dp"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/inputField"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter text" />

    <Button
        android:id="@+id/reverseButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Reverse" />

    <TextView
        android:id="@+id/resultView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="20sp"
        android:paddingTop="12dp" />
</LinearLayout>
```

---

## 🧠 Business Logic – `StringUtil.java`

```java
package com.example.testingexample;

public class StringUtil {
    public static String reverse(String input) {
        if (input == null) return null;
        return new StringBuilder(input).reverse().toString();
    }
}
```

---

## 📱 UI Logic – `MainActivity.java`

```java
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
```

---

## ✅ Unit Testing with JUnit

📂 **File**: `src/test/java/com/example/StringUtilTest.java`

```java
package com.example.testingexample;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void testReverse_normal() {
        assertEquals("cba", StringUtil.reverse("abc"));
    }

    @Test
    public void testReverse_empty() {
        assertEquals("", StringUtil.reverse(""));
    }

    @Test
    public void testReverse_null() {
        assertNull(StringUtil.reverse(null));
    }
}
```

💡 Run with:

```bash
./gradlew testDebugUnitTest
```

Or right-click the test class in Android Studio > **Run**.

---

## ✅ UI Testing with Espresso

📂 **File**: `src/androidTest/java/com/example/MainActivityTest.java`

```java
package com.example.testingexample;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testReverseButton_displaysReversedText() {
        onView(withId(R.id.inputField)).perform(typeText("hello"), closeSoftKeyboard());
        onView(withId(R.id.reverseButton)).perform(click());
        onView(withId(R.id.resultView)).check(matches(withText("olleh")));
    }
}
```

💡 Run with:

```bash
./gradlew connectedDebugAndroidTest
```

Or right-click the test file > **Run ‘MainActivityTest’** (with emulator or device running).

---

## 🧩 Project Structure Recap

```
src/
├── main/
│   └── java/com/example/
│       ├── MainActivity.java
│       └── StringUtil.java
├── test/               ← Unit tests
│   └── StringUtilTest.java
└── androidTest/        ← UI tests
    └── MainActivityTest.java
```

---

## ⚙️ Gradle Dependencies

Ensure you have in `build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.testingexample"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.testingexample"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // App libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.ext.junit)
    implementation(libs.espresso.core)

    // Unit Testing
    testImplementation(libs.junit)

    // UI Testing (Espresso + AndroidX JUnit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
```

---

## 💡 Common Mistake

❌ Trying to run Espresso tests under `testDebugUnitTest` → results in `IllegalStateException`
✅ Always run them with `connectedDebugAndroidTest` and place them in `androidTest/`

---

## ✅ Exercises for Students

1. Add a `capitalize()` method to `StringUtil` and write tests for it.
2. Create a second UI button for capitalizing text and test its behavior.
3. Write an Espresso test that inputs multiple strings and checks multiple outputs.
