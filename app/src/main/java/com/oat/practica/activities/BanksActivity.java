package com.oat.practica.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.strictmode.Violation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.oat.practica.R;
import com.oat.practica.entities.EntityBanks;
import com.oat.practica.parsers.JsonParserUA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BanksActivity extends AppCompatActivity {

    private ArrayList<EntityBanks> entityBanks;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_banks);

        String url = getResources().getString(R.string.api_UA);
        new parsingJson().execute(url);
    }



    //  Строки
    private class parsingJson extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpResponseCache cache = HttpResponseCache.install(getCacheDir(), 10000L);
                HttpURLConnection connection = (HttpURLConnection) new URL(strings[0]).openConnection();
                connection.setRequestMethod("GET");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder builder = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null)
                    builder.append(line);

                bufferedReader.close();

                return builder.toString();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JsonParserUA parserUA = new JsonParserUA();
            if (parserUA.parsingData(s))
                entityBanks = parserUA.getBanksArrayList();

            customAdapter adapter = new customAdapter(getApplicationContext());
            listView = (ListView) findViewById(R.id.listView_Banks);
            listView.setAdapter(adapter);

        }
    }

    private class customAdapter extends ArrayAdapter{

        public customAdapter(@NonNull Context context) {
            super(context, R.layout.row_bank, entityBanks);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            try {
                if (convertView == null){
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = (View) inflater.inflate(R.layout.row_bank, null);
                }

                String time = "";
                EntityBanks banks = (EntityBanks) getItem(position);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                JSONObject jsonObject = new JSONObject(banks.getTimeWork());
                switch (calendar.get(Calendar.DAY_OF_WEEK)){
                    case 2:
                        time = jsonObject.getString("mon");
                        break;
                    case 3:
                        time = jsonObject.getString("tue");
                        break;
                    case 4:
                        time = jsonObject.getString("wed");
                        break;
                    case 5:
                        time = jsonObject.getString("thu");
                        break;
                    case 6:
                        time = jsonObject.getString("fri");
                        break;
                    case 7:
                        time = jsonObject.getString("sat");
                        break;
                    case 1:
                        time = jsonObject.getString("sun");
                        break;
                }
                String[] address = banks.getAddress().split(",");
                String[] house = time.split(" - ");

                TextView status = (TextView) convertView.findViewById(R.id.textView_status_bank);

                double nowHouse = Double.parseDouble(new SimpleDateFormat("HH.mm").format(calendar.getTime()));
                if (Double.parseDouble(house[0].replace(':','.')) < nowHouse &&
                    nowHouse < Double.parseDouble(house[1].replace(':','.'))){
                    status.setText(getResources().getString(R.string.open));
                    status.setTextColor(getResources().getColor(R.color.colorGreen));
                }
                else {
                    status.setText(getResources().getString(R.string.closed));
                    status.setTextColor(getResources().getColor(R.color.colorRed));
                }

                ((TextView) convertView.findViewById(R.id.textView_worktime_bank)).setText(house[0] + "-" + house[1]);
                ((TextView) convertView.findViewById(R.id.textView_address_bank)).setText(address[4] + ", " + address[5]);
                ((TextView) convertView.findViewById(R.id.textView_type_bank)).setText(banks.getType());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }
}