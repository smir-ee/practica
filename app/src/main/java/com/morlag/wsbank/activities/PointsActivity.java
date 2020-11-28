package com.morlag.wsbank.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.morlag.wsbank.R;
import com.morlag.wsbank.adapters.BankomatsAdapter;
import com.morlag.wsbank.models.Bankomat;
import com.morlag.wsbank.utils.Api;

import java.util.ArrayList;

public class PointsActivity extends AppCompatActivity
    implements OnMapReadyCallback
    , BankomatsAdapter.OnLatLngSender {
    RecyclerView mRecyclerView;
    ProgressBar loading;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        setTitle("Главный экран");

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    // Вывести полученные банкоматы в список
    private void setObtainedBankomats(ArrayList<Bankomat> result){
        BankomatsAdapter adapter = new BankomatsAdapter(this,result, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE); // Скрыть загрузку
        setBankomatsToMap(result);
    }
    // Установить полученные банкоматы на карту
    private void setBankomatsToMap(ArrayList<Bankomat> bankomats){
        if(mMap != null){
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Bankomat b : bankomats) {
                LatLng tmp = b.getLatLng();
                builder.include(tmp);
                mMap.addMarker(new MarkerOptions().position(tmp).title(b.getNotFullAddressRu()));
            }
            int margin = getResources().getDimensionPixelSize(R.dimen.map_margin);
            CameraUpdate move = CameraUpdateFactory.newLatLngBounds(builder.build(),margin);
            mMap.animateCamera(move);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void sendLatLng(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));
    }
}