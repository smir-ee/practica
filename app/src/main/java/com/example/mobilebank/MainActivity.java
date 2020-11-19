package com.example.mobilebank;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    TextView textView1;
    TextView USD;
    TextView EUR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        textView1 = findViewById(R.id.textViewDate);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        textView1.setText(format.format(new Date()));

        Button brunch = findViewById(R.id.buttonBranches);
        brunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, separationActivity.class);
                startActivity(i);
            }
        });

        View course = findViewById(R.id.include);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CourseActivity.class);
                startActivity(i);
            }
        });
    }
    public void showDialog(View v) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }
}