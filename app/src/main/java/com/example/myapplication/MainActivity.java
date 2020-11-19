package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        TextView date = (TextView) findViewById(R.id.dataTxt);
        String dateStr = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        date.setText(dateStr);

        final View view = findViewById(R.id.cursBtn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCurseActivity(v);
            }
        });
    }

    private void viewCurseActivity(View v){
        Intent intent = new Intent(this, cursActivity.class);
        startActivity(intent);
    }

    public void viewBanksActivity(View v){
        Intent intent = new Intent(this, BanksActivity.class);
        startActivity(intent);
    }

    public void viewLoginDialog(View v){
        CustomDialog dialog = new CustomDialog();
        dialog.show(getSupportFragmentManager(),"login");
    }
}