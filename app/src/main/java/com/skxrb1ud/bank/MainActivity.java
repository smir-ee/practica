package com.skxrb1ud.bank;

import androidx.appcompat.app.AppCompatActivity;

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

    Dialog dialog;
    EditText txtLogin;
    EditText txtPass;
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLogin = findViewById(R.id.txtLogin);
        txtPass = findViewById(R.id.txtPass);
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
                dialog = new Dialog(this);
                dialog.create();
                dialog.setContentView(R.layout.login_window);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
        }
    }

    public void closeDialog(View view) {
        dialog.cancel();
    }

    public void login(View view) {
        dialog.dismiss();
    }
}