package com.oat.practica.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oat.practica.R;
import com.oat.practica.dialogs.CustomDialog;
import com.oat.practica.entities.EntityValute;
import com.oat.practica.interfaces.tryUserData;
import com.oat.practica.parsers.XmlParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements tryUserData {

    private String login;
    private String password;

    private TextView usd;
    private TextView eur;
    private static ArrayList<EntityValute> valuteArrayList;

    public static ArrayList<EntityValute> getValuteArrayList() {
        return valuteArrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        usd = (TextView) findViewById(R.id.textView_USD);
        eur = (TextView) findViewById(R.id.textView_EUR);

        TextView date = (TextView) findViewById(R.id.textView_Date);
        String dateStr = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        date.setText(dateStr);

        try {
            DecimalFormat format = new DecimalFormat("0.00");
            XmlParser parser = new XmlParser();
            String response = new getValutes().execute(getResources().getString(R.string.server_CB), dateStr).get();
            if (parser.Parsing(response)){
                valuteArrayList = parser.getEntityValutes();
                for (EntityValute valute : valuteArrayList){
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
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    public void viewBanksActivity(View v){
        Intent intent = new Intent(this, BanksActivity.class);
        startActivity(intent);
    }

    public void viewLoginDialog(View v){
        CustomDialog dialog = new CustomDialog();
        dialog.show(getSupportFragmentManager(),"Login");
    }

    @Override
    public void postUserData(String... strings) {
        try{
            JSONObject jsonObject = new JSONObject();
            login = strings[0];
            password = strings[1];
            jsonObject.put("login", login);
            jsonObject.put("password", password);
            new postData().execute("/login", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class getValutes extends AsyncTask<String, Void, String>{

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

    private class postData extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.progress_bar_main).setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                String url = getResources().getString(R.string.Local_server);
                HttpResponseCache cache = HttpResponseCache.install(getCacheDir(), 10000L);
                HttpURLConnection connection = (HttpURLConnection) new URL(url + strings[0]).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-type", "application/json; UTF-8");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(strings[1].getBytes());
                outputStream.flush();
                outputStream.close();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                    builder.append(line);

                bufferedReader.close();
                connection.disconnect();

                return builder.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            findViewById(R.id.progress_bar_main).setVisibility(View.GONE);
            try{
                if (s != null){
                    JSONObject jsonObject = new JSONObject(s);

                    Intent intent = new Intent(getApplicationContext(), ActivityProfile.class);
                    intent.putExtra("fio", jsonObject.getString("fio"));
                    intent.putExtra("token", jsonObject.getString("token"));
                    intent.putExtra("login", login);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Неправильно введены данные", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}