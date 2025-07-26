package com.example.listviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        arrayList.add("D");
        arrayList.add("E");
        arrayList.add("F");
        arrayList.add("G");
        arrayList.add("G");
        arrayList.add("G");
        arrayList.add("G");
        arrayList.add("G");
        arrayList.add("G");
        arrayList.add("G");
        arrayList.add("G");
        arrayList.add("G");
        arrayList.add("G");


        ArrayAdapter arrayAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);


        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(MainActivity.this, " clicked item: " + i
                    + " " + arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();

            arrayList.remove(i);
            arrayAdapter.notifyDataSetChanged();
        });

    }
}