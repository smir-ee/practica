package com.morlag.wsbank.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.morlag.wsbank.R;
import com.morlag.wsbank.adapters.CurrencyAdapter;
import com.morlag.wsbank.models.Valute;
import com.morlag.wsbank.utils.Api;
import com.morlag.wsbank.utils.URLContentFetcher;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CurrenciesActivity extends AppCompatActivity {
    public static final String TAG = "CurrenciesActivity";
    // Адрес получения xml информации
    public static final Uri uri = new Uri.Builder()
            .scheme("http")
            .encodedAuthority("www.cbr.ru")
            .appendPath("scripts")
            .appendPath("XML_daily.asp")
            .build();

    TextView date;
    RecyclerView mRecyclerView;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies);
        setTitle("Главный экран");

        loading = findViewById(R.id.loading);
        mRecyclerView = findViewById(R.id.recycler);
        // Вызов класса для создания асинхронного потока
        Api api = new Api(this,new Handler(getMainLooper()));
        api.obtainCurrencies(new Api.ParseResultReciever<Valute>() {
            @Override
            public void handleResult(ArrayList<Valute> result) {
                setCurrencyData(result);
            }
        });

        // Установка даты
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        date = findViewById(R.id.dateLabel);
        date.setText(format.format(now));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setCurrencyData(ArrayList<Valute> valutes){
        loading.setVisibility(View.GONE); // Выключаем загрузку
        CurrencyAdapter adapter = new CurrencyAdapter(this,valutes);
        mRecyclerView.setAdapter(adapter); // Создаем и присваеваем адаптер списку
    }
}