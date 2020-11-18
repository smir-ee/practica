package com.skxrb1ud.bank;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skxrb1ud.bank.models.Currency;
import com.skxrb1ud.bank.runnables.CurrenciesRunnable;

import org.json.JSONArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Api{
    public static Currency[] parseCurencies(String xml){
        try {
            ArrayList<Currency> currencies = new ArrayList<Currency>();
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(xml));
            String name = null;
            Currency currency = null;
            while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if(xmlPullParser.getName() != null)
                    name = xmlPullParser.getName();
                switch (xmlPullParser.getEventType()){
                    case XmlPullParser.START_TAG:
                        if(name.equals("Valute"))
                            currency = new Currency();
                        break;
                    case XmlPullParser.END_TAG:
                        if(name.equals("Valute"))
                            currencies.add(currency);
                        break;
                    case XmlPullParser.TEXT:
                        if(name.equals("NumCode"))
                            currency.setNumCode(xmlPullParser.getText());
                        if(name.equals("Value"))
                            currency.setValue(Double.parseDouble(xmlPullParser.getText().replace(',','.')));
                        if(name.equals("Nominal"))
                            currency.setNominal(Integer.parseInt(xmlPullParser.getText()));
                        if(name.equals("Name"))
                            currency.setName(xmlPullParser.getText());
                        if(name.equals("CharCode"))
                            currency.setCharCode(xmlPullParser.getText());
                        break;
                }
                xmlPullParser.next();
            }
            Currency[] currenciesArray = new Currency[currencies.size()];
            currenciesArray = currencies.toArray(currenciesArray);
            return currenciesArray;
        }catch (Exception e){
            return null;
        }
    }
    public static void getCurrencies(final Context context, final Date date, final CurrenciesRunnable callback){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        final String dateStr = simpleDateFormat.format(date);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final String xml = GET("https://www.cbr.ru/scripts/XML_daily.asp?date_req="+dateStr);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.run(parseCurencies(xml));
                    }
                });

            }
        });
        t.start();
    }
    public static String GET(String url){
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"windows-1251"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return  response.toString();
        }catch (Exception e){
            return "";
        }

    }
}
