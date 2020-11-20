package com.morlag.wsbank.models;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Bankomat {
    public static final String TAG = "Bankomat";

    private String type;
    private String latitude;
    private String longitude;
    private String[] tw;
    private String fullAddressRu;
    private String placeRu;
    private String cityRU;

    // true - банкомат работает, false - не работает
    public boolean isWorkNow(){
        String[] workTime = getWorkTimeToday().split(" - ");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date now = format.parse(format.format(new Date()));
            Date start = format.parse(workTime[0]);
            Date end = format.parse(workTime[1]);

            if(start.before(now) && end.after(now))
                return true;
            return false;
        }
        catch (Exception ex){
            Log.d(TAG, "isWorkNow: ",ex);
        }
        return false;
    }

    // Время работы банкомата
    public String getWorkTimeToday(){
        return tw[new Date().getDay()-1];
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setTw(String[] tw) {
        this.tw = tw;
    }

    public String getFullAddressRu() {
        return fullAddressRu;
    }

    public void setFullAddressRu(String fullAddressRu) {
        this.fullAddressRu = fullAddressRu;
    }

    public String getPlaceRu() {
        return placeRu;
    }

    public void setPlaceRu(String placeRu) {
        this.placeRu = placeRu;
    }

    public String getCityRU() {
        return cityRU;
    }

    public void setCityRU(String cityRU) {
        this.cityRU = cityRU;
    }
}