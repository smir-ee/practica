package com.skxrb1ud.bank;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class actBanks extends AppCompatActivity {

    ListView listBanks;
    ArrayList<Bank> arrbanks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_banks);
        listBanks = findViewById(R.id.listBanks);
        BanksParser.Parse("ровно", this, new BanksCallback() {
            @Override
            public void run(ArrayList<Bank> banks) {
                listBanks.setAdapter(new banksAdapter(actBanks.this, banks));
                ((ProgressBar)findViewById(R.id.banksLoading)).setVisibility(View.GONE);
            }
        });

    }

    public void banksBack(View v) {
        finish();
    }
}