# 📘 Lecture 2: Building the "Mi Card" App in Native Android (Java)

## 🎯 Objective
Recreate the Flutter "Mi Card" app using Java and Android Studio by displaying a user profile card with web-loaded image, custom fonts, and contact cards.

---

## 🛠️ Project Overview

This project displays:
- A circular profile photo loaded from the web
- User name and title with custom fonts
- Divider line
- Two cards: phone number and email address

---

## 📂 Folder Structure Summary

```
app/
├── java/com/example/iampoor/
│   └── MainActivity.java
├── res/
│   ├── layout/activity_main.xml
│   ├── drawable/            ← Icons, backgrounds (optional)
│   ├── font/
│   │   ├── pacifico_regular.ttf
│   │   └── sourcesanspro_regular.ttf
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   └── themes.xml
├── AndroidManifest.xml
libs.versions.toml
```

---

## 🔧 Step-by-Step Instructions

### ✅ Step 1: Setup Fonts
1. Place `Pacifico-Regular.ttf` and `SourceSansPro-Regular.ttf` inside `res/font/`.
2. Reference fonts in your XML using `android:fontFamily="@font/pacifico_regular"`.

---

### ✅ Step 2: Add Internet Permission
In `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

### ✅ Step 3: Use Glide to Load Web Image

**In `MainActivity.java`:**

```java
ImageView imageView = findViewById(R.id.profile_image);
String imageUrl = "https://example.com/me.png";

Glide.with(this)
    .load(imageUrl)
    .circleCrop()
    .into(imageView);
```

**Add dependencies (in `build.gradle.kts`):**

```kotlin
implementation("com.github.bumptech.glide:glide:4.16.0")
annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
```

Or if you're using Version Catalog:

```toml
[libraries]
glide = { group = "com.github.bumptech.glide", name = "glide", version = "4.16.0" }
glideCompiler = { group = "com.github.bumptech.glide", name = "compiler", version = "4.16.0" }
```

Then in your `dependencies {}` block:

```kotlin
implementation(libs.glide)
annotationProcessor(libs.glideCompiler)
```

---

### ✅ Step 4: Create `activity_main.xml`

- Use `LinearLayout` for vertical stacking
- Add `ImageView`, `TextView`s, `View` (for divider), and `CardView`s

---

### ✅ Step 5: Colors and Strings

**colors.xml**
```xml
<color name="teal">#009688</color>
```

**strings.xml**
```xml
<string name="app_name">Mi Card</string>
<string name="name">Adin Ashby</string>
<string name="title">FLUTTER DEVELOPER</string>
<string name="phone">+1 514 000 0000</string>
<string name="email">adin@example.com</string>
```

---

## ✅ Final Result

The app shows:
- A teal background
- A circular avatar loaded from a web URL
- Beautiful fonts for name and title
- Phone and email contact info using Material `CardView`

---

## 🧠 Learning Outcomes

By completing this project, students will:
- Understand layout creation using XML
- Integrate web image loading using Glide
- Apply custom fonts in Android
- Use `CardView`, `LinearLayout`, and `TextView` effectively
- Structure modern Android apps using Material components