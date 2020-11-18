package com.oat.practica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick (View view){
        Intent i = new Intent(MainActivity.this, BanksActivity.class);
        startActivity(i);
    }
    public void onClickCourse (View view){
        Intent i = new Intent(MainActivity.this, CourseActivity.class);
        startActivity(i);
    }
    public void onClickLogin (View view){
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }
}