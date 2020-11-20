package com.skxrb1ud.bank;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class actBanks extends AppCompatActivity {

    ListView listBanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_banks);
        listBanks = findViewById(R.id.listBanks);
        ArrayList<Bank> banks = new ArrayList<>();
        banks.add(new Bank("Омск, ул. Ленина, 32", "Банкомат", "00:00", "00:00"));
        banks.add(new Bank("Омск, ул. Жукова, 12", "Отделение", "08:00", "16:00"));
        banks.add(new Bank("Омск, ул. Маяковского, 3", "Отделение", "08:00", "16:00"));
        banks.add(new Bank("Омск, ул. 15-я Рабочая, 5", "Банкомат", "00:00", "00:00"));
        banks.add(new Bank("Омск, ул. 10 лет Октября, 51", "Отделение", "12:00", "20:00"));
        banks.add(new Bank("Омск, ул. Рождественского, 18", "Банкомат", "00:00", "00:00"));
        banks.add(new Bank("Омск, ул. Космический проспект, 20А", "Отделение","00:00", "10:00"));
        banks.add(new Bank("Омск, ул. проспект Мира, 41", "Отделение", "02:00", "10:00"));
        banks.add(new Bank("Омск, ул. Гагарина, 23", "Отделение", "10:00", "18:00"));
        banks.add(new Bank("Омск, ул. Лермонтова, 17", "Банкомат", "00:00", "00:00"));
        listBanks.setAdapter(new banksAdapter(this, banks));
    }

    public void banksBack(View v) {
        finish();
    }
}