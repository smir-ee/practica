package com.oat.practica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        // получаем экземпляр элемента ListView
        ListView listView = findViewById(R.id.listView_Course);

// определяем строковый массив
        final String[] courseValues = new String[] {
                "Тугрикк", "Доллар", "Рубль", "Тенге", "Фунт стерлингов", "Крона", "Йен", "Франки",
        };

// используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, courseValues);

        listView.setAdapter(adapter);
    }
}