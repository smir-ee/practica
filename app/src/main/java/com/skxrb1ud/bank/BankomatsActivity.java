package com.skxrb1ud.bank;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skxrb1ud.bank.adapters.BankomatsAdapter;
import com.skxrb1ud.bank.models.Bankomat;
import com.skxrb1ud.bank.runnables.BankomatsRunnable;

import java.util.Date;

public class BankomatsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankomats);
        final RecyclerView recyclerView = findViewById(R.id.bankomats_list);
        Api.getBankomats(this, new BankomatsRunnable() {
            @Override
            public void run(Bankomat[] bankomats) {
                BankomatsAdapter adapter = new BankomatsAdapter(BankomatsActivity.this, bankomats, new TRunnable<Bankomat>() {
                    @Override
                    public void run(Bankomat data) {

                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(BankomatsActivity.this,RecyclerView.VERTICAL,false));
                recyclerView.setAdapter(adapter);
            }
        });

    }
}
