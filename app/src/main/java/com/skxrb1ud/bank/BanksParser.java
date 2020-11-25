package com.skxrb1ud.bank;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

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
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class BanksParser {

    public static ArrayList<Bank> getBanks(String city) {
        ArrayList<Bank> banks = new ArrayList<>();
        try {
            URL URLBanks = new URL("https://api.privatbank.ua/p24api/infrastructure?json&atm&address=&city=" + city);
            HttpsURLConnection connection = (HttpsURLConnection)URLBanks.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String json = "";
                StringBuffer buff = new StringBuffer();
                while ((json = reader.readLine()) != null) {
                    buff.append(json);
                }
                reader.close();
                JSONObject object = new JSONObject(buff.toString());
                JSONArray arrBanks = object.getJSONArray("devices");
                for (int i = 0; i < arrBanks.length(); i++) {
                    JSONObject bank = arrBanks.getJSONObject(i);
                    JSONObject days = bank.getJSONObject("tw");
                    String tw = "";
                    switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                        case 1:
                            tw = days.getString("mon");
                            break;
                        case 2:
                            tw = days.getString("tue");
                            break;
                        case 3:
                            tw = days.getString("wed");
                            break;
                        case 4:
                            tw = days.getString("thu");
                            break;
                        case 5:
                            tw = days.getString("fri");
                            break;
                        case 6:
                            tw = days.getString("sat");
                            break;
                        case 7:
                            tw = days.getString("sun");
                        break;
                    }
                    String[] time = tw.split(" - ");
                    String address = "";
                    try {
                        String[] temp = bank.getString("fullAddressRu").split(",");
                        for (int j = 2; j < temp.length; j++) {
                            address += temp[j] + (j == temp.length - 1 ? "" : ", ");
                        }
                    } catch (Exception e) {
                        address = bank.getString("fullAddressRu");
                    }
                    String type = bank.getString("type").equals("ATM") ? "Банкомат" : "Отделение";
                    double lt = Double.parseDouble(bank.getString("latitude"));
                    double lng = Double.parseDouble(bank.getString("longitude"));
                    banks.add(new Bank(address, bank.getString("fullAddressRu"), bank.getString("placeRu"), type, time[0], time[1], lt, lng));
                }
            }
        } catch (Exception e) { }
        return banks;
    }

    public static void Parse (final String city, final Context context, final BanksCallback callback) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final ArrayList<Bank> list = getBanks(city);
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.run(list);
                        }
                    });
                } catch (Exception e) { }
            }
        });
    }
}
