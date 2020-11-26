package com.skxrb1ud.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((TextView)findViewById(R.id.txtRatesDate)).setText(new SimpleDateFormat("dd.MM.yy").format(c.getTime()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_banks:
                Intent intent = new Intent(this, actBanks.class);
                startActivity(intent);
                break;
            case R.id.btn_rates:
                intent = new Intent(this, rates.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                dialog = new LoginWindow(this);
                dialog.create();
                dialog.show();
                break;
        }
    }

    LoginWindow dialog;

    public void closeDialog(View view) {
        dialog.cancel();
    }

    public void login(View view) {
        String login = dialog.getLogin();
        String pass = dialog.getPass();
        if(login.equals("user") && pass.equals("123456")) {
            Intent intent = new Intent(this, activityApp.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }
}