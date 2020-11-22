package com.morlag.wsbank.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.morlag.wsbank.activities.CurrenciesActivity;
import com.morlag.wsbank.models.Bankomat;
import com.morlag.wsbank.models.Valute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class Api {
    public static final String TAG = "Api";
    // Адрес получения xml информации о курсах валют
    public static final Uri URI_CURRENCY = new Uri.Builder()
            .scheme("http")
            .encodedAuthority("www.cbr.ru")
            .appendPath("scripts")
            .appendPath("XML_daily.asp")
            .build();
    // Адрес получения json информации о банкоматах
    public static final Uri URI_BANCOMAT = new Uri.Builder()
            .scheme("https")
            .encodedAuthority("api.privatbank.ua")
            .appendPath("p24api")
            .appendPath("infrastructure")
            .encodedQuery("json&atm&city=Черкассы")
            .build();
    // Интерфейс с callback функцией
    public interface ParseResultReciever<T>{
        public void handleResult(ArrayList<T> result);
    }
    Context mContext;
    Handler mHandler; // Хэндлер потока, передавшего callback функцию

    public Api(Context context, Handler handler){
        mContext = context;
        mHandler = handler;
    }

    // Метод получения информации о курсах валют
    public void obtainCurrencies(ParseResultReciever<Valute> receiver){
        CurrencyFetcher fetcher = new CurrencyFetcher(receiver);
        fetcher.execute();
    }

    // Метод получения информации о банкоматах
    public void obtainBankomats(ParseResultReciever<Bankomat> receiver){
        BankomatFetcher fetcher = new BankomatFetcher(receiver);
        fetcher.execute();
    }

    // Метод для парсинга курса валют
    private String parseCurrencies(String source, ArrayList<Valute> result){
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
                    v.setNumCode(getNextXmlText(xpp));
                    v.setCharCode(getNextXmlText(xpp));
                    v.setNominal(getNextXmlText(xpp));
                    v.setName(getNextXmlText(xpp));
                    v.setValue(getNextXmlText(xpp));
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
    private String getNextXmlText(XmlPullParser xpp) throws Exception{
        xpp.nextTag(); xpp.next();
        String result = (xpp.getText());
        xpp.nextTag();
        return result;
    }
    // Класс для асинхронного получения результата запроса о валютах
    class CurrencyFetcher extends AsyncTask<Void,Void,String> {
        ParseResultReciever<Valute> mReciever;
        public CurrencyFetcher(ParseResultReciever<Valute> receiver){
            mReciever = receiver;
        }
        @Override
        protected String doInBackground(Void... voids) {
            String xml = null;
            try {
                xml = new URLContentFetcher().getString(mContext,URI_CURRENCY,"GET",true,"Cp1251");
            }
            catch (Exception ex){
                Log.d(TAG, "doInBackground: ",ex);
            }

            return xml;
        }

        // Синхронный метод, выполняющийся после выполнения запроса
        @Override
        protected void onPostExecute(String s) {
            final ArrayList<Valute> valutes = new ArrayList<>();
            String date = parseCurrencies(s,valutes);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mReciever.handleResult(valutes);
                }
            });
        }
    }

    // Метод для парсинга информации банкоматов
    private void parseBankomats(String json, ArrayList<Bankomat> bankomats){
        JSONArray array = null;
        try {
            array = new JSONObject(json).getJSONArray("devices");

            for(int i = 0; i < array.length(); i++){
                Bankomat temp = new Bankomat();
                JSONObject object = array.getJSONObject(i);
                temp.setType(object.getString("type"));
                temp.setCityRU(object.getString("cityRU"));
                temp.setFullAddressRu(object.getString("fullAddressRu"));
                temp.setPlaceRu(object.getString("placeRu"));
                temp.setLatitude(object.getString("latitude"));
                temp.setLongitude(object.getString("longitude"));
                String[] adr = temp.getFullAddressRu().split(",");
                temp.setNotFullAddressRu(adr.length >= 2 ? String.format("%s, %s, %s",temp.getCityRU(),adr[adr.length-2],adr[adr.length-1]) : temp.getFullAddressRu());
                object = object.getJSONObject("tw");
                String[] tempTw = new String[8];
                tempTw[0] = object.getString("sun");
                tempTw[1] = object.getString("mon");
                tempTw[2] = object.getString("tue");
                tempTw[3] = object.getString("wed");
                tempTw[4] = object.getString("thu");
                tempTw[5] = object.getString("fri");
                tempTw[6] = object.getString("sat");
                tempTw[7] = object.getString("hol");
                temp.setTw(tempTw);
                bankomats.add(temp);
            }
        }
        catch (JSONException ex){
            Log.d(TAG, "parseBankomats: JSON exception",ex);
        }
        catch (Exception ex){
            Log.d(TAG, "parseBankomats: ",ex);
        }

    }

    // Класс для асинхронного получения результата запроса о банкоматах
    class BankomatFetcher extends AsyncTask<Void,Void,String> {
        ParseResultReciever<Bankomat> mReciever;
        public BankomatFetcher(ParseResultReciever<Bankomat> receiver){
            mReciever = receiver;
        }
        @Override
        protected String doInBackground(Void... voids) {
            String json = null;
            try {
                json = new URLContentFetcher().getString(mContext,URI_BANCOMAT,"GET",true,"UTF-8");
            }
            catch (Exception ex){
                Log.d(TAG, "doInBackground: ",ex);
            }

            return json;
        }

        // Синхронный метод, выполняющийся после выполнения запроса
        @Override
        protected void onPostExecute(String s) {
            final ArrayList<Bankomat> bankomats = new ArrayList<>();
            parseBankomats(s,bankomats);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mReciever.handleResult(bankomats);
                }
            });
        }
    }
}