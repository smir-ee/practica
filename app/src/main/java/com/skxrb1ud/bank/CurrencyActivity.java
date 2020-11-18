package com.skxrb1ud.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.skxrb1ud.bank.adapters.CurrenciesAdapter;
import com.skxrb1ud.bank.models.Currency;
import com.skxrb1ud.bank.runnables.CurrenciesRunnable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CurrencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curency);
        TextView date = findViewById(R.id.current_date);
        date.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        Api.getCurrencies(this,new GregorianCalendar(2020, Calendar.NOVEMBER, 17).getTime(), new CurrenciesRunnable() {
            @Override
            public void run(Currency[] currencies) {
                RecyclerView recyclerView = findViewById(R.id.currencyList);
                recyclerView.setLayoutManager(new LinearLayoutManager(CurrencyActivity.this,RecyclerView.VERTICAL,false));
                recyclerView.setAdapter(new CurrenciesAdapter(CurrencyActivity.this,currencies));
            }
        });
    }
}