# 📘 Lecture 1: Creating a Simple Android App in Java – “I Am Rich”

## 🎯 Objective
Learn how to build a basic Android app in Java that mimics the Flutter “I Am Rich” app by displaying an image and customizing the ActionBar and background.

---

## 📂 Project Structure Overview

```
IAmRichApp/
├── res/
│   ├── layout/
│   │   └── activity_main.xml       ← Main UI layout
│   ├── values/
│   │   ├── strings.xml             ← App text resources
│   │   ├── colors.xml              ← Color definitions
│   │   └── themes.xml              ← App theme customization
│   ├── drawable/
│   │   └── diamond.png             ← Diamond image
├── java/
│   └── com/example/iamrichapp/
│       └── MainActivity.java       ← Main logic
├── AndroidManifest.xml             ← App configuration
```

---

## 🧱 UI Design (`activity_main.xml`)

```xml
<RelativeLayout ... android:background="@color/teal">
    <ImageView
        android:id="@+id/diamond_image"
        android:layout_width="250dp"
        android:layout_height="250dp"
        android:layout_centerInParent="true"
        android:src="@drawable/diamond"
        android:contentDescription="@string/diamond_desc"/>
</RelativeLayout>
```

- **Background color:** Teal
- **ImageView:** Displays a centered diamond image

---

## 🎨 Colors (`colors.xml`)

```xml
<resources>
    <color name="darkTeal">#00695C</color>   <!-- ActionBar -->
    <color name="teal">#009688</color>       <!-- Screen background -->
</resources>
```

---

## 🎨 Themes (`themes.xml`)

```xml
<style name="Theme.MyRichApp" parent="Theme.AppCompat.Light.DarkActionBar">
    <item name="colorPrimary">@color/darkTeal</item>
    <item name="colorPrimaryDark">@color/darkTeal</item>
    <item name="android:actionBarStyle">@style/MyActionBar</item>
</style>

<style name="MyActionBar" parent="@style/Widget.AppCompat.ActionBar">
    <item name="background">@color/darkTeal</item>
</style>
```

- Customizes the system ActionBar to use a dark teal background

---

## 🔤 Strings (`strings.xml`)

```xml
<resources>
    <string name="app_name">I Am Rich</string>
    <string name="diamond_desc">Diamond Image</string>
</resources>
```

---

## 🧠 Logic (`MainActivity.java`)

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Optional: Set a custom title
        setTitle("I Am Rich");
    }
}
```

- Launches the layout
- Sets the ActionBar title (or inherits from AndroidManifest.xml)

---

## 🧾 Manifest (`AndroidManifest.xml`)

```xml
<activity
    android:name=".MainActivity"
    android:label="@string/app_name"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
    </intent-filter>
</activity>
```

- Declares the main entry point and sets the activity label (title)

---

## ✅ Summary

| Component         | Purpose                              |
|------------------|--------------------------------------|
| `activity_main.xml` | Layout with background + image       |
| `colors.xml`      | Defines theme and layout colors      |
| `themes.xml`      | Applies custom ActionBar style       |
| `MainActivity.java` | Binds layout and sets title          |
| `AndroidManifest.xml` | Declares activity and launch config |

## 🧠 Learning Outcomes

By completing this project, students will:
- Understand the basic structure of an Android app using Java
- Create layouts using `RelativeLayout` and center an image with `ImageView`
- Apply background and theme colors using `colors.xml` and `themes.xml`
- Customize the ActionBar appearance using Android themes and styles
- Use `strings.xml` for cleaner, maintainable text resources
- Configure `AndroidManifest.xml` properly for application setup
- Translate a basic Flutter UI into its native Android Java equivalent
