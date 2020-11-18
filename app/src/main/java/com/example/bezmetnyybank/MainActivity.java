package com.example.bezmetnyybank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView_Date = findViewById(R.id.textView_Date);
        textView_Date.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));

        Button btnSign = findViewById(R.id.btnSign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authorization Authorization = new Authorization();
                Authorization.show(getSupportFragmentManager(), "Authorization");
            }
        });
    }
}
