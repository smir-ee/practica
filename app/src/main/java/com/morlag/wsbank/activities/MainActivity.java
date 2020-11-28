package com.morlag.wsbank.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.morlag.wsbank.LoginDialog;
import com.morlag.wsbank.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
    implements LoginDialog.NoticeDialogListener {

    Button enter;
    ConstraintLayout currencies;
    ConstraintLayout bankomats;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Установка даты
        date = findViewById(R.id.date); // TextView для содержания даты
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        date.setText(format.format(new Date()));

        enter = findViewById(R.id.btnEnter);
        currencies = findViewById(R.id.btnCurrencies);
        bankomats = findViewById(R.id.btnPlaces);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginDialog dialog = new LoginDialog();
                dialog.show(getSupportFragmentManager(),"LoginDialog");
            }
        });
        currencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CurrenciesActivity.class);
                startActivity(i);
            }
        });
        bankomats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,PointsActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onLoginAttempt(String login, String password) {
        // Тут происходит обработка введенных данных, реализуется позднее
        if(login.equals("login") && password.equals("password"))
            Toast.makeText(this,"Login access",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Login denied",Toast.LENGTH_SHORT).show();
    }
}