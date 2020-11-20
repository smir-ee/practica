package com.morlag.wsbank.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.morlag.wsbank.R;
import com.morlag.wsbank.adapters.BankomatsAdapter;
import com.morlag.wsbank.models.Bankomat;
import com.morlag.wsbank.utils.Api;

import java.util.ArrayList;

public class PointsActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        setTitle("Главный экран");

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loading = findViewById(R.id.loading);

        // Создание апи
        Api api = new Api(this,new Handler(getMainLooper()));
        // Вызов метода получения банкоматов с callback функцией
        api.obtainBankomats(new Api.ParseResultReciever<Bankomat>() {
            @Override
            public void handleResult(ArrayList<Bankomat> result) {
                setObtainedBankomats(result);
            }
        });

    }

    private void setObtainedBankomats(ArrayList<Bankomat> result){
        BankomatsAdapter adapter = new BankomatsAdapter(this,result);
        mRecyclerView.setAdapter(adapter);
        loading.setVisibility(View.GONE); // Скрыть загрузку
    }
}