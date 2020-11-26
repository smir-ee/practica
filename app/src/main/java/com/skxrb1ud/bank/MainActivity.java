package com.skxrb1ud.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skxrb1ud.bank.models.EntityValute;
import com.skxrb1ud.bank.models.Login;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView eur;
    private static ArrayList<EntityValute> valuteArrayList;

    public static ArrayList<EntityValute> getValuteArrayList() {
        return valuteArrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView usd = (TextView) findViewById(R.id.usd_txt);
        eur = (TextView) findViewById(R.id.eur_txt);
        TextView date = (TextView) findViewById(R.id.current_date);
        String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=";
        String dateStr = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        date.setText(dateStr);

        try {
            DecimalFormat format = new DecimalFormat("0.00");
            ParserXml parser = new ParserXml();
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
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_currency:
                viewCourseActivity(v);
                break;
            case R.id.btn_bankomats_list:
                viewBanksActivity(v);
                break;
            case R.id.login_button:
                viewLoginDialog(v);
                break;
        }
    }

    private void viewCourseActivity(View v){
        Intent intent = new Intent(this, CurrencyActivity.class);
        startActivity(intent);
    }

    public void viewBanksActivity(View v){
        Intent intent = new Intent(this, BankomatsActivity.class);
        startActivity(intent);
    }

    public void viewLoginDialog(View v){
        Login login = new Login(this) {
            @Override
            public void run(String login, String password) {
            }
        };
        login.show();
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