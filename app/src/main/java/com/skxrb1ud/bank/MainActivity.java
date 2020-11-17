package com.skxrb1ud.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.btn_bankomats_list);
        btn.setOnClickListener(this);
        findViewById(R.id.btn_currency).setOnClickListener(this);


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_bankomats_list:
                Intent intent = new Intent(this,BankomatsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_currency:
                intent = new Intent(this,CurrencyActivity.class);
                startActivity(intent);
                break;
        }

    }
}