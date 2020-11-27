package com.oat.practica.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import com.oat.practica.R;

public class ActivityProfile extends AppCompatActivity {

    private String login;
    private String password;
    private String token;
    private String fio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("login");
        password = bundle.getString("password");
        token = bundle.getString("token");
        fio = bundle.getString("fio");
        ((TextView) findViewById(R.id.fio_profile)).setText(fio);
    }
}