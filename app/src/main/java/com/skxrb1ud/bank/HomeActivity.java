package com.skxrb1ud.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skxrb1ud.bank.adapters.BankomatsAdapter;
import com.skxrb1ud.bank.models.Bankomat;
import com.skxrb1ud.bank.runnables.BankomatsRunnable;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        final RecyclerView recyclerView = findViewById(R.id.bankomats_list);
        Api.getBankomats(this, new BankomatsRunnable() {
            @Override
            public void run(Bankomat[] bankomats) {
                BankomatsAdapter adapter = new BankomatsAdapter(HomeActivity.this,bankomats);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this,RecyclerView.VERTICAL,false));
                recyclerView.setAdapter(adapter);
                
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        LatLng brovari = new LatLng(50.5064267, 30.7772408);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(brovari,13.5f));

    }
}