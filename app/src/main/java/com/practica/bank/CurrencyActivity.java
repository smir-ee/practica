package com.practica.bank;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.practica.bank.adapters.CurrenciesAdapter;
import com.practica.bank.models.Currency;
import com.practica.bank.runnables.CurrenciesRunnable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curency);
        findViewById(R.id.content).setVisibility(View.INVISIBLE);
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
        TextView date = findViewById(R.id.current_date);
        date.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        Api.getCurrencies(this,new Date(), new CurrenciesRunnable() {
            @Override
            public void run(Currency[] currencies) {
                final Currency[] currenciesNow = currencies;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE,-1);
                Api.getCurrencies(CurrencyActivity.this,calendar.getTime(), new CurrenciesRunnable() {
                    @Override
                    public void run(Currency[] currencies) {
                        RecyclerView recyclerView = findViewById(R.id.currencyList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CurrencyActivity.this,RecyclerView.VERTICAL,false));
                        recyclerView.setAdapter(new CurrenciesAdapter(CurrencyActivity.this,currenciesNow,currencies));
                        findViewById(R.id.content).setVisibility(View.VISIBLE);
                        findViewById(R.id.loading).setVisibility(View.INVISIBLE);

                    }
                });
            }
        });
    }
}