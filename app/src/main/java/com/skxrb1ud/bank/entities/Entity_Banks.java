package com.skxrb1ud.bank.entities;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Entity_Banks {

    private String city;
    private String address;
    private String place;
    private String timeWork;
    private String type;
    private LatLng position;

    public String getAddress() {
        return address;
    }

    public String getTimeWork() {
        return timeWork;
    }

    public String getType() {
        return type;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTimeWork(String timeWork) throws JSONException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        String time = "";
        JSONObject jsonObject = new JSONObject(timeWork);
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 2:
                time = jsonObject.getString("mon");
                break;
            case 3:
                time = jsonObject.getString("tue");
                break;
            case 4:
                time = jsonObject.getString("wed");
                break;
            case 5:
                time = jsonObject.getString("thu");
                break;
            case 6:
                time = jsonObject.getString("fri");
                break;
            case 7:
                time = jsonObject.getString("sat");
                break;
            case 1:
                time = jsonObject.getString("sun");
                break;
        }
        this.timeWork = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPosition(double x, double y) {
        this.position = new LatLng(x, y);
    }

    public boolean getStatus() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        String[] house = timeWork.split(" - ");
        double nowHouse = Double.parseDouble(new SimpleDateFormat("HH.mm").format(calendar.getTime()));
        double startWork = Double.parseDouble(house[0].replace(':','.'));
        double endWork = Double.parseDouble(house[1].replace(':','.'));
        return (startWork < nowHouse && nowHouse < endWork);
    }

    public String getStreet(){
        String[] addressArray = address.split(",");
        String street = addressArray[addressArray.length - 2] + ", " + addressArray[addressArray.length - 1];
        return street;
    }
}
