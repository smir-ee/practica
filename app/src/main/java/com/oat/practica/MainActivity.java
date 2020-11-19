package com.oat.practica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    private TextView usd;
    private TextView eur;
    private static ArrayList<EntityValute> valuteArrayList;

    public static ArrayList<EntityValute> getValuteArrayList() {
        return valuteArrayList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        usd = (TextView) findViewById(R.id.textView_USD);
        eur = (TextView) findViewById(R.id.textView_EUR);

        TextView date = (TextView) findViewById(R.id.textView_Date);
        String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=";
        String dateStr = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        date.setText(dateStr);

        try {
            DecimalFormat format = new DecimalFormat("0.00");
            XmlParser parser = new XmlParser();
            String response = new getValutes().execute(url, dateStr).get();
            if (parser.Parsing(response)){
                valuteArrayList = parser.getEntityValutes();
                for (EntityValute valute : valuteArrayList){
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

        final View view = findViewById(R.id.course_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCourse(v);
            }
        });
    }
    public void onClickBank (View view){
        Intent i = new Intent(MainActivity.this, BanksActivity.class);
        startActivity(i);
    }
    public void onClickCourse (View view){
        Intent i = new Intent(MainActivity.this, CourseActivity.class);
        startActivity(i);
    }
    public void onClickLogin (View view){
        //Intent i = new Intent(MainActivity.this, LoginActivity.class);
        //startActivity(i);
        CustomDialog dialog = new CustomDialog();
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