package com.skxrb1ud.bank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.skxrb1ud.bank.models.EntityValute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

public class CurrencyActivity extends AppCompatActivity {
    private ArrayList<EntityValute> valuteArrayList;
    private ArrayList<EntityValute> valuteArrayListLast;
    private String dateLast;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curency);
        valuteArrayList = MainActivity.getValuteArrayList();
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            TextView date = (TextView) findViewById(R.id.current_date);
            date.setText(format.format(calendar.getTime()));

            calendar.add(Calendar.DAY_OF_MONTH, -1);
            dateLast = format.format(calendar.getTime());
            String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=";
            ParserXml parser = new ParserXml();
            if (parser.Parsing(new getValutes().execute(url, dateLast).get()))
                valuteArrayListLast = parser.getEntityValutes();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) findViewById(R.id.lv_currency);
        customAdapter adapter = new customAdapter(getApplicationContext());
        listView.setAdapter(adapter);
    }

    private class getValutes extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpResponseCache cache = HttpResponseCache.install(getCacheDir(), 100000L);
                HttpURLConnection connection = (HttpURLConnection) new URL(strings[0] + strings[1]).openConnection();

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

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnBack:
                viewMainActivity(v);
                break;
        }
    }

    private void viewMainActivity(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class customAdapter extends ArrayAdapter {
        public customAdapter(@NonNull Context context) {
            super(context, R.layout.currency_item, valuteArrayList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.currency_item, null);
            }

            EntityValute valute = (EntityValute) getItem(position);

            TextView title = (TextView) convertView.findViewById(R.id.name);
            title.setText(valute.getName());

            TextView charcode = (TextView) convertView.findViewById(R.id.charcode);
            charcode.setText(valute.getCharCode());

            TextView buy = (TextView) convertView.findViewById(R.id.buy);
            buy.setText(new DecimalFormat("0.00").format(valute.getValue() * 1.5));

            TextView sale = (TextView) convertView.findViewById(R.id.sale);
            sale.setText(new DecimalFormat("0.00").format(valute.getValue() * 1.25));

            ImageView buyArrow = (ImageView) convertView.findViewById(R.id.uparrow);
            ImageView saleArrow = (ImageView) convertView.findViewById(R.id.downarrow);

            if (valute.getValue() > valuteArrayListLast.get(position).getValue()){
                buyArrow.setRotation(0);
                saleArrow.setRotation(0);
                buyArrow.setColorFilter(getResources().getColor(R.color.bankomat_on));
                saleArrow.setColorFilter(getResources().getColor(R.color.bankomat_on));
            }
            else{
                if (valute.getValue() < valuteArrayListLast.get(position).getValue()){
                    buyArrow.setRotation(180);
                    saleArrow.setRotation(180);
                    buyArrow.setColorFilter(getResources().getColor(R.color.bankomat_off));
                    saleArrow.setColorFilter(getResources().getColor(R.color.bankomat_off));
                }
                else{
                    buyArrow.setVisibility(View.GONE);
                    saleArrow.setVisibility(View.GONE);
                }
            }

            return convertView;
        }
    }
}