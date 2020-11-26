package com.skxrb1ud.bank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.skxrb1ud.bank.adapters.BankomatsAdapter;
import com.skxrb1ud.bank.models.Bankomat;

import java.util.ArrayList;

public class BankomatsActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    ArrayList<Banks> arrayBanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks);final RecyclerView recyclerView = findViewById(R.id.recycleBanks);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        LatLng kremenchug = new LatLng(49.06802, 33.42041);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kremenchug,13.5f));

        final RecyclerView recyclerView = findViewById(R.id.recycleBanks);
        ParserJson.Parse(this, new BanksCallback() {
            @Override
            public void run(ArrayList<Banks> banks) {
                Banks[] bankomats = new Banks[banks.size()];
                bankomats = banks.toArray(bankomats);
                BankomatsAdapter adapter = new BankomatsAdapter(BankomatsActivity.this, bankomats, new TRunnable<Banks>() {
                    @Override
                    public void run(Banks data) {
                        LatLng point = new LatLng(data.Lng, data.Lat);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,20f));
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(BankomatsActivity.this,RecyclerView.VERTICAL,false));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnBack:
                viewMainActivity(v);
                break;
        }
    }

    private void viewMainActivity(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
