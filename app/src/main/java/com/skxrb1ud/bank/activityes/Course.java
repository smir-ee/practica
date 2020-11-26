package com.skxrb1ud.bank.activityes;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.skxrb1ud.bank.R;
import com.skxrb1ud.bank.entities.Entity_Valute;
import com.skxrb1ud.bank.parsers.Parser_xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

public class Course extends AppCompatActivity {
    private ArrayList<Entity_Valute> valuteArrayList;
    private ArrayList<Entity_Valute> valuteArrayListLast;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        listView = (ListView) findViewById(R.id.listView_Course);
        valuteArrayList = MainActivity.getValuteArrayList();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        TextView date = (TextView) findViewById(R.id.textView_Date_CourseAct);
        date.setText(format.format(calendar.getTime()));

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String dateLast = format.format(calendar.getTime());

        new getValutes().execute(getResources().getString(R.string.CB_valutes), dateLast);
    }




    private class getValutes extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpResponseCache cache = HttpResponseCache.install(getCacheDir(), 100000L);
                HttpURLConnection connection = (HttpURLConnection) new URL(strings[0] + strings[1]).openConnection();

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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Parser_xml parser = new Parser_xml();
            if (parser.Parsing(s))
                valuteArrayListLast = parser.getEntityValutes();

            customAdapter adapter = new customAdapter(getApplicationContext());
            listView.setAdapter(adapter);
        }
    }

    private class customAdapter extends ArrayAdapter {
        public customAdapter(@NonNull Context context) {
            super(context, R.layout.row_course, valuteArrayList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_course, null);
            }

            Entity_Valute valute = (Entity_Valute) getItem(position);

            TextView title = (TextView) convertView.findViewById(R.id.valute);
            title.setText(valute.getName());

            TextView charcode = (TextView) convertView.findViewById(R.id.charcode);
            charcode.setText(valute.getCharCode());

            TextView buy = (TextView) convertView.findViewById(R.id.textView_Buy_Course);
            buy.setText(new DecimalFormat("0.00").format(valute.getValue() * 1.5));

            TextView sale = (TextView) convertView.findViewById(R.id.textView_Sale_Course);
            sale.setText(new DecimalFormat("0.00").format(valute.getValue() * 1.25));

            ImageView buyArrow = (ImageView) convertView.findViewById(R.id.arrow_Buy_Course);
            ImageView saleArrow = (ImageView) convertView.findViewById(R.id.arrow_Sale_Course);

            if (valute.getValue() > valuteArrayListLast.get(position).getValue()){
                buyArrow.setRotation(0);
                saleArrow.setRotation(0);
                buyArrow.setColorFilter(getResources().getColor(R.color.colorGreen));
                saleArrow.setColorFilter(getResources().getColor(R.color.colorGreen));
            }
            else{
                if (valute.getValue() < valuteArrayListLast.get(position).getValue()){
                    buyArrow.setRotation(180);
                    saleArrow.setRotation(180);
                    buyArrow.setColorFilter(getResources().getColor(R.color.colorRed));
                    saleArrow.setColorFilter(getResources().getColor(R.color.colorRed));
                }
                else{
                    buyArrow.setVisibility(View.GONE);
                    saleArrow.setVisibility(View.GONE);
                }
            }

            return convertView;
        }
    }
}
