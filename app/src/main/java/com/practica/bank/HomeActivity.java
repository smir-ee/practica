package com.practica.bank;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.practica.bank.adapters.BankomatsAdapter;
import com.practica.bank.models.Bankomat;
import com.practica.bank.runnables.BankomatsRunnable;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        LatLng brovari = new LatLng(50.5064267, 30.7772408);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(brovari,13.5f));

        final RecyclerView recyclerView = findViewById(R.id.bankomats_list);
        Api.getBankomats(this, new BankomatsRunnable() {
            @Override
            public void run(Bankomat[] bankomats) {
                BankomatsAdapter adapter = new BankomatsAdapter(HomeActivity.this, bankomats, new TRunnable<Bankomat>() {
                    @Override
                    public void run(Bankomat data) {
                        LatLng point = new LatLng(data.getLongitude(), data.getLatitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,13.5f));
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this,RecyclerView.VERTICAL,false));
                recyclerView.setAdapter(adapter);
                for (Bankomat bank:
                     bankomats) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(bank.getLongitude(),bank.getLatitude()))
                            .title(bank.getAddress()));
                }
            }
        });
    }
}