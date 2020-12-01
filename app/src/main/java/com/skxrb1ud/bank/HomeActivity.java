package com.skxrb1ud.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
                            .title(bank.getAddress())).setTag(bank);
                }
            }
        });
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.marker_info, null);
                Bankomat bank = (Bankomat)marker.getTag();

                TextView status = view.findViewById(R.id.bankomat_status);
                if (bank.getStatus()){
                    status.setText(getResources().getString(R.string.bankomat_on));
                    status.setTextColor(getResources().getColor(R.color.bankomat_on));
                }
                else {
                    status.setText(getResources().getString(R.string.bankomat_off));
                    status.setTextColor(getResources().getColor(R.color.bankomat_off));
                }

                ((TextView) view.findViewById(R.id.bankomat_address)).setText(bank.getAddress());
                ((TextView) view.findViewById(R.id.bankomat_timings)).setText(bank.getTimings());

                return view;
            }
        });
    }
}