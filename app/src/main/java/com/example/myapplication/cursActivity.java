package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cursActivity extends AppCompatActivity {
    private Document doc;
    private Thread secondThread;
    private Runnable runnable;
    public ListView listView;
    private CustomArrayAdapter adapter;
    private List<ListItem> arrayList;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curs);
        getSupportActionBar().hide();

        textView = findViewById(R.id.dateCursTxt);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        textView.setText(format.format(new Date()));

        init();
    }
    private void init(){
        listView = findViewById(R.id.cursListView);
        arrayList = new ArrayList<>();
        adapter = new CustomArrayAdapter(this, R.layout.curs_maket, arrayList ,getLayoutInflater());
        listView.setAdapter(adapter);
        runnable = new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        };
        secondThread = new Thread(runnable);
        secondThread.start();
    }
    private void getWeb(){
        try {
            doc = Jsoup.connect("https://www.cbr.ru/scripts/XML_daily.asp").get();
            Elements table = doc.getElementsByTag("ValCurs");
            Element newtable = table.get(0);
            Elements valute = newtable.children();
            Element item1 = valute.get(0);
            Element name = item1.children().get(1);
            Element fullname = item1.children().get(3);
            Element cost = item1.children().get(4);
            Log.i("MyLog", "Итог " + name.text() + " " + fullname.text()+ " " + cost.text());
            for (int i = 0; i < newtable.childrenSize(); i++){
                ListItem items = new ListItem();
                items.setData1(valute.get(i).child(1).text());
                items.setData2(valute.get(i).child(3).text());
                items.setData3(valute.get(i).child(4).text());
                items.setData4(valute.get(i).child(4).text());
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
