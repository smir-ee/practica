package com.example.praktika;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseActivity extends AppCompatActivity {

    private Document doc;
    private Thread secThread;
    private Runnable runnable;
    private ListView listView;
    private CustomArrayAdapter adapter;
    private List<ListItemClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course);

            TextView textView_Date_CourseAct = findViewById(R.id.textView_Date_CourseAct);

            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String dateText = dateFormat.format(currentDate);
            textView_Date_CourseAct .setText(dateText);

            init();
        }

    private void init()
    {
        listView = findViewById(R.id.listView_Course);
        arrayList = new ArrayList<>();
        adapter = new CustomArrayAdapter(this, R.layout.row_course, arrayList,getLayoutInflater());
        listView.setAdapter(adapter);
        runnable = new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
    }
    private void getWeb()
    {
        try {
            doc = Jsoup.connect("https://www.cbr.ru/scripts/XML_daily.asp").get();
            Elements tables = doc.getElementsByTag("ValCurs");
            Element our_table = tables.get(0);
            Elements elements_from_table = our_table.children();
            Element dollar = elements_from_table.get(0);
            Elements dollar_elements = dollar.children();

            for(int i = 0; i<our_table.childrenSize(); i++)
            {
                ListItemClass items = new ListItemClass();
                items.setData1(our_table.children().get(i).child(1).text());
                items.setData2(our_table.children().get(i).child(3).text());
                items.setData3(our_table.children().get(i).child(4).text());
                items.setData4(our_table.children().get(i).child(4).text());
                arrayList.add(items);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
