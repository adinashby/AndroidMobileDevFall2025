# 📘 Lecture 8 - SQL Example (Local Database with SQLite)

## 🎯 Learning Objectives

By the end of this lesson, students will be able to:

* Use `SQLiteOpenHelper` to manage a local SQLite database.
* Perform basic database operations (Insert, Update, Delete, View).
* Connect a database helper class to a UI built with XML.
* Handle button interactions to manipulate persistent data.

---

## 🛠 Tools Used

* **Java**
* **SQLite (via `SQLiteOpenHelper`)**
* **Android Studio**
* **XML UI layout (`RelativeLayout`)**

---

## 🧩 App Overview

This app allows users to:

* Insert a new record (Name, Contact, DOB)
* Update a record (based on Name)
* Delete a record (based on Name)
* View all records (in a dialog)

---

## 🖼️ UI Design (`activity_main.xml`)

* Layout: `RelativeLayout`
* Widgets used:

  * `EditText` for Name, Contact, and Date of Birth
  * `Button` for Insert, Update, Delete, View
* Example:

```xml
<EditText
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/textTitle"
        android:text="Please enter the details below"
        android:textSize="24sp"
        android:layout_marginTop="20dp"/>

    <EditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/name"
        android:hint="Name"
        android:textSize="24sp"
        android:layout_below="@id/textTitle"
        android:inputType="textPersonName"
        />

    <EditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/contact"
        android:hint="Contact"
        android:textSize="24sp"
        android:layout_below="@id/name"
        android:inputType="number"
        />

    <EditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/dob"
        android:hint="Date of Birth"
        android:textSize="24sp"
        android:layout_below="@id/contact"
        android:inputType="number"
        />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btnInsert"
        android:text="Insert New Data"
        android:textSize="24sp"
        android:layout_marginTop="30dp"
        android:layout_below="@+id/dob"
        />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btnUpdate"
        android:text="Update Data"
        android:textSize="24sp"
        android:layout_below="@+id/btnInsert"
        />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btnDelete"
        android:text="Delete Existing Data"
        android:textSize="24sp"
        android:layout_below="@+id/btnUpdate"
        />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btnView"
        android:text="View Data"
        android:textSize="24sp"
        android:layout_below="@+id/btnDelete"
        />


</RelativeLayout>
```

---

## 🧠 Core Logic

### 🔹 1. `DBHelper.java`

* Inherits from `SQLiteOpenHelper`
* Handles:

  * `onCreate()`: Creates table
  * `insertUserData()`
  * `updateUserData()`
  * `deleteUserData()`
  * `getData()`

#### Table Structure

```sql
package com.example.sqlexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE UserDetails(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, contact TEXT, dob TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserDetails");
        onCreate(db);
    }

    public Boolean insertUserData(String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        long result = DB.insert("Userdetails", null, contentValues);

        Log.i("info", String.valueOf(result));

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateUserData(String keyName, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("contact", contact);
        contentValues.put("dob", dob);

        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[] {keyName});

        if(cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "name=?", new String[]{keyName});

            if(result == -1) {
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    public Boolean deleteData(String keyName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[] {keyName});

        if(cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "name=?", new String[]{keyName});

            if(result == -1) {
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    public Cursor getData() {
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);

        return cursor;
    }
}
```

---

### 🔹 2. `MainActivity.java`

* Connects UI components with database logic
* Gets user input from `EditText` fields
* Sets `OnClickListener` for each button

#### Example: Insert Logic

```java
package com.example.sqlexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name, contact, dob;
    Button insert, update, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);

        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();

                Boolean checkInsertData = DB.insertUserData(nameTXT, contactTXT, dobTXT);

                if(checkInsertData == true) {
                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();

                Boolean checkUpdateData = DB.updateUserData(nameTXT, contactTXT, dobTXT);

                if(checkUpdateData == true) {
                    Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Entry Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                Boolean checkDeleteData = DB.deleteData(nameTXT);

                if(checkDeleteData == true) {
                    Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getData();

                if(res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while(res.moveToNext()) {
                    buffer.append("Name : " + res.getString(0) + "\n");
                    buffer.append("Contact : " + res.getString(1) + "\n");
                    buffer.append("Date of Birth : " + res.getString(2) + "\n\n");
                }

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}
```

---

## 🔄 Full Flow Summary

| Step | Action                         | Component   | Outcome                             |
| ---- | ------------------------------ | ----------- | ----------------------------------- |
| 1    | User enters Name, Contact, DOB | `EditText`  | Inputs are ready                    |
| 2    | Clicks "Insert New Data"       | `btnInsert` | Data saved in DB                    |
| 3    | Clicks "Update Data"           | `btnUpdate` | Updates based on `name`             |
| 4    | Clicks "Delete Existing Data"  | `btnDelete` | Deletes record with matching `name` |
| 5    | Clicks "View Data"             | `btnView`   | Dialog shows all entries            |

---

## 🧪 Emulator Debug Tip

To view the actual SQLite database:

1. Open **Device File Explorer**
2. Navigate to:

```
/data/data/com.example.sqlexample/databases/UserDetails
```

3. Download and inspect it using **DB Browser for SQLite**

---

## 🧼 Cleanup & Caution

* **Uninstall** the app after schema changes to reset the DB.
* **Avoid using names as Primary Keys.** Use `id` instead.

---

## ✅ Exercises for Students

1. Add a search functionality for a specific name.
2. Add email field to the form and database.
3. Replace `Toast` with `Snackbar`.
4. Show the View results in a `RecyclerView` instead of a Dialog.