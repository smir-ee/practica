package com.skxrb1ud.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skxrb1ud.bank.dialogs.LoginDialog;
import com.skxrb1ud.bank.models.Currency;
import com.skxrb1ud.bank.runnables.CurrenciesRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        findViewById(R.id.btn_bankomats_list).setOnClickListener(this);
        findViewById(R.id.btn_currency).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        TextView date = findViewById(R.id.current_date);
        date.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        Api.getCurrencies(this, new Date(), new CurrenciesRunnable() {
            @Override
            public void run(Currency[] currencies) {
                for(int i = 0; i < currencies.length;i++){
                    Currency currency = currencies[i];
                    if(currency.getCharCode().equals("USD")){
                        ((TextView)findViewById(R.id.usd_cur)).setText(String.valueOf(currency.getValue()));
                    }
                    if(currency.getCharCode().equals("EUR")){
                        ((TextView)findViewById(R.id.eur_cur)).setText(String.valueOf(currency.getValue()));
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_bankomats_list:
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_currency:
                intent = new Intent(this,CurrencyActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                LoginDialog loginDialog = new LoginDialog(this) {
                    @Override
                    public void run(String login, String password) {

                    }
                };
                loginDialog.show();
                //loginDialog.show(getSupportFragmentManager(),null);
//                intent = new Intent(this,HomeActivity.class);
//                startActivity(intent);
                break;
        }

    }
}