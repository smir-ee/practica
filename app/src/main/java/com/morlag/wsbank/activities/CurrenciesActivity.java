package com.morlag.wsbank.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.morlag.wsbank.R;
import com.morlag.wsbank.adapters.CurrencyAdapter;
import com.morlag.wsbank.models.Valute;
import com.morlag.wsbank.utils.URLContentFetcher;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CurrenciesActivity extends AppCompatActivity {
    public static final String TAG = "CurrenciesActivity";
    // Адрес получения xml информации
    public static final Uri uri = new Uri.Builder()
            .scheme("http")
            .encodedAuthority("www.cbr.ru")
            .appendPath("scripts")
            .appendPath("XML_daily.asp")
            .build();

    TextView date;
    RecyclerView mRecyclerView;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies);
        setTitle("Главный экран");

        // Вызов класса для создания асинхронного потока
        CurrencyFetcher fetcher = new CurrencyFetcher();
        fetcher.execute();

        // Установка даты
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        date = findViewById(R.id.dateLabel);
        date.setText(format.format(now));

        loading = findViewById(R.id.loading);
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setCurrencyData(ArrayList<Valute> valutes, String date){
        //this.date.setText(date);
        loading.setVisibility(View.GONE); // Выключаем загрузку
        CurrencyAdapter adapter = new CurrencyAdapter(this,valutes);
        mRecyclerView.setAdapter(adapter); // Создаем и присваеваем адаптер списку
    }
    // Метод для парсинга курса валют
    private String getValutes(String source, ArrayList<Valute> result){
        String date = null;
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xpp = xmlPullParserFactory.newPullParser();
            xpp.setInput(new StringReader(source));

            xpp.next();
            date = xpp.getAttributeValue(0);
            while(xpp.getEventType() != XmlPullParser.END_DOCUMENT){
                if(xpp.next() == XmlPullParser.START_TAG && xpp.getName().equals("Valute")){
                    Valute v = new Valute();
                    v.setID(xpp.getAttributeValue(0));
                    v.setNumCode(getNextText(xpp));
                    v.setCharCode(getNextText(xpp));
                    v.setNominal(getNextText(xpp));
                    v.setName(getNextText(xpp));
                    v.setValue(getNextText(xpp));
                    result.add(v);
                }
            }
        }
        catch (XmlPullParserException ex){
            Log.d(TAG, "onCreate: XmlPullParserException", ex);
        }
        catch (Exception ex){
            Log.d(TAG, "onCreate: ", ex);
        }
        return date;
    }
    // Вспомогательный метод для парсинга
    private String getNextText(XmlPullParser xpp) throws Exception{
        xpp.nextTag(); xpp.next();
        String result = (xpp.getText());
        xpp.nextTag();
        return result;
    }
    // Класс для асинхронного получения результата запроса
    class CurrencyFetcher extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... voids) {
            String xml = null;
            try {
                xml = new URLContentFetcher().getString(CurrenciesActivity.this,uri,"GET",true);
            }
            catch (Exception ex){
                Log.d(TAG, "doInBackground: ",ex);
            }

            return xml;
        }

        // Синхронный метод, выполняющийся после выполнения запроса
        @Override
        protected void onPostExecute(String s) {
            ArrayList<Valute> valutes = new ArrayList<>();
            String date = getValutes(s,valutes);
            setCurrencyData(valutes,date);
        }
    }
}