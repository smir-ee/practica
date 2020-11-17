package com.skxrb1ud.bank;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skxrb1ud.bank.adapters.BankomatsAdapter;
import com.skxrb1ud.bank.models.Bankomat;

public class BankomatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankomats);
        RecyclerView recyclerView = findViewById(R.id.bankomats_list);
        Bankomat[] bankomats = new Bankomat[2];
        bankomats[0] = new Bankomat();
        bankomats[1] = new Bankomat();

        bankomats[0].setAddress("Ивановский район, д. Андреево, д. 10");
        bankomats[1].setAddress("Москва, Ленинский проспект, дом 4");
        bankomats[0].setTimings("00:00-12:00");
        bankomats[1].setTimings("12:00-00:00");
        bankomats[0].setStatus(true);
        bankomats[1].setStatus(false);

        BankomatsAdapter adapter = new BankomatsAdapter(this,bankomats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }
}
