package com.oat.practica;

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

public class BanksParser {

    public static ArrayList<Bank> getBanks(String city) {
        ArrayList<Bank> banks = new ArrayList<>();
        try {
            URL URLBanks = new URL("https://api.privatbank.ua/p24api/infrastructure?json&atm&address=&city=" + city);
            HttpsURLConnection connection = (HttpsURLConnection)URLBanks.openConnection();
            connection.setRequestProperty("User-Agent","banks-app");
            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String json = "";
                StringBuffer buff = new StringBuffer();
                while ((json = reader.readLine()) != null) {
                    buff.append(json);
                }
                reader.close();
                json = buff.toString();
                JSONObject object = new JSONObject(json);
                JSONArray arrBanks = object.getJSONArray("devices");
                for (int i = 0; i < arrBanks.length(); i++) {
                    JSONObject days = ((JSONObject)arrBanks.get(i)).getJSONObject("tw");
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
                        String[] temp = ((JSONObject)arrBanks.get(i)).getString("fullAddressRu").split(",");
                        for (int j = 2; j < temp.length; j++) {
                            address += temp[j] + (j == temp.length - 1 ? "" : ", ");
                        }
                    } catch (Exception e) {
                        address = ((JSONObject)arrBanks.get(i)).getString("fullAddressRu");
                    }
                    String type = ((JSONObject)arrBanks.get(i)).getString("type").equals("ATM") ? "Банкомат" : "Отделение";
                    banks.add(new Bank(address, type, time[0], time[1]));
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
