package com.example.bezmetnyybank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bezmetnyybank.entities.ValuteEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private TextView eur;
    private static ArrayList<ValuteEntity> valuteArrayList;

    public static ArrayList<ValuteEntity> getValuteArrayList() {
        return valuteArrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnSign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLoginDialog(v);
            }
        });

        findViewById(R.id.btnOffices).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBanksActivity(v);
            }
        });

        TextView usd = (TextView) findViewById(R.id.textView_USD);
        eur = (TextView) findViewById(R.id.textView_EUR);

        TextView date = (TextView) findViewById(R.id.textView_Date);
        String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=";
        String dateStr = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        date.setText(dateStr);

        try {
            DecimalFormat format = new DecimalFormat("0.00");
            xml_parser parser = new xml_parser();
            String response = new getValutes().execute(url, dateStr).get();
            if (parser.Parsing(response)){
                valuteArrayList = parser.getEntityValutes();
                for (ValuteEntity valute : valuteArrayList){
                    String cha = valute.getCharCode();
                    if (valute.getCharCode().equalsIgnoreCase("USD"))
                        usd.setText(format.format(valute.getValue()));
                    if (valute.getCharCode().equalsIgnoreCase("EUR"))
                        eur.setText(format.format(valute.getValue()));
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        final View view = findViewById(R.id.button_enjoy);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCourseActivity(v);
            }
        });
    }

    private void viewCourseActivity(View v){
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    public void viewBanksActivity(View v){
        Intent intent = new Intent(this, Bank.class);
        startActivity(intent);
    }

    public void viewLoginDialog(View v){
        Authorization dialog = new Authorization();
        dialog.show(getSupportFragmentManager(),"Login");
    }



    private class getValutes extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpResponseCache cache = HttpResponseCache.install(getCacheDir(), 100000L);
                String url = strings[0] + strings[1];
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

                StringBuilder builder = new StringBuilder();
                if (connection.getResponseCode() == 200){
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "windows-1251"));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null)
                        builder.append(line);
                    return builder.toString();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}