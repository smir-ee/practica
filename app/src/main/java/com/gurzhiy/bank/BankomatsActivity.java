package com.gurzhiy.bank;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gurzhiy.bank.models.Bankomat;

public class BankomatsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankomat);
        Bankomat bankomat = new Bankomat();
        bankomat = new Bankomat();
        bankomat.setAddress("Омск, ул. Гашека, д. 11");
        bankomat.setTimings("00:00-00:00");
        bankomat.setStatus(true);
    }
}
