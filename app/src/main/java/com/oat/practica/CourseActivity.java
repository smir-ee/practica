package com.oat.practica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        // получаем экземпляр элемента ListView
        ListView listView = findViewById(R.id.listView_Course);

// определяем строковый массив
        final String[] courseValues = new String[] {
                "63", "65", "64", "63", "65", "63", "64", "65",
        };

// используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, courseValues);

        listView.setAdapter(adapter);
        TextView date = findViewById(R.id.textView_Date_CourseAct);
        date.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));

    }
}