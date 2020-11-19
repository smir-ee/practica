package com.skxrb1ud.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

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
            }
        });
    }

    public void ratesBack(View v) { finish(); }
}