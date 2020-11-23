package com.skxrb1ud.bank;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skxrb1ud.bank.models.Bankomat;
import com.skxrb1ud.bank.models.Currency;
import com.skxrb1ud.bank.runnables.BankomatsRunnable;
import com.skxrb1ud.bank.runnables.CurrenciesRunnable;

import org.json.JSONArray;
import org.json.JSONObject;
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
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static Bankomat[] parseBankomats(String json){
        try {
            JSONObject object = new JSONObject(json);
            JSONArray arrayOfBankomats = object.getJSONArray("devices");
            Bankomat[] bankomats = new Bankomat[arrayOfBankomats.length()];
            for(int i = 0; i < bankomats.length;i++){
                bankomats[i] = new Bankomat();
                String address = arrayOfBankomats.getJSONObject(i).getString("fullAddressRu");

                final Pattern pattern = Pattern.compile("(улица.+)", Pattern.MULTILINE);
                final Matcher matcher = pattern.matcher(address);

                if(matcher.find()){
                    address = matcher.group(1);
                }
                bankomats[i].setAddress(address);
                bankomats[i].setLatitude(arrayOfBankomats.getJSONObject(i).getDouble("latitude"));
                bankomats[i].setLongitude(arrayOfBankomats.getJSONObject(i).getDouble("longitude"));
                bankomats[i].setStatus(true);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int d = calendar.get(Calendar.DAY_OF_WEEK);
                String week = "sun";
                switch (d){
                    case 2:
                        week = "mon";
                    case 3:
                        week = "tue";
                    case 4:
                        week = "wed";
                    case 5:
                        week = "thu";
                    case 6:
                        week = "fri";
                    case 7:
                        week = "sat";
                }
                bankomats[i].setTimings(arrayOfBankomats.getJSONObject(i).getJSONObject("tw").getString(week));
            }
            return bankomats;
        }catch (Exception e){
            return null;
        }
    }
    public static void getBankomats(final Context context, final BankomatsRunnable callback){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final String json = GET("https://api.privatbank.ua/p24api/infrastructure?json&atm&address=&city=%D0%91%D1%80%D0%BE%D0%B2%D0%B0%D1%80%D1%8B","utf-8");
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.run(parseBankomats(json));
                    }
                });

            }
        });
        t.start();
    }
    public static void getCurrencies(final Context context, final Date date, final CurrenciesRunnable callback){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        final String dateStr = simpleDateFormat.format(date);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final String xml = GET("https://www.cbr.ru/scripts/XML_daily.asp?date_req="+dateStr,"windows-1251");
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
    public static String GET(String url, String charset){
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
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
