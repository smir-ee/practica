package com.oat.practica;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class rates extends AppCompatActivity {

    Calendar c = Calendar.getInstance();
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);
        list = findViewById(R.id.listRates);
        ratesParser.Parse(c, this, new RatesCallback() {
            @Override
            public void run(ArrayList<Rate> rates) {
                list.setAdapter(new RatesAdapter(rates.this, rates));
                ((ProgressBar)findViewById(R.id.ratesLoading)).setVisibility(View.GONE);
            }
        });
    }

    public void ratesBack(View v) { finish(); }
}