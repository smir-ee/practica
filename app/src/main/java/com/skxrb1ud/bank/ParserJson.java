package com.skxrb1ud.bank;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class ParserJson {
    public static ArrayList<Banks> getBanks() {
        ArrayList<Banks> banks = new ArrayList<>();
        try {
            URL url = new URL("https://api.privatbank.ua/p24api/infrastructure?json&atm&address=&city=Кременчуг");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String json = "";
                StringBuffer buffer = new StringBuffer();
                while ((json = reader.readLine()) != null) {
                    buffer.append(json);
                }
                reader.close();
                JSONObject jsonObj = new JSONObject(buffer.toString());
                JSONArray arrayBank = jsonObj.getJSONArray("devices");
                for (int i = 0; i < arrayBank.length(); i++) {
                    JSONObject days = ((JSONObject) arrayBank.get(i)).getJSONObject("tw");
                    String tw = "";
                    switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                        case 1:
                            tw = days.getString("mon");
                        case 2:
                            tw = days.getString("tue");
                        case 3:
                            tw = days.getString("wed");
                        case 4:
                            tw = days.getString("thu");
                        case 5:
                            tw = days.getString("fri");
                        case 6:
                            tw = days.getString("sun");
                        case 7:
                            tw = days.getString("hol");
                    }
                    String[] time = tw.split(" - ");
                    String address = "";
                    double lt = Double.parseDouble(((JSONObject) arrayBank.get(i)).getString("latitude"));
                    double lng = Double.parseDouble(((JSONObject) arrayBank.get(i)).getString("longitude"));
                    banks.add(new Banks(address, ((JSONObject)arrayBank.get(i)).getString("fullAddressRu"), ((JSONObject)arrayBank.get(i)).getString("placeRu"), time[0], time[1], lt, lng));
                }
            }
        } catch (Exception e) { }
        return banks;
    }

    public static void Parse (final Context context, final BanksCallback callback) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final ArrayList<Banks> list = getBanks();
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
