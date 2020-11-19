package com.skxrb1ud.bank;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class ratesParser {

    static ArrayList<Rate> getRatesForDate(String date) {
        ArrayList<Rate> rates = new ArrayList<Rate>();
        try {
            URL cbrf = new URL("https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date);
            HttpsURLConnection connection = (HttpsURLConnection)cbrf.openConnection();
            connection.setRequestProperty("User-Agent","banks-app");
            if (connection.getResponseCode() == 200) {
                InputStream response_body = connection.getInputStream();
                InputStreamReader response_reader = new InputStreamReader(response_body, "windows-1251");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response_reader);

                String tmp = "";
                Rate r = new Rate();
                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if(xpp.getName() != null) tmp = xpp.getName();
                    switch (xpp.getEventType()) {
                        case XmlPullParser.START_TAG:
                            if(tmp.equals("Valute"))
                                r = new Rate();
                            break;
                        case XmlPullParser.TEXT:
                            if(tmp.equals("Name"))
                                r.Name = xpp.getText();
                            if(tmp.equals("CharCode"))
                                r.Code = xpp.getText();
                            if(tmp.equals("Nominal"))
                                r.Nominal = Integer.parseInt(xpp.getText());
                            if(tmp.equals("Value"))
                                r.Price = Float.parseFloat(xpp.getText().replace(',','.'));
                            break;
                        case XmlPullParser.END_TAG:
                            if(tmp.equals("Valute"))
                                rates.add(r);
                            break;
                    }
                    xpp.next();
                }
            }
        } catch (Exception e) { }
        return rates;
    }
    static ArrayList<Rate> rates = new ArrayList<Rate>();

    public static void Parse (final Calendar date, final Context context, final RatesCallback callback) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    ArrayList<Rate> today = getRatesForDate(df.format(date.getTime()));
                    date.add(Calendar.DATE, -1);
                    ArrayList<Rate> yesterday = getRatesForDate(df.format(date.getTime()));
                    for (int i = 0; i < today.size(); i++) {
                        for (int j = 0; j < today.size(); j++) {
                            Rate r1 = today.get(i);
                            Rate r2 = yesterday.get(j);
                            if (r1.Code.equals(r2.Code)) {
                                r1.isSellUP = r1.Price > r2.Price;
                                r1.isBuyUP = r1.Price * r1.k > r2.Price * r2.k;
                            }
                        }
                    }
                    rates = today;
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.run(rates);
                        }
                    });
                } catch (Exception e) { }
            }
        });
    }
}
