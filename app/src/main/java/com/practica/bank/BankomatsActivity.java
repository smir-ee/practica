package com.practica.bank;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.practica.bank.adapters.BankomatsAdapter;
import com.practica.bank.models.Bankomat;
import com.practica.bank.runnables.BankomatsRunnable;

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
