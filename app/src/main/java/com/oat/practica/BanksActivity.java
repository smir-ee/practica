package com.oat.practica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks);
        // получаем экземпляр элемента ListView
        ListView listView = findViewById(R.id.listView_Banks);

// определяем строковый массив
        final String[] bankNames = new String[] {
                "Ул. Вавилова, 32", "ул. Репина, 23", "Ул. Воробьева, 64", "Ул. Объективного, 35", "Ул. Васьки Громова, 325",
                "Ул. Гончарова, 65", "Ул. Орбакайте, 35", "Ул. Земли и Пуха, 256"
        };

// используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, bankNames);

        listView.setAdapter(adapter);
    }
}