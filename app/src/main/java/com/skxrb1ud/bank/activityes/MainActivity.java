package com.skxrb1ud.bank.activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skxrb1ud.bank.Banks;
import com.skxrb1ud.bank.R;
import com.skxrb1ud.bank.entities.Entity_Valute;
import com.skxrb1ud.bank.parsers.Parser_xml;

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
    private static ArrayList<Entity_Valute> valuteArrayList;

    public static ArrayList<Entity_Valute> getValuteArrayList() {
        return valuteArrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView usd = (TextView) findViewById(R.id.textView_USD);
        eur = (TextView) findViewById(R.id.textView_EUR);
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLoginDialog(v);
            }
        });
        TextView date = (TextView) findViewById(R.id.textView_Date);
        String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=";
        String dateStr = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        date.setText(dateStr);

        try {
            DecimalFormat format = new DecimalFormat("0.00");
            Parser_xml parser = new Parser_xml();
            String response = new getValutes().execute(url, dateStr).get();
            if (parser.Parsing(response)){
                valuteArrayList = parser.getEntityValutes();
                for (Entity_Valute valute : valuteArrayList){
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

        final View view = findViewById(R.id.custom_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCourseActivity(v);
            }
        });
    }

    private void viewCourseActivity(View v){
        Intent intent = new Intent(this, Course.class);
        startActivity(intent);
    }

    public void viewBanksActivity(View v){
        Intent intent = new Intent(this, Banks.class);
        startActivity(intent);
    }

    public void viewLoginDialog(View v){
        Login dialog = new Login();
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