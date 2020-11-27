package com.example.praktika;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.praktika.R;
import com.example.praktika.Entity_Banks;
import com.example.praktika.Parser_json;
import com.google.android.gms.maps.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.example.praktika.R.id.textView_address_bank;

public class BanksActivity extends AppCompatActivity {

    private GoogleMap gMap;
    private Context context = this;

    private ArrayList<Entity_Banks> entityBanks;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks);

        String url = getResources().getString(R.string.API);
        new parsingJson().execute(url);
    }

    private void createMapView() {
        ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMapAsync(new OnMapReadyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }
                else{
                    gMap.setMyLocationEnabled(true);
                }

                for (int i = 0; i < entityBanks.size(); i++){
                    final Entity_Banks banks = entityBanks.get(i);
                    String status = "";

                    if (banks.getStatus())
                        status = "Открыто";
                    else
                        status = "Закрыто";

                    gMap.addMarker(new MarkerOptions().position(banks.getPosition()).title("" + i));
                }

                gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View view = getLayoutInflater().inflate(R.layout.info_map, null);
                        Entity_Banks banks = entityBanks.get(Integer.parseInt(marker.getTitle()));
                        String tee = marker.getTitle();

                        TextView status = (TextView) view.findViewById(R.id.textView_status_map);
                        if (banks.getStatus()){
                            status.setText("Открыто");
                            status.setTextColor(getResources().getColor(R.color.colorGreen));
                        }
                        else {
                            status.setText("Закрыто");
                            status.setTextColor(getResources().getColor(R.color.colorRed));
                        }

                        ((TextView) view.findViewById(R.id.textView_address_map)).setText(banks.getStreet());
                        ((TextView) view.findViewById(R.id.textView_type_map)).setText(banks.getType());
                        ((TextView) view.findViewById(R.id.textView_timework_map)).setText(banks.getTimeWork());

                        return view;
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(entityBanks.get(position).getPosition())
                                .zoom(15)
                                .build();
                        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                });
            }
        });
    }

    private class parsingJson extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                HttpResponseCache cache = HttpResponseCache.install(getCacheDir(), 10000L);
                HttpURLConnection connection = (HttpURLConnection) new URL(strings[0]).openConnection();
                connection.setRequestMethod("GET");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder builder = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null)
                    builder.append(line);

                bufferedReader.close();

                return builder.toString();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Parser_json parserUA = new Parser_json();
            if (parserUA.parsingData(s))
                entityBanks = parserUA.getBanksArrayList();

            createMapView();
            customAdapter adapter = new customAdapter(getApplicationContext());
            listView = (ListView) findViewById(R.id.listView_Banks);
            listView.setAdapter(adapter);
        }
    }

    private class customAdapter extends ArrayAdapter{

        public customAdapter(@NonNull Context context) {
            super(context, R.layout.row_bank, entityBanks);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = (View) inflater.inflate(R.layout.row_bank, null);
            }

            Entity_Banks banks = (Entity_Banks) getItem(position);

            TextView status = (TextView) convertView.findViewById(R.id.textView_status_bank);

            if (banks.getStatus()){
                status.setText("Открыто");
                status.setTextColor(getResources().getColor(R.color.colorGreen));
            }
            else {
                status.setText("Закрыто");
                status.setTextColor(getResources().getColor(R.color.colorRed));
            }

            ((TextView) convertView.findViewById(R.id.textView_worktime_bank)).setText(banks.getTimeWork().replace(" ", ""));
            ((TextView) convertView.findViewById(R.id.textView_address_bank)).setText(banks.getStreet());
            ((TextView) convertView.findViewById(R.id.textView_type_bank)).setText(banks.getType());

            return convertView;
        }
    }
}