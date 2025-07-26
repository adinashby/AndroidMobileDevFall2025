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
