package com.skxrb1ud.bank;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class actBanks extends AppCompatActivity implements OnMapReadyCallback {

    ListView listBanks;
    CustomLayout header;
    Fragment map;
    LinearLayout main;
    GoogleMap mMap;
    ArrayList<Bank> arrbanks;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_banks);
        map = getSupportFragmentManager().findFragmentById(R.id.map);

        listBanks = findViewById(R.id.listBanks);
        listBanks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LatLng bank = new LatLng(arrbanks.get(position).Lng, arrbanks.get(position).Lat);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bank, 17));
            }
        });
        header = findViewById(R.id.txtBanksHeader);
        main = findViewById(R.id.mainLayout);
        BanksParser.Parse("ровно", this, new BanksCallback() {
            @Override
            public void run(ArrayList<Bank> banks) {
                arrbanks = banks;
                listBanks.setAdapter(new banksAdapter(actBanks.this, banks));
                ((ProgressBar)findViewById(R.id.banksLoading)).setVisibility(View.GONE);
                SupportMapFragment mapFragment = (SupportMapFragment)map;
                mapFragment.getMapAsync(actBanks.this);
            }
        });
        header.setVisibility(View.GONE);
        header.setVisibility(View.VISIBLE);
        header.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (map.getView().getHeight() + (event.getY() + header.getHeight() / 2) <= (main.getHeight() - header.getHeight() - findViewById(R.id.topBar).getHeight())) {
                            map.getView().setMinimumHeight((int)(map.getView().getHeight() + (event.getY() + header.getHeight() / 2)));
                            if (map.getView().getHeight() + (event.getY() + header.getHeight() / 2) >= 0) {
                                map.getView().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int)(map.getView().getHeight() + (event.getY() + header.getHeight() / 2))));
                            }
                        } else {
                            map.getView().setMinimumHeight((int)(main.getHeight() - header.getHeight() - findViewById(R.id.topBar).getHeight()));
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        header.performClick();
                        return true;
                }
                return false;
            }
        });
    }

    public void banksBack(View v) {
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < arrbanks.size(); i++) {
            LatLng bank = new LatLng(arrbanks.get(i).Lng, arrbanks.get(i).Lat);
            mMap.addMarker(new MarkerOptions().position(bank).title(arrbanks.get(i).Description));
            if (i == arrbanks.size() - 1) mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bank, 17));
        }
    }
}